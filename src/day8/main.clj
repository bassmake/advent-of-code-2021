(ns day8.main
  (:require [tools.main :as t]
            [clojure.string :as s]))


(defn parse-line [line]
  (let [parts (s/split line #"\|")
        patterns (s/split (s/trim (first parts)) #" ")
        output (s/split (s/trim (last parts)) #" ")]
    {:patterns patterns
     :output output}))

(defn solution1 [file]
  (->> (s/split-lines (slurp (str "src/day8/" file)))
       (map parse-line)
       (map :output)
       (flatten)
       (map count)
       (filter (fn [length] (some #{length} [2 3 4 7])))
       (count)))



(comment
  
  (solution1 "input-sample.txt")
  (solution1 "input.txt")

  (count "fcge")
  (contains? [2 3 4 7] 7)

  (parse-line "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe"))
