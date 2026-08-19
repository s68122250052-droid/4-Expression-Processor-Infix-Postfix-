package group4.algorithms;

import group4.models.EvaluationResult;
import group4.utils.Tokenizer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class AlgorithmB {
    public static EvaluationResult evaluate(String expr) {
        long start = System.nanoTime();
        int push = 0, pop = 0, comp = 0;

        List<String> tokens = Tokenizer.tokenize(expr);
        Deque<Integer> values = new ArrayDeque<>();
        Deque<String> ops = new ArrayDeque<>();

        for (String token : tokens) {
            comp++;
            if (Character.isDigit(token.charAt(0))) {
                values.push(Integer.parseInt(token)); push++;
            } else if (token.equals("(")) {
                ops.push(token); push++;
            } else if (token.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) {
                    int[] c = applyTop(values, ops);
                    push += c[0]; pop += c[1]; comp += c[2];
                }
                if (ops.isEmpty()) throw new ArithmeticException("Invalid parentheses.");
                ops.pop(); pop++;
            } else {
                while (!ops.isEmpty()
                        && Tokenizer.precedence(ops.peek()) >= Tokenizer.precedence(token)) {
                    comp++;
                    int[] c = applyTop(values, ops);
                    push += c[0]; pop += c[1]; comp += c[2];
                }
                ops.push(token); push++;
            }
        }

        while (!ops.isEmpty()) {
            int[] c = applyTop(values, ops);
            push += c[0]; pop += c[1]; comp += c[2];
        }

        long end = System.nanoTime();
        return new EvaluationResult("N/A (Direct Infix)", values.pop(),
                end - start, push, pop, comp);
    }

    private static int[] applyTop(Deque<Integer> values, Deque<String> ops) {
        String op = ops.pop();
        int b = values.pop();
        int a = values.pop();
        if (op.equals("/") && b == 0) throw new ArithmeticException("Division by zero.");
        int r;
        switch (op) {
            case "+": r = a + b; break;
            case "-": r = a - b; break;
            case "*": r = a * b; break;
            case "/": r = a / b; break;
            default: throw new IllegalArgumentException("Unsupported operator: " + op);
        }
        values.push(r);
        return new int[]{1, 3, 0};
    }
}
