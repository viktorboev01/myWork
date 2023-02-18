package bg.sofia.uni.fmi.mjt.markdown;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkdownConverterTest {

    private MarkdownConverter mConverter = new MarkdownConverter();

    private static final String[] HTML_STRINGS = {"<html>", "<body>",
            "<code>.close()</code> <em>your</em> <strong>eyes</strong>", "</body>", "</html>"};

    @Test
    public void testConvertBoldItalicToHtml() {
        assertEquals("<em>your</em> <strong>eyes</strong>", mConverter.convertBoldItalicToHtml("*your* **eyes**"));
    }

    @Test
    public void testConvertHeadersToHtml() {
        assertEquals("<h3>Heading level 3</h3>", mConverter.convertHeadersToHtml("### Heading level 3"));
        assertEquals("<h5>Heading level 5</h5>", mConverter.convertHeadersToHtml("##### Heading level 5"));
    }

    @Test
    public void testConvertLineToHtml() {
        assertEquals("<h5><code>.close()</code> <em>your</em> <strong>eyes</strong></h5>",
                mConverter.convertLineToHtml("##### `.close()` *your* **eyes**"));
    }

    @Test
    public void testConvertMarkdownPathToPath() {
        try {
            Path path1 = File.createTempFile("file1", ".md").toPath();
            Path path2 = File.createTempFile("file2", ".html").toPath();

            Writer fileWriter = Files.newBufferedWriter(path1);
            fileWriter.write("`.close()` *your* **eyes**");
            fileWriter.close();

            mConverter.convertMarkdown(path1, path2);
            Stream<String> lines = Files.lines(path2);
            String[] list = lines.toArray(size -> new String[size]);

            for (int i = 0; i < list.length; i++) {
                assertEquals(HTML_STRINGS[i], list[i]);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testConvertMarkdownReaderWriter() {

        try {
            File file1 = File.createTempFile("file1", ".md");
            File file2 = File.createTempFile("file2", ".html");

            Writer writerFromString = new FileWriter(file1);
            writerFromString.write("`.close()` *your* **eyes**");
            writerFromString.flush();

            Reader readerFirst = new FileReader(file1);
            Writer writerFromReader = new FileWriter(file2);
            mConverter.convertMarkdown(readerFirst, writerFromReader);

            Reader readerSecond = new FileReader(file2);
            BufferedReader bf = new BufferedReader(readerSecond);

            for (int i = 0; i < 5; i++) {
                assertEquals(HTML_STRINGS[i], bf.readLine());
            }

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void testConvertAllMarkdownFiles() {

        try {
            Path sourceDir = Files.createTempDirectory("TempDirectorySource");
            Path file1 = Files.createTempFile(sourceDir, "first", ".md");
            Path file2 = Files.createTempFile(sourceDir, "second", ".md");
            Path outputDir = Files.createTempDirectory("TempDirectoryOutput");

            mConverter.convertAllMarkdownFiles(sourceDir, outputDir);
            String[] fileNames = outputDir.toFile().list();
            int total = 0;

            for (int i = 0; i < fileNames.length; i++) {
                if (fileNames[i].contains(".html")) {
                    total++;
                }
            }
            assertEquals(2, total);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
