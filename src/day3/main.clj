(ns day3.main
  (:require [tools.main :as tools]
            [clojure.math.numeric-tower :as math]))

(defn parse [line]
  (map #(= \1 %) (seq (char-array line))))

(defn from-binary [binary]
  (let [powers (map #(math/expt 2 %) (range))]
    (->> binary
         (reverse)
         (map vector powers)
         (map #(if (true? (last %)) (first %) 0))
         (reduce +))))

(defn sum [total line]
  (let [pairs (map vector (concat total (repeat 0)) line)]
    (map (fn [[value bit]] (if (true? bit) (inc value) value)) pairs)))

(defn increase-counter [counter line]
  (let [total (inc (:total counter))
        sum (sum (:sum counter) line)]
    {:total total :sum sum}))

(defn solution1 [lines]
  (reduce increase-counter {:total 0 :sum []} (map parse lines)))

(defn to-tree [line]
  (let [[bit & rest] line]
    (cond
      (nil? bit) {:count 1}
      (true? bit) {:count 1 :1 (to-tree rest)}
      :else {:count 1 :0 (to-tree rest)})))

(defn merge-trees [tree1 tree2]
  (cond
    (nil? tree1) tree2
    (nil? tree2) tree1
    :else {:count (+ (:count tree1) (:count tree2))
           :1 (merge-trees (:1 tree1) (:1 tree2))
           :0 (merge-trees (:0 tree1) (:0 tree2))}))

(defn provide-solution1 [file]
  (let [{total :total sum :sum} (tools/with-open-file (str "src/day3/" file) solution1)
        half (/ total 2)
        gamma_bin (map #(> half %) sum)
        epsilon_bin (map #(< half %) sum)
        gamma (from-binary gamma_bin)
        epsilon (from-binary epsilon_bin)]
    {:gamma gamma :epsilon epsilon :solution (* gamma epsilon)}))

(defn solution2-tree [lines]
  (reduce merge-trees (map (comp to-tree parse) lines)))

(defn oxygen [{ones :1 zeroes :0}]
  (if (and (nil? ones) (nil? zeroes)) []
      (let [count1 (:count ones)
            count0 (:count zeroes)]
        (cond
          (and (nil? count1) (nil? count0))  []
          (nil? count1) (cons false (oxygen zeroes))
          (nil? count0) (cons true (oxygen ones))
          (< count1 count0) (cons false (oxygen zeroes))
          :else (cons true (oxygen ones))))))

(defn co2 [{ones :1 zeroes :0}]
  (if (and (nil? ones) (nil? zeroes)) []
      (let [count1 (:count ones)
            count0 (:count zeroes)]
        (cond
          (and (nil? count1) (nil? count0)) (do (println "both nil") [])
          (nil? count1) (do (println "1 nil") (cons false (co2 zeroes)))
          (nil? count0) (do (println "0 nil") (cons true (co2 ones)))
          (< count1 count0) (do (println "aa") (cons true (co2 ones)))
          :else (cons false (co2 zeroes))))))

(defn provide-solution2 [file]
  (let [tree (tools/with-open-file (str "src/day3/" file) solution2-tree)
        oxygen-level ((comp from-binary oxygen) tree)
        co2-level ((comp from-binary co2) tree)]
    {:oxygen oxygen-level
     :co2 co2-level
     :solution (* oxygen-level co2-level)}))

(comment

  (provide-solution2 "input-sample.txt")
  (provide-solution2 "input.txt")

  (provide-solution1 "input-sample.txt")
  (provide-solution1 "input.txt")

  (parse "1010001")

  (from-binary (parse "01001"))

  (sum [1 2 3 4] (parse "1010001"))

  (to-tree [])
  (to-tree [true true false])
  (to-tree [false true false])


  (merge-trees (to-tree [true true false]) (to-tree [false true false]))
  (merge-trees (to-tree [true true false]) (to-tree [true true false])))