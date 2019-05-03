import com.michael.amadasun.service.FileHandler;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class MarkdownTest {
    private static final String RESOURCE_DIR = System.getProperty("user.dir") + "/src/test/resources/";

    @Test
    public void sample0() {
        validate("sample0");
    }

    @Test
    public void sample1() {
        validate("sample1");
    }

    @Test
    public void sample2() {
        validate("sample2");
    }

    private void validate(String directory) {
        try {
            String input = RESOURCE_DIR + directory + "/" + directory;
            String output = RESOURCE_DIR + directory + "/output.html";

            FileHandler.deleteFile(output);
            FileHandler.processInput(input + ".md", output);

            File actual = new File(output);
            File expected = new File(input + ".html");

            assertTrue("The files differ!", FileUtils.contentEquals(actual, expected));
        } catch (IOException e) {
            fail("Error processing file comparison: " + e.getMessage());
        }
    }
}
