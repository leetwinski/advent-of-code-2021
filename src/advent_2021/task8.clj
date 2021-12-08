(ns advent-2021.task8
  (:require [clojure.set :refer [union difference intersection]]))

(let [[I D U] [intersection difference union]
      match (fn [f data]
              (let [found (first (filter f data))]
                [found (remove #{found} data)]))]
  (defn infer-digits [legend]
    (let [[[a1 a7 a4 & unknown] a8] ((juxt butlast last) (sort-by count legend))
          [a3 unknown] (match #(= 3 (count (D % a1))) unknown)
          [a6 unknown] (match #(= 5 (count (D % a1))) unknown)
          [a5 unknown] (match #(= % (I % a6)) unknown)
          [a2 unknown] (match #(not= % (U % a1)) unknown)
          [a9 [a0]] (match #(= % (U % a4)) unknown)]
      (zipmap [a0 a1 a2 a3 a4 a5 a6 a7 a8 a9] (range)))))

(defn parse [path]
  (->> path
       slurp
       (re-seq #"[a-z]+")
       (map set)
       (partition 14)
       (map #(let [[legend data] (split-at 10 %)]
               (map (infer-digits legend) data)))))

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
