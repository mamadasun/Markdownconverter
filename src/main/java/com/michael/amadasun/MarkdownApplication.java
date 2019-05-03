package com.michael.amadasun;

import com.michael.amadasun.service.FileHandler;
import java.util.Scanner;

public class MarkdownApplication {
    private static final String OUTPUT_FILE = System.getProperty("user.dir") + "/src/main/resources/output.html";

    public static void main(String[] args){
        FileHandler.deleteFile(OUTPUT_FILE);

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the path to your markdown file:");
        String input = sc.nextLine();

        FileHandler.processInput(input, OUTPUT_FILE);
        System.out.println("Completed converting markdown to HTML.");
    }

}
