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
  (= (Math/abs (- x2 x1)) (Math/abs (- y2 y1))))
  ;; (or (and (= x1 y1) (= x2 y2))
  ;;     (and (= x1 y2) (= x2 y1))


(defn all-points [coordinates]
  (tools/log coordinates false)
  (let [{x1 :x1 y1 :y1 x2 :x2 y2 :y2} coordinates
        vector-x (- x2 x1)
        vector-y (- y2 y1)
        step-x (cond (< vector-x 0) -1
                     (> vector-x 0) 1
                     :else 0)
        step-y (cond (< vector-y 0) -1
                     (> vector-y 0) 1
                     :else 0)
        move-x (fn [step] (+ x1 (* step step-x)))
        move-y (fn [step] (+ y1 (* step step-y)))
        vector-range (range 0 (inc (max (* step-x vector-x) (* step-y vector-y))))]
    (for [step vector-range]
      {:x (move-x step) :y (move-y step)})))

(defn append-point [points point]
  (let [new-points (update points point #(inc (or % 0)))]
    (tools/log new-points false)
    new-points))

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
       (into (sorted-map-by
              (fn [{x1 :x y1 :y} {x2 :x y2 :y}]
                (if (= x1 x2) (compare y1 y2) (compare x1 x2)))))
      ;;  (#(do (tools/log % true) %))
       (filter (fn [[_ v]] (> v 1)))
       (count)))


(defn provide-solution [solution file]
  (tools/with-open-file (str "src/day5/" file) solution))

(comment
  (tools/log (provide-solution solution1 "input-sample.txt") true)
  (tools/log (provide-solution solution1 "input.txt") true)

  (Math/abs -11)
  (or 0 1)
  (all-points {:x1 3 :y1 4 :x2 1 :y2 4})
  (all-points {:x1 1 :y1 4 :x2 3 :y2 4})
  (all-points {:x1 3 :y1 4 :x2 3 :y2 6})
  (all-points {:x1 3 :y1 16 :x2 3 :y2 6})
  (all-points {:x1 1 :y1 1 :x2 4 :y2 4})
  (all-points {:x1 1 :y1 3 :x2 3 :y2 1})
  (max 1 2)
  (range 7 7 1))
