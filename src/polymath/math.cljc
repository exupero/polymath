(ns polymath.math)

(def extent (juxt (partial apply min) (partial apply max)))

(defn round
  ([x] (round x 1))
  ([x p]
   (* p (Math/round (float (/ x p))))))

(defn floor
  ([x] (floor x 1))
  ([x p]
   (* p (Math/floor (float (/ x p))))))

(defn ceil
  ([x] (ceil x 1))
  ([x p]
   (* p (Math/ceil (float (/ x p))))))

(defn average
  ([xs]
   (/ (reduce + xs)
      (count xs)))
  ([x1 x2 & more]
   (average (concat [x1 x2] more))))

(defn to-radians [degrees]
  (* degrees (/ Math/PI 180)))

(defn to-degrees [radians]
  (* radians (/ 180 Math/PI)))
