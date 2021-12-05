(ns advent-2021.task5
  (:require [clojure.edn :refer [read-string]]))

(defn parse-data [s]
  (for [[_ & coords] (re-seq #"(\d+),(\d+) -> (\d+),(\d+)" s)]
    (let [[a b c d] (map read-string coords)]
      [[a b] [c d]])))

(defn to-range [x y]
  (cond (= x y) (repeat x)
        (< x y) (range x (inc y))
        :else (range x (dec y) -1)))

(defn windline [[[a b :as start] [c d :as end]]]
  (if (= start end)
    [start]
    (map vector (to-range a c) (to-range b d))))

(defn is-diagonal? [[[a b] [c d]]]
  (and (not= a c) (not= b d)))

(defn solve [include-diagonal? data]  
  (->> (if include-diagonal? data (remove is-diagonal? data))
       (mapcat windline)
       frequencies
       vals
       (filter #(> % 1))
       count))

(defmacro execute []
  (let [data (parse-data (slurp "./resources/data5.txt"))]
    [(solve false data) (solve true data)]))
