(ns polymath.dom)

(defn ancestor [nm node]
  (let [nm (name nm)]
    (loop [node node]
      (cond
        (nil? node) nil
        (= nm (.-nodeName node)) node
        :else (recur (.-parentNode node))))))
