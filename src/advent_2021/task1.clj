(ns advent-2021.task1
  (:require [clojure.edn :as edn]))

(defn get-data [path]
  (edn/read-string (str "[" (slurp path) "]")))

(defn count-increasing [data]
  (->> data
       (partition 2 1)
       (filter (fn [[a b]] (< a b)))
       count))

(defn solve1 [data]
  (count-increasing data))

(defn solve2 [data]
  (->> data
       (partition 3 1)
       (map (partial apply +))
       count-increasing))

(defmacro execute []
  (let [data (get-data "./resources/data1.txt")]
    [(solve1 data)
     (solve2 data)]))
