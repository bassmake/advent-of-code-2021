(ns day3.main
  (:require [tools.main :as tools]
            [clojure.math.numeric-tower :as math]))

(defn parse [line]
  (map #(= \1 %) (seq (char-array line))))

(defn sum [total line]
  (let [pairs (map vector (concat total (repeat 0)) line)]
    (map (fn [[value bit]] (if (true? bit) (inc value) value)) pairs)))
    ;; pairs))

(defn increase-counter [counter line]
  (let [total (inc (:total counter))
        sum (sum (:sum counter) line)]
    {:total total :sum sum}))

(defn solution [lines]
  (reduce increase-counter {:total 0 :sum []} (map parse lines)))

(defn from-binary [binary]
  (let [powers (map #(math/expt 2 %) (range))]
    (->> binary
         (reverse)
         (map vector powers)
         (map #(if (true? (last %)) (first %) 0))
         (reduce +))))
    ;; powers))

;; pairs (map vector binary powers)
;; numbers (map)

(defn provide-solution [solution file]
  (let [{total :total sum :sum} (tools/with-open-file (str "src/day3/" file) solution)
        half (/ total 2)
        gamma_bin (map #(> half %) sum)
        epsilon_bin (map #(< half %) sum)
        gamma (from-binary gamma_bin)
        epsilon (from-binary epsilon_bin)]
    {:gamma gamma :epsilon epsilon :solution (* gamma epsilon)}))

(comment

  (parse "1010001")

  (from-binary (parse "01001"))

  (sum [1 2 3 4] (parse "1010001"))

  (provide-solution solution "input-sample.txt")

  (provide-solution solution "input.txt"))