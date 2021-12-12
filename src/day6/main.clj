(ns day6.main
  (:require [tools.main :as tools]
            [clojure.string :as str]))

(defn next-population [population]
  {0 (get population 1 0)
   1 (get population 2 0)
   2 (get population 3 0)
   3 (get population 4 0)
   4 (get population 5 0)
   5 (get population 6 0)
   6 (+ (get population 7 0) (get population 0 0))
   7 (get population 8 0)
   8 (get population 0 0)})

(defn get-n-th-population [init days-to-pass]
  (letfn [(step
            [previous _]
            (next-population previous))]
    (reduce step init (take days-to-pass (range)))))

(defn count-population [population]
  (reduce + (map (fn [[_ v]] v) population)))

(defn load-first-population [file]
  (let [input (tools/with-open-file (str "src/day6/" file) first)
        line (str/split input #",")
        fishes (map tools/to-int line)
        grouped (group-by identity fishes)]
    (into {} (for [[k, v] grouped] [k (count v)]))))

(defn get-solution [file days-to-pass]
  (let [first-population (load-first-population file)
        n-th-population (get-n-th-population first-population days-to-pass)]
    (count-population n-th-population)))

(comment
  (get-solution "input-sample.txt" 256)
  (get-solution "input.txt" 256)


  (load-first-population "input-sample.txt")
  (get 1 [0 3, 1 4, 2 3, 3 1,4 2])

  (get-n-th-population {1 1, 2 1, 3 2, 4 1} 1)
  (next-population {2 1 0 4}))