package bg.sofia.uni.fmi.mjt.grading.simulator;

import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;
import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.AssignmentType;
import bg.sofia.uni.fmi.mjt.grading.simulator.grader.StudentGradingAPI;

import java.util.Random;

public class Student implements Runnable {

    private static final int SECOND = 1000;
    private String name;
    private int fn;
    private StudentGradingAPI grader;

    public Student(int fn, String name, StudentGradingAPI studentGradingAPI) {
        this.fn = fn;
        this.name = name;
        this.grader = studentGradingAPI;
    }

    @Override
    public void run() {
        AssignmentType[] numForType = {AssignmentType.LAB,
            AssignmentType.PLAYGROUND, AssignmentType.HOMEWORK, AssignmentType.PROJECT};
        AssignmentType type = numForType[new Random().nextInt(numForType.length)];
        try {
            Thread.sleep(new Random().nextInt(SECOND));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        grader.submitAssignment(new Assignment(fn, name, type));
    }

    public int getFn() {
        return fn;
    }

    public String getName() {
        return name;
    }

    public StudentGradingAPI getGrader() {
        return grader;
    }

}