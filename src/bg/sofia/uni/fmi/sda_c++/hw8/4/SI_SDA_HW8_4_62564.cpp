#include <cmath>
#include <cstdio>
#include <vector>
#include <map>
#include <set>
#include <iostream>
#include <algorithm>
#include<climits>
using namespace std;

void get_seconds(multimap<pair<int, int>, int> exams, long long counter_seconds, long long& counter_waiting)
{
    int size = exams.size();
    if (size == 0)
        return;
    int time_exam, pos_el;
    int time_start = INT_MAX;
    time_exam = exams.begin()->first.first;
    time_start = exams.begin()->second;
    pos_el = exams.begin()->first.second;
    pair<int, int> el_to_del;
    for (const auto& exam : exams)
    {
        if (time_start > exam.second)
        {
            time_start = exam.second;
            time_exam = exam.first.first;
            pos_el = exam.first.second;
        }
        if (exam.second <= counter_seconds)
            break;
    }
    counter_waiting += (counter_seconds - time_start > 0 ? counter_seconds - time_start + time_exam : time_exam);
    if (time_start > counter_seconds)
        counter_seconds = time_start;
    counter_seconds += time_exam;
    el_to_del.first = time_exam;
    el_to_del.second = pos_el;
    exams.erase(el_to_del);
    get_seconds(exams, counter_seconds, counter_waiting);
}

int main() 
{
    int n, value_finish, value_time;
    long long counter_waiting = 0;
    pair<int, int> current_student;
    cin >> n;
    multimap<pair<int, int>, int> exams;
    set<int> s;
    for (int i = 0; i < n; i++)
    {
        cin >> value_finish;
        cin >> value_time;
        current_student.first = value_time;
        current_student.second = i;
        exams.insert(pair<pair<int, int>, int>(current_student, value_finish));
    }
    for (const auto& exam : s)
    {
        cout << exam << '\n';
    }
    get_seconds(exams, 0, counter_waiting);
    cout << counter_waiting / n << '\n';
    return 0;
}
