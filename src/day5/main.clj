(ns day5.main
  (:require [tools.main :as tools]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :as pp]))



(defn solution1 [lines]
  (doall lines))


(defn provide-solution [solution file]
  (tools/with-open-file (str "src/day5/" file) solution))

(comment
  (pp/pprint (provide-solution solution1 "input-sample.txt"))
  (pp/pprint (provide-solution solution1 "input.txt")))