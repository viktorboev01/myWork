#include <cmath>
#include <cstdio>
#include <vector>
#include <list>
#include <iostream>
#include <algorithm>
using namespace std;

class Hash
{
private:
    static const int MOD = 10007;
    list<pair<int, int>> table[MOD];
public:
    int get_hash(int key);
    void insert(int key);
    bool remove(int key);
};

int Hash::get_hash(int key)
{
    return key % MOD;
}

void Hash::insert(int key)
{
    int hash = get_hash(key);
    bool IsThere = false;
    for (auto it = begin(table[hash]); it != end(table[hash]); it++)
    {
        if (it->first == key)
        {
            IsThere = true;
            it->second++;
            break;
        }
    }
    if (!IsThere)
        table[hash].emplace_back(key, 0);
}

bool Hash::remove(int key)
{
    int hash = get_hash(key);
    bool IsThere = false;
    for (auto it = begin(table[hash]); it != end(table[hash]); it++)
    {
        if (it->first == key)
        {
            IsThere = true;
            if (it->second == 0)
            {
                table[hash].erase(it);
            }
            else
                it->second--;
            break;
        }
    }
    return !IsThere;
}

int main()
{
    int n, value;
    long result = 0;
    cin >> n;
    vector<int> keys;
    Hash keys_check;
    int curr_door;

    for (int i = 0; i < n; i++)
    {
        cin >> value;
        keys.push_back(value);
    }
    for (int i = 0; i < n; i++)
    {
        cin >> curr_door;
        keys_check.insert(keys[i]);
        result += keys_check.remove(curr_door);
    }
    cout << result << '\n';
    return 0;
}