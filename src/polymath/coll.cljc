(ns polymath.coll)

(defn assoc-some [m & kvs]
  (->> kvs
       (partition 2)
       (reduce
         (fn [m [k v]]
           (if v
             (assoc m k v)
             m))
         m)))

(defn index-by [f coll]
  (into {} (map (juxt f identity)) coll))

(defn project [fns & args]
  (apply map (fn [f & more]
               (apply f more))
         fns args))
