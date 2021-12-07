(ns day1.main
  (:require [clojure.java.io :as io]))

(defn print-pairs [file]
  (with-open
   [rdr (io/reader (str "src/day1/" file))]
    (let [lines (line-seq rdr)
          pairs (make-pairs lines)]
      (println pairs))))


(print-pairs "test-input.txt")



(def xf (comp (filter odd?) (map inc)))

(->> (range 12)
     (filter odd?)
     (map inc))

(transduce xf + (range 5))

(transduce xf + 100 (range 5))

(into [] xf (range 10))

(sequence xf (range 10))

(defn make-pairs ([]
                  (fn [xf]
                    (let [prev (volatile! ::none)]
                      (fn
                        ([] (xf))
                        ([result] (xf result))
                        ([result input]
                         (let [prior @prev]
                           (vreset! prev input)
                           (if (= prior ::none)
                             []
                             (xf result [prior input]))))))))
  ([coll] (sequence (make-pairs) coll)))

(comment
  (make-pairs [1 2 3 4]))