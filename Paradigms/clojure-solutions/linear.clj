(defn create-func [f condition]
    (fn [& args]
        ;{:pre [(condition args)]}
        (apply mapv f args)))
(defn only-num? [v] (every? number? v))
(defn equal-size? [vs] (apply = (map count vs)))
(defn checker [elems condition] (and (not-empty elems) (every? condition elems) (equal-size? elems)))

(defn num-vectors? [vs] (checker vs only-num?))
(defn vector-func [f]
    (create-func f num-vectors?))
(def v+ (vector-func +))
(def v- (vector-func -))
(def v* (vector-func *))
(def vd (vector-func /))
(defn v*s [v & args]
    ;{:pre [(every? number? v) (every? number? args)]}
    (mapv (partial * (reduce * args)) v))
(defn s*v [s v] (v*s v s))
(defn scalar [& args]
    ;{:pre [(not-empty args) (== (count args) 2) (num-vectors? args)]}
    (reduce + (v* (nth args 0) (nth args 1))))
(defn vect-for-2 ([v1 v2]
    [(- (* (nth v1 1) (nth v2 2)) (* (nth v1 2) (nth v2 1)))
     (- (* (nth v1 2) (nth v2 0)) (* (nth v1 0) (nth v2 2)))
     (- (* (nth v1 0) (nth v2 1)) (* (nth v1 1) (nth v2 0)))]))
(defn vect
    [& vs]
    ;{:pre [(and (num-vectors? vs) (= (count (first vs)) 3))]}
    (reduce vect-for-2 vs))

(defn num-matrix? [ms] (checker ms num-vectors?))
(defn matrix-func [f]
    (create-func f num-matrix?))
(def m+ (matrix-func v+))
(def m- (matrix-func v-))
(def m* (matrix-func v*))
(def md (matrix-func vd))
(defn m*s [m & args]
    ;{:pre [(and (not-empty m) (num-vectors? m) (every? number? args))]}
    (mapv (partial s*v (reduce * args)) m))
(defn m*v [m v]
    ;{:pre [(num-vectors? m) (every? number? v)]}
    (mapv (partial scalar v) m))
(defn transpose [m] 
    ;{:pre [(num-vectors? m)]}
    (apply mapv vector m))
(defn m*m-for-2 [m1 m2]
    ;{:pre [(== (count (first m1)) (count m2))]}
    (transpose (mapv (partial m*v m1) (transpose m2))))
(defn m*m [& args]
    ;{:pre [(every? num-vectors? args)]}
    (reduce m*m-for-2 args))

(defn num-cuboid? [cs] (checker cs num-matrix?))
(defn cuboid-func [f]
    (create-func f num-cuboid?))
(def c+ (cuboid-func m+))
(def c- (cuboid-func m-))
(def c* (cuboid-func m*))
(def cd (cuboid-func md))

;(def v1 [1 2 3])
;(def v2 [10 20 30])
;(println (scalar v1 v2))
;(def m1 [[1 2 3] [4 5 6] [7 8 9]])
;(def m2 [[10 20 30] [40 50 60] [70 80 90]])
;(def c1 [[[1 2] [3 4]] [[5 6] [7 8]]])
;(def c2 [[[10 20] [30 40]] [[50 60] [70 80]]])
;(println (c+ c1 c2 c2 c1))
;(println (c- c1 c2 c2 c1))
;(println (cd c1 c2 c2 c1))
;(println (c* c1 c2 c2 c1))
;
;(println (vd v1 v2))
;(println (m+ m1 m2 m1))
;(println (m* m1 m2 m1))
;(println (md m1 m2 m2))
;(println (m- m1 m2 m1))
;(println (m*s m1 1 2 3))
;(println (m*v m1 v1))
;(println (m*m m1 m2))
;
;(println (v*s (vector) 1 2 3))
;(println (v+ v1 v2))
;(println (scalar v1 v2))
;(println (vd v2 v1 v2 v1))
;(println (vect v1 v2 v2))