(ns day2.main
  (:require [tools.main :as tools]
            [clojure.string :as str]))

(defn parse [line]
  (let [[direction units] (str/split line #" ")]
    {:direction direction :units (Integer/parseInt units)}))

(defn move [position command]
  (let [{horizontal :horizontal depth :depth} position {direction :direction units :units} command]
    (cond
      (= direction "forward") {:horizontal (+ horizontal units) :depth depth}
      (= direction "up") {:horizontal horizontal :depth (- depth units)}
      (= direction "down") {:horizontal horizontal :depth (+ depth units)}
      :else (throw (AssertionError. (str "Wrong input." position command))))))

(defn solution1 [lines]
  (reduce move {:horizontal 0 :depth 0} (map parse lines)))

(defn provide-solution1 [file]
  (let [{horizontal :horizontal depth :depth} (tools/with-open-file (str "src/day2/" file) solution1)]
    (* horizontal depth)))

(comment
  (provide-solution1 "input-sample.txt")

  (provide-solution1 "input.txt")

  (move {:horizontal 1 :depth 3} {:direction "down" :units 23})

  (parse "forward 3"))
