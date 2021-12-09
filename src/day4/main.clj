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

(defn mark-number [draw]
  (map #(replace {(:number draw) nil} %) (:board draw)))

(defn wins? [board]
  (letfn [(wins-lines? [b] (some? (some #(every? nil? %) b)))]
    (or (wins-lines? board)
        (wins-lines? (apply map list board)))))

(defn score [board number])

(defn solution1 [file]
  (let [input (load-input file)
        numbers (:numbers input)
        boards (:boards input)
        draws (for [number numbers board boards] {:number number :board board})]
    ;; draws))
    (map mark-number  draws)))

(comment

  (pp/pprint (solution1 "input-sample.txt"))

  (replace {3 nil} [1 2 4 3])

  (pp/pprint (load-input "input-sample.txt"))

  (wins? [[1 3 4] [3 2 5]])
  (wins? [[3 2 5] [nil nil nil]])
  (wins? [[nil 2 5] [nil 1 3]])
  (apply map list [[nil 2 5] [nil 1 3]])

  (load-board-line " 9 18 13 17  5"))
