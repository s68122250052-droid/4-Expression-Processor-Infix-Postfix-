package group4.utils;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    public static List<String> tokenize(String expr) {
        List<String> tokens = new ArrayList<>();
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            if (Character.isWhitespace(c)) {
                if (number.length() > 0) {
                    tokens.add(number.toString());
                    number.setLength(0);
                }
            } else if (Character.isDigit(c)) {
                number.append(c);
            } else if (c == '(' || c == ')' || isOperatorChar(c)) {
                if (number.length() > 0) {
                    tokens.add(number.toString());
                    number.setLength(0);
                }
                tokens.add(String.valueOf(c));
            } else {
                throw new IllegalArgumentException(
                    "Invalid character found in expression: " + c
                );
            }
        }

        if (number.length() > 0) tokens.add(number.toString());
        return tokens;
    }

    public static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-")
            || token.equals("*") || token.equals("/");
    }

    private static boolean isOperatorChar(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static int precedence(String op) {
        switch (op) {
            case "+": case "-": return 1;
            case "*": case "/": return 2;
            default: return 0;
        }
    }
}
