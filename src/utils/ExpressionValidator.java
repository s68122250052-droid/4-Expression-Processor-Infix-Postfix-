package group4.utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class ExpressionValidator {
    public static void validate(String expr) {
        if (expr == null || expr.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression is empty.");
        }

        List<String> tokens = Tokenizer.tokenize(expr);
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expression contains no valid tokens.");
        }

        Deque<Character> stack = new ArrayDeque<>();
        for (char c : expr.toCharArray()) {
            if (c == '(') stack.push(c);
            else if (c == ')') {
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Unbalanced parentheses: Extra ')' found.");
                }
                stack.pop();
            }
        }
        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("Unbalanced parentheses: Missing ')' at end.");
        }

        boolean expectOperand = true;
        for (String token : tokens) {
            if (Tokenizer.isOperator(token)) {
                if (expectOperand) {
                    throw new IllegalArgumentException("Invalid operator sequence near '" + token + "'");
                }
                expectOperand = true;
            } else if (token.equals("(")) {
                if (!expectOperand) {
                    throw new IllegalArgumentException("Missing operator before '('");
                }
                expectOperand = true;
            } else if (token.equals(")")) {
                if (expectOperand) {
                    throw new IllegalArgumentException("Invalid ')' placement");
                }
                expectOperand = false;
            } else {
                if (!expectOperand) {
                    throw new IllegalArgumentException("Missing operator between operands");
                }
                expectOperand = false;
            }
        }

        if (expectOperand) {
            throw new IllegalArgumentException("Expression ends with an operator or empty parentheses.");
        }
    }
}
