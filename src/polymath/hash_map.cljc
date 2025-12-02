(ns polymath.hash-map)

(defn prefix-keys [m prefix]
  (let [prefix (name prefix)]
    (into {}
          (map (fn [[k v]]
                 [(keyword prefix (name k)) v]))
          m)))
