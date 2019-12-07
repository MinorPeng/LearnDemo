package com.phs1024.studydemo.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

/**
 * @author PHS1024
 * @date 2019/9/26 10:51:27
 */
public class CalculateUtil {

    private static final char mEqual = '=';
    private static final char mMultiply = '*', mDivide = '/', mAdd = '+', mSubtract = '-',
            mRemainder = '%', mPow = '^';
    private static final char mLParenthesis = '(', mRParenthesis = ')';
    private static final String mSin = "sin", mCos = "cos", mTan = "tan";
    /**
     * Chinese Operator divide or multiply
     */
    private static final char mCODivide = '÷', mCOMultiply = '×';

    private static Stack<Character> mOperationStack = new Stack<>();
    private static Stack<String> mValueStack = new Stack<>();

    private CalculateUtil() {

    }

    /**
     * 根据表达式计算结果
     * @param expression 表达式，保证合法性
     * @return
     */
    public static String calculate(String expression) {
        String result = "";

        expression = addBlank(expression);
        calculateExpression(expression);
        result = new BigDecimal(mValueStack.pop()).divide(new BigDecimal("1"), 2,
                RoundingMode.HALF_UP).toString();
        return result.replaceAll("0+$", "").replaceAll("\\.$", "");
    }

    private static String addBlank(String expression) {
        StringBuilder result = new StringBuilder();
        for (char c : expression.toCharArray()) {
            if (c == mEqual) {
                continue;
            }
            if (c == mCODivide) {
                c = mDivide;
            } else if (c == mCOMultiply) {
                c = mMultiply;
            }
            if (c == mMultiply || c == mDivide || c == mAdd || c == mSubtract || c == mRemainder
                    || c == mPow || c == mLParenthesis || c == mRParenthesis) {
                result.append(" ").append(c).append(" ");
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private static void calculateExpression(String expression) {
        mOperationStack.clear();
        mValueStack.clear();
        String[] strings = expression.split(" ");
        for (String s : strings) {
            if (s.isEmpty()) {
                continue;
            }
            char operator = s.charAt(0);
            switch (operator) {
                case mAdd:
                case mSubtract:
                    // + or -
                    while (!mOperationStack.empty() && (mOperationStack.peek() == mAdd ||
                            mOperationStack.peek() == mSubtract || mOperationStack.peek() == mMultiply
                            || mOperationStack.peek() == mDivide)) {
                        mValueStack.push(calculate(mValueStack.pop(), mValueStack.pop(),
                                mOperationStack.pop()));
                    }
                    mOperationStack.push(operator);
                    break;
                case mMultiply:
                case mDivide:
                    // * or /
                    while (!mOperationStack.empty() && (mOperationStack.peek() == mMultiply
                            || mOperationStack.peek() == mDivide)) {
                        mValueStack.push(calculate(mValueStack.pop(), mValueStack.pop(),
                                mOperationStack.pop()));
                    }
                    mOperationStack.push(operator);
                    break;
                case mLParenthesis:
                    // (
                    mOperationStack.push(mLParenthesis);
                    break;
                case mRParenthesis:
                    // )
                    while (mOperationStack.peek() != mLParenthesis) {
                        mValueStack.push(calculate(mValueStack.pop(), mValueStack.pop(),
                                mOperationStack.pop()));
                    }
                    mOperationStack.pop();
                    break;
                case mPow:
                    // ^
                    break;
                case mRemainder:
                    // %
                    break;
                default:
                    // num
                    mValueStack.push(s);
                    break;
            }
        }
        while (!mOperationStack.empty()) {
            mValueStack.push(calculate(mValueStack.pop(), mValueStack.pop(),
                    mOperationStack.pop()));
        }
    }

    private static String calculate(String value1, String value2, Character operator) {
        switch (operator) {
            case mAdd:
                return new BigDecimal(value2).add(new BigDecimal(value1)).toString();
            case mSubtract:
                return new BigDecimal(value2).subtract(new BigDecimal(value1)).toString();
            case mMultiply:
                return new BigDecimal(value2).multiply(new BigDecimal(value1)).toString();
            case mDivide:
                return new BigDecimal(value2).divide(new BigDecimal(value1), 10,
                        RoundingMode.HALF_UP).toString();
            case mRemainder:
                return new BigDecimal(value2).remainder(new BigDecimal(value1)).toString();
            case mPow:
                return new BigDecimal(value2).pow(new BigDecimal(value1).toBigInteger().intValue()).toString();
            default:
                throw new UnsupportedOperationException("Unsupported this expression with " +
                        "operator:" + operator);
        }
    }

    private static boolean illegalExpression(String expression) {
        if (expression.charAt(0) == mMultiply || expression.charAt(0) == mDivide) {
            return false;
        }
        return true;
    }
}
