function Expression(toString, evaluate, prefix) {
    this.toString = toString
    this.evaluate = evaluate
    this.prefix = prefix
}

const Const = function(value) {
    Expression.call(this, 
        () => value.toString(),
        (...args) => value,
        () => value.toString()
    )
    this.value = Number(value)
    this.diff = (vrbl) => new Const(0)
}

const VARIABLE_INDEX = { "x": 0, "y": 1, "z": 2 }
const Variable = function(vrbl) {
    Expression.call(this,
        () => vrbl,
        (...args) => args[VARIABLE_INDEX[vrbl]],
        () => vrbl
    )
    this.vrbl = vrbl
    this.diff = (vrbl) => this.vrbl === vrbl ? new Const(1) : new Const(0)
}

function Operation(strOper, func, ...args) {
    Expression.call(this,
        () => this.args.map(element => element.toString()).join(" ") + " " + strOper,
        (...args) => func(...this.args.map(element => element.evaluate(...args))),
        () => "(" + strOper + " " + this.args.map(element => element.prefix()).join(" ") + ")"
    )
    this.args = args
    this.strOper = strOper
}

const Cosh = function(...args) {
    Operation.call(this, "cosh", Math.cosh, ...args)
    this.diff = (vrbl) => new Multiply(
        new Sinh(...this.args),
        ...this.args.map(element => element.diff(vrbl))
    )
}

const Sinh = function(...args) {
    Operation.call(this, "sinh", Math.sinh, ...args)
    this.diff = (vrbl) => new Multiply(
        new Cosh(...this.args),
        ...this.args.map(element => element.diff(vrbl))
    )
}

const Min3 = function(...args) {
    Operation.call(this, "min3", Math.min, ...args)
    this.diff = (vrbl) => new Min3(...this.args.map(element => element.diff(vrbl)))
}

const Max5 = function(...args) {
    Operation.call(this, "max5", Math.max, ...args)
    this.diff = (vrbl) => new Max5(...this.args.map(element => element.diff(vrbl)))
}

const Add = function(...args) {
    Operation.call(this, "+", (...args) => args.reduce((a, b) => a + b), ...args)
    this.diff = (vrbl) => new Add(...this.args.map(element => element.diff(vrbl)))
}

const Subtract = function(...args) {
    Operation.call(this, "-", (...args) => args.reduce((a, b) => a - b), ...args)
    this.diff = (vrbl) => new Subtract(...this.args.map(element => element.diff(vrbl)))
}

const Multiply = function(...args) {
    Operation.call(this, "*", (...args) => args.reduce((a, b) => a * b), ...args)
    this.diff = (vrbl) => new Add(
        new Multiply(args[0].diff(vrbl), args[1]),
        new Multiply(args[0], args[1].diff(vrbl))
    )
}

const Divide = function(...args) {
    Operation.call(this, "/", (...args) => args.reduce((a, b) => a / b), ...args)
    this.diff = (vrbl) => new Divide(
        new Subtract(
            new Multiply(args[0].diff(vrbl), args[1]),
            new Multiply(args[0], args[1].diff(vrbl))
        ),
        new Multiply(args[1], args[1])
    )
}

const Negate = function(value) {
    Operation.call(this, "negate", (value) => -value, value)
    this.diff = (vrbl) => new Negate(value.diff(vrbl))
}

function ParseError(message) {
    Error.call(this, message);
    this.message = message
}
ParseError.prototype = Object.create(Error.prototype)
ParseError.prototype.name = "ParserError"
ParseError.prototype.constructor = ParseError

const PARSE_VARIABLE = {
    "x": new Variable("x"),
    "y": new Variable("y"), 
    "z": new Variable("z"),
}
const PARSE_OPERATION = {
    "+": [Add, 2], "-": [Subtract, 2], "*": [Multiply, 2], "/": [Divide, 2],
    "negate": [Negate, 1], "min3": [Min3, 3], "max5": [Max5, 5],
    "cosh": [Cosh, 1], "sinh": [Sinh, 1]
}
const parse = str => {
    let expr = []
    str.split(" ").filter(a => a.length > 0).forEach(element => {
        if (element in PARSE_OPERATION) {
            let elements = []
            for (let i = 0; i < PARSE_OPERATION[element][1]; i++) {
                elements.push(expr.pop())
            }
            elements = elements.reverse()
            expr.push(new PARSE_OPERATION[element][0](...elements))
        } else if (element in PARSE_VARIABLE) {
            expr.push(PARSE_VARIABLE[element])
        } else if (!isNaN(element)) {
            expr.push(new Const(Number(element)))
        } else {
            throw new ParseError("Invalid value")
        }
    });
    return expr.pop()
}

let ind = 0
function parsing(str) {
    if (str[ind] in PARSE_VARIABLE) {
        return PARSE_VARIABLE[str[ind++]]
    } else if (!isNaN(str[ind])) {
        return new Const(Number(str[ind++]))
    } else if (str[ind] === "(") {
        ind++;
        if (str[ind] in PARSE_OPERATION) {
            let oper = PARSE_OPERATION[str[ind++]]
            let elements = []
            for (let i = 0; i < oper[1]; i++) {
                elements.push(parsing(str))
            }
            if (str[ind++] !== ")") {
                throw new ParseError("Missibg bracket");
            }
            return new oper[0](...elements)
        }
    } else {
        throw new ParseError("Invalid value")
    }
}

function parsePrefix(str) {
    str = str.split("(").join(" ( ")
    str = str.split(")").join(" ) ")
    str = str.split(" ").filter(a => a.length > 0)
    ind = 0
    try {
        res = parsing(str)
    } catch (e) {
        throw e
    }
    if (ind !== str.length) {
        throw new ParseError("Invalid expression")
    }
    return res
}