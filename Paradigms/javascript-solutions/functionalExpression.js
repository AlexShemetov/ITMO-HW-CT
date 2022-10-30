const operation = func => (first, second) => (...args) => func(first(...args), second(...args))

const multiply = operation((a, b) => a * b)
const add = operation((a, b) => a + b)
const subtract = operation((a, b) => a - b)
const divide = operation((a, b) => a / b)
const negate = value => (...args) => -value(...args)

const VARIABLE_INDEX = { "x": 0, "y": 1, "z": 2 }
const variable = value => (...args) => args[VARIABLE_INDEX[value]]
const cnst = value => () => value
const e = cnst(Math.E)
const pi = cnst(Math.PI)

const PARSE_VARIABLE = {
    "x": variable("x"),
    "y": variable("y"),
    "z": variable("z"),
}
const PARSE_PIE = { "e": e, "pi": pi }
const PARSE_OPERATION = { "+": add, "-": subtract, "*": multiply, "/": divide }
const parse = str => {
    let expr = []
    let first, second
    str.split(" ").filter(x => x.length > 0).forEach(element => {
        if (element in PARSE_OPERATION) {
            second = expr.pop()
            first = expr.pop()
            expr.push(PARSE_OPERATION[element](first, second))
        } else if (element in PARSE_VARIABLE) {
            expr.push(PARSE_VARIABLE[element])
        } else if (element in PARSE_PIE) {
            expr.push(PARSE_PIE[element])
        } else if (element === "negate") {
            expr.push(negate(expr.pop()))
        } else {
            expr.push(cnst(Number(element)))
        }
    });
    return expr.pop()
}