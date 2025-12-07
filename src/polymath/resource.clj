(ns polymath.resource
  (:require clojure.edn
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clj-yaml.core :as yaml]))

(defmacro edn [path]
  (if-let [resource (io/resource path)]
    `(quote ~(clojure.edn/read-string (slurp resource)))
    (throw (ex-info (str "Resource not found: " path) {}))))

(defmacro edn-dir [path & {:keys [nils?] :or {nils? true}}]
  (if-let [dir (io/resource path)]
    (let [files (sequence
                  (comp
                    (filter #(and (.isFile %)
                                  (-> % .getName (str/ends-with? ".edn"))))
                    (map (comp clojure.edn/read-string slurp))
                    (if nils?
                      (comp)
                      (remove #(some nil? (tree-seq coll? seq %)))))
                  (file-seq (io/file dir)))]
      `(quote ~files))
    (throw (ex-info (str "Resource directory not found: " path) {}))))

(defmacro yaml [path]
  (if-let [resource (io/resource path)]
    `(quote ~(yaml/parse-string (slurp resource)))
    (throw (ex-info (str "Resource not found: " path) {}))))

(defmacro yaml-dir [path & {:keys [nils?] :or {nils? true}}]
  (if-let [dir (io/resource path)]
    (let [files (sequence
                  (comp
                    (filter #(and (.isFile %)
                                  (-> % .getName (str/ends-with? ".yaml"))))
                    (map (comp yaml/parse-string slurp))
                    (if nils?
                      (comp)
                      (remove #(some nil? (tree-seq coll? seq %)))))
                  (file-seq (io/file dir)))]
      `(quote ~files))
    (throw (ex-info (str "Resource directory not found: " path) {}))))
