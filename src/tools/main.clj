(ns tools.main
  (:require [clojure.java.io :as io]
            [clojure.pprint :as pp]))


(defn with-open-file [file fn]
  (with-open
   [rdr (io/reader file)]
    (fn (line-seq rdr))))

(defn to-int [str]
  (Integer/parseInt str))

(defn log [data log?]
  (when log? (pp/pprint data)))

(comment
  (defn print-lines [lines]
    (println lines))

  (to-int "3")

  (with-open-file "src/tools/test.txt" print-lines))
