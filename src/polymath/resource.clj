(ns polymath.resource
  (:require clojure.edn
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clj-yaml.core :as yaml]))

(defmacro edn [path]
  (if-let [resource (io/resource path)]
    `(quote ~(clojure.edn/read-string (slurp resource)))
    (throw (ex-info (str "Resource not found: " path) {}))))

(defmacro edn-dir [path]
  (if-let [dir (io/resource path)]
    (let [files (filter #(and (.isFile %)
                              (-> % .getName (str/ends-with? ".edn")))
                        (file-seq (io/file dir)))]
      `(quote ~(map (comp clojure.edn/read-string slurp) files)))
    (throw (ex-info (str "Resource directory not found: " path) {}))))

(defmacro yaml [path]
  (if-let [resource (io/resource path)]
    `(quote ~(yaml/parse-string (slurp resource)))
    (throw (ex-info (str "Resource not found: " path) {}))))

(defmacro yaml-dir [path]
  (if-let [dir (io/resource path)]
    (let [files (filter #(and (.isFile %)
                              (-> % .getName (str/ends-with? ".yaml")))
                        (file-seq (io/file dir)))]
      `(quote ~(map (comp yaml/parse-string slurp) files)))
    (throw (ex-info (str "Resource directory not found: " path) {}))))
