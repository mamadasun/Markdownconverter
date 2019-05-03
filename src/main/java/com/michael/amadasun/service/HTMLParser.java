package com.michael.amadasun.service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLParser {

    public static String convertToHeader(String line, boolean isPreviousLineParagraph) {
        line = convertToAnchor(line);

        int headerLevel = 1;
        for (int i = headerLevel; i < 6; i++) {
            if (line.charAt(i) == '#') {
                headerLevel++;
            } else break;
        }
        line = line.substring(headerLevel).trim();

        String startTag = "<h" + headerLevel + ">";
        String endTag = "</h" + headerLevel + ">\n";

        if (isPreviousLineParagraph)
            return "</p>\n" + startTag + line + endTag;

        return startTag + line + endTag;
    }

    public static String convertToParagraph(String line, boolean isPreviousLineParagraph) {
        line = convertToAnchor(line);

        if (isPreviousLineParagraph) {
            return "\n" + line;
        }

        return "<p>" + line;
    }

    private static String convertToAnchor(String line) {
        Set<String> urls = getUrls(line);
        if (!urls.isEmpty()) {
            for (String url : urls) {
                String anchorTag = getAnchorTag(url);
                line = line.replace(url, anchorTag);
            }
        }

        return line;
    }

    private static Set<String> getUrls(String line) {
        Set<String> allMatches = new HashSet<>();

        Matcher m = Pattern.compile("\\[[^\\]]+\\]\\([^)]+\\)")
                .matcher(line);
        while (m.find()) {
            allMatches.add(m.group());
        }

        return allMatches;
    }

    private static String getAnchorTag(String url) {
        String text = url.substring(url.indexOf('[') + 1, url.indexOf(']'));
        String hyperlink = url.substring(url.indexOf('(') + 1, url.indexOf(')'));

        return "<a href=\"" + hyperlink + "\">" + text + "</a>";
    }

}
