(ns tools.main
  (:require [clojure.java.io :as io]))


(defn with-open-file [file fn]
  (with-open
   [rdr (io/reader file)]
    (fn (line-seq rdr))))

(defn to-int [str]
  (Integer/parseInt str))

(comment
  (defn print-lines [lines]
    (println lines))

  (to-int "3")

  (with-open-file "src/tools/test.txt" print-lines))
