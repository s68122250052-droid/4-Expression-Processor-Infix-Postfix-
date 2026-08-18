package group4.algorithms;


import group4.models.EvaluationResult;
import group4.utils.Tokenizer;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


public class AlgorithmA {
    public static EvaluationResult evaluate(String expr) {
        long startTime = System.nanoTime();
        int pushCount = 0, popCount = 0, compCount = 0;


        List tokens = Tokenizer.tokenize(expr);
        List postfix = new ArrayList<>();
        Deque opStack = new ArrayDeque<>();


        // Phase 1: Infix to Postfix
        for (String token : tokens) {
            compCount++;
            if (Character.isDigit(token.charAt(0))) {
                postfix.add(token);
            } else if (token.equals("(")) {
                opStack.push(token);
                pushCount++;
            } else if (token.equals(")")) {
                while (!opStack.isEmpty() && !opStack.peek().equals("(")) {
                    postfix.add(opStack.pop());
                    popCount++;
                }
                if (!opStack.isEmpty()) { opStack.pop(); popCount++; }
            } else { // Operator
                while (!opStack.isEmpty() && Tokenizer.precedence(opStack.peek()) >= Tokenizer.precedence(token)) {
                    compCount++;
                    postfix.add(opStack.pop());
                    popCount++;
                }
                opStack.push(token);
                pushCount++;
            }
        }
        while (!opStack.isEmpty()) {
            postfix.add(opStack.pop());
            popCount++;
        }


        // Phase 2: Postfix Evaluation
        Deque valStack = new ArrayDeque<>();
        for (String token : postfix) {
            if (Character.isDigit(token.charAt(0))) {
                valStack.push(Integer.parseInt(token));
                pushCount++;
            } else {
                if (valStack.size() < 2) throw new ArithmeticException("Invalid postfix structure.");
                int v2 = valStack.pop(); popCount++;
                int v1 = valStack.pop(); popCount++;
                if (token.equals("/") && v2 == 0) throw new ArithmeticException("Division by zero.");
                int res = applyOp(v1, v2, token);
                valStack.push(res); pushCount++;
            }
        }


        long endTime = System.nanoTime();
        return new EvaluationResult(String.join(" ", postfix), valStack.pop(), (endTime - startTime), pushCount, popCount, compCount);
    }


    private static int applyOp(int a, int b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            default: return 0;
        }
    }
}
