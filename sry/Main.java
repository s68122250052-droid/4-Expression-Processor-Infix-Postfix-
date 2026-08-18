package group4;

import group4.algorithms.AlgorithmA;
import group4.algorithms.AlgorithmB;
import group4.models.EvaluationResult;
import group4.utils.ExpressionValidator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================================");
        System.out.println("   Group 4: Expression Processor (Infix & Postfix)");
        System.out.println("==================================================");

        while (true) {

            System.out.println("\nSelect Menu:");
            System.out.println("1. Test Custom Expression");
            System.out.println("2. Run Mandatory Assignment Test Cases (1-8)");
            System.out.println("3. Exit");
            System.out.print("Enter choice (1-3): ");

            String inputChoice = scanner.nextLine().trim();

            if (inputChoice.equals("3")) {

                System.out.println("Exiting program. Thank you!");
                break;

            } else if (inputChoice.equals("1")) {

                System.out.print("Enter Infix Expression: ");
                String expr = scanner.nextLine();

                processExpression(expr);

            } else if (inputChoice.equals("2")) {

                runMandatoryTestCases();

            } else {

                System.out.println(
                    "Invalid choice. Please enter 1, 2, or 3."
                );
            }
        }

        scanner.close();
    }

    private static void processExpression(String expr) {

        System.out.println(
            "\n--- Processing Expression: \"" + expr + "\" ---"
        );

        // Validate Expression
        try {

            ExpressionValidator.validate(expr);

        } catch (Exception e) {

            System.out.println(
                "Validation Error: " + e.getMessage()
            );

            return;
        }

        // Algorithm A
        try {

            EvaluationResult resultA =
                AlgorithmA.evaluate(expr);

            System.out.println(
                "\n[Algorithm A - Infix to Postfix]"
            );

            System.out.println(
                "Postfix Notation : "
                + resultA.getPostfixExpression()
            );

            System.out.println(
                "Computed Result   : "
                + resultA.getResult()
            );

            System.out.println(
                "Execution Time    : "
                + resultA.getExecutionTimeNs()
                + " ns"
            );

            System.out.println(
                "Operations (Push/Pop/Comp): "
                + resultA.getOpCount()
            );

            // Algorithm B
            EvaluationResult resultB =
                AlgorithmB.evaluate(expr);

            System.out.println(
                "\n[Algorithm B - Direct Infix]"
            );

            System.out.println(
                "Computed Result   : "
                + resultB.getResult()
            );

            System.out.println(
                "Execution Time    : "
                + resultB.getExecutionTimeNs()
                + " ns"
            );

            System.out.println(
                "Operations (Push/Pop/Comp): "
                + resultB.getOpCount()
            );

        } catch (Exception e) {

            System.out.println(
                "Execution Error: " + e.getMessage()
            );
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

            System.out.println(
                "\n=========================================="
            );

            System.out.println(
                "Mandatory Test Case "
                + (i + 1)
                + ": \""
                + testCases[i]
                + "\""
            );

            System.out.println(
                "=========================================="
            );

            processExpression(testCases[i]);
        }
    }
}