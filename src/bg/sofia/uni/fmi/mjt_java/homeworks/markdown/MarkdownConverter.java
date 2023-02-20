package bg.sofia.uni.fmi.mjt.markdown;

import java.io.*;
import java.nio.file.*;

public class MarkdownConverter implements MarkdownConverterAPI {

    protected static final String HTML_OPEN_TAG = "<html>" + System.lineSeparator();
    protected static final String HTML_CLOSE_TAG = "</html>" + System.lineSeparator();
    protected static final String BODY_OPEN_TAG = "<body>" + System.lineSeparator();
    protected static final String BODY_CLOSE_TAG = "</body>" + System.lineSeparator();

    protected String convertHeadersToHtml(String line) {

        String res = "";
        if (line.length() == 0) {
            return res;
        }
        String[] headers = {"h1", "h2", "h3", "h4", "h5", "h6"};
        int counter = 0;
        while (line.charAt(counter) == '#') {
            counter++;
        }
        if (counter != 0) {
            res += "<" + headers[counter - 1] + ">";
            res += line.substring(counter + 1);
            res += "</" + headers[counter - 1] + ">";
        } else {
            return line;
        }
        return res;
    }

    protected String convertBoldItalicToHtml(String line) {

        String res = "";
        boolean prevStar = false;
        boolean firstItalicSign = false;
        boolean firstBoldSign = false;

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '*') {
                if (prevStar) {
                    if (firstBoldSign) {
                        firstBoldSign = false;
                        res += "</strong>";
                    } else {
                        firstBoldSign = true;
                        res += "<strong>";
                    }
                    prevStar = false;
                } else if (firstItalicSign) {
                    firstItalicSign = false;
                    res += "</em>";
                } else {
                    prevStar = true;
                }
            } else if (prevStar) {
                firstItalicSign = true;
                res += "<em>";
                prevStar = false;
                res += line.charAt(i);
            } else {
                res += line.charAt(i);
            }
        }
        return res;
    }

    protected String convertFragmentCodeToHtml(String line) {
        String res = "";
        boolean firstSign = false;
        for (int i = 0; i < line.length(); i++) {

            if (line.charAt(i) == '`') {
                if (firstSign) {
                    firstSign = false;
                    res += "</code>";
                } else {
                    firstSign = true;
                    res += "<code>";
                }
            } else {
                res += line.charAt(i);
            }
        }
        return res;
    }

    protected String convertLineToHtml(String line) {
        line = convertHeadersToHtml(line);
        line = convertBoldItalicToHtml(line);
        line = convertFragmentCodeToHtml(line);
        return line;
    }

    public MarkdownConverter() {
    }

    public void convertMarkdown(Reader source, Writer output) {
        if (source.toString() == output.toString()) {
            throw new IllegalStateException("Read and write from the same place in convertMarkdown");
        }
        try (BufferedReader reader = new BufferedReader(source)) {

            output.write(HTML_OPEN_TAG);
            output.write(BODY_OPEN_TAG);

            String line;
            while ((line = reader.readLine()) != null) {
                output.write(convertLineToHtml(line));
                output.write(System.lineSeparator());
            }
            output.write(BODY_CLOSE_TAG);
            output.write(HTML_CLOSE_TAG);
            output.flush();

        } catch (Exception e) {
            throw new IllegalStateException("A problem occurred in convertMarkdown", e);
        }
    }

    public void convertMarkdown(Path from, Path to) {
        try (InputStream inputStream = Files.newInputStream(from)) {

            Writer fileWriter = Files.newBufferedWriter(to);
            String text = "";
            int c;

            while ((c = inputStream.read()) != -1) {
                text += (char) c;
            }

            fileWriter.write(HTML_OPEN_TAG);
            fileWriter.write(BODY_OPEN_TAG);

            String[] lines = text.split(System.getProperty("line.separator"));
            for (String line : lines) {
                fileWriter.write(convertLineToHtml(line));
                fileWriter.write(System.lineSeparator());
            }

            fileWriter.write(BODY_CLOSE_TAG);
            fileWriter.write(HTML_CLOSE_TAG);
            fileWriter.flush();

        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred in convertMarkdown", e);
        }
    }

    public void convertAllMarkdownFiles(Path sourceDir, Path targetDir) {

        try (var paths = Files.newDirectoryStream(sourceDir)) {

            for (Path p : paths) {
                File file = new File(targetDir.toString() + FileSystems.getDefault().getSeparator()
                        + p.getFileName().toString().replace(".md", ".html"));
                file.createNewFile();
                convertMarkdown(p, file.toPath());
            }
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred in convertAllMarkdownFiles", e);
        }
    }

}
