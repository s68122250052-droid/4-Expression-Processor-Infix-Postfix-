package group4.algorithms;


import group4.models.EvaluationResult;
import group4.utils.Tokenizer;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;


public class AlgorithmB {
    public static EvaluationResult evaluate(String expr) {
        long startTime = System.nanoTime();
        int pushCount = 0, popCount = 0, compCount = 0;


        List tokens = Tokenizer.tokenize(expr);
        Deque valStack = new ArrayDeque<>();
        Deque opStack = new ArrayDeque<>();


        for (String token : tokens) {
            compCount++;
            if (Character.isDigit(token.charAt(0))) {
                valStack.push(Integer.parseInt(token));
                pushCount++;
            } else if (token.equals("(")) {
                opStack.push(token);
                pushCount++;
            } else if (token.equals(")")) {
                while (!opStack.isEmpty() && !opStack.peek().equals("(")) {
                    processTop(valStack, opStack);
                    pushCount++; popCount += 3;
                }
                if (!opStack.isEmpty()) { opStack.pop(); popCount++; }
            } else { // Operator
                while (!opStack.isEmpty() && Tokenizer.precedence(opStack.peek()) >= Tokenizer.precedence(token)) {
                    compCount++;
                    processTop(valStack, opStack);
                    pushCount++; popCount += 3;
                }
                opStack.push(token);
                pushCount++;
            }
        }


        while (!opStack.isEmpty()) {
            processTop(valStack, opStack);
            pushCount++; popCount += 3;
        }


        long endTime = System.nanoTime();
        return new EvaluationResult("N/A (Direct Infix)", valStack.pop(), (endTime - startTime), pushCount, popCount, compCount);
    }


    private static void processTop(Deque valStack, Deque opStack) {
        String op = opStack.pop();
        int v2 = valStack.pop();
        int v1 = valStack.pop();
        if (op.equals("/") && v2 == 0) throw new ArithmeticException("Division by zero.");
        switch (op) {
            case "+": valStack.push(v1 + v2); break;
            case "-": valStack.push(v1 - v2); break;
            case "*": valStack.push(v1 * v2); break;
            case "/": valStack.push(v1 / v2); break;
        }
    }
}
