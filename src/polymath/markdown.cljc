(ns polymath.markdown
  (:require [markdown.core :as md]))

#?(:clj
   (defmacro md->replicant [s]
     [:div {:innerHTML (md/md-to-html-string s)}])
   :cljs
   (defn md->replicant [s]
     [:div {:innerHTML (md/md-to-html-string s)}]))
