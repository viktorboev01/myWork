#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

int find_root(int node, int* parent)
{
    if (node == parent[node])
    {
        return node;
    }
    int root = find_root(parent[node], parent);
    parent[node] = root;
    return root;
}

void unite(int u, int v, int* depth, int* parent)
{
    if (depth[u] > depth[v])
    {
        parent[v] = u;
    }
    else if (depth[v] > depth[u])
    {
        parent[u] = v;
    }
    else
    {
        parent[v] = u;
        depth[u]++;
    }
}

void add_element(pair<int, int> p, vector<int>* adj)
{
    adj[p.first].push_back(p.second);
    adj[p.second].push_back(p.first);
}

void add_to_mst(pair<int, int> p, int* depth, int* parent, int N, vector<int>* mst)
{
    int first_root = find_root(p.first, parent);
    int second_root = find_root(p.second, parent);

    if (first_root != second_root)
    {
        unite(first_root, second_root, depth, parent);
        add_element(p, mst);
    }
}

bool isExistPath(pair<int, int> p, int* parent, vector<int>* mst)
{
    int first_root = find_root(p.first, parent);
    int second_root = find_root(p.second, parent);

    if (first_root != second_root)
        return false;
    else
        return true;
}

int main()
{
    static const int MAX_SIZE = 1001;
    vector<bool> result;
    int N, M, Q, OneOrTwo;
    cin >> N >> M;
    vector<int> mst[MAX_SIZE];
    int depth[MAX_SIZE], parent[MAX_SIZE];
    pair<int, int> p;

    for (int i = 0; i < N; i++)
    {
        parent[i] = i;
        depth[i] = 0;
    }

    for (int i = 0; i < M; i++)
    {
        cin >> p.first >> p.second;
        p.first--, p.second--;
        add_to_mst(p, depth, parent, N, mst);
    }

    cin >> Q;
    for (int i = 0; i < Q; i++)
    {
        cin >> OneOrTwo;
        cin >> p.first >> p.second;
        p.first--, p.second--;

        if (OneOrTwo == 1)
            result.push_back(isExistPath(p, parent, mst));

        else if (OneOrTwo == 2)
            add_to_mst(p, depth, parent, N, mst);
    }
    for (bool x : result)
    {
        cout << x;
    }
    return 0;
}