(ns polymath.svg
  (:require [clojure.string :as str]))

(defn svg [attrs & children]
  (into [:svg (assoc attrs
                     :xmlns "http://www.w3.org/2000/svg"
                     :xmlns:xhtml "http://www.w3.org/1999/xhtml")]
        children))

(defn view-box
  ([w h] (view-box 0 0 w h))
  ([x y w h]
   (str x " " y " " w " " h)))

(defn translate
  ([[x y]] (translate x y))
  ([x y]
   (str "translate(" x "," y ")")))

(defn rotate [d]
  (str "rotate(" d ")"))

(defn scale
  ([s]
   (str "scale(" s ")"))
  ([x y]
   (str "scale(" x ", " y ")")))

(defn transformations [ts]
  (str/join (reverse ts)))

(defn path [pts]
  (transduce
    (comp
      (map (fn [[x y]]
             (str x \, y)))
      (interpose "L"))
    str "M" pts))

(defn path-multi [lines]
  (str/join (map path lines)))

(defn path-closed [pts]
  (str (path pts) "Z"))

(defn points
  ([pts]
   (transduce
     (comp
       (map (fn [[px py]]
              (str px \, py)))
       (interpose " "))
     str pts))
  ([[x y] pts]
   (transduce
     (comp
       (map (fn [[px py]]
              (str (x px) \, (y py))))
       (interpose " "))
     str pts)))

(def extent (juxt (partial apply min) (partial apply max)))

(defn bounds [xys]
  (let [[left right] (extent (map first xys))
        [top bottom] (extent (map second xys))]
    [left top (- right left) (- bottom top)]))

(defn zoom-to
  ([bounds window]
   (zoom-to bounds window 1))
  ([[x y w h] [x' y' w' h'] scale-fraction]
   (let [s (min (/ w' w) (/ h' h))]
     (str (translate (float (+ x' (/ w' 2)))
                     (float (+ y' (/ h' 2))))
          (scale (float (* scale-fraction s)))
          (translate (float (- (+ x (/ w 2))))
                     (float (- (+ y (/ h 2)))))))))


(defn hsl [h s l]
  (str "hsl(" (float h) "," (float s) "%," (float l) "%)"))

#?(:cljs
    (defn blob [svg-content]
      (js/Blob. #js [svg-content] #js {:type "image/svg+xml"})))

#?(:cljs
    (defn canvas-of-size [width height]
      (let [canvas (js/document.createElement "canvas")]
        (set! (.-width canvas) width)
        (set! (.-height canvas) height)
        (set! (.. canvas -style -width) (str width "px"))
        (set! (.. canvas -style -height) (str height "px"))
        canvas)))

#?(:cljs
    (defn canvas-blob [svg-blob {:keys [width height scale]}]
      (let [scale (* scale (or js/window.devicePixelRatio 1))
            canvas (canvas-of-size (* width scale) (* height scale))
            context (doto (.getContext canvas "2d")
                      (.setTransform scale 0 0 scale 0 0))
            img (js/Image.)
            url (js/URL.createObjectURL svg-blob)]
        (set! (.-src img) url)
        (js/Promise. (fn [res]
                       (set! (.-onload img)
                             (fn []
                               (.drawImage context img 0 0)
                               (js/URL.revokeObjectURL url)
                               (.toBlob canvas res))))))))

#?(:cljs
    (defn download! [blob filename]
      (let [link (js/document.createElement "a")]
        (set! (.-download link) filename)
        (set! (.. link -style -display) "none")
        (js/document.body.appendChild link)
        (try
          (let [url (js/URL.createObjectURL blob)]
            (set! (.-href link) url)
            (set! (.-onclick link)
                  (fn []
                    (js/requestAnimationFrame #(js/URL.revokeObjectURL url))))
            (.click link))
          (catch :default e
            (js/console.error e))
          (finally
            (js/document.body.removeChild link))))))

#?(:cljs
    (defn coord
      "Converts a clientX and clientY coordinate pair into a coordinate in the given SVG element."
      [^js/SVGSVGElement svg [x y]]
      (when svg
        (let [pt (.createSVGPoint svg)
              _ (set! (.-x pt) x)
              _ (set! (.-y pt) y)
              pt' (.matrixTransform pt (.inverse (.getScreenCTM svg)))]
          [(.-x pt') (.-y pt')]))))
