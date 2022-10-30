package expression.parser;

import expression.*;

import java.util.Scanner;

public class ExpressionParser implements Parser {
    private static StringBuilder strBl;
    private static TokenValue currToken;
    private static int number;

    public ExpressionParser() {};

    @Override
    public UltimateExpression parse(String expression) {
        strBl = new StringBuilder();
        Scanner scn = new Scanner(expression);
        while (scn.hasNext()) {
            strBl.append(scn.next());
        }
        strBl.append('\n');

        getToken();
        return minMax(false);
    }

    private static void convertToNum(StringBuilder str) {
        while (Character.isDigit(strBl.charAt(0))) {
            str.append(strBl.charAt(0));
            strBl.deleteCharAt(0);
        }
        number = Integer.parseInt(str.toString());
    }

    private static void getToken() {
        StringBuilder str = new StringBuilder();
        str.append(strBl.charAt(0));
        strBl.deleteCharAt(0);
        switch (str.charAt(0)) {
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
                } else if (Character.isDigit(strBl.charAt(0))) {
                    convertToNum(str);
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
                break;
            case ')':
                currToken = TokenValue.RP;
                break;
            case 'm':
                if (strBl.charAt(0) == 'i' && strBl.charAt(1) == 'n') {
                    strBl.deleteCharAt(0);
                    strBl.deleteCharAt(0);
                    currToken = TokenValue.MIN;
                    break;
                }
                if (strBl.charAt(0) == 'a' && strBl.charAt(1) == 'x') {
                    strBl.deleteCharAt(0);
                    strBl.deleteCharAt(0);
                    currToken = TokenValue.MAX;
                    break;
                }
                throw new IllegalArgumentException("Unexpected value: " + str);
            default:
                if (Character.isDigit(str.charAt(0))) {
                    convertToNum(str);
                    currToken = TokenValue.CONST;
                } else {
                    throw new IllegalArgumentException("Unexpected value: " + str);
                }
        }
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
                return new Negate(prim(true));
            }
            case LP -> {
                UltimateExpression exp = minMax(true);
                getToken();
                return exp;
            }
            default -> {
                throw new IllegalArgumentException("unsoported argument");
            }
        }
    }

    private static UltimateExpression term(boolean get) {
        UltimateExpression exp = prim(get);
        while (true) {
            switch (currToken) {
                case MUL:
                    exp = new Multiply(exp, prim(true));
                    break;
                case DIV:
                    exp = new Divide(exp, prim(true));
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
                    exp = new Add(exp, term(true));
                    break;
                case SUB:
                    exp = new Subtract(exp, term(true));
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