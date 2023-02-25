package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String treeString = "(A(B((C(D)))";
        final Pattern pattern = Pattern.compile("(\\([A-Za-z0-9]+\\([A-Za-z0-9]+\\))+\\(.*", Pattern.CASE_INSENSITIVE);
        boolean leftDone = false;
        final Matcher matcher = pattern.matcher(treeString);
        if (matcher.find()) {
            leftDone = true;
        }
        System.out.println(leftDone);
    }
}