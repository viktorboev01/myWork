#include <cmath>
#include <cstdio>
#include <vector>
#include <set>
#include <climits>
#include <iostream>
#include <algorithm>
using namespace std;

static const int MOD = int(1e9) + 7;

pair<long long, int> dijkstra(int N,
    vector<vector<pair<int, int>>> graph, vector<long long> dist, const int size_v)
{
    pair<long long, int> result;
    vector<long long> counter (size_v);
    for (int i = 1; i < N + 1; i++)
    {
        dist[i] = LLONG_MAX;
    }
    dist[1] = 0;
    counter[1] = 1;
    set<pair<long long, int>> next_nodes;
    next_nodes.insert({ 0, 1 });
    while (!next_nodes.empty())
    {
        int u = (*next_nodes.begin()).second;
        next_nodes.erase(next_nodes.begin());
        for (pair<int, int> v : graph[u])
        {
            if (dist[v.second] == dist[u] + v.first)
                counter[v.second] += counter[u];
            else if (dist[v.second] > dist[u] + v.first)
            {
                next_nodes.erase({ dist[v.second], v.second });
                dist[v.second] = dist[u] + v.first;
                next_nodes.insert({ dist[v.second], v.second });
                counter[v.second] = counter[u];
            }
        }
    }
    result.first = (dist[N] == LLONG_MAX ? -1 : dist[N]);
    result.second = counter[N] % MOD;
    return result;
}

int main()
{
    const int size_v = 100001;
    int N, M, cost, from, to;
    vector<vector<pair<int, int>>> graph(size_v);
    vector<long long> dist(size_v);
    cin >> N >> M;
    for (int i = 0; i < M; i++)
    {
        cin >> from >> to;
        cin >> cost;
        graph[from].push_back({ cost, to });
    }
    pair<long long, int> p = dijkstra(N, graph, dist, size_v);
    cout << p.first << ' ' << p.second << '\n';
    return 0;
}