#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

struct Node
{
    int sum;
    int key;
    struct Node* parent;
    Node(int _key, Node* _parent) : sum(0), key(_key), parent(_parent)
    {}
};

class Tree
{
private:
    vector<Node*> all_workers;
public:
    Tree()
    {
        Node* current = new Node(0, nullptr);
        all_workers.push_back(current);
    }
    void add(int parent_key, int current_key)
    {
        Node* current = new Node(current_key, all_workers[parent_key]);
        all_workers.push_back(current);
        while (current->parent != nullptr)
        {
            current = current->parent;
            current->sum++;
        }
    }
    void print()
    {
        int size = all_workers.size();
        for (int i = 0; i < size; i++)
            cout << all_workers[i]->sum << ' ';
        cout << '\n';
    }
};

int main() 
{
    Tree company;
    int n, value_parent, value_current;
    cin >> n;
    for (int i = 0; i < n - 1; i++)
    {
        cin >> value_parent >> value_current;
        company.add(value_parent, value_current);
    }
    company.print();
    return 0;
}
