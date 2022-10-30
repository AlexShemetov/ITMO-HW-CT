package expression.generic;

import expression.generic.solver.*;
import java.util.Scanner;

public class ExpressionParser<T> implements Parser<T> {
    private StringBuilder strBl;
    private Solver<T> solver;
    private TokenValue currToken;
    private int countLPRP;
    private String number;
    private int j;

    public ExpressionParser (Solver<T> solver) {
        this.solver = solver;
    }

    @Override
    public TripleExpression<T> parse(String expression) throws IllegalArgumentException {
        number = "";
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
        strBl.append('\n');

        TripleExpression<T> exp;
        getToken();
        exp = minMax(false);

        if (countLPRP > 0) {
            throw new IllegalArgumentException("No closing parenthesis");
        }

        return exp;
    }

    private void convertToNum() {
        int indexStart = j;
        if (strBl.charAt(j) == '-') {
            j++;
        }
        while (j < strBl.length() && Character.isDigit(strBl.charAt(j))) {
            j++;
        }
        number =strBl.substring(indexStart, j);
        j--;
    }

    private void getToken() {
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
            // case 'l':
            //     if (strBl.charAt(j + 1) == '0') {
            //         j += 1;
            //         currToken = TokenValue.L0;
            //         break;
            //     }
            //     throw new IllegalArgumentException("Unexpected value: " + strBl.substring(j, j + 2));
            // case 't':
            //     if (strBl.charAt(j + 1) == '0') {
            //         j += 1;
            //         currToken = TokenValue.T0;
            //         break;
            //     }
            //     throw new IllegalArgumentException("Unexpected value: " + strBl.substring(j, j + 2));
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
            case 'c':
                if (strBl.substring(j, j + 5).equals("count")) {
                    j += 4;
                    currToken = TokenValue.COUNT;
                    break;
                }
                throw new IllegalArgumentException("Unexpected value: " + strBl.substring(j, j + 5));
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

    private TripleExpression<T> prim(boolean get) {
        if (get) {
            getToken();
        }

        switch (currToken) {
            case CONST -> {
                getToken();
                return new Const<T>(solver.parse(number), solver);
            }
            case X -> {
                getToken();
                return new Variable<>("x");
            }
            case Y -> {
                getToken();
                return new Variable<>("y");
            }
            case Z -> {
                getToken();
                return new Variable<>("z");
            }
            case NEGATE -> {
                return new Negate<>(prim(true), solver);
            }
            case COUNT -> {
                return new Count<>(prim(true), solver);
            }
            // case L0 -> {
            //     return new l0(prim(true));
            // }
            // case T0 -> {
            //     return new t0(prim(true));
            // }
            case LP -> {
                TripleExpression<T> exp = minMax(true);
                getToken();
                return exp;
            }
            default -> throw new IllegalArgumentException("MissingArgumentException");
        }
    }

    private TripleExpression<T> term(boolean get) {
        TripleExpression<T> exp = prim(get);
        while (true) {
            switch (currToken) {
                case MUL:
                    exp = new Multiply<>(exp, prim(true), solver);
                    break;
                case DIV:
                    exp = new Divide<>(exp, prim(true), solver);
                    break;
                default:
                    return exp;
            }
        }
    }

    private TripleExpression<T> expr(boolean get) {
        TripleExpression<T> exp = term(get);
        while (true) {
            switch (currToken) {
                case ADD:
                    exp = new Add<>(exp, term(true), solver);
                    break;
                case SUB:
                    exp = new Subtract<>(exp, term(true), solver);
                    break;
                default:
                    return exp;
            }
        }
    }

    private TripleExpression<T> minMax(boolean get) {
        TripleExpression<T> exp = expr(get);
        while (true) {
            switch (currToken) {
                case MIN:
                    exp = new Minimum<>(exp, expr(true), solver);
                    break;
                case MAX:
                    exp = new Maximum<>(exp, expr(true), solver);
                    break;
                default:
                    return exp;
            }
        }
    }
}