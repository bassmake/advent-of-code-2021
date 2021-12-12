(ns day5.main
  (:require [tools.main :as tools]
            [clojure.string :as str]))

(defn parse-line [line]
  (let [parts (str/split line  #" -> |,")
        numbers (map tools/to-int parts)]
    {:x1 (nth numbers 0)
     :y1 (nth numbers 1)
     :x2 (nth numbers 2)
     :y2 (nth numbers 3)}))

(defn is-straight? [{x1 :x1 y1 :y1 x2 :x2 y2 :y2}]
  (or (= x1 x2) (= y1 y2)))

(defn is-diagonal? [{x1 :x1 y1 :y1 x2 :x2 y2 :y2}]
  (or (and (= x1 y1) (= x2 y2))
      (and (= x1 y2) (= x2 y1))))

(defn all-points [coordinates]
  (tools/log coordinates false)
  (let [{x1 :x1 y1 :y1 x2 :x2 y2 :y2} coordinates
        start-x (min x1 x2)
        vector-x (- (max x1 x2) start-x)
        step-x (if (= vector-x 0) 0 1)
        move-x (fn [step] (+ start-x (* step step-x)))
        start-y (min y1 y2)
        vector-y (- (max y1 y2) start-y)
        step-y (if (= vector-y 0) 0 1)
        move-y (fn [step] (+ start-y (* step step-y)))
        vector-range (range 0 (inc (max vector-x vector-y)))]
    (for [step vector-range]
      {:x (move-x step) :y (move-y step)})))

(defn append-point [points point]
  (update points point #(inc (or % 0))))

(defn solution1 [lines]
  (->> lines
       (map parse-line)
      ;;  (filter is-straight?)
       (filter #(or (is-straight? %) (is-diagonal? %)))
      ;;  (filter is-diagonal?)
       (map all-points)
       (flatten)
      ;;  (doall)))
       (reduce append-point {})
       (filter (fn [[_ v]] (> v 1)))
       (count)))


(defn provide-solution [solution file]
  (tools/with-open-file (str "src/day5/" file) solution))

(comment
  (tools/log (provide-solution solution1 "input-sample.txt") true)
  (tools/log (provide-solution solution1 "input.txt") true)

  (or 0 1)
  (all-points {:x1 3 :y1 4 :x2 1 :y2 4})
  (max 1 2)
  (range 7 7 1))
