#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

int main() {
    unsigned int size_arr, result = 1;
    int num;
    cin >> size_arr;
    vector<int> vec;
    for (int i = 0; i < size_arr; i++) {
        cin >> num;
        vec.push_back(num);
    }
    sort(vec.begin(), vec.end());
    unsigned int counter = 0;
    while (vec[counter] <= 0 || counter == vec.size())
        counter++;
    while (vec[counter] == result && counter + 1 != vec.size()) {
        counter++;
        if (vec[counter] != result)
            result++;
    }
    if (vec[counter] == result) {
        cout << result + 1 << '\n';
    }
    else {
        cout << result << '\n';
    }
    return 0;
}