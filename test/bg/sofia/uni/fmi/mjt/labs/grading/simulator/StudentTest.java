package bg.sofia.uni.fmi.mjt.grading.simulator;

import bg.sofia.uni.fmi.mjt.grading.simulator.grader.CodePostGrader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StudentTest {

    @Test
    public void testGetters() {
        CodePostGrader cpg = new CodePostGrader(5);
        Student s1 = new Student(12345, "Georgi Georgiev", cpg);
        cpg.finalizeGrading();

        Assertions.assertEquals(12345, s1.getFn());
        Assertions.assertEquals("Georgi Georgiev", s1.getName());
        Assertions.assertEquals(cpg, s1.getGrader());

    }
}
