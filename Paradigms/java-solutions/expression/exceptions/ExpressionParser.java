package expression.exceptions;

import expression.*;

import java.util.Scanner;

public class ExpressionParser implements Parser {
    private static StringBuilder strBl;
    private static TokenValue currToken;
    private static int countLPRP;
    private static int number;
    private static int j;

    public ExpressionParser() {};

    @Override
    public UltimateExpression parse(String expression) {
        number = 0;
        strBl = new StringBuilder();
        countLPRP = 0;
        j = 0;
        currToken = null;

        Scanner scn = new Scanner(expression);
        boolean lastIsDigit = false;
        while (scn.hasNext()) {
            String str = scn.next();
            if (lastIsDigit && Character.isDigit(str.charAt(0))) {
                throw new IllegalArgumentException("WhitespaceBetweenNumbers");
            }

            for (int i = 1; i < str.length() - 3; i++) {
                if (str.substring(i, i + 3).equals("min") || str.substring(i, i + 3).equals("max")) {
                    if (Character.isDigit(str.charAt(i + 3)) || Character.isDigit(str.charAt(i - 1))) {
                        throw new IllegalArgumentException("NoWhitespaceBetweenMinMaxAndNum");
                    }
                }
            }

            strBl.append(str);
            if (str.length() >= 2) {
                if (str.substring(str.length() - 2, str.length()).equals("l0")
                 || str.substring(str.length() - 2, str.length()).equals("t0")) {
                    continue;
                } else if (str.length() >= 3 &&
                        (str.substring(str.length() - 3, str.length() - 1).equals("l0")
                        || str.substring(str.length() - 3, str.length() - 1).equals("t0"))) {
                    if (Character.isLetterOrDigit(str.charAt(str.length() - 1))) {
                        throw new IllegalArgumentException("IncorrectInput");
                    }
                }
            }
            lastIsDigit = Character.isDigit(str.charAt(str.length() - 1));
        }

//        boolean lastIsDigit = false;
//        for (int i = 0; i < expression.length(); i++) {
//            if (!Character.isWhitespace(expression.charAt(i))) {
//                if (lastIsDigit && Character.isDigit(expression.charAt(i))) {
//                    throw new IllegalArgumentException("WhitespaceBetweenNumbers");
//                }
//
//                if (expression.charAt(i) == 'm') {
//                    if (expression.substring(i, i + 3).equals("min")
//                            || expression.substring(i, i + 3).equals("max")) {
//                        if (Character.isDigit(strBl.charAt(i + 5))) {
//                            throw new IllegalArgumentException("NoWhitespaceBetweenMinMaxAndNumber");
//                        }
//                        strBl.append(expression.substring(i, i + 3));
//                        i += 2;
//                        continue;
//                    }
//                }
//
//                lastIsDigit = false;
//                strBl.append(expression.charAt(i));
//            } else if (Character.isDigit(strBl.charAt(strBl.length() - 1))) {
//                lastIsDigit = true;
//            }
//        }
        strBl.append('\n');

        UltimateExpression exp;
        getToken();
        exp = minMax(false);

        if (countLPRP > 0) {
            throw new IllegalArgumentException("No closing parenthesis");
        }

        return exp;
    }

    private static void convertToNum() {
        int indexStart = j;
        if (strBl.charAt(j) == '-') {
            j++;
        }
        while (j < strBl.length() && Character.isDigit(strBl.charAt(j))) {
            j++;
        }
        number = Integer.parseInt(strBl.substring(indexStart, j));
        j--;
    }

