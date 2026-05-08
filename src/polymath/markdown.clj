(ns polymath.markdown
  (:require [markdown.core :as md]))

(defmacro md->replicant [s]
  [:div {:innerHTML (md/md-to-html-string s)}])
