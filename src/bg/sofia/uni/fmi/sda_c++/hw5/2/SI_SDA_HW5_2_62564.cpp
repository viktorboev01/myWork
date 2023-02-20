#include <cmath>
#include <cstdio>
#include <vector>
#include <string>
#include <queue>
#include <stack>
#include <iostream>
#include <algorithm>
using namespace std;

void queue_to_result(queue<int> right_army, vector<int>& result)
{
    int size = right_army.size();
    for (int i = 0; i < size; i++)
    {
        result.push_back(right_army.front());
        right_army.pop();
    }
}

void fight(stack<int>& left_army, queue<int>& right_army, vector<int>& result)
{
    int left_size = left_army.size(), right_size = right_army.size();
    while (left_size != 0 && right_size != 0)
    {
        if (left_army.top() > -right_army.front())
        {
            right_army.pop();
            right_size--;
        }
        else if (left_army.top() < -right_army.front())
        {
            left_army.pop();
            result.pop_back();
            left_size--;
        }
        else
        {
            left_army.pop();
            result.pop_back();
            left_size--;
            right_army.pop();
            right_size--;
        }
    }
}

void get_result(vector<int> all_fighters, vector<int>& result, stack<int> left_army)
{
    queue<int> right_army;
    int size = all_fighters.size();
    int i = 0;
    while (i < size)
    {
        while (all_fighters[i] > 0)
        {
            left_army.push(all_fighters[i]);
            result.push_back(all_fighters[i]);
            i++;
            if (i == size)
                break;
        }
        if (i == size)
            break;
        while (all_fighters[i] < 0)
        {
            right_army.push(all_fighters[i]);
            i++;
            if (i == size)
                break;
        }
        fight(left_army, right_army, result);
        queue_to_result(right_army, result);
        if (i == size)
            break;
        right_army = queue<int>();
    }
    return;
}

int main()
{
    int n, value;
    cin >> n;
    vector<int> all_fighters;
    vector<int> result;
    stack<int> left_army;
    for (int i = 0; i < n; i++)
    {
        cin >> value;
        all_fighters.push_back(value);
    }
    int size = all_fighters.size();
    get_result(all_fighters, result , left_army);
    size = result.size();
    for (int i = 0; i < size; i++)
        cout << result[i] << ' ';
    return 0;
}