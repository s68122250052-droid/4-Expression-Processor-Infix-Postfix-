package group4.utils;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;


public class ExpressionValidator {
    public static void validate(String expr) throws IllegalArgumentException {
        if (expr == null || expr.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression is empty.");
        }


        List tokens = Tokenizer.tokenize(expr);
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expression contains no valid tokens.");
        }


        // 1. Check Parentheses Balance
        Deque stack = new ArrayDeque<>();
        for (char c : expr.toCharArray()) {
            if (c == '(') stack.push(c);
            else if (c == ')') {
                if (stack.isEmpty()) throw new IllegalArgumentException("Unbalanced parentheses: Extra ')' found.");
                stack.pop();
            }
        }
        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("Unbalanced parentheses: Missing ')' at end.");
        }


        // 2. Check Operator Sequence
        boolean lastWasOperator = true; // Expect operand or '(' at start
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (Tokenizer.isOperator(token)) {
                if (lastWasOperator) {
                    throw new IllegalArgumentException("Invalid operator sequence near '" + token + "'");
                }
                lastWasOperator = true;
            } else if (!token.equals("(") && !token.equals(")")) {
                lastWasOperator = false;
            }
        }
        if (lastWasOperator) {
            throw new IllegalArgumentException("Expression ends with an operator.");
        }
    }
}
