(ns day3.main
  (:require [tools.main :as tools]))

(defn parse [line]
  (map #(= \1 %) (seq (char-array line))))

(defn add-line [counter line]
  
  )

(defn count-ones [lines]
  (reduce add-line (map parse lines)))

(defn solution [lines]
  (println lines))

(defn provide-solution [solution file]
  (tools/with-open-file (str "src/day3/" file) solution))

(comment

  (parse "1010001")

  (provide-solution solution "input-sample.txt")
  (provide-solution solution "input.txt"))