(ns polymath.spy)

(defn basic [x]
  `(let [x# ~x]
     (println
       (pr-str '~x)
       "=>"
       (pr-str x#))
     x#))

(defn ansi [x]
  `(let [x# ~x]
     (println
       "\u001b[38;5;35m"
       (pr-str '~x)
       "\n\u001b[38;5;15m"
       (pr-str x#))
     x#))

(defn js-console-light [x]
  `(let [x# ~x]
     (js/console.log
       "%c%s\n%c%s"
       "color:mediumseagreen"
       (pr-str '~x)
       "color:black"
       (pr-str x#))
     x#))

(defn js-console-dark [x]
  `(let [x# ~x]
     (js/console.log
       "%c%s\n%c%s"
       "color:mediumseagreen"
       (pr-str '~x)
       "color:lightgray"
       (pr-str x#))
     x#))
