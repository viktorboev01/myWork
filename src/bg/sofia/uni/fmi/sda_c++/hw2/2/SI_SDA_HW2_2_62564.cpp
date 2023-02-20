#include <cmath>
#include <cstdio>
#include <vector>
#include <string>
#include <fstream>
#include <iostream>
#include <algorithm>
using namespace std;

void updateValues(string& temp_arr, char& index, int& counter, int* num_arr, int& i) {
    temp_arr.push_back(index);
    counter++;
    num_arr[i]--;
    i--;
}

int main() {
    int num, counter = 0;
    const int array_size = 62;
    cin >> num;
    int num_arr[array_size];
    for (int i = 0; i < array_size; i++)
        num_arr[i] = 0;
    string char_arr;
    string temp_arr;
    cin >> char_arr;
    for (int i = 0; i < num; i++) {
        if (char_arr[i] >= '0' && char_arr[i] <= '9') {
            num_arr[char_arr[i] - 48]++;
        }
        else if (char_arr[i] >= 'a' && char_arr[i] <= 'z') {
            num_arr[char_arr[i] - 87]++;
        }
        else {
            num_arr[char_arr[i] - 29]++;
        }
    }
    for (int i = 0; i < 10; i++) {
        if (num_arr[i] != 0) {
            char index = '0' + i;
            updateValues(temp_arr, index, counter, num_arr, i);
        }
    }
    for (int i = 10; i < 36; i++)
    {
        if (num_arr[i] != 0) {
            char index = 87 + i;
            updateValues(temp_arr, index, counter, num_arr, i);
        }
    }
    for (int i = 36; i < 62; i++)
    {
        if (num_arr[i] != 0) {
            char index = 29 + i;
            updateValues(temp_arr, index, counter, num_arr, i);
        }
    }
    for (int i = 0; i < num; i++) {
        char_arr[i] = temp_arr[i];
    }
    cout << char_arr << '\n';
    return 0;
}