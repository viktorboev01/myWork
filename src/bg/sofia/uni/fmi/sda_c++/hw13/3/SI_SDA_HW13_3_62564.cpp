#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

static const int MAX_SIZE = int(1e5);

void dfs(int x, vector<bool>& visited, vector<vector<int>>& graph, int& counter)
{
    if (!visited[x])
        counter++;
    visited[x] = true;
    for (int k : graph[x])
    {
        if (!visited[k])
        {
            dfs(k, visited, graph, counter);
        }
    }
}

bool isConnected(vector<vector<int>>& graph, int n, int start, vector<bool>& visited)
{
    int counter = 0;
    dfs(start, visited, graph, counter);
    if (counter == n)
        return true;
    return false;
}

int isEulerian(vector<vector<int>>& graph, int n, int start, vector<bool>& visited)
{
    if (!isConnected(graph, n, start, visited))
        return 0;
    int odd = 0;
    for (int i = 0; i < n; i++)
    {
        if (graph[i].size() & 1)
            odd++;
    }
    if (odd > 2)
        return 0;
    return (odd) ? 1 : 2;
}

void query(vector<vector<int>> graph, vector<bool> visited, vector<int>& result)
{
    int from, to, start, n, m;
    cin >> n >> m;
    cin >> from >> to;
    while (from == to)
        cin >> from >> to;
    start = from;
    graph[from].push_back(to);
    graph[to].push_back(from);
    for (int j = 1; j < m; j++)
    {
        cin >> from >> to;
        if (from == to)
            continue;
        graph[from].push_back(to);
        graph[to].push_back(from);
    }
    result.push_back(isEulerian(graph, n, start, visited));
}

int main()
{
    int q, n, m;
    vector<vector<int>> graph(MAX_SIZE);
    vector<bool> visited(MAX_SIZE, false);
    vector<int> result;
    cin >> q;
    for (int i = 0; i < q; i++)
    {
        query(graph, visited, result);
    }
    for (int i = 0; i < q; i++)
    {
        if (result[i] == 0)
            cout << "none" << '\n';
        else if (result[i] == 1)
            cout << "epath" << '\n';
        else
            cout << "ecycle" << '\n';
    }
    return 0;
}

/*
2
5 7
4 5
2 3
3 4 
3 4
1 2
5 2
1 4

6 7
3 4
1 2
1 3
2 3
4 5 
5 6
4 6
*/