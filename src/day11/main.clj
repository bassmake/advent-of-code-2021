(ns day11.main
  (:require
   [clojure.pprint :as pp]
   [clojure.string :as s]))

(defn increase-energy [octopuses]
  (map (fn [row] (map inc row)) octopuses))
(comment
  (increase-energy [[1 2] [3 4]]))


(defn reset-flashed [octopuses]
  (map (fn [row] (map (fn [octopus] (or octopus 0)) row)) octopuses))
(comment
  (reset-flashed [[nil 2] [3 nil]]))