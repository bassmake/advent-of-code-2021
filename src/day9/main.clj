(ns day9.main
  (:require
   [tools.main :as t]
   [clojure.pprint :as pp]
   [clojure.string :as s]))


(defn get-point [m x y]
  (cond (< y 0) 9
        (>= y (count m)) 9
        :else (let [row (nth m y)]
                (cond (< x 0) 9
                      (>= x (count row)) 9
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

(defn put-point [m x y value]
  (assoc m y (assoc (nth m y) x value)))
(comment
  (put-point [[1 2] [3 4]] 0 0 9))


(defn is-low-point [{v :v t :t b :b l :l r :r}]
  (and (< v t) (< v b) (< v l) (< v r)))

(defn solution1 [input]
  (let [chars (map vec (s/split-lines (slurp (str "src/day9/" input))))
        matrix (into [] (map (fn [row] (into [] (map (comp t/to-int str) row))) chars))
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

(defn basin-size [matrix lp-x lp-y]
  (let [to-search (atom matrix)]
    (letfn [(search-basin [x y]
              (swap! to-search #(put-point % x y 9))
              (+ 1
                 (if (< (get-point @to-search (dec x) y) 9) (search-basin (dec x) y) 0)
                 (if (< (get-point @to-search (inc x) y) 9) (search-basin (inc x) y) 0)
                 (if (< (get-point @to-search x (dec y)) 9) (search-basin x (dec y)) 0)
                 (if (< (get-point @to-search x (inc y)) 9) (search-basin x (inc y)) 0)))]
      (search-basin lp-x lp-y))))


(comment
  (basin-size [[1 2] [3 4]] 0 0)
  (basin-size [[1 2 3] [4 5 9] [7 9 9]] 0 0))


(defn solution2 [input]
  (let [chars (map vec (s/split-lines (slurp (str "src/day9/" input))))
        matrix (into [] (map (fn [row] (into [] (map (comp t/to-int str) row))) chars))
        points (map-indexed (fn [idy row]
                              (map-indexed (fn [idx point]
                                             {:v point
                                              :x idx
                                              :y idy
                                              :risk-level (inc point)
                                              :t (get-point matrix idx (dec idy))
                                              :b (get-point matrix idx (inc idy))
                                              :l (get-point matrix (dec idx) idy)
                                              :r (get-point matrix (inc idx) idy)}) row)) matrix)
        low-points (filter is-low-point (flatten points))]
    (reduce * (take 3 (reverse (sort
                                (->> low-points
                                     (map (fn [low-point] (basin-size matrix (:x low-point) (:y low-point)))))))))))

(comment

  (pp/pprint
   (solution2 "input-sample.txt"))
  (pp/pprint
   (solution2 "input.txt"))

  (pp/pprint
   (solution1 "input-sample.txt"))
  (pp/pprint
   (solution1 "input.txt")))



(comment
  (nth [[1 2] [3 4]] -1))