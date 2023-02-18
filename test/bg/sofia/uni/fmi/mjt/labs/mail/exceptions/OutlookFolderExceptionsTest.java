package bg.sofia.uni.fmi.mjt.mail.exceptions;

import bg.sofia.uni.fmi.mjt.mail.Outlook;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OutlookFolderExceptionsTest {

    private Outlook outlook = new Outlook();

    @Test
    public void testCreateFolderFolderAlreadyExistsException() throws FolderAlreadyExistsException {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        assertThrows(FolderAlreadyExistsException.class, () ->
                outlook.createFolder("johnDoe", "/inbox"));
    }

    @Test
    public void testAddRuleFolderNotFoundException() throws FolderNotFoundException {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        assertThrows(FolderNotFoundException.class, () ->
                outlook.addRule("johnDoe", "/inbox/hobbies", "from: gosho@gmail.com", 2));
    }

    @Disabled
    public void testReceiveMailFolderNotFoundException() throws FolderNotFoundException {
        outlook.addNewAccount("John Doe", "johndoe@gmail.com");
        outlook.addNewAccount("Georgi Georgiev", "ggeorgiev@gmail.com");
        assertThrows(FolderNotFoundException.class, () ->
                outlook.receiveMail("John Doe", "sender: ggeorgiev@gmail.com"
                        + System.lineSeparator() + "subject: exam mjt 2022"
                        + System.lineSeparator() + "recipients: johndoe@gmail.com"
                        + System.lineSeparator() + "received: 2022-12-11 12:12",
                        "The final exam is crucial for your grade"));
    }

    @Test
    public void testGetMailsFromFolderFolderNotFoundException() throws FolderNotFoundException {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        assertThrows(FolderNotFoundException.class, () ->
                outlook.getMailsFromFolder("johnDoe", "/inbox/hobbies"));
    }
}
