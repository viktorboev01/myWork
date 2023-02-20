from datetime import time
import unittest

from SchoolSchedule import Days, Interval, Teacher, Subject, \
InstitutionData, Program, Group

class TestInitClasses(unittest.TestCase):
    
    @classmethod
    def setUpClass(self):

        self._days = [Days.Monday, Days.Tuesday, Days.Wensday, Days.Thursday, Days.Friday, Days.Saturday]
            
        self._level = 3
        self._subject1 = Subject('geography', self._level)
        self._subject2 = Subject('maths', self._level)
        self._subject3 = Subject('sport', self._level)

        self._dict_subjects = {self._subject1:6, self._subject2:6, self._subject3:6}

        self._teacher1 = Teacher('Ivan', 'Ivanov', self._subject1)
        self._teacher2 = Teacher('Petar', 'Petrov', self._subject2)
        self._teacher3 = Teacher('Georgi', 'Georgiev', self._subject3)

        self._group1 = Group(self._level, 'a', self._dict_subjects)
        self._group2 = Group(self._level, 'b', self._dict_subjects)
        self._group3 = Group(self._level, 'c', self._dict_subjects)

        self._set_teachers = {self._teacher1, self._teacher2, self._teacher3}
        self._set_subjects = {self._subject1, self._subject2, self._subject3}
        self._set_groups = {self._group1, self._group2, self._group3}

        self._institution = InstitutionData(self._set_teachers, self._set_subjects, self._days, time(8, 00), time(11, 00), self._level, 60)
        self._program = Program(self._set_groups, self._institution)

    def test_intervals_init(self):
        
        day = Days.Saturday
        start = time(8, 00)

        with self.assertRaises(Exception):
            Interval(day, '9:41')

        with self.assertRaises(Exception):
            Interval('Saturday', start)

    def test_teachers_init(self):

        first_name = 'Ivan'
        last_name = 'Ivanov'

        with self.assertRaises(Exception):
            Teacher(first_name, last_name, [('maths', 2)])

        with self.assertRaises(Exception):
            Teacher('ivan', last_name, self._subject1)

        with self.assertRaises(Exception):
            Teacher(first_name, 'ivanov', self._subject1)

        with self.assertRaises(Exception):
            Teacher('Iva2n', last_name, self._subject1)

        with self.assertRaises(Exception):
            Teacher(first_name, 'ivano2v', self._subject1)

        self.assertEqual(1, self._teacher2.id - self._teacher1.id)

    def test_subjects_init(self):
        
        with self.assertRaises(Exception):
            Subject('Maths2', 2)

        with self.assertRaises(Exception):
            Subject(2, 2)

        with self.assertRaises(Exception):
            Subject('Maths', '2')

    def test_group_init(self):

        subject_with_different_level = Subject('maths', self._level + 1)
        group_name = 'a'

        with self.assertRaises(Exception):
            Group('incorrect data', group_name, self._dict_subjects)

        with self.assertRaises(Exception):
            Group(self._level, 1, self._dict_subjects)

        with self.assertRaises(Exception):
            Group(self._level, group_name, 'incorrect data')

        with self.assertRaises(Exception):
            Group(self._level, group_name, {self._subject1:2, subject_with_different_level:3})

        with self.assertRaises(Exception):
            Group(self._level, group_name, {self._subject1:2, self._subject2:'a'})

    def test_institution_init(self):

        self.assertEqual(self._set_teachers, self._institution.teachers)
        self.assertEqual(self._set_subjects, self._institution.subjects)
        self.assertEqual(self._days, self._institution.days)

        with self.assertRaises(Exception):
            InstitutionData({'incorrect data'}, self._set_subjects, self._days, time(8, 00), time(10, 00), 4, 60)

        with self.assertRaises(Exception):
            InstitutionData(self._set_teachers, {'incorrect data'}, self._days, time(8, 00), time(10, 00), 4, 60)

        with self.assertRaises(Exception):
            InstitutionData(self._set_teachers, self._set_subjects, ['incorrect data'], time(8, 00), time(10, 00), 4, 60)

        with self.assertRaises(Exception):
            InstitutionData(self._set_teachers, self._set_subjects, self._days, 'incorrect data', time(10, 00), 4, 60)

        with self.assertRaises(Exception):
            InstitutionData(self._set_teachers, self._set_subjects, self._days, time(8, 00), 'incorrect data', 4, 60)

        with self.assertRaises(Exception):
            InstitutionData(self._set_teachers, self._set_subjects, self._days, time(8, 00), time(10, 00), 0, 60)

        with self.assertRaises(Exception):
            InstitutionData(self._set_teachers, self._set_subjects, self._days, time(8, 00), time(10, 00), 4, 0)

    def test_program_init(self):
        
        with self.assertRaises(Exception):
            Program('incorrect data', self._institution)

        with self.assertRaises(Exception):
            Program(self._set_groups, 'incorrect data')
        
        self.assertEqual(3, len(self._program.subjects))
        self.assertEqual(3, len(self._program.teachers))
        self.assertEqual(3, len(self._program.all_times_per_day))
        self.assertEqual(18, len(self._program.all_intervals))      

