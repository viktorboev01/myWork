#include <cmath>
#include <cstdio>
#include <vector>
#include <queue>
#include <stack>
#include <iostream>
#include <algorithm>
using namespace std;

int get_result(queue<int> numbers, int result)
{
    bool is_final_day = true;
    queue<int> after_day;
    int size = numbers.size();
    int value_prev = numbers.front();
    for (int i = 1; i < size; i++)
    {
        numbers.pop();
        if (value_prev <= numbers.front())
            after_day.push(value_prev);
        else
            is_final_day = false;
        value_prev = numbers.front();
    }
    after_day.push(value_prev);
    if (is_final_day == true)
        return result;
    else
    {
        result++;
        return get_result(after_day, result);
    }

}

bool final_time(vector<int> vec)
{
    int size = vec.size();
    for (int i = 1; i < size; i++)
    {
        if (vec[i] < vec[i - 1])
            return false;
    }
    return true;
}

int main() 
{
    int n, value, result = 0;
    bool is_final_day = true;
    cin >> n;
    stack<int> numbers;
    queue<int> after_first_day;
    for (int i = 0; i < n; i++)
    {
        cin >> value;
        numbers.push(value);
    }
    int value_prev = numbers.top();
    for (int i = 1; i < n; i++)
    {
        numbers.pop();
        if (value_prev <= numbers.top())
            after_first_day.push(value_prev);
        else
            is_final_day = false;
        value_prev = numbers.top();
    }
    after_first_day.push(value_prev);
    if (is_final_day == false)
        result++;
    else
    {
        cout << result << '\n';
        return 0;
    }
    result = get_result(after_first_day, result);
    cout << result << '\n';
    return 0;
}

/*
    for (int i = 0; i < size; i++)
    {
        cout << after_first_day.front();
        after_first_day.pop();
    }
*/