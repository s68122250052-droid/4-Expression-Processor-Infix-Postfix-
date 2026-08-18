package group4.utils;


import java.util.ArrayList;
import java.util.List;


public class Tokenizer {
    public static List tokenize(String expr) {
        List tokens = new ArrayList<>();
        StringBuilder numberBuffer = new StringBuilder();


        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (Character.isWhitespace(c)) {
                if (numberBuffer.length() > 0) {
                    tokens.add(numberBuffer.toString());
                    numberBuffer.setLength(0);
                }
            } else if (Character.isDigit(c)) {
                numberBuffer.append(c);
            } else if (c == '(' || c == ')' || isOperatorChar(c)) {
                if (numberBuffer.length() > 0) {
                    tokens.add(numberBuffer.toString());
                    numberBuffer.setLength(0);
                }
                tokens.add(String.valueOf(c));
            } else {
                throw new IllegalArgumentException("Invalid character found in expression: " + c);
            }
        }
        if (numberBuffer.length() > 0) {
            tokens.add(numberBuffer.toString());
        }
        return tokens;
    }


    public static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
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
