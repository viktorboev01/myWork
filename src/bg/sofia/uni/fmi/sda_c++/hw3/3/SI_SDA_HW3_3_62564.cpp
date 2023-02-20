#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

bool compare(pair<int, int> el1, pair<int, int> el2)
{
    if (el1.first < el2.first && el1.second < el2.second)
        return true;
    else
        return false;
}

unsigned int dead_monsters(vector<pair<int, int>> deck)
{
    sort(deck.begin(), deck.end());
    unsigned int size = deck.size();
    vector<int> counter(size, 1);
    for (unsigned int i = 0; i < size; i++)
    {
        for (unsigned int j = 0; j < i; j++)
        {
            if (compare(deck[j], deck[i]) == true && counter[j] + 1 > counter[i])
                counter[i] = counter[j] + 1;
        }
    }
    int result = counter[0];
    for (unsigned int i = 1; i < size; i++)
    {
        if (counter[i] > result)
            result = counter[i];
    }
    return result;
}

int main()
{
    unsigned int n, value;
    cin >> n;
    vector<pair<int, int>> deck;
    pair <int, int> demon;
    for (unsigned int i = 0; i < n; i++)
    {
        cin >> value;
        demon.first = value;
        cin >> value;
        demon.second = value;
        deck.push_back(demon);
    }
    cout << dead_monsters(deck);
    return 0;
}
