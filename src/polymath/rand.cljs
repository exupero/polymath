(ns polymath.rand
  (:refer-clojure :exclude [nth shuffle])
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
