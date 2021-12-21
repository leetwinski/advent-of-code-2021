(ns advent-2021.task17
  (:require [clojure.edn :as edn]))

(defn parse-data [path]
  (let [[x1 x2 y1 y2] (->> path
                           slurp
                           (re-seq #"-?\d+")
                           (map edn/read-string))]
    [[x1 x2] [y1 y2]]))

(defn solve1 [[_ [y1]]]
  (/ (* y1 (inc y1)) 2))

(defmacro execute []
  (let [data (parse-data "./resources/data17.txt")]
    [(solve1 data)
     :unimplemented]))

