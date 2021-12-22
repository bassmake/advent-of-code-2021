(ns day11.main
  (:require
   [clojure.pprint :as pp]
   [clojure.string :as s]))


(defn map-opuses [f opuses]
  (map (fn [row] (map f row)) opuses))

(defn increase-energy [opuses]
  (map-opuses inc opuses))
(comment
  (increase-energy [[1 2] [3 4]]))

(defn to-flash [opuses]
  (->> (map-opuses (fn [opus] (if (= opus 9) 1 0)) opuses)
       (flatten)
       (reduce +)))
(comment
  (to-flash [[9 2 9] [1 3 4]]))

(defn flash [opuses]
  opuses)

(defn reset-flashed [opuses]
  (map-opuses (fn [opus] (or opus 0)) opuses))
(comment
  (reset-flashed [[nil 2] [3 nil]]))


(defn step [opuses]
  (let [energized (increase-energy opuses)]
    (loop [ops energized flashes 0])



    {:flashes (:flashes flashed)
     :opuses (reset-flashed  (:opuses opuses))}))


(comment
  (defn fact [n]
    (loop [cnt n acc 1]
      (if (pos? cnt)
        (recur (dec cnt) (* acc cnt)) acc)))

  (fact 16))