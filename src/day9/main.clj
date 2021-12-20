(ns day9.main
  (:require
   [tools.main :as t]
   [clojure.pprint :as pp]
   [clojure.string :as s]))


(defn get-point [m x y]
  (cond (< y 0) Integer/MAX_VALUE
        (>= y (count m)) Integer/MAX_VALUE
        :else (let [row (nth m y)]
                (cond (< x 0) Integer/MAX_VALUE
                      (>= x (count row)) Integer/MAX_VALUE
                      :else (nth row x)))))
(comment
  (get-point [[1 2] [3 4]] 0 0)
  (get-point [[1 2] [3 4]] 1 0)
  (get-point [[1 2] [3 4]] 0 1)
  (get-point [[1 2] [3 4]] 1 1)
  (get-point [[1 2] [3 4]] -1 1)
  (get-point [[1 2] [3 4]] 2 1)
  (get-point [[1 2] [3 4]] 2 -1)
  (get-point [[1 2] [3 4]] 2 1)
  (get-point [[1 2] [3 4]] 1 2))


(defn is-low-point [{v :v t :t b :b l :l r :r}]
  (and (< v t) (< v b) (< v l) (< v r)))

(defn solution1 [input]
  (let [chars (map vec (s/split-lines (slurp (str "src/day9/" input))))
        matrix (map (fn [row] (map (comp t/to-int str) row)) chars)
        points (map-indexed (fn [idy row]
                              (map-indexed (fn [idx point]
                                             {:v point
                                              :x idx
                                              :y idy
                                              :risk-level (inc point)
                                              :t (get-point matrix idx (dec idy))
                                              :b (get-point matrix idx (inc idy))
                                              :l (get-point matrix (dec idx) idy)
                                              :r (get-point matrix (inc idx) idy)}) row)) matrix)]
    ;; (pp/pprint matrix)
    (->> (flatten points)
         (filter is-low-point)
         (map :risk-level)
         (reduce +))))

(comment

  (pp/pprint
   (solution1 "input-sample.txt"))
  (pp/pprint
   (solution1 "input.txt")))



(comment
  (nth [[1 2] [3 4]] -1))