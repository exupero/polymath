(ns polymath.hash-map)

(defn namespace-keys [m nmsp]
  (let [nmsp (name nmsp)]
    (into {}
          (map (fn [[k v]]
                 [(keyword nmsp (name k)) v]))
          m)))
