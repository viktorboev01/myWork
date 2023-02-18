package bg.sofia.uni.fmi.mjt.mail.exceptions;

import bg.sofia.uni.fmi.mjt.mail.Outlook;
import bg.sofia.uni.fmi.mjt.mail.exceptions.AccountAlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OutlookInvalidPathExceptionTest {
    private Outlook outlook = new Outlook();

    @Test
    public void testCreateFolderFolderInvalidPathExceptionWithoutInbox() throws InvalidPathException {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        assertThrows(InvalidPathException.class, () ->
                        outlook.createFolder("johnDoe", "/sent/hobbies"));
    }

    @Test
    public void testCreateFolderFolderInvalidPathExceptionWithInbox() throws InvalidPathException {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        assertThrows(InvalidPathException.class, () ->
                outlook.createFolder("johnDoe", "/inbox/hobbies/chess"));
    }
}
