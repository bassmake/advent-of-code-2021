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
    (Math/round (double (/ summed total)))))

(defn compute-median [positions]
  (let [sorted (sort positions)
        total (count positions)
        position (Math/floor (/ total 2))]
    (nth sorted position)))

(defn total-fuel-consumption1 [grouped-positions desired]
  (reduce + (map (fn [[k v]] (* v (Math/abs (- k desired)))) grouped-positions)))

(defn fuel-consumption2 [from to]
  (let [total (Math/abs (- to from))]
    (/ (* total (+ total 1)) 2)))

(defn total-fuel-consumption2 [grouped-positions desired]
  (reduce + (map (fn [[k v]] (* v (fuel-consumption2 k desired))) grouped-positions)))

(defn provide-solution1 [file]
  (let [positions (load-positions file)
        median (compute-median positions)
        grouped (group-positions positions)]
    (total-fuel-consumption1 grouped median)))

(defn provide-solution2 [file]
  (let [positions (load-positions file)
        grouped (group-positions positions)
        average (compute-average grouped)]
    (total-fuel-consumption2 grouped average)))

(comment

  (provide-solution2 "input-sample.txt")
  (provide-solution2 "input.txt")

  (provide-solution1 "input-sample.txt")
  (provide-solution1 "input.txt")

  ;; 474 is average, 473 has the lowest fuel consumtion
  (total-fuel-consumption2 (group-positions (load-positions "input.txt")) 474)
  (total-fuel-consumption2 (group-positions (load-positions "input.txt")) 473)
  (total-fuel-consumption2 (group-positions (load-positions "input.txt")) 475)

  (fuel-consumption2 1 5)

  (compute-average (group-positions (load-positions "input.txt")))
  (compute-average (group-positions (load-positions "input-sample.txt")))

  (Math/round (double 3/2))

  (total-fuel-consumption1 (load-positions "input-sample.txt") 2)
  (total-fuel-consumption1 (load-positions "input-sample.txt") 5)


  (compute-median (load-positions "input-sample.txt"))
  (compute-median (load-positions "input.txt"))

  (compute-average (load-positions "input-sample.txt"))
  (compute-average (load-positions "input.txt"))


  (total-count (load-positions "input-sample.txt"))
  (total-count (load-positions "input.txt"))

  (load-positions "input-sample.txt")
  (load-positions "input.txt"))