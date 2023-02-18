package bg.sofia.uni.fmi.mjt.grading.simulator.grader;

import bg.sofia.uni.fmi.mjt.grading.simulator.Assistant;
import bg.sofia.uni.fmi.mjt.grading.simulator.Student;
import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CodePostGrader implements AdminGradingAPI {

    private Assistant[] assistants;
    private Queue<Assignment> uncompletedAssignments;
    private boolean isFinalNow;
    private boolean isFinalExecuted;
    private int counterSubmittedAssignments;

    public CodePostGrader(int numAssistants) {

        this.assistants = new Assistant[numAssistants];
        this.uncompletedAssignments = new ArrayDeque<>();
        counterSubmittedAssignments = 0;

        isFinalNow = false;
        isFinalExecuted = false;

        for (int i = 0; i < numAssistants; i++) {
            assistants[i] = new Assistant("assistant-" + Integer.toString(i + 1), this);
            assistants[i].start();
        }
    }

    @Override
    public synchronized Assignment getAssignment() {
        if (uncompletedAssignments.size() == 0) {
            return null;
        }
        Assignment a = uncompletedAssignments.peek();
        uncompletedAssignments.remove();
        return a;
    }

    @Override
    public synchronized int getSubmittedAssignmentsCount() {
        if (isFinalNow && uncompletedAssignments.size() == 0) {
            return -1;
        }
        return counterSubmittedAssignments;
    }

    @Override
    public void finalizeGrading() {
        isFinalExecuted = true;
        isFinalNow = true;
        for (int i = 0; i < assistants.length; i++) {
            try {
                assistants[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        isFinalNow = false;
    }

    @Override
    public List<Assistant> getAssistants() {
        return List.of(assistants);
    }

    @Override
    public synchronized void submitAssignment(Assignment assignment) {
        if (!isFinalExecuted) {
            uncompletedAssignments.add(assignment);
        }
        counterSubmittedAssignments++;
    }
}
