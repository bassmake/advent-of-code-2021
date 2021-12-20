(ns day10.main
  (:require
   [clojure.pprint :as pp]
   [clojure.string :as s]))

(def error-score
  {\) 3
   \] 57
   \} 1197
   \> 25137})

(def score
  {\) 1
   \] 2
   \} 3
   \> 4})

(defn is-left? [c] (some #{c} [\(, \[, \{, \<]))
(comment (is-left? \{)
         (is-left? \>))

(defn get-left [right]
  (case right
    \) \(
    \] \[
    \} \{
    \> \<))

(defn get-right [left]
  (case left
    \( \)
    \[ \]
    \{ \}
    \< \>))

(defn check-chunks [line]
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
  (check-chunks "{([(<{}[<>[]}>{[]{[(<()>")
  (check-chunks "[({(<(())[]>[[{[]{<()<>>"))


(defn compute-score
  ([completion] (compute-score 0 completion))
  ([total-score completion]
   (let [[first & remaining] completion
         new-total (+ (*  total-score 5) (get score first))]
     (if (nil? remaining) new-total (compute-score new-total remaining)))))
(comment
  (compute-score 0 (vec "]"))
  (compute-score (vec "])}>"))
  (compute-score 0 (vec "]]}}]}]}>")))



(defn solution1 [input]
  (->> (s/split-lines (slurp (str "src/day10/" input)))
       (map check-chunks)
       (map :invalid)
       (filter some?)
       (map error-score)
       (reduce +)))

(defn solution2 [input]
  (let [scores (->> (s/split-lines (slurp (str "src/day10/" input)))
                    (map check-chunks)
                    (filter #(not (map? %)))
                    (map #(map get-right %))
                    (map compute-score)
                    (sort))
        half (/ (count scores) 2)]
    (nth scores half)))


(comment

  (pp/pprint (solution2 "input-sample.txt"))
  (solution2 "input.txt")

  (solution1 "input-sample.txt")
  (solution1 "input.txt"))