package bg.sofia.uni.fmi.mjt.grading.simulator.assignment;

public enum AssignmentType {
    LAB(200), PLAYGROUND(4), HOMEWORK(8), PROJECT(12);

    private final int gradingTime;

    AssignmentType(int gradingTime) {
        this.gradingTime = gradingTime;
    }

    public int getGradingTime() {
        return gradingTime;
    }
}