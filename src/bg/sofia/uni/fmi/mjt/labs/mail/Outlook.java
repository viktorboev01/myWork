package bg.sofia.uni.fmi.mjt.mail;

import bg.sofia.uni.fmi.mjt.mail.exceptions.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Outlook implements MailClient {

    // name + email address
    Map<String, String> accounts;

    // name + collection of paths
    Map<String, Set<String>> paths;

    // path + collection of mails
    Map<String, List<Mail>> mails;

    // name + collection of rules
    Map<String, List<Rule>> rules;

    // email address + name
    Map<String, String> emails;

    public Outlook() {
        accounts = new HashMap<>();
        emails = new HashMap<>();
        mails = new HashMap<>();
        paths = new HashMap<>();
        rules = new HashMap<>();
    }

    private void checkString(String str) {
        if (str == null || str.isEmpty() || str.isBlank()) {
            throw new IllegalArgumentException("Not valid string");
        }
    }

    private void checkAccountName(String accountName) {
        if (!accounts.containsKey(accountName)) {
            throw new AccountNotFoundException("Account: " + accountName + " is not found");
        }
    }

    private LocalDateTime StringToDate(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        boolean startToWrite = false;
        String date = "";

        for (int i = 0; i < str.length(); i++) {
            if (startToWrite) {
                date += str.charAt(i);
            }
            if (str.charAt(i) == ' ') {
                startToWrite = true;
            }
        }

        return LocalDateTime.parse(date, formatter);
    }

    private void checkFolderExistence(String folderPath) {
        checkString(folderPath);
        if (!mails.containsKey(folderPath)) {
            throw new FolderNotFoundException("");
        }
    }

    private boolean isKeywordsMatches(Mail mail, Rule rule) {
        if (!rule.sender().isEmpty() && !mail.sender().emailAddress().equals(rule.sender())) {
            return false;
        }

        if (rule.subjectKeywords().size() != 0) {
            for (String keyword : rule.subjectKeywords()) {
                if (!mail.subject().contains(keyword) &&
                        rule.subjectKeywords().contains(keyword)) {
                    return false;
                }
            }
        }

        if (rule.subjectBodyKeywords().size() != 0) {
            for (String keyword : rule.subjectBodyKeywords()) {
                if (!mail.body().contains(keyword)) {
                    return false;
                }
            }
        }

        if (rule.recipients().size() != 0 && !mail.recipients().equals(rule.recipients())) {
            return false;
        }
        return true;
    }

    private void validatePathForCreateFolder(String accountName, String path) {
        if (!path.startsWith(accountName + "/inbox") && !path.equals(accountName + "/sent")) {
            throw new InvalidPathException("Root folder for received mails missed in: " + path);
        }

        String tempPath = accountName + "/inbox";

        for (int i = tempPath.length(); i < path.length(); i++) {
            if (path.charAt(i) == '/' && !mails.containsKey(tempPath)) {
                throw new InvalidPathException("");
            } else {
                tempPath += path.charAt(i);
            }
        }

        if (mails.containsKey(path)) {
            throw new FolderAlreadyExistsException("");
        }
    }

    private void addMail(String accountName, Mail mail, Rule rule) {
        if (rule == null) {
            mails.get(accountName + "/inbox").add(mail);
        } else {
            mails.get(rule.folderPath()).add(mail);
        }
    }

    private Mail getMailObject(String mailMetadata, String mailContent) {

        String senderName = "";
        Set<String> recipients = new HashSet<>();
        String subject = "";
        LocalDateTime dateTime = LocalDateTime.now();

        String[] metadataLines = mailMetadata.split(System.lineSeparator());

        for (String line : metadataLines) {

            if (line.startsWith("sender:")) {
                senderName = line.split(":")[1].strip();

            } else if (line.startsWith("subject:")) {
                subject = line.split(":")[1].strip();

            } else if (line.startsWith("recipients:")) {
                String[] stringRecipients = line.split(":")[1].split(",");
                for (int i = 0; i < stringRecipients.length; i++) {
                    stringRecipients[i] = stringRecipients[i].strip();
                }
                recipients = new HashSet<>(List.of(stringRecipients));

            } else if (line.startsWith("received:")) {
                dateTime = StringToDate(line);
            }
        }
        return new Mail(new Account(senderName, accounts.get(senderName)), recipients, subject, mailContent, dateTime);
    }

    private Rule getRule(String accountName, Mail mail) {

        int currentPriority = 11;
        Rule selectedRule = null;

        if (rules.get(accountName) == null) {
            return null;
        }

        for (Rule rule : rules.get(accountName)) {
            if (rule.priority() < currentPriority && isKeywordsMatches(mail, rule)) {
                currentPriority = rule.priority();
                selectedRule = rule;
            }
        }

        return selectedRule;
    }

    private Rule makeRuleFromString(String ruleDefinition, int priority, String folderPath) {

        Set<String> subjectKeywords = new HashSet<>();
        Set<String> subjectBodyKeywords = new HashSet<>();
        Set<String> recipients = new HashSet<>();
        String sender = "";

        String[] metadataLines = ruleDefinition.split(System.lineSeparator());

        for (String line : metadataLines) {
            line = line.replaceAll("\\s", "");
            String[] headAndInfo = line.split(":");
            Set<String> items = Set.of(headAndInfo[1].split(","));
            if (headAndInfo[0].equals("subject-includes")) {
                subjectKeywords = items;
            } else if (headAndInfo[0].equals("subject-or-body-includes")) {
                subjectBodyKeywords = items;
            } else if (headAndInfo[0].equals("recipients-includes")) {
                recipients = items;
            } else if (headAndInfo[0].equals("from")) {
                sender = items.iterator().next();
            }
        }
        return new Rule(subjectKeywords, subjectBodyKeywords, folderPath, recipients, sender, priority);
    }

    @Override
    public Account addNewAccount(String accountName, String email) {
        checkString(accountName);
        checkString(email);
        Account accountToAdd = new Account(email, accountName);
        if (accounts.containsKey(accountName) || emails.containsKey(email)) {
            throw new AccountAlreadyExistsException("The account already exists in the system");
        }

        accounts.put(accountName, email);
        emails.put(email, accountName);

        createFolder(accountName, "/inbox");
        createFolder(accountName, "/sent");

        return accountToAdd;
    }

    @Override
    public void createFolder(String accountName, String path) {

        checkString(accountName);
        checkString(path);
        checkAccountName(accountName);

        if (!accounts.containsKey(accountName)) {
            throw new AccountNotFoundException("The account already exists in the system");
        }

        path = accountName + path;
        validatePathForCreateFolder(accountName, path);

        mails.put(path, new ArrayList<>());

        if (!paths.containsKey(accountName)) {
            paths.put(accountName, new HashSet<>());
        }
        paths.get(accountName).add(path);
    }

    @Override
    public void addRule(String accountName, String folderPath, String ruleDefinition, int priority) {

        checkString(accountName);
        checkString(folderPath);
        checkString(ruleDefinition);
        checkAccountName(accountName);

        folderPath = accountName + folderPath;
        checkFolderExistence(folderPath);

        Rule rule = makeRuleFromString(ruleDefinition, priority, folderPath);
        if (rules.get(accountName) == null) {
            List<Rule> list = new ArrayList<>();
            list.add(rule);
            rules.put(accountName, list);
        } else {
            for (Rule toCompare : rules.get(accountName)) {
                if (rule.equal(toCompare)) {
                    throw new RuleAlreadyDefinedException("Rule: " + System.lineSeparator()
                            + ruleDefinition + System.lineSeparator() + "is already defined");
                }
            }
            rules.get(accountName).add(rule);
        }
    }

    @Override
    public void receiveMail(String accountName, String mailMetadata, String mailContent) {

        checkString(accountName);
        checkString(mailMetadata);
        checkString(mailContent);
        checkAccountName(accountName);

        Rule wantedRule;
        Mail mail = getMailObject(mailMetadata, mailContent);

        if (emails.containsKey(accounts.get(accountName))) {
            wantedRule = getRule(accountName, mail);
            addMail(accountName, mail, wantedRule);
        } else {
            throw new AccountNotFoundException("");
        }
    }

    @Override
    public Collection<Mail> getMailsFromFolder(String account, String folderPath) {

        checkString(account);
        checkString(folderPath);
        checkAccountName(account);

        folderPath = account + folderPath;
        checkFolderExistence(folderPath);

        return mails.get(folderPath);
    }

    @Override
    public void sendMail(String accountName, String mailMetadata, String mailContent) {
        checkString(accountName);
        checkString(mailMetadata);
        checkString(mailContent);
        checkAccountName(accountName);

        String[] emailsMetadata = null;
        boolean isSender = false;
        String[] metadataLines = mailMetadata.split(System.lineSeparator());
        mailMetadata = "";

        for (int i = 0; i < metadataLines.length; i++) {
            if (metadataLines[i].startsWith("sender: ")) {
                isSender = true;
                metadataLines[i] = "sender: " + accounts.get(accountName);
            }
            if (metadataLines[i].startsWith("recipients: ")) {
                emailsMetadata = metadataLines[i].split(":")[1].split(",");
            }
            mailMetadata += metadataLines[i] + System.lineSeparator();
        }

        mailMetadata.strip();
        if (!isSender) {
            mailMetadata += System.lineSeparator() + "sender: " + accounts.get(accountName);
        }
        if (emailsMetadata == null) {
            return;
        }

        Mail mail = getMailObject(mailMetadata, mailContent);
        mails.get(accountName + "/sent").add(mail);
        for (String email : emailsMetadata) {
            email = email.strip();
            if (emails.containsKey(email)) {
                receiveMail(emails.get(email), mailMetadata, mailContent);
            }
        }
    }
}
