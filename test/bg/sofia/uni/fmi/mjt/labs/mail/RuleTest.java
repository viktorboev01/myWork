package bg.sofia.uni.fmi.mjt.mail;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

public class RuleTest {

    @Test
    public void equalTrueTest() {
        Rule r1 = new Rule(Set.of("juice"),Set.of("apple", "orange"),"/inbox/hobbies",
                Set.of("georgi@gmail.com", "ivan@gmail.com"), "petar@gmail.com", 5);
        Rule r2 = new Rule(Set.of("juice"),Set.of("apple", "orange"),"/inbox/hobbies/chess",
                Set.of("georgi@gmail.com", "ivan@gmail.com"), "petar@gmail.com", 5);
        assertEquals(true, r2.equal(r1));
    }

    @Test
    public void equalFalseTest() {
        Rule r1 = new Rule(Set.of("juice"),Set.of("apple", "orange"),"/inbox/hobbies",
                Set.of("georgi@gmail.com", "ivan@gmail.com"), "petar@gmail.com", 5);
        Rule r2 = new Rule(Set.of("juice"),Set.of("apple"),"/inbox/hobbies/chess",
                Set.of("georgi@gmail.com", "ivan@gmail.com"), "petar@gmail.com", 5);
        assertEquals(false, r2.equal(r1));
    }

    @Test
    public void checkPriorityTest() {
        assertThrows(IllegalArgumentException.class, () -> new Rule(Set.of("juice"),Set.of("apple", "orange"),"/inbox/hobbies",
                Set.of("georgi@gmail.com", "ivan@gmail.com"), "petar@gmail.com", 11));
    }
}