class TestCreateProgramSameLevelGroups(unittest.TestCase):

    @classmethod
    def setUp(self):
        self._days = [Days.Monday, Days.Thursday]
            
        self._level = 3
        self._subject1 = Subject('geography', self._level)
        self._subject2 = Subject('maths', self._level)
        self._subject3 = Subject('sport', self._level)
        self._subject4 = Subject('history', self._level)

        self._dict_subjects = {self._subject1:1, self._subject2:1, self._subject3:1, self._subject4:1}

        self._teacher1 = Teacher('Ivan', 'Ivanov', self._subject1)
        self._teacher2 = Teacher('Petar', 'Petrov', self._subject2)
        self._teacher3 = Teacher('Georgi', 'Georgiev', self._subject3)
        self._teacher4 = Teacher('Dimitar', 'Dimitrov', self._subject4)
    
    def test_simple_even_intervals(self):
        
        self._dict_subjects[self._subject4] = 1

        group1 = Group(self._level, 'a', self._dict_subjects)
        group2 = Group(self._level, 'b', self._dict_subjects)
        group3 = Group(self._level, 'c', self._dict_subjects)
        group4 = Group(self._level, 'd', self._dict_subjects)

        set_teachers = {self._teacher1, self._teacher2, self._teacher3, self._teacher4}
        set_subjects = {self._subject1, self._subject2, self._subject3, self._subject4}

        set_groups = {group1, group2, group3, group4}
        institution = InstitutionData(set_teachers, set_subjects, self._days, time(8, 00), time(10, 00), self._level, 60)

        program = Program(set_groups, institution)
        program.make_program()

        self.assertEqual(16, program.count_units())

    def test_simple_odd_intervals(self):
        
        self._dict_subjects.pop(self._subject4)

        group1 = Group(self._level, 'a', self._dict_subjects)
        group2 = Group(self._level, 'b', self._dict_subjects)
        group3 = Group(self._level, 'c', self._dict_subjects)

        set_teachers = {self._teacher1, self._teacher2, self._teacher3}
        set_subjects = {self._subject1, self._subject2, self._subject3}

        set_groups = {group1, group2, group3}
        institution = InstitutionData(set_teachers, set_subjects, self._days, time(8, 00), time(10, 00), self._level, 60)

        program = Program(set_groups, institution)
        program.make_program()

        self.assertEqual(9, program.count_units())
        
class TestCreateProgramDifferentLevelGroups(unittest.TestCase):

    @classmethod
    def setUp(self):

        self._days = [Days.Monday, Days.Wensday]
        
        self._level = 3
        self._subject1 = Subject('geography', self._level + 1)
        self._subject2 = Subject('maths', self._level + 1)
        self._subject3 = Subject('sport', self._level)
        self._subject4 = Subject('history', self._level)

        self._first_name = 'Ivan'
        self._second_name = 'Ivanov'
        self._teacher1 = Teacher(self._first_name, self._second_name, self._subject3)
        self._teacher2 = Teacher(self._first_name, self._second_name, self._subject1)
        self._teacher3 = Teacher(self._first_name, self._second_name, self._subject2)
        self._teacher4 = Teacher(self._first_name, self._second_name, self._subject4)
        
        self._group1 = Group(self._level, 'a', {self._subject3:1, self._subject4:1})
        self._group2 = Group(self._level + 1, 'a', {self._subject1:1, self._subject2:1})
        self._group3 = Group(self._level, 'b', {self._subject3:1, self._subject4:1})
        self._group4 = Group(self._level + 1, 'b', {self._subject1:1, self._subject2:1})
        
    def test_simple_even_intervals_with_different_levels(self):

        set_teachers = {self._teacher1, self._teacher2, self._teacher3, self._teacher4}
        set_subjects = {self._subject1, self._subject2, self._subject3, self._subject4}

        set_groups = {self._group1, self._group2, self._group3, self._group4}
        institution = institution = InstitutionData(set_teachers, set_subjects, self._days, time(8, 00), time(9, 00), self._level + 1, 60)

        program = Program(set_groups, institution)
        program.make_program()

        self.assertEqual(8 ,program.count_units())

