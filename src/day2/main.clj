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

(defn aim-move [position command]
  (let [{horizontal :horizontal depth :depth aim :aim} position {direction :direction units :units} command]
    (cond
      (= direction "forward") {:horizontal (+ horizontal units) :depth (+ depth (* aim units)) :aim aim}
      (= direction "up") {:horizontal horizontal :depth depth :aim (- aim units)}
      (= direction "down") {:horizontal horizontal :depth depth :aim (+ aim units)}
      :else (throw (AssertionError. (str "Wrong input." position command))))))

(defn solution1 [lines]
  (reduce move {:horizontal 0 :depth 0} (map parse lines)))

(defn solution2 [lines]
  (reduce aim-move {:horizontal 0 :depth 0 :aim 0} (map parse lines)))

(defn provide-solution [solution file]
  (let [{horizontal :horizontal depth :depth} (tools/with-open-file (str "src/day2/" file) solution)]
    (* horizontal depth)))

(comment
  (= (provide-solution solution1 "input-sample.txt") 150)
  (= (provide-solution solution1 "input.txt") 2073315)

  (= (provide-solution solution2 "input-sample.txt") 900)
  (= (provide-solution solution2 "input.txt") 1840311528)


  (move {:horizontal 1 :depth 3} {:direction "down" :units 23})

  (parse "forward 3"))
