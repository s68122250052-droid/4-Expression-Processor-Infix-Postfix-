package group4;

import java.util.Random;

public class ExpressionGenerator {
    private static final char[] OPS = {'+', '-', '*', '/'};

    public static String generate(int numberOfOperands, long seed) {
        Random random = new Random(seed);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numberOfOperands; i++) {
            int value = 1 + random.nextInt(9);
            sb.append(value);
            if (i < numberOfOperands - 1) {
                sb.append(' ').append(OPS[random.nextInt(OPS.length)]).append(' ');
            }
        }
        return sb.toString();
    }
}
