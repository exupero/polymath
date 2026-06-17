(ns polymath.functions)

(defn linear [[x1 x2] [y1 y2]]
  (let [m (/ (- y1 y2) (- x1 x2))
        b (- y1 (* m x1))]
    (with-meta
      #(+ b (* m %))
      {:inverse #(/ (- % b) m)
       :change-domain #(linear % [y1 y2])
       :change-range #(linear [x1 x2] %)})))

(defn inverse [f]
  (if-let [f' (-> f meta :inverse)]
    f'
    (throw (ex-info "Function does not have an inverse" {:function f}))))

(defn invert [f & args]
  (apply (inverse f) args))

(defn change-domain [f new-domain]
  (if-let [f' (-> f meta :change-domain)]
    (f' new-domain)
    (throw (ex-info "Function does not support changing domain" {:function f}))))

(defn change-range [f new-range]
  (if-let [f' (-> f meta :change-range)]
    (f' new-range)
    (throw (ex-info "Function does not support changing range" {:function f}))))
