#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

void find_unique_masks(bool unique_masks[], int value)
{
    for (int i = 1; i <= value; i++)
    {
        if ((i | value) == value)
            unique_masks[i] = true;
    }
}

int get_sum(bool unique_masks[])
{
    int result = 0;
    int size = 1048576;
    for (int i = 1; i < size; i++)
    {
        if (unique_masks[i] == true)
            result++;
    }
    return result;
}

int main() 
{
    bool unique_masks[1048576] = { false };
    int n, value;
    cin >> n;
    for (int i = 0; i < n; i++)
    {
        cin >> value;
        find_unique_masks(unique_masks, value);
    }
    cout << get_sum(unique_masks) + 1 << endl;
    return 0;
}