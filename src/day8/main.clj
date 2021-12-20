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

(defn produce-mapping [patterns]
  (let [four (first (filter #(= 4 (count %)) patterns))]
    (letfn [(identify-values [[from count]]
              [from (case count
                      4 \e
                      6 \b
                      7 (if (some #{from} four) \d \g)
                      8 (if (some #{from} four) \c \a)
                      9 \f)])]
      (->> patterns
           (map vec)
           (flatten)
           (group-by identity)
           (map (fn [[k v]] [k (count v)]))
           (map (fn [count] (identify-values count)))
           (into {})))))
(comment
  (produce-mapping (:patterns (parse-line "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce"))))


(defn decode [input mapping]
  (let [decoded (->> (vec input)
                     (map mapping)
                     (sort)
                     (apply str))]
    (case decoded
      "abcefg" 0
      "cf" 1
      "acdeg" 2
      "acdfg" 3
      "bcdf" 4
      "abdfg" 5
      "abdefg" 6
      "acf" 7
      "abcdefg" 8
      "abcdfg" 9)))

(comment ; d-a e-b a-c f-d g-e b-f c-g
  (decode "fdgacbe" {\a \c, \c \g, \e \b, \d \a, \g \e, \f \d, \b \f})
  (decode "fbcad" {\a \c, \c \g, \e \b, \d \a, \g \e, \f \d, \b \f})
  (decode "cdfbe" {\a \c, \c \g, \e \b, \d \a, \g \e, \f \d, \b \f}))

(defn parse-and-decode  [line]
  (let [parsed (parse-line line)
        patterns (:patterns parsed)
        output (:output parsed)
        mapping (produce-mapping patterns)
        decoded (apply str (map #(decode % mapping) output))]
    {:patterns patterns
     :ouptput output
     :mapping mapping
     :decoded-output (Integer/parseInt decoded)}))

(comment
  (pp/pprint
   (parse-and-decode "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce")))

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
      ;;  (#(do (pp/pprint %) %))
       (map parse-and-decode)
       (map :decoded-output)
       (reduce +)))

(comment

  (pp/pprint (solution2 "input-sample.txt"))
  (solution2 "input.txt")

  (solution1 "input-sample.txt")
  (solution1 "input.txt")


  (set "abc")

  (count "fcge")
  (contains? [2 3 4 7] 7)

;;  dddd (A)
;; e B   a C
;; e    a
;;  ffff D
;; g E   b F
;; g    b
;;  cccc G
;; 



;; ([\a 8] [\c 7] [\e 6] [\d 8] [\g 4] [\f 7] [\b 9])
  (vec "eafb")

  (parse-line "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe"))
