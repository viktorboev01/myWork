#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;


int main() 
{
    int n, m, value_first, value_second, current_max = 1;
    vector<pair<int, int>> operators;
    pair<int, int> single_operator;
    cin >> n >> m;
    for (int i = 0; i < m; i++)
    {
        cin >> value_first;
        cin >> value_second;
        single_operator.first = value_first + value_second;
        single_operator.second = 1;
        operators.push_back(single_operator);
    }
    for (int i = m; i < n; i++)
    {
        cin >> value_first;
        cin >> value_second;
        int counter = i % m;
        while (counter != m)
        {
            if (operators[counter].first <= value_first)
                break;
            counter++;
        }
        if (counter == m)
        {
            counter = 0;
            while (counter != i % m)
            {
                if (operators[counter].first <= value_first)
                    break;
                counter++;
            }
            if (counter == i % m)
                continue;
        }
        operators[counter].first = value_first + value_second;
        operators[counter].second++;
        if (current_max < operators[counter].second)
            current_max = operators[counter].second;
    }
    for (int i = 0; i < m; i++)
    {
        if (operators[i].second == current_max)
            cout << i << ' ';
    }
    return 0;
}
