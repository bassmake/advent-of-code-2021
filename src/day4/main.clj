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

(defn mark-number [number board]
  (map #(replace {number nil} %) board))

(defn make-draw [boards number]
  (map #(mark-number number %) boards))

(defn wins? [board]
  (letfn [(wins-lines? [b] (some? (some #(every? nil? %) b)))]
    (or (wins-lines? board)
        (wins-lines? (apply map list board)))))

(defn score [board number]
  (let [flattened (flatten board)
        summed (reduce + (map #(or % 0) flattened))]
    (* number summed)))

(defn first-winning-board [file]
  (let [input (load-input file)
        numbers (:numbers input)
        boards (:boards input)]
    (reduce (fn [boards number]
              (let [new-boards (make-draw boards number)
                    winning-board (first (filter wins? new-boards))]
                (if (some? winning-board)
                  (reduced {:board winning-board :number number})
                  new-boards)))
            boards numbers)))

(defn last-winning-board [file]
  (let [input (load-input file)
        numbers (:numbers input)
        boards (:boards input)]
    (reduce (fn [boards number]
              (let [new-boards (make-draw boards number)
                    not-winning-boards (remove wins? new-boards)]
                ;; (pp/pprint {:number number :new-boards new-boards :winning-board winning-board})
                (if (empty? not-winning-boards)
                  (reduced {:board (first new-boards) :number number})
                  not-winning-boards)))
            boards numbers)))


(defn solution [fn file]
  (let [winning-board (fn file)]
    ;; {:winning winning-board}))
    {:winning winning-board
     :score (score (:board winning-board) (:number winning-board))}))

(comment

  (pp/pprint (solution last-winning-board "input-sample.txt"))
  (pp/pprint (solution last-winning-board "input.txt"))

  (pp/pprint (solution first-winning-board "input-sample.txt"))
  (pp/pprint (solution first-winning-board "input.txt"))

  (replace {3 nil} [1 2 4 3])

  (pp/pprint (load-input "input-sample.txt"))

  (wins? [[1 3 4] [3 2 5]])
  (wins? [[3 2 5] [nil nil nil]])
  (wins? [[nil 2 5] [nil 1 3]])
  (apply map list [[nil 2 5] [nil 1 3]])

  (mark-number 3 [[1 3 4] [3 2 5]])

  (load-board-line " 9 18 13 17  5"))
