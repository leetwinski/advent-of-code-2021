(ns advent-2021.task3
  (:require [clojure.string :refer [split-lines join]]))

(defn min-max [data]
  (let [freqs (frequencies data)]
    (if (< (freqs \1 0) (freqs \0 0))
      {:min \1 :max \0}
      {:min \0 :max \1})))

(def map-column #(partial apply map (comp % vector)))

;; part 1 solution
(def solve1
  (comp (map-column join)
        (map-column (comp vals min-max))))

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
                     (filter #(= first-char (first %)))
                     (map #(subs % 1))
                     (narrow min-or-max)
                     (str first-char)))))]
    [(narrow :min data)
     (narrow :max data)]))

(defmacro execute []
  (let [data (split-lines (slurp "./resources/data3.txt"))
        normalize (fn [result] (->> result
                                    (map #(Integer/parseInt % 2))
                                    (apply *)))]
    [(normalize (solve1 data))
     (normalize (solve2 data))]))
