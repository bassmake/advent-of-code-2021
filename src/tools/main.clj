(ns tools.main
  (:require [clojure.java.io :as io]))


(defn with-open-file [file fn]
  (with-open
   [rdr (io/reader file)]
    (fn (line-seq rdr))))

(comment
  (defn print-lines [lines]
    (println lines))

  (with-open-file "src/tools/test.txt" print-lines))
