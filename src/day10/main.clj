(ns day10.main
  (:require
   [tools.main :as t]
   [clojure.pprint :as pp]
   [clojure.string :as s]))

(def score {\) 3, \] 57, \} 1197 \> 25137})

(defn is-left? [c] (some #{c} [\(, \[, \{, \<]))
(comment (is-left? \{)
         (is-left? \>))

(defn get-left [right]
  (case right \) \(
        \] \[
        \} \{
        \> \<))

(defn first-illegal [line]
  (letfn [(count-opened [opened actual]
            ;; (pp/pprint opened)
            ;; (pp/pprint actual)
            (let [latest (first opened)]
              (cond
                (is-left? actual) (cons actual opened)
                (= latest (get-left actual)) (rest opened)
                :else (reduced {:invalid actual}))))]
    (reduce count-opened [] (vec line))))
(comment
  (first-illegal "{([(<{}[<>[]}>{[]{[(<()>")
  (first-illegal "[({(<(())[]>[[{[]{<()<>>"))

(defn solution1 [input]
  (->> (s/split-lines (slurp (str "src/day10/" input)))
       (map first-illegal)
       (map :invalid)
       (filter some?)
       (map score)
       (reduce +)))

(comment
  (solution1 "input-sample.txt")
  (solution1 "input.txt"))

