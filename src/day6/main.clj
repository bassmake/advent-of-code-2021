(ns day6.main
  (:require [tools.main :as tools]
            [clojure.string :as str]))

(defn next-fisht-state [fish]
  (cond
    (= 0 fish) [8 6]
    :else [(dec fish)]))

(defn next-population [population]
  (flatten (map next-fisht-state population)))

(defn get-n-th-population [init days-to-pass]
  (letfn [(step
            [previous day]
            {:day (inc day) :population (next-population (:population previous))})]
    (reduce step {:day 0 :population init} (take days-to-pass (range)))))


(defn get-solution [file days-to-pass]
  (let [input (tools/with-open-file (str "src/day6/" file) first)
        first (str/split input #",")
        first-population (map tools/to-int first)
        n-th-population (get-n-th-population first-population days-to-pass)]
    (count (:population n-th-population))))



(comment
  (get-solution "input-sample.txt" 256)
  (get-solution "input.txt" 80)



  (get-n-th-population [3,4,3,1,2] 5)
  (next-fisht-state 0)
  (next-fisht-state 3)
  (next-population [0 3 4 3]))