#include<iostream>
#include<climits>
#include<string>
#include<set>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;
    set<int> array;
    array.insert(0);
    string output;

    int min_xor = INT_MAX;

    for (int i = 1; i <= n; i++) {
        int tmp;
        cin >> tmp;
        if (array.count(tmp) > 0) {
            output += to_string(min_xor) + '\n';
            continue;
        }
        array.insert(tmp);

        auto it0 = array.find(tmp);
        auto it1 = it0;
        it1--;
        auto it2 = it0;
        it2++;
        int tmp1 = *it1 ^ tmp;
        int tmp2;
        if (it2 != array.end()) {
            tmp2 = tmp ^ *it2;

            if (tmp1 >= tmp2 && min_xor > tmp2) {
                min_xor = tmp2;
            }
            else if (tmp1 <= tmp2 && min_xor > tmp1) {
                min_xor = tmp1;
            }
        }
        else {
            if (tmp1 < min_xor) {
                min_xor = tmp1;
            }
        }
        output += to_string(min_xor) + '\n';
    }

    cout << output;

    return 0;
}