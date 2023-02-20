#include <cmath>
#include <cstdio>
#include <vector>
#include <map>
#include <string>
#include <iostream>
#include <algorithm>
#include<climits>
using namespace std;

void get_result(multimap<pair<int, int>, int>& tasks, long long& counter_days, string& result)
{
    int size = tasks.size();
    if (size == 0)
        return;
    int i = 0;
    int diff_days = 0, for_counter;
    int min = INT_MAX;
    int pos_result = tasks.begin()->first.second;
    pair<int, int> el_to_del;
    for_counter = tasks.begin()->first.first;
    min = tasks.begin()->second;
    for (const auto& task : tasks)
    {
        if (min > task.second)
        {
            min = task.second;
            for_counter = task.first.first;
            pos_result = task.first.second;
        }
        if (task.second <= counter_days)
            break;
        i++;
    }
    if (i == size)
    {
        diff_days = min - counter_days;
    }
    counter_days += for_counter + diff_days;
    result += to_string(pos_result) + ' ';
    el_to_del.first = for_counter;
    el_to_del.second = pos_result;
    tasks.erase(el_to_del);
    get_result(tasks, counter_days, result);
}

int main()
{
    int n, value_start, value_time;
    long long counter_days = 0;
    cin >> n;
    string result;
    pair<int, int> single_pair;
    multimap <pair<int, int>, int> tasks;
    for (int i = 0; i < n; i++)
    {
        cin >> value_start;
        cin >> value_time;
        single_pair.first = value_time;
        single_pair.second = i;
        tasks.insert(pair<pair<int, int>, int>(single_pair, value_start));
    }
    get_result(tasks, counter_days, result);
    cout << result << '\n';
    return 0;
}