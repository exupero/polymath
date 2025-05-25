(ns polymath.math)

(def extent (juxt (partial apply min) (partial apply max)))

(defn linear [[x1 x2] [y1 y2]]
  (let [m (/ (- y1 y2) (- x1 x2))
        b (- y1 (* m x1))]
    #(+ b (* m %))))
