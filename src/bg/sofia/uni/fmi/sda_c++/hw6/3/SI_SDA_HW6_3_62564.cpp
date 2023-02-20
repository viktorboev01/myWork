#include <cmath>
#include <cstdio>
#include <vector>
#include <queue>
#include <list>
#include <iostream>
#include <algorithm>
using namespace std;

struct Node
{
    int x;
    int value;
    Node(int _x, int _value) : x(_x), value(_value)
    {}
};

class Tree
{
private: 
    queue<Node> current_and_next_line;
    int counter_children;
    int min;
    int max;
    list<int> result;
public:
    Tree()
    {
        Node root(0, 0);
        current_and_next_line.push(root);
        counter_children = 0;
        result.push_back(0);
        min = 0;
        max = 0;
    }
    void add(int value)
    {
        counter_children++;
        int position;
        if (counter_children == 1)
            position = current_and_next_line.front().x - 1;
        else
            position = current_and_next_line.front().x + 1;
        if (value != -1)
        {
            Node current_node(position, value);
            if (position < min)
            {
                result.push_front(value);
                min = position;
            }
            else if (position > max)
            {
                result.push_back(value);
                max = position;
            }
            current_and_next_line.push(current_node);
        }
        if (counter_children == 2)
        {
            current_and_next_line.pop();
            counter_children = 0;
        }
    }
    void print()
    {
        list<int> temp_result = result;
        int size = temp_result.size();
        for (int i = 0; i < size; i++)
        {
            cout << temp_result.front() << ' ';
            temp_result.pop_front();
        }
    }
};

int main() 
{
    Tree tree;
    int n, value = 0;
    cin >> n;
    while (value != n - 1)
    {
        cin >> value;
        tree.add(value);
    }
    tree.print();
    return 0;
}
