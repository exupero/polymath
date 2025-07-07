(ns polymath.rand
  (:refer-clojure :exclude [nth shuffle take])
  (:require ["seedrandom" :as seedrandom]
            [goog.array :as garray]))

(def rng seedrandom)

(defn between [rng low high]
  (+ low (* (rng) (- high low))))

(defn int-between [rng low high]
  (int (between rng low high)))

(defn nth [rng coll]
  (clojure.core/nth coll (int (between rng 0 (count coll)))))

(defn shuffle [rng coll]
  (let [a (to-array coll)]
    (garray/shuffle a rng)
    (vec a)))

(defn take
  ([rng coll] (take rng 0 coll))
  ([rng min coll]
   (let [n (int-between rng min (count coll))]
     (clojure.core/take n coll))))

(defn split
  ([rng coll] (split rng 0 (count coll) coll))
  ([rng min coll] (split rng min (count coll) coll))
  ([rng min max coll]
   (let [n (int-between rng min max)]
     (split-at n coll))))
