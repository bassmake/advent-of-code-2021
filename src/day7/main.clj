(ns day7.main
  (:require [tools.main :as tools]
            [clojure.string :as str]))


(defn load-positions [file]
  (let [input (tools/with-open-file (str "src/day7/" file) first)
        line (str/split input #",")]
    (map tools/to-int line)))

(defn group-positions [positions]
  (let [grouped (group-by identity positions)]
    (into {} (for [[k, v] grouped] [k (count v)]))))

(defn total-count [positions]
  (reduce + (map (fn [[_ v]] v) positions)))

(defn compute-average [grouped-positions]
  (let [summed (reduce + (map (fn [[k v]] (* k v)) grouped-positions))
        total (total-count grouped-positions)]
    (Math/floor  (/ summed total))))

(defn compute-median [positions]
  (let [sorted (sort positions)
        total (count positions)
        position (Math/floor (/ total 2))]
    (nth sorted position)))

(defn fuel-consumption [grouped-positions desired]
  (reduce + (map (fn [[k v]] (* v (Math/abs (- k desired)))) grouped-positions)))

(defn provide-solution [file]
  (let [positions (load-positions file)
        median (compute-median positions)
        grouped (group-positions positions)]
    (fuel-consumption grouped median)))

(comment
  
  (provide-solution "input-sample.txt")
  (provide-solution "input.txt")

  (fuel-consumption (load-positions "input-sample.txt") 2)
  (fuel-consumption (load-positions "input-sample.txt") 5)


  (compute-median (load-positions "input-sample.txt"))
  (compute-median (load-positions "input.txt"))

  (compute-average (load-positions "input-sample.txt"))
  (compute-average (load-positions "input.txt"))


  (total-count (load-positions "input-sample.txt"))
  (total-count (load-positions "input.txt"))

  (load-positions "input-sample.txt")
  (load-positions "input.txt"))