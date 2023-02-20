#include <cmath>
#include <cstdio>
#include <vector>
#include <queue>
#include <iostream>
#include <algorithm>
using namespace std;

struct Node
{
    int data;
    Node* left, * right;

    Node(int _data = 0) : data(_data), left(nullptr), right(nullptr)
    {}
};

class Tree
{
private:
    Node* root;
    vector<int> sort_nodes;
    int find_position(int k, Node* current)
    {
        if (current != nullptr)
        {
            find_position(k, current->right);
            sort_nodes.push_back(current->data);
            find_position(k, current->left);
        }
        int size = sort_nodes.size();
        if (size > k)
            return sort_nodes[k - 1];
        else
            return -1;
    }
    void Free(Node* node)
    {
        node = nullptr;
        delete node;
    }
    Node* find_min(Node* node)
    {
        if (node->left == nullptr)
            return node;
        return find_min(node->left);
    }
    Node* private_insert(Node* root, int data)
    {
        if (root == nullptr)
        {
            return new Node(data);
        }
        if (data < root->data)
            root->left = private_insert(root->left, data);
        else if (data > root->data)
            root->right = private_insert(root->right, data);
        return root;
    }
    Node* private_delete_element(Node* current, int data)
    {
        if (current == nullptr)
            return nullptr;
        if (current->data > data)
            current->left = private_delete_element(current->left, data);
        else if (current->data < data)
            current->right = private_delete_element(current->right, data);
        else
        {
            if (!current->left)
            {
                struct Node* temp = current->right;
                Free(current);
                return temp;
            }
            else if (!current->right)
            {
                struct Node* temp = current->left;
                Free(current);
                return temp;
            }
            else
            {
                struct Node* temp = find_min(current->right);
                current->data = temp->data;
                current->right = private_delete_element(current->right, temp->data);
            }
        }
        return current;
    }
public:
    Tree() : root(nullptr)
    {}
    void insert(int value)
    {
        root = private_insert(root, value);
    }
    void delete_element(int k)
    {
        private_delete_element(root, find_position(k, root));
    }
    void print()
    {
        queue<Node*> q;
        q.push(root);
        q.push(nullptr);
        while (true)
        {
            Node* current = q.front();
            q.pop();
            if (current != nullptr) // Add the data from the next line
            {
                cout << current->data << ' ';
                if (current->left)
                    q.push(current->left);
                if (current->right)
                    q.push(current->right);
            }
            else
            {
                if (q.empty() == true)
                    break;
                q.push(nullptr); //Already printed all data from the line
            }
        }
    }
};

int main()
{
    Tree tree;
    int n, position_el_to_delete, data;
    cin >> n >> position_el_to_delete;
    for (int i = 0; i < n; i++)
    {
        cin >> data;
        tree.insert(data);
    }
    tree.delete_element(position_el_to_delete);
    tree.print();
    return 0;
}