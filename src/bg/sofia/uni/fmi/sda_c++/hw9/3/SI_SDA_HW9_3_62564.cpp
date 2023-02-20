#include <cmath>
#include <cstdio>
#include <vector>
#include <string>
#include <iostream>
#include <algorithm>
using namespace std;


static const int MOD = 1000000000 + 7; 
static const int BASE = 29; 
static const int MAX_SIZE = 10001;

long long base_powers[MAX_SIZE];
long long prefix_hash[MAX_SIZE];

void get_powers(int to) 
{
    base_powers[0] = 1;
    for (int i = 1; i <= to; i++) 
    {
        base_powers[i] = (base_powers[i - 1] * BASE) % MOD;
    }
}

void get_prefix_hashes(string text) 
{
    for (int i = 0; i < text.size(); i++) 
    {
        prefix_hash[i + 1] = (prefix_hash[i] + text[i] * base_powers[i]) % MOD;
    }
}

long long get_hash(string word)
{
    long long result = 0;
    int size = word.size();
    for (int i = 0; i < size; i++)
    {
        result = (result + word[i] * base_powers[i]) % MOD;
    }
    return result;
}

bool IsWordExist(string text, string word)
{
    int text_size = text.size();
    int word_size = word.size();
    get_powers(text_size);
    get_prefix_hashes(text);
    long long word_hash = get_hash(word);
    long long current_word = prefix_hash[word.size()];
    if (word_hash == current_word)
        return true;
    for (int i = word_size + 1; i <= text_size; i++)
    {
        current_word = (prefix_hash[i] - prefix_hash[i - word_size]) % MOD;
        if (current_word < 0)
            current_word += MOD;
        if (current_word == (word_hash * base_powers[i - word_size]) % MOD)
            return true;
    }
    return false;
}

int main() 
{
    string str, text, word;
    bool IsMatch = false;
    cin >> text >> str;
    int size_str = str.size();
    int size_text = text.size();
    int curr_str_lenght = 1, curr_str_pos = 0, result = 0;
    while (curr_str_lenght != size_str)
    {
        while (curr_str_pos != size_str - curr_str_lenght)
        {
            if (IsWordExist(text, str.substr(curr_str_pos, curr_str_lenght)))
            {
                IsMatch = true;
                break;
            }
            curr_str_pos++;
        }
        if (!IsMatch)
            break;
        result++;
        curr_str_lenght++;
        curr_str_pos = 0;
        IsMatch = false;
    }
    cout << result << '\n';
    return 0;
}