#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

int num_lower_than_word_length(char letter, char* word, long long num)
{
    int result = 0;
    int counter = 0;
    while (counter != num)
    {
        if (word[counter] == letter)
            result++;
        counter++;
    }
    return result;
}

int main() 
{
    long long num, result;
    unsigned int counter = 0, counter_letter = 0;
    char letter;
    const int size_array = 101;
    char* word = new char[size_array];
    std::cin.getline(word, size_array);
    std::cin >> num;
    if (num > pow(10, 9) || num < 1)
        cout << "The number is invalid!";
    cin >> letter;
    while (word[counter] != '\0')
    {
        if (word[counter] == letter)
            counter_letter++;
        counter++;
    }
    result = num_lower_than_word_length(letter, word, num % counter) + (num / counter) * counter_letter;
    cout << result << std::endl;
    delete[] word;
    return 0;
}