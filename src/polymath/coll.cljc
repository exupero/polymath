(ns polymath.coll)

(defn assoc-if [m & kvs]
  (->> kvs
       (partition 2)
       (reduce
         (fn [m [k v]]
           (if v
             (assoc m k v)
             m))
         m)))
