#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

class SinglyLinkedListNode
{
public:
    int data;
    SinglyLinkedListNode* next;
    SinglyLinkedListNode* previous;
    SinglyLinkedListNode(int data = 0, SinglyLinkedListNode* next = nullptr, SinglyLinkedListNode* previous = nullptr)
    {
        this->data = data;
        this->next = next;
        this->previous = previous;
    }
};

class SinglyLinkedList
{
public:
    SinglyLinkedListNode* head;
    SinglyLinkedListNode* tail;
    SinglyLinkedListNode* middle_position;
    int lenght;
    SinglyLinkedList()
    {
        this->head = nullptr;
        this->tail = nullptr;
        this->lenght = 0;
        this->middle_position = nullptr;
    }
    void push_back(int node_data) 
    {
        lenght++;
        if (lenght % 2 == 0)
        {
            if (!middle_position)
                middle_position = head;
            else
                middle_position = middle_position->next;
        }
        SinglyLinkedListNode* node = new SinglyLinkedListNode(node_data);
        SinglyLinkedListNode* prev = this->tail;

        if (this->head == nullptr) 
            this->head = node;
        else
            this->tail->next = node;
        this->tail = node;
        this->tail->previous = prev;
    }
    void pop_back()
    {
        lenght--;
        if (lenght % 2 != 0)
        {
            if (!middle_position->previous)
                middle_position = nullptr;
            else
                middle_position = middle_position->previous;
        }
        if (this->head == this->tail)
        {
            head = nullptr;
            tail = nullptr;
        }
        else
        {
            SinglyLinkedListNode* new_tail = this->tail->previous;
            this->tail->previous->next = nullptr;
            delete tail;
            tail = new_tail;
        }
    }
    void milen()
    {
        SinglyLinkedListNode* current = middle_position;
        if (lenght == 1)
            return;
        if (lenght % 2 == 0)
            middle_position = tail;
        else
            middle_position = tail->previous;
        tail->next = head;
        head->previous = tail;
        head = current->next;
        tail = current;
        current->next = nullptr;
        head->previous = nullptr;
    }
    void print()
    {
        cout << lenght << '\n';
        SinglyLinkedListNode* current = head;
        while (current != nullptr)
        {
            cout << current->data << ' ';
            current = current->next;
        }
    }
};

int main() 
{
    SinglyLinkedList list;
    int n, data_node;
    string str;
    cin >> n;
    for (int i = 0; i < n; i++)
    {
        cin >> str;
        if (str == "add")
        {
            cin >> data_node;
            list.push_back(data_node);
        }
        else if (str == "gun")
        {
            list.pop_back();
        }
        else
        {
            list.milen();
        }
    }
    list.print();
    return 0;
}
