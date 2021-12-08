(ns advent-2021.task8
  (:require [clojure.set :refer [union difference intersection]]))

(let [[I D U] [intersection difference union]
      match (fn [f data]
              (let [found (first (filter f data))]
                [found (remove #{found} data)]))]
  (defn parse-in [item]
    (let [item (sort-by count (map set item))
          [a1 a7 a4 & unknown] (butlast item)
          a8 (last item)
          [a3 unknown] (match #(= 3 (count (D % a1))) unknown)
          [a6 unknown] (match #(= 5 (count (D % a1))) unknown)
          [a5 unknown] (match #(= % (I % a6)) unknown)
          [a2 unknown] (match #(not= % (U % a1)) unknown)
          [a9 [a0]] (match #(= % (U % a4)) unknown)]
      (zipmap [a0 a1 a2 a3 a4 a5 a6 a7 a8 a9] (range)))))

(defn parse [path]
  (map #(let [[legend data] (split-at 10 %)
              digits (parse-in legend)]
          (map (comp digits set) data))
       (partition 14 (re-seq #"[a-z]+" (slurp path)))))

(defn solve-1 [data]
  (->> data
       (apply concat)
       (filter #{1 4 7 8})
       count))

(defn solve-2 [data]
  (->> data
       (map #(Integer/parseInt (apply str %)))
       (apply +)))

(defmacro execute []
  (let [data (parse "./resources/data8.txt")]
    [(solve-1 data) (solve-2 data)]))
