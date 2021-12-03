(ns advent-2021.task3
  (:require [clojure.string :refer [split-lines join]]))

(def min-max #(sort-by (frequencies %) "01"))

(def map-column #(partial apply map (comp % vector)))

;; part 1 solution
(def solve1
  (comp (map-column join)
        (map-column min-max)))

;; part 2 solution
(defn solve2 [data]
  (letfn [(narrow [min-max-component data]
            (if (= 1 (count data))
              (first data)
              (let [first-char (->> data
                                    (map first)
                                    min-max
                                    min-max-component)]
                (->> data
                     (filter #(= first-char (first %)))
                     (map #(subs % 1))
                     (narrow min-max-component)
                     (str first-char)))))]
    [(narrow first data)
     (narrow second data)]))

(defmacro execute []
  (let [data (split-lines (slurp "./resources/data3.txt"))
        normalize (fn [result] (->> result
                                    (map #(Integer/parseInt % 2))
                                    (apply *)))]
    [(normalize (solve1 data))
     (normalize (solve2 data))]))
