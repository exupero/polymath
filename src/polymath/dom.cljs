(ns polymath.dom
  (:require [clojure.string :as str]))

(defn ancestor [nm node]
  (let [nm (str/lower-case (name nm))]
    (loop [node node]
      (cond
        (nil? node) nil
        (= nm (str/lower-case (.-nodeName node))) node
        :else (recur (.-parentNode node))))))
