#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

bool compare_strings(string line1, string line2)
{
    int size = line1.size();
    for (int i = size - 4; i < size; i++)
    {
        if (line1[i] > line2[i])
        {
            return true;
        }
        if (line1[i] < line2[i])
        {
            return false;
        }
    }
    for (int i = size - 7; i < size - 5; i++)
    {
        if (line1[i] > line2[i])
        {
            return true;
        }
        if (line1[i] < line2[i])
        {
            return false;
        }
    }
    for (int i = size - 10; i < size - 8; i++)
    {
        if (line1[i] > line2[i])
        {
            return true;
        }
        if (line1[i] < line2[i])
        {
            return false;
        }
    }
    for (int i = 0; i < 2; i++)
    {
        if (line1[i] > line2[i])
        {
            return true;
        }
        if (line1[i] < line2[i])
        {
            return false;
        }
    }
    for (int i = 3; i < 5; i++)
    {
        if (line1[i] > line2[i])
        {
            return true;
        }
        if (line1[i] < line2[i])
        {
            return false;
        }
    }
    for (int i = 6; i < 8; i++)
    {
        if (line1[i] > line2[i])
        {
            return true;
        }
        if (line1[i] < line2[i])
        {
            return false;
        }
    }
    return false;
}

void merge(vector<string>& vec, int start, int middle, int end, vector<int>& result)
{
    vector<string> left_vec;
    vector<string> right_vec;
    vector<int> left_num_vec;
    vector<int> right_num_vec;
    for (int i = start; i <= middle; i++)
    {
        left_vec.push_back(vec[i]);
        left_num_vec.push_back(result[i]);
    }
    for (int i = middle + 1; i <= end; i++)
    {
        right_vec.push_back(vec[i]);
        right_num_vec.push_back(result[i]);
    }
    int i = 0, j = 0, k = start;
    while (i <= middle - start && j < end - middle)
    {
        if (compare_strings(left_vec[i], right_vec[j]) == false)
        {
            vec[k] = left_vec[i];
            result[k] = left_num_vec[i];
            i++;
        }
        else
        {
            vec[k] = right_vec[j];
            result[k] = right_num_vec[j];
            j++;
        }
        k++;
    }
    if (i != middle - start + 1)
    {
        while (i <= middle - start)
        {
            vec[k] = left_vec[i];
            result[k] = left_num_vec[i];
            i++;
            k++;
        }
    }
    else
    {
        while (j < end - middle)
        {
            vec[k] = right_vec[j];
            result[k] = right_num_vec[j];
            j++;
            k++;
        }
    }
}

void mergesort(vector<string>& vec, int start, int end, vector<int>& result)
{
    if (start < end)
    {
        int middle = (start + end) / 2;
        mergesort(vec, start, middle, result);
        mergesort(vec, middle + 1, end, result);
        merge(vec, start, middle, end, result);
    }
}

int main() {
    vector<string> vec;
    vector<int> result;
    int num;
    cin >> num;
    for (int i = 0; i < num; i++)
    {
        string line, helper_line;
        cin >> line;
        vec.push_back(line);
        cin >> line;
        vec[i] += line;
        result.push_back(i + 1);
    }
    mergesort(vec, 0, num - 1, result);
    for (int i = 0; i < num; i++)
    {
        cout << result[i] << endl;
    }
    return 0;
}

/*
int get_number(string line, int from, int to)
{
    int result = 0;
    for (int i = from; i <= to; i++)
    {
        result += (line[i] - '0') * pow(10, to - i);
    }
    return result;
}

bool compare_strings(string line1, string line2)
{
    int size = line1.size();
    if (get_number(line1, size - 4, size - 1) > get_number(line2, size - 4, size - 1))
        return true;
    if (get_number(line1, size - 4, size - 1) < get_number(line2, size - 4, size - 1))
        return false;
    if (get_number(line1, size - 7, size - 6) > get_number(line2, size - 7, size - 6))
        return true;
    if (get_number(line1, size - 7, size - 6) < get_number(line2, size - 7, size - 6))
        return false;
    if (get_number(line1, size - 10, size - 9) > get_number(line2, size - 10, size - 9))
        return true;
    if (get_number(line1, size - 10, size - 9) < get_number(line2, size - 10, size - 9))
        return false;
    if (get_number(line1, 0 , 1) > get_number(line2, 0, 1))
        return true;
    if (get_number(line1, 0, 1) < get_number(line2, 0, 1))
        return false;
    if (get_number(line1, 3, 4) > get_number(line2, 3, 4))
        return true;
    if (get_number(line1, 3, 4) < get_number(line2, 3, 4))
        return false;
    if (get_number(line1, 6, 7) > get_number(line2, 6, 7))
        return true;
    if (get_number(line1, 6, 7) < get_number(line2, 6, 7))
        return false;
    return true;
}

*/