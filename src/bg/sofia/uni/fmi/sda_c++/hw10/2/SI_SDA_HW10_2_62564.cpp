#include <cmath>
#include <cstdio>
#include <vector>
#include <list>
#include <iostream>
#include <algorithm>
using namespace std;

class Graph 
{
private: 
    int** adj;
    int healthy_students, rows, cols;
    void spread(int time)
    {
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (adj[i][j] == time - 1)
                {
                    if (i != 0)
                    {
                        if (adj[i - 1][j] == -1) 
                        {
                            adj[i - 1][j] = time;
                            healthy_students--;
                        }
                    }
                    if (i != rows - 1)
                    {
                        if (adj[i + 1][j] == -1)
                        {
                            adj[i + 1][j] = time;
                            healthy_students--;
                        }
                    }
                    if (i != cols - 1) 
                    {
                        if (adj[i][j + 1] == -1)
                        {
                            adj[i][j + 1] = time;
                            healthy_students--;
                        }
                    }
                    if (j != 0) 
                    {
                        if (adj[i][j - 1] == -1)
                        {
                            adj[i][j - 1] = time;
                            healthy_students--;
                        }
                    }
                }
            }
        }
    }
public:
    Graph(int _rows, int _cols) : rows(_rows), cols(_cols)
    {
        adj = new int*[rows];
        for (int i = 0; i < rows; i++)
        {
            adj[i] = new int[cols];
        }
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                adj[i][j] = -1;
            }
        }
        healthy_students = rows * cols;
    }
    void add_element(int pos_height, int pos_widht)
    {
        adj[pos_height - 1][pos_widht - 1] = 0;
        healthy_students--;
    }
    void print()
    {
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                cout << adj[i][j] << ' ';
            }
            cout << '\n';
        }
    }
    int result(int days)
    {
        for (int i = 1; i <= days; i++)
        {
            spread(i);
            if (healthy_students == 0)
                break;
        }
        return healthy_students;
    }
};

int main()
{
    int N, M, T, K, row, col;
    cin >> N >> M;
    Graph gr(N, M);
    cin >> T >> K;
    for (int i = 0; i < K; i++)
    {
        cin >> row >> col;
        gr.add_element(row, col);
    }
    cout << gr.result(T) << '\n';
    return 0;
}

/*int main()
{
    Graph gr(10, 8);
    gr.add_element(10, 8);
    gr.add_element(2, 1);
    gr.print();
    cout << endl;
    cout << gr.result(2) << endl;
    gr.print();
    return 0;
}*/