class TestCreateProgramMultipleTimesSubjectPerWeek(unittest.TestCase):

    @classmethod
    def setUp(self):

        self._days = [Days.Monday, Days.Tuesday, Days.Wensday, Days.Thursday, Days.Friday, Days.Saturday]
        
        self._level = 3
        self._subject1 = Subject('geography', self._level)
        self._subject2 = Subject('maths', self._level)
        self._subject3 = Subject('sport', self._level)
        self._subject4 = Subject('history', self._level)
        
        self._dict_subjects = {self._subject1:2, self._subject2:2, self._subject3:2}

        self._first_name = 'Ivan'
        self._second_name = 'Ivanov'
        self._teacher1 = Teacher(self._first_name, self._second_name, self._subject3)
        self._teacher2 = Teacher(self._first_name, self._second_name, self._subject1)
        self._teacher3 = Teacher(self._first_name, self._second_name, self._subject2)
        self._teacher4 = Teacher(self._first_name, self._second_name, self._subject4)
        
        self._group1 = Group(self._level, 'a', self._dict_subjects)
        self._group2 = Group(self._level, 'b', self._dict_subjects)
        self._group3 = Group(self._level, 'c', self._dict_subjects)
        self._group4 = Group(self._level, 'd', self._dict_subjects)

    def test_multiple_times_subject_per_week(self):

        set_teachers = {self._teacher1, self._teacher2, self._teacher3}
        set_subjects = {self._subject1, self._subject2, self._subject3}

        set_groups = {self._group1, self._group2, self._group3}
        institution = InstitutionData(set_teachers, set_subjects, self._days, time(8, 00), time(9, 00), self._level, 60)

        program = Program(set_groups, institution)
        program.make_program()

        self.assertEqual(18, program.count_units())

class TestSettersProgram(unittest.TestCase):

    @classmethod
    def setUp(self):

        self._days = [Days.Monday, Days.Tuesday, Days.Wensday, Days.Thursday, Days.Friday, Days.Saturday]
        
        self._level = 3
        self._subject1 = Subject('geography', self._level)
        self._subject2 = Subject('maths', self._level)
        self._subject3 = Subject('sport', self._level)
        
        self._dict_subjects = {self._subject1:6, self._subject2:6, self._subject3:6}

        self._teacher1 = Teacher('Petar', 'Petrov', self._subject1)
        self._teacher2 = Teacher('Ivan', 'Ivanov', self._subject2)
        self._teacher3 = Teacher('Georgi', 'Georgiev', self._subject3)
        
        self._group1 = Group(self._level, 'a', self._dict_subjects)
        self._group2 = Group(self._level, 'b', self._dict_subjects)
        self._group3 = Group(self._level, 'c', self._dict_subjects)

        set_teachers = {self._teacher1, self._teacher2, self._teacher3}
        set_subjects = {self._subject1, self._subject2, self._subject3}

        set_groups = {self._group1, self._group2, self._group3}
        institution = InstitutionData(set_teachers, set_subjects, self._days, time(8, 00), time(12, 00), self._level, 60)

        self._program = Program(set_groups, institution)
        self._program.make_program()

        self._interval1 = Interval(Days.Friday, time(8,00), 0)
        self._interval2 = Interval(Days.Saturday, time(8,00), 0)

    def test_move_unit(self):
        self.assertTrue(self._program.move_unit(self._interval1, self._interval2, self._group2))

    def test_switch_two_units(self):
        self._program.move_unit(self._interval1, self._interval2, self._group2)
        self.assertTrue(self._program.switch_two_units(self._interval1, self._group1, self._interval2, self._group2))

    def test_change_subject_in_filled_interval(self):
        self._program.move_unit(self._interval1, self._interval2, self._group2)
        self._program.switch_two_units(self._interval1, self._group1, self._interval2, self._group2)
        self.assertTrue(self._program.change_subject(self._interval2, self._group1, self._teacher1))

    def test_change_subject_with_busy_teacher(self):
        self._program.move_unit(self._interval1, self._interval2, self._group2)
        self._program.switch_two_units(self._interval1, self._group1, self._interval2, self._group2)
        self._program.change_subject(self._interval2, self._group1, self._teacher1)
        self.assertFalse(self._program.change_subject(self._interval2, self._group2, self._teacher1))
    
    def test_change_subject_in_free_interval(self):
        self._program.move_unit(self._interval1, self._interval2, self._group2)
        self._program.switch_two_units(self._interval1, self._group1, self._interval2, self._group2)
        self._program.change_subject(self._interval2, self._group1, self._teacher1)
        self.assertTrue(self._program.change_subject(self._interval2, self._group2, self._teacher2))

if __name__ == '__main__':
    unittest.main()