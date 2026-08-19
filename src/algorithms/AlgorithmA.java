package group4.algorithms;

import group4.models.EvaluationResult;
import group4.utils.Tokenizer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class AlgorithmA {
    public static EvaluationResult evaluate(String expr) {
        long start = System.nanoTime();
        int push = 0, pop = 0, comp = 0;

        List<String> tokens = Tokenizer.tokenize(expr);
        List<String> postfix = new ArrayList<>();
        Deque<String> opStack = new ArrayDeque<>();

        for (String token : tokens) {
            comp++;
            if (Character.isDigit(token.charAt(0))) {
                postfix.add(token);
            } else if (token.equals("(")) {
                opStack.push(token); push++;
            } else if (token.equals(")")) {
                while (!opStack.isEmpty() && !opStack.peek().equals("(")) {
                    postfix.add(opStack.pop()); pop++;
                }
                if (opStack.isEmpty()) throw new ArithmeticException("Invalid parentheses.");
                opStack.pop(); pop++;
            } else {
                while (!opStack.isEmpty()
                        && Tokenizer.precedence(opStack.peek()) >= Tokenizer.precedence(token)) {
                    comp++;
                    postfix.add(opStack.pop()); pop++;
                }
                opStack.push(token); push++;
            }
        }

        while (!opStack.isEmpty()) {
            String op = opStack.pop();
            if (op.equals("(")) throw new ArithmeticException("Invalid parentheses.");
            postfix.add(op); pop++;
        }

        Deque<Integer> values = new ArrayDeque<>();
        for (String token : postfix) {
            if (Character.isDigit(token.charAt(0))) {
                values.push(Integer.parseInt(token)); push++;
            } else {
                if (values.size() < 2) throw new ArithmeticException("Invalid postfix structure.");
                int b = values.pop(), a = values.pop(); pop += 2;
                if (token.equals("/") && b == 0) throw new ArithmeticException("Division by zero.");
                values.push(apply(a, b, token)); push++;
            }
        }

        long end = System.nanoTime();
        return new EvaluationResult(String.join(" ", postfix), values.pop(),
                end - start, push, pop, comp);
    }

    private static int apply(int a, int b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            default: throw new IllegalArgumentException("Unsupported operator: " + op);
        }
    }
}
