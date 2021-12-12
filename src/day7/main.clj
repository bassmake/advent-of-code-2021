(ns day7.main
  (:require [tools.main :as tools]
            [clojure.string :as str]))


(defn load-positions [file]
  (let [input (tools/with-open-file (str "src/day7/" file) first)
        line (str/split input #",")
        fishes (map tools/to-int line)
        grouped (group-by identity fishes)]
    (into {} (for [[k, v] grouped] [k (count v)]))))


(defn total-count [positions]
  (reduce + (map (fn [[_ v]] v) positions)))

(defn compute-average [positions]
  (let [summed (reduce + (map (fn [[k v]] (* k v)) positions))
        total (total-count positions)]
    (Math/floor  (/ summed total))))

(defn fuel-consumption [positions desired]
  (reduce + (map (fn [[k v]] (* v (Math/abs (- k desired)))) positions)))

(comment

  (fuel-consumption (load-positions "input-sample.txt") 2)
  (fuel-consumption (load-positions "input-sample.txt") 5)

  (compute-average (load-positions "input-sample.txt"))
  (compute-average (load-positions "input.txt"))


  (total-count (load-positions "input-sample.txt"))
  (total-count (load-positions "input.txt"))

  (load-positions "input-sample.txt")
  (load-positions "input.txt"))