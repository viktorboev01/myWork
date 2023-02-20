#include <cmath>
#include <cstdio>
#include <vector>
#include <string>
#include <iostream>
#include <algorithm>
using namespace std;

class Stack
{
private:
    string str;
public:
    Stack(string _array = "") : str(_array)
    {}
    void pop_back()
    {
        str.pop_back();
    }
    void push_back(char a)
    {
        str.push_back(a);
    }
    void get_result()
    {
        string result = "";
        int size = str.size();
        const int array_size = 62;
        int num_arr[array_size];
        for (int i = 0; i < array_size; i++)
            num_arr[i] = 0;
        for (int i = 0; i < size; i++)
        {
            if (str[i] >= '0' && str[i] <= '9')
            {
                num_arr[str[i] - 48]++;
            }
            else if (str[i] >= 'a' && str[i] <= 'z')
            {
                num_arr[str[i] - 87]++;
            }
            else
            {
                num_arr[str[i] - 29]++;
            }
        }
        for (int i = 0; i < size; i++)
        {
            if (str[i] >= '0' && str[i] <= '9')
            {
                if (num_arr[str[i] - 48] == 1)
                    result += to_string(i) + ' ';
            }
            else if (str[i] >= 'a' && str[i] <= 'z')
            {
                if (num_arr[str[i] - 87] == 1)
                    result += to_string(i) + ' ';
            }
            else
            {
                if (num_arr[str[i] - 29] == 1)
                    result += to_string(i) + ' ';
            }
        }
        cout << result << '\n';
    }
};

int main() 
{
    string str;
    cin >> str;
    Stack stack(str);
    stack.get_result();
    return 0;
}