    private static void getToken() {
        switch (strBl.charAt(j)) {
            case 'x':
                currToken = TokenValue.X;
                break;
            case 'y':
                currToken = TokenValue.Y;
                break;
            case 'z':
                currToken = TokenValue.Z;
                break;
            case '+':
                currToken = TokenValue.ADD;
                break;
            case '-':
                if (currToken == TokenValue.RP || currToken == TokenValue.CONST
                        || currToken == TokenValue.X || currToken == TokenValue.Y || currToken == TokenValue.Z) {
                    currToken = TokenValue.SUB;
                } else if (Character.isDigit(strBl.charAt(j + 1))) {
                    convertToNum();
                    currToken = TokenValue.CONST;
                } else {
                    currToken = TokenValue.NEGATE;
                }
                break;
            case '*':
                currToken = TokenValue.MUL;
                break;
            case '/':
                currToken = TokenValue.DIV;
                break;
            case '\n':
                currToken = TokenValue.END;
                break;
            case '(':
                currToken = TokenValue.LP;
                countLPRP++;
                break;
            case ')':
                currToken = TokenValue.RP;
                countLPRP--;
                if (countLPRP < 0) {
                    throw new IllegalArgumentException("NoOpeningParenthesis");
                }
                break;
            case 'l':
                if (strBl.charAt(j + 1) == '0') {
                    j += 1;
                    currToken = TokenValue.L0;
                    break;
                }
                throw new IllegalArgumentException("Unexpected value: " + strBl.substring(j, j + 2));
            case 't':
                if (strBl.charAt(j + 1) == '0') {
                    j += 1;
                    currToken = TokenValue.T0;
                    break;
                }
                throw new IllegalArgumentException("Unexpected value: " + strBl.substring(j, j + 2));
            case 'm':
                if (strBl.charAt(j + 1) == 'i' && strBl.charAt(j + 2) == 'n') {
                    j += 2;
                    currToken = TokenValue.MIN;
                    break;
                }
                if (strBl.charAt(j + 1) == 'a' && strBl.charAt(j + 2) == 'x') {
                    j += 2;
                    currToken = TokenValue.MAX;
                    break;
                }
                throw new IllegalArgumentException("Unexpected value: " + strBl.substring(j, j + 3));
            default:
                if (Character.isDigit(strBl.charAt(j))) {
                    convertToNum();
                    currToken = TokenValue.CONST;
                } else {
                    throw new IllegalArgumentException("IncorrectSymbolException");
                }
        }
        j++;
    }

    private static UltimateExpression prim(boolean get) {
        if (get) {
            getToken();
        }

        switch (currToken) {
            case CONST -> {
                getToken();
                return new Const(number);
            }
            case X -> {
                getToken();
                return new Variable("x");
            }
            case Y -> {
                getToken();
                return new Variable("y");
            }
            case Z -> {
                getToken();
                return new Variable("z");
            }
            case NEGATE -> {
                return new CheckedNegate(prim(true));
            }
            case L0 -> {
                return new l0(prim(true));
            }
            case T0 -> {
                return new t0(prim(true));
            }
            case LP -> {
                UltimateExpression exp = minMax(true);
                getToken();
                return exp;
            }
            default -> throw new IllegalArgumentException("MissingArgumentException");
        }
    }

    private static UltimateExpression term(boolean get) {
        UltimateExpression exp = prim(get);
        while (true) {
            switch (currToken) {
                case MUL:
                    exp = new CheckedMultiply(exp, prim(true));
                    break;
                case DIV:
                    exp = new CheckedDivide(exp, prim(true));
                    break;
                default:
                    return exp;
            }
        }
    }

    private static UltimateExpression expr(boolean get) {
        UltimateExpression exp = term(get);
        while (true) {
            switch (currToken) {
                case ADD:
                    exp = new CheckedAdd(exp, term(true));
                    break;
                case SUB:
                    exp = new CheckedSubtract(exp, term(true));
                    break;
                default:
                    return exp;
            }
        }
    }

    private static UltimateExpression minMax(boolean get) {
        UltimateExpression exp = expr(get);
        while (true) {
            switch (currToken) {
                case MIN:
                    exp = new Minimum(exp, expr(true));
                    break;
                case MAX:
                    exp = new Maximum(exp, expr(true));
                    break;
                default:
                    return exp;
            }
        }
    }
}