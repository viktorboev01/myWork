#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

int find_number(vector<int> complexity, int number)
{
    int start = 0, end = complexity.size() - 1;
    if (complexity[start] >= number)
        return complexity[0];
    else if (complexity[end] <= number)
        return complexity[complexity.size() - 1];
    while (start <= end)
    {
        int middle = start + (end - start) / 2;
        if (complexity[middle] == number)
            return complexity[middle];
        else if (number > complexity[middle])
            start = middle + 1;
        else
            end = middle - 1;
    }
    return complexity[start] - number < number - complexity[end] ? complexity[start] : complexity[end];
}

vector<int> get_result(vector<int> complexity, vector<int> team_knowedge)
{
    vector<int> result;
    unsigned int size_knowedge = team_knowedge.size();
    for (unsigned int i = 0; i < size_knowedge; i++)
    {
        result.push_back(find_number(complexity, team_knowedge[i]));
    }
    return result;
}

int main() {
    unsigned int n, m;
    int value;
    cin >> n >> m;
    vector<int> complexity;
    vector<int> team_knowedge;
    vector<int> result;
    cin >> value;
    complexity.push_back(value);
    for (unsigned int i = 0; i < n - 1; i++)
    {
        cin >> value;
        if (value != complexity[complexity.size() - 1])
            complexity.push_back(value);
    }
    for (unsigned int i = 0; i < m; i++)
    {
        cin >> value;
        team_knowedge.push_back(value);
    }
    sort(complexity.begin(), complexity.end());
    result = get_result(complexity, team_knowedge);
    for (unsigned int i = 0; i < m; i++)
        cout << result[i] << '\n';
    return 0;
}