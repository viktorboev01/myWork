#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

void hamilton(vector<vector<char>> graph, int rows, int cols, int i, int j, int num_keys, int& result)
{
    if (i != 0)
    {
        if (graph[i - 1][j] == '-')
        {
            graph[i - 1][j] = 'x';
            num_keys--;
            hamilton(graph, rows, cols, i - 1, j, num_keys, result);
            graph[i - 1][j] = '-';
            num_keys++;
        }
        else if (graph[i - 1][j] == 'e' && num_keys == 0)
        {
            result++;
            return;
        }
    }
    if (i != rows - 1)
    {
        if (graph[i + 1][j] == '-')
        {
            graph[i + 1][j] = 'x';
            num_keys--;
            hamilton(graph, rows, cols, i + 1, j, num_keys, result);
            graph[i + 1][j] = '-';
            num_keys++;
        }
        else if (graph[i + 1][j] == 'e' && num_keys == 0)
        {
            result++;
            return;
        }
    }
    if (j != cols - 1)
    {
        if (graph[i][j + 1] == '-')
        {
            graph[i][j + 1] = 'x';
            num_keys--;
            hamilton(graph, rows, cols, i, j + 1, num_keys, result);
            graph[i][j + 1] = '-';
            num_keys++;
        }
        else if (graph[i][j + 1] == 'e' && num_keys == 0)
        {
            result++;
            return;
        }
    }
    if (j != 0)
    {
        if (graph[i][j - 1] == '-')
        {
            graph[i][j - 1] = 'x';
            num_keys--;
            hamilton(graph, rows, cols, i, j - 1, num_keys, result);
            graph[i][j - 1] = '-';
            num_keys++;
        }
        else if (graph[i][j - 1] == 'e' && num_keys == 0)
        {
            result++;
            return;
        }
    }
    return;
}

int main() 
{
    int rows, cols, num_keys = 0, result = 0;
    cin >> rows >> cols;
    pair<int, int> start;
    vector<vector<char>> graph(rows);
    string row;
    for (int i = 0; i < rows; i++)
    {
        cin >> row;
        for (int j = 0; j < cols; j++)
        {
            graph[i].push_back(row[j]);
            if (graph[i][j] == '-')
            {
                num_keys++;
            }
            else if (graph[i][j] == 's')
            {
                start.first = i;
                start.second = j;
            }
        }
    }
    hamilton(graph, rows, cols, start.first, start.second, num_keys, result);
    cout << result;
    return 0;
}