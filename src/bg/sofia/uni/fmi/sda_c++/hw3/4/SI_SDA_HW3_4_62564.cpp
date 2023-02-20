#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
#include<string>
using namespace std;

int get_element(vector<int> values, int index)
{
    int size = values.size();
    int square = sqrt(size);
    int step = index + square;
    if (step >= size)
        step = size - 1;
    while (values[step] == values[index])
    {
        step += square;
        if (step >= size)
        {
            step = size - 1;
            break;
        }
    }
    while (values[step] > values[index])
    {
        step--;
    }
    return step;
}

int find_index_number(vector<int> values, int number)
{
    int start = 0, end = values.size() - 1;
    if (values[start] == number)
        return get_element(values, 0);
    else if (values[end] <= number)
        return end;
    while (start <= end)
    {
        int middle = start + (end - start) / 2;
        if (values[middle] == number)
            return get_element(values, middle);
        else if (number > values[middle])
            start = middle + 1;
        else
            end = middle - 1;
    }
    return get_element(values, end);
}

string get_result(vector<pair<int, int>> queries, vector<int> values)
{
    string result;
    sort(values.begin(), values.end());
    int size = queries.size(), sum = 0, counter = 0;
    for (int i = 0; i < size; i++)
    {
        if (values[0] > queries[i].second)
            result += "0\n";
        else
        {
            int j = find_index_number(values, queries[i].second);
            while (sum <= queries[i].first && j >= 0)
            {
                sum += values[j];
                counter++;
                j--;
            }
            if (sum > queries[i].first)
                counter--;
            result += to_string(counter) + '\n';
            sum = 0;
            counter = 0;
        }
    }
    return result;
}

int main()
{
    int num_values, num_queries, value;
    cin >> num_values >> num_queries;
    pair<int, int> temp;
    vector<int> values, result;
    vector<pair<int, int>> queries;
    for (int i = 0; i < num_values; i++)
    {
        cin >> value;
        values.push_back(value);
    }
    for (int i = 0; i < num_queries; i++)
    {
        cin >> value;
        temp.first = value;
        cin >> value;
        temp.second = value;
        queries.push_back(temp);
    }
    cout << get_result(queries, values);
}