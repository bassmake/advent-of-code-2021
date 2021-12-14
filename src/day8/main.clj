(ns day8.main
  (:require [tools.main :as t]
            [clojure.set :as set]
            [clojure.pprint :as pp]
            [clojure.string :as s]))


(defn parse-line [line]
  (let [parts (s/split line #"\|")
        patterns (s/split (s/trim (first parts)) #" ")
        output (s/split (s/trim (last parts)) #" ")]
    {:patterns patterns
     :output output}))


(defn only-subset
  ([patterns contains]
   (->> patterns
        (filter #(set/subset? contains %))
        (first)))
  ([patterns not-contains contains]
   (let [filtered (filter #(not= not-contains %) patterns)]
     (only-subset filtered contains))))

(defn produce-mapping [patterns]
  (letfn [(patterns-of-size [size] (map set (get patterns size)))
          (single [size]  (first (patterns-of-size size)))]
    (let [one (single 2)
          seven (single 3)
          eight (single 7)
          four (single 4)
          three (only-subset (patterns-of-size 5) one)
          nine (only-subset (patterns-of-size 6) four)
          three (only-subset (patterns-of-size 5) three one)]
      {:one one
       :three three
       :four four
       :seven seven
       :eight eight
       :nine nine})))
(comment
  (produce-mapping {5 ["cdfbe" "gcdfa" "fbcad"]
                    3 ["dab"]
                    7 ["acedgfb"]
                    2 ["ab"]
                    6 ["cefabd" "cdfgeb" "cagedb"]
                    4 ["eafb"]}))


(defn parse-and-group-line  [line]
  (let [parsed (parse-line line)
        patterns (:patterns parsed)
        grouped-patterns (group-by count patterns)
        output (:output parsed)]
    {:patterns grouped-patterns
     :mapping (produce-mapping grouped-patterns)
     :ouptput output}))

(defn solution1 [file]
  (->> (s/split-lines (slurp (str "src/day8/" file)))
       (map parse-line)
       (map :output)
       (flatten)
       (map count)
       (filter (fn [length] (some #{length} [2 3 4 7])))
       (count)))


(defn solution2 [file]
  (->> (s/split-lines (slurp (str "src/day8/" file)))
       (map parse-and-group-line)))


(comment

  (pp/pprint (solution2 "input-sample.txt"))
  (solution2 "input.txt")

  (solution1 "input-sample.txt")
  (solution1 "input.txt")


  (set "abc")

  (count "fcge")
  (contains? [2 3 4 7] 7)

  (parse-line "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe"))
