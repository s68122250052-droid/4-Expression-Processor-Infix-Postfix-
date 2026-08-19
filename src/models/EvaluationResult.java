package group4.models;

public class EvaluationResult {
    private final String postfixExpression;
    private final int result;
    private final long executionTimeNs;
    private final int pushCount;
    private final int popCount;
    private final int comparisonCount;

    public EvaluationResult(String postfixExpression, int result, long executionTimeNs,
                            int pushCount, int popCount, int comparisonCount) {
        this.postfixExpression = postfixExpression;
        this.result = result;
        this.executionTimeNs = executionTimeNs;
        this.pushCount = pushCount;
        this.popCount = popCount;
        this.comparisonCount = comparisonCount;
    }

    public String getPostfixExpression() { return postfixExpression; }
    public int getResult() { return result; }
    public long getExecutionTimeNs() { return executionTimeNs; }
    public int getPushCount() { return pushCount; }
    public int getPopCount() { return popCount; }
    public int getComparisonCount() { return comparisonCount; }

    public String getOpCount() {
        return "Push=" + pushCount + ", Pop=" + popCount + ", Comp=" + comparisonCount;
    }
}
