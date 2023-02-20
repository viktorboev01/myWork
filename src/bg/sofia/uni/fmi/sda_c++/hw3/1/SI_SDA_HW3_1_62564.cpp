#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

unsigned long get_difference(unsigned long long n)
{
    unsigned long long i = 1;
    unsigned long long max = -1;
    unsigned long long limit = max / i;
    while ((i + 1) * (i + 1) * (i + 1) <= n)
    {
        i++;
        limit = max / (i + 1);
        if ((i + 1) * (i + 1) >= limit)
            break;
    }
    if ((i + 1) * (i + 1) * (i + 1) == n && (i + 1) * (i + 1) < limit)
    {
        return 1;
    }
    return n - i * i * i;
}

unsigned int get_result(unsigned long long n)
{
    unsigned int counter = 1;
    while (get_difference(n) != 0)
    {
        n = get_difference(n);
        counter++;
    }
    return counter;
}

int main() {
    unsigned long long n;
    cin >> n; 
    cout << get_result(n);
    return 0;
}
