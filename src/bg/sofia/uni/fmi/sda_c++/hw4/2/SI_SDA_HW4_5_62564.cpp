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
    int lenght;
    SinglyLinkedList()
    {
        this->head = nullptr;
        this->tail = nullptr;
        this->lenght = 0;
    }
    void erase(SinglyLinkedListNode* begin, SinglyLinkedListNode* end)
    {
        lenght--;
        if (begin->previous && end->next)
        {
            begin->previous->next = end->next;
            end->next->previous = begin->previous;
        }
        else if(!begin->previous && end->next)
        {
            end->next->previous = nullptr;
            head = end->next;
        }
        else if (begin->previous && !end->next)
        {
            begin->previous->next = nullptr;
            tail = begin->previous;
        }
        else
        {
            head = nullptr;
            tail = nullptr;
        }
    }
    void push_back(int node_data)
    {
        lenght++;
        SinglyLinkedListNode* node = new SinglyLinkedListNode(node_data);
        SinglyLinkedListNode* prev = this->tail;

        if (this->head == nullptr)
            this->head = node;
        else
            this->tail->next = node;
        this->tail = node;
        this->tail->previous = prev;
    }
    SinglyLinkedListNode* insert(int value, int position)
    {
        lenght++;
        SinglyLinkedListNode* insert_node = new SinglyLinkedListNode(value);
        SinglyLinkedListNode* current = head;
        if (position == lenght - 1)
        {
            tail->next = insert_node;
            tail = insert_node;
        }
        for (int i = 0; i < position; i++)
        {
            if (!current->next)
                std::out_of_range("The position is bigger than the lenght of the list!");
            else
                current = current->next;
        }
        insert_node->next = current;
        current->previous->next = insert_node;
        insert_node->previous = current->previous;
        current->previous = insert_node;
        return insert_node;
    }
    void shoot(int value, int position)
    {
        SinglyLinkedListNode* insert_node = insert(value, position);
        SinglyLinkedListNode* current_bef = insert_node->previous;
        SinglyLinkedListNode* current_after = insert_node->next;
        while (current_bef->data == insert_node->data)
        {
            lenght--;
            if (!current_bef->previous)
                break;
            current_bef = current_bef->previous;
        }
        while (current_after->data == insert_node->data)
        {
            lenght--;
            if (!current_after->next)
                break;
            current_after = current_after->next;
        }
        if (current_bef != insert_node->previous && current_after != insert_node->next)
            erase(current_bef->next, current_after->previous);
        else if (current_bef != insert_node->previous)
            erase(current_bef->next, insert_node);
        else if (current_after != insert_node->next)
            erase(insert_node, current_after->previous);
        else
            return;
        if (current_bef == current_after)
        {

        }
    }
    void print()
    {
        cout << lenght << '\n';
        SinglyLinkedListNode* current = head;
        if (current == nullptr)
            cout << -1;
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
    int n, q, position, value;
    cin >> n;
    for (int i = 0; i < n; i++)
    {
        cin >> value;
        list.push_back(value);
    }
    cin >> q;
    for (int i = 0; i < q; i++)
    {
        cin >> value;
        cin >> position;
        list.shoot(value, position);
    }
    list.print();
    return 0;
}
