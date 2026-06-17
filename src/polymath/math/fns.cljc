(ns polymath.math.fns)

(defprotocol Invertible
  (inverse [this]))

(defprotocol Adjustable
  (change-domain [this new-domain])
  (change-range [this new-range]))

(declare linear)

(defrecord Linear [m b domain rng]
  #?(:clj clojure.lang.IFn :cljs IFn)
  (#?(:clj invoke :cljs -invoke) [_ x]
    (+ b (* m x)))
  Invertible
  (inverse [_]
    (linear rng domain))
  Adjustable
  (change-domain [_ new-domain]
    (linear new-domain rng))
  (change-range [_ new-range]
    (linear domain new-range)))

(defn linear [[x1 x2 :as domain] [y1 y2 :as rng]]
  (let [m (/ (- y1 y2) (- x1 x2))
        b (- y1 (* m x1))]
    (->Linear m b domain rng)))

(defn invert [f & args]
  (apply (inverse f) args))
