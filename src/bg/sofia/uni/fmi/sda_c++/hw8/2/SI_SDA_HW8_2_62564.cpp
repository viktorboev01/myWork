#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
#include <iomanip>
#include <set>
using namespace std;

int main() 
{
    cout << fixed << setprecision(1);
    double median;
    int n, value;
    multiset<int> front_elements;
    multiset<int> back_elements;
    vector<double> sums;
    cin >> n;
    cin >> value;
    front_elements.insert(value);
    sums.push_back(value);
    cin >> value;
    if (*front_elements.begin() > value)
    {
        back_elements.insert(*front_elements.begin());
        front_elements.erase(--front_elements.end());
        front_elements.insert(value);
    }
    else
        back_elements.insert(value);
    double temp = (double)*back_elements.begin() + (double)*front_elements.begin();
    sums.push_back( temp / (double)2);
    for (int i = 2; i < n; i++)
    {
        cin >> value;
        if (front_elements.size() <= back_elements.size())
        {
            if (value > * back_elements.begin())
            {
                front_elements.insert(*back_elements.begin());
                back_elements.erase(back_elements.begin());
                back_elements.insert(value);
            }
            else
                front_elements.insert(value);
        }
        else
        {
            if (value < *--front_elements.end())
            {
                back_elements.insert(*--front_elements.end());
                front_elements.erase(--front_elements.end());
                front_elements.insert(value);
            }
            else
                back_elements.insert(value);
        }
        long first = *--front_elements.end();
        long second = *back_elements.begin();
        if (i % 2 == 0)
            second = *--front_elements.end();
        temp = (double)first + (double)second;
        median = temp / (double)2;
        sums.push_back(median);
    }
    for (int i = 0; i < n; i++)
        cout << sums[i] << '\n';
    return 0;
}