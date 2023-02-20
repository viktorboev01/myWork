#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

void merge(vector<int>& arr, int start, int middle, int end)
{
    for (int i = 0; i < arr.size(); i++)
    {
        cout << arr[i] << " ";
    }
    cout << endl;
    vector<int> start_arr;
    vector<int> end_arr;
    for (int i = start; i < middle + 1; i++)
    {
        start_arr.push_back(arr[i]);
    }
    for (int i = middle + 1; i <= end; i++)
    {
        end_arr.push_back(arr[i]);
    }
    int i = 0, j = 0, k = start;
    while (i <= middle - start && j < end - middle)
    {
        if (start_arr[i] > end_arr[j])
        {
            arr[k] = end_arr[j];
            j++;
        }
        else
        {
            arr[k] = start_arr[i];
            i++;
        }
        k++;
    }
    if (i != middle - start + 1)
    {
        while (i <= middle - start)
        {
            arr[k] = start_arr[i];
            i++;
            k++;
        }
    }
    else
    {
        while (j < end - middle)
        {
            arr[k] = end_arr[j];
            j++;
            k++;
        }
    }
}


void sort(vector<int>& arr, int l, int r)
{
    if (l < r)
    {
        int m = (l + r) / 2;
        sort(arr, l, m);
        sort(arr, m + 1, r);
        merge(arr, l, m, r);
    }

}
int main() {
    int num, value, k;
    long long sum = 0;
    cin >> num;
    vector<int> vec;
    for (int i = 0; i < num; i++)
    {
        cin >> value;
        vec.push_back(value);
    }
    cin >> k;
    sort(vec, 0, num - 1);
    for (int i = 0; i < vec.size(); i++)
    {
        if (vec[i - 1] != vec[i])
            cout << vec[i] << " ";
    }
    return 0;
}