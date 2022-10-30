(defn constant [const] (fn [args] const))
(defn variable [var] (fn [args] (args var)))
(defn create-operation [f]
    (fn [& args]
        (fn [vars]
            (apply f (map (fn [name] (name vars)) args)))))
(def add (create-operation +))
(def subtract (create-operation -))
(def multiply (create-operation *))
(defn div ([expr] (/ 1.0 (double expr)))
          ([dividend & divisors] (reduce (fn [expr1 expr2] (/ expr1 (double expr2))) dividend divisors)))
(def divide (create-operation div))
(def negate (create-operation -))
(def exp (create-operation (fn [expr] (Math/exp expr))))
(def ln (create-operation (fn [expr] (Math/log expr))))



(defn evaluate [this args] (((this :prototype) :evaluate) this args))
(defn toString [this] (((this :prototype) :toString) this))
(defn diff [this var] (((this :prototype) :diff) this var))

(defn constructor
  "Defines constructor"
  [ctor prototype]
  (fn [& args] (apply ctor {:prototype prototype} args)))

(defn const-constructor [this value]
  (assoc this :value value))
(def Constant (constructor const-constructor
                           {:evaluate (fn [this args] (this :value))
                            :toString (fn [this] (str (this :value)))
                            :diff (fn [this var] (Constant 0))}))

(defn var-constructor [this var]
  (assoc this :var var))
(def Variable (constructor var-constructor
                           {:evaluate (fn [this args] (args (this :var)))
                            :toString (fn [this] (this :var))
                            :diff (fn [this var] (if (= (this :var) var) (Constant 1) (Constant 0)))}))

(defn operation-constructor [this & operation]
  (assoc this :operation operation))
(defn create-operation-obj [f op diff]
  (constructor operation-constructor
               {:evaluate (fn [this args] (apply f (map #(evaluate % args) (this :operation))))
                :toString (fn [this] (str "(" op " " (clojure.string/join " " (map toString (this :operation))) ")"))
                :diff (fn [this var] (diff (this :operation) var))}))
(def Add (create-operation-obj + "+" (fn [expr var] (apply Add (map #(diff % var) expr)))))
(def Subtract (create-operation-obj - "-" (fn [expr var] (apply Subtract (map #(diff % var) expr)))))

(def Multiply (create-operation-obj * "*" 
    (fn [expr var] (Add (Multiply (diff (first expr) var) (second expr)) (Multiply (first expr) (diff (second expr) var))))))
(def Divide (create-operation-obj div "/"
    (fn [expr var] (Divide (Subtract (Multiply (diff (first expr) var) (second expr)) (Multiply (first expr) (diff (second expr) var))) (Multiply (second expr) (second expr))))))
(def Negate (create-operation-obj - "negate" (fn [expr var] (Negate (diff (first expr) var)))))
(def Exp (create-operation-obj #(Math/exp %) "exp" (fn [expr var] (Multiply (Exp (first expr)) (diff (first expr) var)))))
(def Ln (create-operation-obj #(Math/log %) "ln" (fn [expr var] (Multiply (Divide (Constant 1.0) (first expr)) (diff (first expr) var)))))

(def operation-func {'+ add, '- subtract, '* multiply, '/ divide, 'negate negate, "cnst" constant, "var" variable, 'exp exp, 'ln ln})
(def operation-obj {'+ Add, '- Subtract, '* Multiply, '/ Divide, 'negate Negate, "cnst" Constant, "var" Variable, 'exp Exp, 'ln Ln})
(defn parser [expr, operation]
  (cond
    (seq? expr) (apply (operation (first expr)) (map #(parser % operation) (rest expr)))
    (number? expr) ((operation "cnst") expr)
    :else ((operation "var") (str expr))))
(defn parseFunction [str-expr] (parser (read-string str-expr) operation-func))
(def parseObject (comp #(parser % operation-obj) read-string))