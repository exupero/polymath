(ns polymath.viewport
  (:require [polymath.svg :as svg]))

(defn pan [event viewport node coord]
  (merge viewport
         (case event
           :start {:panning? true
                   :pan-start (svg/coord node coord)}
           :move (let [{[sx sy] :pan-start [cx cy] :center :keys [width height]} viewport
                       [px py] (svg/coord node coord)
                       cx (+ cx (- sx px))
                       cy (+ cy (- sy py))]
                   {:center [cx cy]
                    :x (- cx (/ width 2))
                    :y (- cy (/ height 2))
                    :width width
                    :height height})
           :end {:panning? false})))
