(ns polymath.dom
  (:require [clojure.string :as str]))

(defn ancestor [nm node]
  (let [nm (name nm)]
    (loop [node node]
      (cond
        (nil? node)
        , nil
        (or (= (str/lower-case nm) (str/lower-case (.-nodeName node)))
            (.matches node nm))
        , node
        :else
        , (recur (.-parentNode node))))))
