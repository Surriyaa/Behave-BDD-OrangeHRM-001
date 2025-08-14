package com.bridgelabz.pages;

import java.util.Arrays;
import java.util.StringTokenizer;
public class Strings {
    public static void main(String[] args) {
        System.out.println("=== ADVANCED STRING OPERATIONS ===");
        String data = "John,25,Engineer,New York;Jane,30,Doctor,Boston;Bob,28,Teacher,Chicago";
// Complex splitting and processing
        String[] records = data.split(";");
        System.out.println("Number of records: " + records.length);
        for (String record : records) {
            String[] fields = record.split(",");
            String name = fields[0].trim();
            int age = Integer.parseInt(fields[1].trim());
            String profession = fields[2].trim();
            String city = fields[3].trim();
            System.out.println("Name: " + name + ", Age: " + age +
                    ", Profession: " + profession + ", City: " +

                    city);
        }
        System.out.println("\n=== STRING TOKENIZER DEMO ===");
        String sentence = "Java,Python;JavaScript:C++|Ruby";
        StringTokenizer tokenizer = new StringTokenizer(sentence, ",;:|");
        System.out.println("Tokens found:");
        while (tokenizer.hasMoreTokens()) {
            System.out.println("- " + tokenizer.nextToken());
        }
        System.out.println("\n=== STRING FORMATTING ===");
        String name = "Alice";
        int score = 95;
        double percentage = 94.56789;
// Different formatting approaches
        String formatted1 = String.format("Student: %s, Score: %d, Percentage: %.2f%%",

        name, score, percentage);
        System.out.println("String.format(): " + formatted1);
// StringBuilder with formatting
        StringBuilder formatted2 = new StringBuilder();
        formatted2.append("Student: ").append(name)
                .append(", Score: ").append(score)
                .append(", Percentage: ").append(String.format("%.2f",

                        percentage))

                .append("%");

        System.out.println("StringBuilder: " + formatted2);
        System.out.println("\n=== PRACTICAL EXAMPLE: EMAIL VALIDATOR ===");
        String[] emails = {"user@example.com", "invalid.email",
                "test@domain.co.uk", "@invalid.com"};
        for (String email : emails) {
            boolean isValid = validateEmail(email);
            System.out.println(email + " -> " + (isValid ? "VALID" :

                    "INVALID"));
        }
    }
    public static boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
// Basic validation
        return email.contains("@") &&email.indexOf("@") > 0 &&
                email.lastIndexOf("@") == email.indexOf("@") &&
                email.indexOf("@") < email.length() - 1 &&
                email.contains(".") &&
                email.lastIndexOf(".") > email.indexOf("@");

    }
}