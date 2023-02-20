#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

void spec_dfs(int x, vector<bool>& visited, vector<vector<int>>& adj, int counter, int end, int lenght, pair<int, int>& result)
{
    visited[x] = true;
    for (int k : adj[x])
    {
        if (visited[end] == true)
            break;
        else if (!visited[k])
        {
            counter++;
            spec_dfs(k, visited, adj, counter, end, lenght, result);
            counter--;
        }
    }
    if (lenght % 2 != 0 && lenght / 2 == (counter - 1))
        result.second = x;
    else if (lenght / 2 == counter)
        result.first = x;
    return;
}

void dfs(int x, vector<bool>& visited, vector<vector<int>>& adj,
    int counter, pair<int, int>& end)
{
    visited[x] = true;
    for (int k : adj[x])
    {
        if (!visited[k])
        {
            counter++;
            dfs(k, visited, adj, counter, end);
            counter--;
        }
    }
    if (end.second < counter)
    {
        end.first = x;
        end.second = counter;
    }
    visited[x] = false;
}

pair<int, pair<int, int>> find_longest_path(vector<vector<int>>& graph,
    int n, vector<bool>& visited)
{
    pair<int, pair<int, int>> result;
    pair<int, int> end;
    for (int i = 0; i < n; i++)
    {
        if (graph[i].size() != 1)
            continue;
        dfs(i, visited, graph, 0, end);
        if (end.second > result.first)
        {
            result.first = end.second;
            result.second.first = i;
            result.second.second = end.first;
        }
    }
    return result;
}

int main()
{
    int n, from, to;
    cin >> n;
    vector<vector<int>> graph(n);
    vector<bool> visited(n, false);
    vector<pair<int, int>> end;
    pair<int, int> result({ -1,-1 });
    for (int i = 0; i < n - 1; i++)
    {
        cin >> from >> to;
        graph[from].push_back(to);
        graph[to].push_back(from);
    }
    pair<int, pair<int, int>> p = find_longest_path(graph, n, visited);
    spec_dfs(p.second.first, visited, graph, 0, p.second.second, p.first, result);
    if (result.second == -1)
        cout << result.first;
    else if (result.first > result.second)
        cout << result.second << ' ' << result.first;
    else
        cout << result.first << ' ' << result.second;
    return 0;
}