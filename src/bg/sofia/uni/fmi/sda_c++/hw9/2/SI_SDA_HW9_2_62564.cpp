#include <cmath>
#include <cstdio>
#include <vector>
#include <unordered_map>
#include <iostream>
#include <algorithm>
using namespace std;

long long sum(int n)
{
    if (n <= 0)
        return 0;
    int counter = 0;
    long long result = 0;
    while (counter != n)
    {
        counter++;
        result += counter;
    }
    return result;
}

class Hash {
private:
    unordered_map<int, pair<int, long long>> table;
    unordered_map<int, int> for_ones;
    int k;
    long long result;
public:
    Hash(int _k);
    void insert(int key);
    long long get_result() const;
};

Hash::Hash(const int _k) : k(_k), result(0)
{
}

void Hash::insert(int key)
{
    if (k == 1)
    {
        if (for_ones.find(key) == for_ones.end())
        {
            for_ones.insert({ key, 1 });
        }
        else
        {
            for_ones.find(key)->second++;
            result += for_ones.find(key)->second > 2 ? sum(for_ones.find(key)->second - 2) : 0;
        }
    }
    else
    {
        if (table.find(key) != table.end())
        {
            table.find(key)->second.first++;
        }
        else if (table.find(key) == table.end())
        {
            pair<int, long long> curr = { 1, 0 };
            table.insert({ key, curr });
        }
        if (key % k == 0)
        {
            int prev_num = key / k;
            if (table.find(prev_num) != table.end())
            {
                table.find(key)->second.second += table.find(prev_num)->second.first;
            }
            if (table.find(prev_num) != table.end() && table.find(prev_num / k) != table.end())
            {
                result += table.find(prev_num)->second.second;
            }
        }
    }
}

long long Hash::get_result() const
{
    return result;
}

int main()
{

    int n, k, value;
    cin >> n;
    cin >> k;
    Hash elements(k);
    for (int i = 0; i < n; i++)
    {
        cin >> value;
        elements.insert(value);
    }
    cout << elements.get_result() << '\n';
    return 0;
}
