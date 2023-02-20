#include <cmath>
#include <climits>
#include <cstdio>
#include <vector>
#include <string>
#include <iostream>
#include <algorithm>
using namespace std;

void print(std::vector<int> vec)
{
    for (int i = 0; i < (int)vec.size(); i++)
        cout << vec[i] << " ";
    cout << '\n';
}
void change_values(std::vector<int> vec)
{
    int distance = 1;
    int position_to = 0;
    // Result is n n-1 ... 2 1 0 m m-1 ... 2 1 0 ...
    for (int i = 0; i < (int)vec.size(); i++){
        if (vec[i] == 0){
            for (int j = i - 1; j >= position_to; j--){
                if (vec[j] > distance) {
                    vec[j] = distance;
                    distance++;
                }
            }
            position_to = i + 1;
            distance = 1;
        }
    }
    // Fill the values from last 0 to the end
    for (int j = position_to; j < (int)vec.size(); j++){
        vec[j] = distance;
        distance++;
    }

    // Correct the values right after the 0's
    for (int i = 0; i < position_to; i++){
        if (vec[i] == 0){
            distance = 1;
            int j = i + 1;
            if (j == position_to)
                break;
            while (vec[j] != 0){
                if (vec[j] > distance)
                    vec[j] = distance;
                else
                    break;
                distance++;
                j++;
            }
        }
    }
    print(vec);
}

int main()
{
    std::vector<int> vec;
    string participants;
    cin >> participants;
    char variation;
    cin >> variation;
    int counter = 0;

    //Sort our variation from the others
    while (participants[counter] != '\0'){
        if (participants[counter] == variation){
            vec.push_back(0);
        }
        else{
            vec.push_back(INT_MAX);
        }
        counter++;
    }

    //Print answer
    change_values(vec);
    return 0;
}