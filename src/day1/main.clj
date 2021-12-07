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

(defn append-to-pair [pair to-add]
  (cond
    (empty? pair) [to-add]
    :else [(last pair) to-add]))

(defn make-triplets ([]
                     (fn [xf]
                       (let [prev (volatile! [])]
                         (fn
                           ([] (xf))
                           ([result] (xf result))
                           ([result input]
                            (let [prior @prev]
                              (vreset! prev (append-to-pair prior input))
                              (if (< (count prior) 2)
                                []
                                (xf result (conj prior input)))))))))
  ([coll] (sequence (make-triplets) coll)))

(defn compare-change [[first second]]
  (cond
    (> first second) ::decreased
    (< first second) ::increased
    (= first second) ::same
    :else ::unknown))

(defn provide-solution-1 [file]
  (with-open
   [rdr (io/reader (str "src/day1/" file))]
    (let [changes (->> (line-seq rdr)
                       (map #(Integer/parseInt %))
                       (make-pairs)
                       (map compare-change))]
      (count (filter #(= ::increased %) changes)))))


(defn provide-solution-2 [file]
  (with-open
   [rdr (io/reader (str "src/day1/" file))]
    (let [changes (->> (line-seq rdr)
                       (map #(Integer/parseInt %))
                       (make-triplets)
                       (map #(reduce + %))
                       (make-pairs)
                       (map compare-change))]
      (count (filter #(= ::increased %) changes)))))


(provide-solution-1 "input.txt")
(provide-solution-2 "input.txt")


(comment
  (make-pairs [1 2 3 4])

  (append-to-pair [] 2)
  (append-to-pair [1] 2)
  (append-to-pair [3 4] 1)
  (append-to-pair [3 4 9] 1)

  (make-triplets [1 2 3 4 5 6])

  (compare-change [2 3])
  (compare-change [2])
  (compare-change [])


  (provide-solution-1 "input-sample.txt")
  (provide-solution-2 "input-sample.txt"))