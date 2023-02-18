package bg.sofia.uni.fmi.mjt.mail.exceptions;

import bg.sofia.uni.fmi.mjt.mail.Outlook;
import bg.sofia.uni.fmi.mjt.mail.exceptions.AccountAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.mail.exceptions.AccountNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OutlookAccountExceptionsTest {

    private Outlook outlook = new Outlook();

    @Test
    public void testAddNewAccountAlreadyExistsException() throws AccountAlreadyExistsException {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        assertThrows(AccountAlreadyExistsException.class, () ->
                        outlook.addNewAccount("John Doe", "johndoe@gmail.com"),
                "AccountAlreadyExistsException expected to be thrown when account already exists in the system.");
    }

    @Test
    public void testCreateFolderAccountNotFoundException() throws AccountNotFoundException {
        assertThrows(AccountNotFoundException.class, () ->
                outlook.createFolder("johnDoe", "/inbox/hobbies"));
    }

    @Test
    public void testAddRuleAccountNotFoundException() throws AccountNotFoundException {
        assertThrows(AccountNotFoundException.class, () ->
                outlook.addRule("johnDoe", "/inbox/hobbies", "from: sb@gmail.com", 1));
    }

    @Test
    public void testReceiveMailAccountNotFoundException() throws AccountNotFoundException {
        assertThrows(AccountNotFoundException.class, () ->
                outlook.receiveMail("johnDoe", "/inbox/hobbies", "text"));
    }

    @Test
    public void testSentMailAccountNotFoundException() throws AccountNotFoundException {
        assertThrows(AccountNotFoundException.class, () ->
                outlook.sendMail("johnDoe", "/inbox/hobbies", "text"));
    }

    @Test
    public void testGetMailsFromFolderAccountNotFoundException() throws AccountNotFoundException {
        assertThrows(AccountNotFoundException.class, () ->
                outlook.getMailsFromFolder("johnDoe", "/inbox/hobbies"));
    }
}
