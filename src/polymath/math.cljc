(ns polymath.math)

(def extent (juxt (partial apply min) (partial apply max)))

(defn linear [[x1 x2] [y1 y2]]
  (let [m (/ (- y1 y2) (- x1 x2))
        b (- y1 (* m x1))]
    (with-meta
      #(+ b (* m %))
      {:inverse #(/ (- % b) m)})))

(defn inverse [f & args]
  (if-let [f' (-> f meta :inverse)]
    (apply f' args)
    (throw (ex-info "Function does not have an inverse" {:function f}))))

(defn round
  ([x] (round x 1))
  ([x p]
   (* p (Math/round (float (/ x p))))))

(defn average
  ([xs]
   (/ (reduce + xs)
      (count xs)))
  ([x1 x2 & more]
   (average (concat [x1 x2] more))))
