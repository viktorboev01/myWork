package bg.sofia.uni.fmi.mjt.mail;

import java.util.Set;

public record Rule(Set<String> subjectKeywords, Set<String> subjectBodyKeywords, String folderPath,
                   Set<String> recipients, String sender, int priority) {

    public Rule{
        if (priority < 1 || priority > 10) {
            throw new IllegalArgumentException("Priority is not between 1 and 10");
        }
    }

    public boolean equal(Rule rhs) {
        return this.subjectKeywords.equals(rhs.subjectKeywords)
                && this.subjectBodyKeywords.equals(rhs.subjectBodyKeywords)
                && this.priority == rhs.priority && this.recipients.equals(rhs.recipients);
    }
}
