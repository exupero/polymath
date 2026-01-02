(ns polymath.hash-map)

(defn compact [m]
  (into {} (remove (comp nil? val)) m))

(defn namespace-keys [m nmsp]
  (let [nmsp (name nmsp)]
    (into {}
          (map (fn [[k v]]
                 [(keyword nmsp (name k)) v]))
          m)))
