#include <cmath>
#include <cstdio>
#include <vector>
#include <list>
#include <string>
#include <unordered_map>
#include <unordered_set>
#include <iostream>
#include <algorithm>
using namespace std;

void add_element(unordered_map<int, list<int>>& adj, int u, int v)
{
    if (adj.find(u) == adj.end())
    {
        list<int> l = { v };
        adj.insert({ u, l });
    }
    else
    {
        adj.find(u)->second.push_back(v);
    }
}

void recursive_part(int v, unordered_set<int> visited, unordered_map<int, list<int>>& adj, bool& result, unordered_set<int>& visited_lists)
{
    if (adj.find(v) == adj.end() || result || visited_lists.find(v) != visited_lists.end())
        return;
    visited.insert(v);
    visited_lists.insert(v);
    for (auto i = adj.find(v)->second.begin(); i != adj.find(v)->second.end(); i++)
    {
        if (visited.find(*i) == visited.end())
            recursive_part(*i, visited, adj, result, visited_lists);
        else
        {
            result = true;
            return;
        }
    }
}

void dfs(unordered_map<int, list<int>> adj, int size, bool& result)
{
    unordered_set<int> visited;
    unordered_set<int> visited_lists;
    for (auto i = adj.begin(); i != adj.end(); i++)
    {
        if (result)
            return;
        recursive_part(i->first, visited, adj, result, visited_lists);
    }
}

int main()
{
    int Q, E, start, end;
    cin >> Q;
    bool result = false;
    string results;
    for (int i = 0; i < Q; i++)
    {
        cin >> E;
        unordered_map<int, list<int>> adj;
        for (int j = 0; j < E; j++)
        {
            cin >> start >> end;
            add_element(adj, start, end);
        }
        dfs(adj, adj.size(), result);
        results += to_string(result) + ' ';
        result = false;
    }
    cout << results << '\n';
    return 0;
}