package bg.sofia.uni.fmi.mjt.grading.simulator;

import bg.sofia.uni.fmi.mjt.grading.simulator.grader.CodePostGrader;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodePostGraderTest {

    @Test
    public void testGradedAssignmentsWithoutSubmitted() {
        CodePostGrader codePostGrader = new CodePostGrader(1);
        codePostGrader.finalizeGrading();

        int sum = 0;

        for (Assistant a : codePostGrader.getAssistants()) {
            sum += a.getNumberOfGradedAssignments();
        }
        assertEquals(0, sum);
    }

    @Test
    public void testGetAssistants() {
        CodePostGrader cpg = new CodePostGrader(5);
        assertEquals(5, cpg.getAssistants().size());
        cpg.finalizeGrading();
    }

    @Test
    public void testSubmission() {
        CodePostGrader cpg = new CodePostGrader(1);
        List<Thread> list = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        int counter = 10000;
        final int numberOfStudents = 30;

        for (int i = 0; i < numberOfStudents; i++) {
            students.add(new Student(counter++, "Georgi Georgiev", cpg));
        }

        for (Student s : students) {
            Thread tr = new Thread(s);
            tr.start();
            list.add(tr);
        }

        for (Thread s : list) {
            try {
                s.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        cpg.finalizeGrading();
        int actual = cpg.getAssistants().iterator().next().getNumberOfGradedAssignments();
        assertEquals(numberOfStudents, actual);
    }

    @Test
    public void testGradedAssignmentsWithSubmittedWithSleep() {

        CodePostGrader cpg = new CodePostGrader(50);
        List<Thread> list = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        int counter = 10000;
        final int numberOfStudents = 30;

        for (int i = 0; i < numberOfStudents; i++) {
            students.add(new Student(counter++, "Georgi Georgiev", cpg));
        }

        for (Student s : students) {
            Thread tr = new Thread(s);
            tr.start();
            list.add(tr);
        }

        for (Thread s : list) {
            try {
                s.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        cpg.finalizeGrading();
        int sum = 0;
        for (Assistant a : cpg.getAssistants()) {
            sum += a.getNumberOfGradedAssignments();
        }
        assertEquals(numberOfStudents, sum);
    }
}
