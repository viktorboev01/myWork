package bg.sofia.uni.fmi.mjt.mail.exceptions;

import bg.sofia.uni.fmi.mjt.mail.Outlook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OutlookRuleExceptionsTest {

    private Outlook outlook = new Outlook();

    @Test
    public void testAddRuleRuleAlreadyDefinedException() throws RuleAlreadyDefinedException  {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        outlook.addNewAccount("georgiGeorgiev", "ggeorgiev@gmail.com");
        outlook.createFolder("johnDoe", "/inbox/hobbies");
        outlook.createFolder("johnDoe", "/inbox/hobbies/chess");
        outlook.addRule("johnDoe", "/inbox/hobbies", "subject-includes: juice"
                + System.lineSeparator() + "subject-or-body-includes: orange, apple"
                + System.lineSeparator() + "from: aosho@gmail.com", 1);
        assertThrows(RuleAlreadyDefinedException.class, () ->
                outlook.addRule("johnDoe", "/inbox/hobbies/chess",
                        "subject-includes: juice"
                        + System.lineSeparator() + "subject-or-body-includes: orange, apple"
                        + System.lineSeparator() + "from: aosho@gmail.com", 1));
    }
}
