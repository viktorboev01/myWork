#include <cmath>
#include <cstdio>
#include <vector>
#include <queue>
#include <list>
#include <iostream>
#include <algorithm>
using namespace std;


int add(int value, int i, int lenght, queue<int>& numbers, list<int>& min, list<int>& positions)
{
    if (positions.front() == i - lenght)
    {
        min.pop_front();
        positions.pop_front();
    }
    numbers.push(value);
    numbers.pop();
    if (value > min.front() && value > min.back())
    {
        min.push_back(value);
        positions.push_back(i);
    }
    else if (value > min.front() && value <= min.back())
    {
        while (value <= min.back())
        {
            min.pop_back();
            positions.pop_back();
        }
        min.push_back(value);
        positions.push_back(i);
    }
    else
    {
        min = list<int>();
        positions = list<int>();
        min.push_front(value);
        positions.push_front(i);
    }
    return min.front();
}

int main() 
{
    long long sum = 0;
    int n, lenght, value;
    cin >> n >> lenght;
    list<int> min;
    list<int> positions;
    queue<int> numbers;
    cin >> value;
    min.push_back(value);
    positions.push_back(0);
    numbers.push(value);
    for (int i = 1; i < lenght; i++)
    {
        cin >> value;
        numbers.push(value);
        if (value > min.front() && value > min.back())
        {
            min.push_back(value);
            positions.push_back(i);
        }
        else if (value > min.front() && value < min.back())
        {
            while (value < min.back())
            {
                min.pop_back();
                positions.pop_back();
            }
            min.push_back(value);
            positions.push_back(i);
        }
        else
        {
            min = list<int>();
            positions = list<int>();
            min.push_front(value);
            positions.push_front(i);
        }
    }
    sum += min.front();
    for (int i = lenght; i < n; i++)
    {
        cin >> value;
        sum += add(value, i, lenght, numbers, min, positions);
    }
    cout << sum << '\n';
    return 0;
}