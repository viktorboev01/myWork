package bg.sofia.uni.fmi.mjt.mail.exceptions;

import bg.sofia.uni.fmi.mjt.mail.Outlook;
import bg.sofia.uni.fmi.mjt.mail.exceptions.AccountAlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OutlookIllegalArgumentTest {

    private Outlook outlook = new Outlook();


    @Test
    public void testAddNewAccountIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addNewAccount(null, "johndoe@gmail.com"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addNewAccount("", "johndoe@gmail.com"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addNewAccount(" ", "johndoe@gmail.com"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addNewAccount("johnDoe", null));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addNewAccount("johnDoe", ""));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addNewAccount("johnDoe", " "));
    }

    @Test
    public void testCreateFolderIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                outlook.createFolder(null, "/inbox/hobbies"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.createFolder("", "/inbox/hobbies"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.createFolder(" ", "/inbox/hobbies"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.createFolder("johnDoe", null));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.createFolder("johnDoe", ""));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.createFolder("johnDoe", " "));
    }

    @Test
    public void testAddRuleIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addRule(null, "/inbox/hobbies", "from: gosho@gmail.com", 2));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addRule("", "/inbox/hobbies", "from: gosho@gmail.com", 2));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addRule(" ", "/inbox/hobbies", "from: gosho@gmail.com", 2));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addRule("johnDoe", null, "from: gosho@gmail.com", 2));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addRule("johnDoe", "", "from: gosho@gmail.com", 2));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addRule("johnDoe", " ", "from: gosho@gmail.com", 2));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addRule("johnDoe", "/inbox/hobbies", null, 2));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addRule("johnDoe", "/inbox/hobbies", "", 2));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.addRule("johnDoe", "/inbox/hobbies", " ", 2));
    }

    @Test
    public void testReceiveMailIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail(null, "/inbox/hobbies", "text"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail("", "/inbox/hobbies", "text"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail(" ", "/inbox/hobbies", "text"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail("johnDoe", null, "text"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail("johnDoe", "", "text"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail("johnDoe", " ", "text"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail("johnDoe", "/inbox/hobbies", null));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail("johnDoe", "/inbox/hobbies", ""));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail("johnDoe", "/inbox/hobbies", " "));
    }

    @Test
    public void testSentMailIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail(null, "/inbox/hobbies", "text"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail("", "/inbox/hobbies", "text"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail(" ", "/inbox/hobbies", "text"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail("johnDoe", null, "text"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail("johnDoe", "", "text"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail("johnDoe", " ", "text"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail("johnDoe", "/inbox/hobbies", null));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail("johnDoe", "/inbox/hobbies", ""));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.receiveMail("johnDoe", "/inbox/hobbies", " "));
    }

    @Test
    public void testGetMailsFromFolderIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                outlook.createFolder(null, "/inbox/hobbies"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.createFolder("", "/inbox/hobbies"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.createFolder(" ", "/inbox/hobbies"));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.createFolder("johnDoe", null));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.createFolder("johnDoe", ""));
        assertThrows(IllegalArgumentException.class, () ->
                outlook.createFolder("johnDoe", " "));
    }

}
