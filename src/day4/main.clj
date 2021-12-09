(ns day4.main
  (:require [tools.main :as tools]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :as pp]))


(defn load-board-line [line]
  (->> (str/split line #"\s")
       (remove str/blank?)
       (map tools/to-int)))

(defn load-board [input]
  (map load-board-line input))

(defn load-input [file]
  (let [lines (with-open
               [rdr (io/reader (str "src/day4/" file))] (doall (line-seq rdr)))
        sections (filter #(not= [""] %) (partition-by empty? lines))
        numbers (first (first sections))
        boards (next sections)]
    {:numbers (map #(Integer/parseInt %) (str/split numbers #","))
     :boards (map load-board boards)}))

(defn solution1 [file]
  (let [input (load-input file)]
    input))

(comment

  (pp/pprint (solution1 "input-sample.txt"))

  (load-board-line " 9 18 13 17  5"))
