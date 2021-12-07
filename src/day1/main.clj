(ns day1.main
  (:require [clojure.java.io :as io]))

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

(defn compare-change [[first second]]
  (cond
    (> first second) ::decreased
    (< first second) ::increased
    (= first second) ::same
    :else ::unknown))

(defn provide-solution [file]
  (with-open
   [rdr (io/reader (str "src/day1/" file))]
    (let [changes (->> (line-seq rdr)
                       (map #(Integer/parseInt %))
                       (make-pairs)
                       (map compare-change))]
      (count (filter #(= ::increased %) changes)))))

(provide-solution "input.txt")

(comment
  (make-pairs [1 2 3 4])
  (compare-change [2 3])
  (compare-change [2])
  (compare-change [])
  (provide-solution "input-sample.txt"))