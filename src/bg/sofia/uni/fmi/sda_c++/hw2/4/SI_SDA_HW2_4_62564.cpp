#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

void swap_values_result(vector<int>& result) {
    int size = result.size();
    for (int i = 0; i < size / 2; i++) {
        int temp = result[i];
        result[i] = result[size - i - 1];
        result[size - i - 1] = temp;
    }
}

bool compare(pair<int, int> first_el, pair<int, int> second_el) {
    double result1 = pow(first_el.first, 2) / first_el.second,
        result2 = pow(second_el.first, 2) / second_el.second;
    if (result1 > result2)
        return true;
    if (result1 == result2)
        return first_el.first > second_el.first;
    return false;
}

void merge(vector<pair<int, int>>& values, int start, int middle, int end, vector<int>& result) {
    vector<pair<int, int>> left_values;
    vector<pair<int, int>> right_values;
    pair<int, int> supporter;
    vector<int> left_result;
    vector<int> right_result;
    for (int i = start; i <= middle; i++) {
        supporter.first = values[i].first;
        supporter.second = values[i].second;
        left_values.push_back(supporter);
        left_result.push_back(result[i]);
    }
    for (int i = middle + 1; i <= end; i++) {
        supporter.first = values[i].first;
        supporter.second = values[i].second;
        right_values.push_back(supporter);
        right_result.push_back(result[i]);
    }
    int i = 0, j = 0, k = start;
    while (i <= middle - start && j < end - middle) {
        if (compare(left_values[i], right_values[j]) == false) {

            values[k].first = left_values[i].first;
            values[k].second = left_values[i].second;
            result[k] = left_result[i];
            i++;
        }
        else {
            values[k].first = right_values[j].first;
            values[k].second = right_values[j].second;
            result[k] = right_result[j];
            j++;
        }
        k++;
    }
    if (i != middle - start + 1) {
        while (i <= middle - start) {
            values[k].first = left_values[i].first;
            values[k].second = left_values[i].second;
            result[k] = left_result[i];
            k++;
            i++;
        }
    }
    else {
        while (i < end - middle) {
            values[k].first = right_values[j].first;
            values[k].second = right_values[j].second;
            result[k] = right_result[j];
            k++;
            j++;
        }
    }
}

void mergesort(vector<pair<int, int>>& values, int start, int end, vector<int>& result) {
    if (start < end) {
        int middle = (start + end) / 2;
        mergesort(values, start, middle, result);
        mergesort(values, middle + 1, end, result);
        merge(values, start, middle, end, result);
    }
}

int main() {
    vector<pair<int, int>> values;
    pair<int, int> supporter;
    vector<int> result;
    int num, value;
    cin >> num;
    for (int i = 0; i < num; i++) {
        cin >> value;
        supporter.first = value;
        cin >> value;
        supporter.second = value;
        values.push_back(supporter);
        result.push_back(i + 1);
    }
    mergesort(values, 0, num - 1, result);
    swap_values_result(result);
    for (int i = 0; i < num; i++)
        cout << result[i] << ' ';
    return 0;
}