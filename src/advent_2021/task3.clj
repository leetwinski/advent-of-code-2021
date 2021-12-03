(ns advent-2021.task3
  (:require [clojure.string :refer [split-lines]]))

(def get-data (comp split-lines slurp))

(defn normalize [results]
  (->> results
       (map #(Integer/parseInt % 2))
       (apply *)))

(defn min-max [data]
  (let [freqs (frequencies data)]
    (if (< (freqs \1 0) (freqs \0 0))
      {:min \1 :max \0}
      {:min \0 :max \1})))

;; part 1 solution
(defn solve1 [data]
  (->> data
       (apply map (comp
                   vals
                   min-max
                   vector))
       (apply map str)
       normalize))

;; part 2 solution
(defn solve2 [data]
  (letfn [(narrow [min-or-max data]
            (if (= 1 (count data))
              (first data)
              (let [first-char (->> data
                                    (map first)
                                    min-max
                                    min-or-max)]
                (->> data
                     (filter (comp #{first-char} first))
                     (map #(subs % 1))
                     (narrow min-or-max)
                     (str first-char)))))]
    (normalize [(narrow :min data)
                (narrow :max data)])))

(defmacro execute []
  (let [data (get-data "./resources/data3.txt")]
    [(solve1 data)
     (solve2 data)]))



