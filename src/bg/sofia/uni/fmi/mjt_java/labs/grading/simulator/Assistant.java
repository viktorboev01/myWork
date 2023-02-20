package bg.sofia.uni.fmi.mjt.grading.simulator;

import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;
import bg.sofia.uni.fmi.mjt.grading.simulator.grader.AdminGradingAPI;

public class Assistant extends Thread {

    private AdminGradingAPI grader;
    private String name;
    private int gradedAssignments;


    public Assistant(String name, AdminGradingAPI grader) {
        this.name = name;
        this.grader = grader;
        this.gradedAssignments = 0;
    }

    @Override
    public void run() {
        while (grader.getSubmittedAssignmentsCount() != -1) {
            try {
                Assignment assignmentToGrade = grader.getAssignment();
                if (assignmentToGrade == null) {
                    continue;
                }
                sleep(assignmentToGrade.type().getGradingTime());
                gradedAssignments++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getNumberOfGradedAssignments() {
        return gradedAssignments;
    }

}