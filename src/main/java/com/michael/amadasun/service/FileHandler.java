package com.michael.amadasun.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandler {
    private static final Logger LOGGER = Logger.getLogger(FileHandler.class.getName());
    public static boolean previousLineIsParagraph = false;

    public static void deleteFile(String path) {
        try {
            Files.delete(Paths.get(path));
            LOGGER.log(Level.INFO, "Deleted previous output file successfully");
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Unable to delete file:\n" + e.getMessage());
        }
    }

    public static void processInput(String input, String output) {
        try {
            File file = new File(output);

            if (file.createNewFile()) {
                // In the case of an empty input file do I return null
                List<String> allLines = Files.readAllLines(Paths.get(input));
                for (String line : allLines) {
                    process(line, output);
                }
                if(previousLineIsParagraph)
                    process("", output);
            }

        } catch (IOException e) {
            LOGGER.severe("Unable to read from markdown file:\n");
            e.printStackTrace();
        }

    }

    private static void process(String line, String output) throws IOException {
        String htmlLine = convertMarkdownToHTML(line.trim());
        Files.write(Paths.get(output), htmlLine.getBytes(), StandardOpenOption.APPEND);
    }

    private static String convertMarkdownToHTML(String line) {
        String result = null;
        if(line.startsWith("#")) {
            // Count number of # at start and pass to HTML Parser for Header tags
            result = HTMLParser.convertToHeader(line, previousLineIsParagraph);
            previousLineIsParagraph = false;
        } else if (!line.isEmpty()){
            // Pass to HTML parser for Paragraph
            result = HTMLParser.convertToParagraph(line, previousLineIsParagraph);
            previousLineIsParagraph = true;
        } else if (line.isEmpty() && previousLineIsParagraph) {
            // Close out paragraph tag
            result = "</p>\n";
            previousLineIsParagraph = false;
        } else {
            result = line;
        }

        return result;
    }
}
