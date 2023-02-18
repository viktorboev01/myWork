package bg.sofia.uni.fmi.mjt.mail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutlookTest {

    private Outlook outlook = new Outlook();

    @Test
    public void testAddNewAccount() {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        outlook.addNewAccount("georgiGeorgiev", "ggeorgiev@gmail.com");
        assertEquals(2, outlook.accounts.size());
        assertEquals(2, outlook.emails.size());
        assertEquals(2, outlook.paths.size());
        assertEquals(4, outlook.mails.size());
    }

    @Test
    public void testCreateFolder() {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        outlook.addNewAccount("georgiGeorgiev", "ggeorgiev@gmail.com");
        outlook.createFolder("johnDoe", "/inbox/hobbies");
        assertEquals(5, outlook.mails.size());
    }

    @Test
    public void testAddFullRule() {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        outlook.createFolder("johnDoe", "/inbox/hobbies");
        outlook.addRule("johnDoe", "/inbox/hobbies", "subject-includes: juice"
                + System.lineSeparator() + "subject-or-body-includes: orange, apple"
                + System.lineSeparator() + "from: ggeorgiev@gmail.com", 1);
        assertEquals(1, outlook.rules.get("johnDoe").size());
    }

    @Test
    public void testAddNotFullRule() {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        outlook.createFolder("johnDoe", "/inbox/hobbies");
        outlook.addRule("johnDoe", "/inbox/hobbies", "subject-includes: juice", 1);
        assertEquals(1, outlook.rules.get("johnDoe").size());
    }

    @Test
    public void testReceiveMailWithoutRules() {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        outlook.addNewAccount("georgiGeorgiev", "ggeorgiev@gmail.com");
        outlook.receiveMail("johnDoe", "sender: ggeorgiev@gmail.com"
                + System.lineSeparator() + "subject: Buying juice for 8th december"
                + System.lineSeparator() + "recipients: johndoe@gmail.com"
                + System.lineSeparator() + "received: 2022-12-11 12:12", "I prefer orange than apple");
        assertEquals(1, outlook.getMailsFromFolder("johnDoe", "/inbox").size());
    }

    @Test
    public void testReceiveMailWithFullRule() {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        outlook.addNewAccount("georgiGeorgiev", "ggeorgiev@gmail.com");
        outlook.addNewAccount("petarPetrov", "petar01@gmail.com");
        outlook.createFolder("johnDoe", "/inbox/hobbies");
        outlook.addRule("johnDoe", "/inbox/hobbies", "subject-includes: juice"
                + System.lineSeparator() + "subject-or-body-includes: orange, apple"
                + System.lineSeparator() + "from: ggeorgiev@gmail.com"
                + System.lineSeparator() + "recipients-includes: johndoe@gmail.com, petar01@gmail.com", 1);
        outlook.receiveMail("johnDoe", "sender: ggeorgiev@gmail.com"
                + System.lineSeparator() + "subject: Buying juice for 8th december"
                + System.lineSeparator() + "recipients: johndoe@gmail.com, petar01@gmail.com"
                + System.lineSeparator() + "received: 2022-12-11 12:12", "I prefer orange than apple");
        assertEquals(1, outlook.getMailsFromFolder("johnDoe", "/inbox/hobbies").size());
        assertEquals(0, outlook.getMailsFromFolder("johnDoe", "/inbox").size());
    }

    @Test
    public void testReceiveMailWithNotFullRule() {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        outlook.addNewAccount("georgiGeorgiev", "ggeorgiev@gmail.com");
        outlook.createFolder("johnDoe", "/inbox/hobbies");
        outlook.addRule("johnDoe", "/inbox/hobbies", "subject-includes: juice", 1);
        outlook.receiveMail("johnDoe", "sender: ggeorgiev@gmail.com"
                + System.lineSeparator() + "subject: Buying juice for 8th december"
                + System.lineSeparator() + "recipients: johndoe@gmail.com"
                + System.lineSeparator() + "received: 2022-12-11 12:12", "I prefer orange than apple");
        assertEquals(1, outlook.getMailsFromFolder("johnDoe", "/inbox/hobbies").size());
        assertEquals(0, outlook.getMailsFromFolder("johnDoe", "/inbox").size());
    }

    @Test
    public void testReceiveMailWithTwoRulesSelectOneByPriority() {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        outlook.addNewAccount("georgiGeorgiev", "ggeorgiev@gmail.com");
        outlook.createFolder("johnDoe", "/inbox/hobbies");
        outlook.createFolder("johnDoe", "/inbox/party");
        outlook.addRule("johnDoe", "/inbox/hobbies", "subject-includes: juice"
                + System.lineSeparator() + "subject-or-body-includes: orange, apple"
                + System.lineSeparator() + "from: ggeorgiev@gmail.com", 2);
        outlook.addRule("johnDoe", "/inbox/party", "subject-includes: juice"
                + System.lineSeparator() + "subject-or-body-includes: orange, apple"
                + System.lineSeparator() + "from: ggeorgiev@gmail.com", 1);
        outlook.receiveMail("johnDoe", "sender: ggeorgiev@gmail.com"
                + System.lineSeparator() + "subject: Buying juice for 8th december"
                + System.lineSeparator() + "recipients: johndoe@gmail.com"
                + System.lineSeparator() + "received: 2022-12-11 12:12", "I prefer orange than apple");
        assertEquals(1, outlook.getMailsFromFolder("johnDoe", "/inbox/party").size());
        assertEquals(0, outlook.getMailsFromFolder("johnDoe", "/inbox/hobbies").size());
        assertEquals(0, outlook.getMailsFromFolder("johnDoe", "/inbox").size());
    }

    @Test
    public void testSendMail() {
        outlook.addNewAccount("johnDoe", "johndoe@gmail.com");
        outlook.addNewAccount("georgiGeorgiev", "ggeorgiev@gmail.com");
        outlook.createFolder("johnDoe", "/inbox/hobbies");
        outlook.createFolder("johnDoe", "/inbox/party");
        outlook.addRule("johnDoe", "/inbox/party","from: ggeorgiev@gmail.com"
                + System.lineSeparator() + "subject-includes: juice"
                + System.lineSeparator() + "subject-or-body-includes: orange, apple"
                + System.lineSeparator() + "recipients-includes: johndoe@gmail.com, petar01@gmail.com", 2);
        outlook.addRule("johnDoe", "/inbox/hobbies", "subject-includes: juice"
                + System.lineSeparator() + "subject-or-body-includes: orange, apple"
                + System.lineSeparator() + "from: ggeorgiev@gmail.com"
                + System.lineSeparator() + "recipients-includes: johndoe@gmail.com, ", 1);
        outlook.sendMail("georgiGeorgiev", "sender: johndoe@gmail.com"
                + System.lineSeparator() + "subject: Buying juice for 8th december"
                + System.lineSeparator() + "recipients: johndoe@gmail.com"
                + System.lineSeparator() + "received: 2022-12-11 12:12", "I prefer orange than apple");
        assertEquals(1, outlook.getMailsFromFolder("georgiGeorgiev","/sent").size());
        assertEquals(1, outlook.getMailsFromFolder("johnDoe","/inbox/hobbies").size());
    }
}
