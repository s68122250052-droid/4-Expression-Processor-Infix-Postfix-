package group4;

import group4.algorithms.AlgorithmA;
import group4.algorithms.AlgorithmB;
import group4.models.EvaluationResult;
import group4.utils.ExpressionValidator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==============================================");
        System.out.println(" Group 4: Expression Processor (Infix & Postfix)");
        System.out.println("==============================================");

        while (true) {
            System.out.println("\nSelect Menu:");
            System.out.println("1. Test Custom Expression");
            System.out.println("2. Run Mandatory Assignment Test Cases (1-8)");
            System.out.println("3. Exit");
            System.out.print("Enter choice (1-3): ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("3")) break;
            if (choice.equals("1")) {
                System.out.print("Enter Infix Expression: ");
                processExpression(scanner.nextLine());
            } else if (choice.equals("2")) {
                runMandatoryTestCases();
            } else {
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
        scanner.close();
    }

    private static void processExpression(String expr) {
        System.out.println("\n--- Processing: \"" + expr + "\" ---");
        try {
            ExpressionValidator.validate(expr);
            EvaluationResult a = AlgorithmA.evaluate(expr);
            EvaluationResult b = AlgorithmB.evaluate(expr);

            System.out.println("[Algorithm A - Infix to Postfix]");
            System.out.println("Postfix : " + a.getPostfixExpression());
            System.out.println("Result  : " + a.getResult());
            System.out.println("Time    : " + a.getExecutionTimeNs() + " ns");
            System.out.println("Ops     : " + a.getOpCount());

            System.out.println("\n[Algorithm B - Direct Infix]");
            System.out.println("Result  : " + b.getResult());
            System.out.println("Time    : " + b.getExecutionTimeNs() + " ns");
            System.out.println("Ops     : " + b.getOpCount());
        } catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Execution Error: " + e.getMessage());
        }
    }

    private static void runMandatoryTestCases() {
        String[] testCases = {
            "3 + 4 * 2",
            "(3 + 4) * 2",
            "((8 + 2) * 5)",
            "(3 + 4",
            "3 + 4)",
            "3 + * 4",
            "10 / (5 - 5)",
            ""
        };
        for (int i = 0; i < testCases.length; i++) {
            System.out.println("\n=== Mandatory Test Case " + (i + 1) + " ===");
            processExpression(testCases[i]);
        }
    }
}
