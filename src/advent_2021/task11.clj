(ns advent-2021.task11
  (:require [clojure.edn :refer [read-string]]
            [clojure.string :refer [split-lines]]))

(defn indices [size]
  (for [y (range size) x (range size)] [y x]))

(def inc-all (partial mapv (partial mapv inc)))

(def inc-some (partial reduce #(update-in %1 %2 inc)))

(defn in-rect [size [y x]]
  (and (<= 0 y (dec size))
       (<= 0 x (dec size))))

(defn around [size point]
  (for [y [-1 0 1]
        x [-1 0 1]
        :when (not= 0 y x)
        :let [point' (mapv + point [y x])]
        :when (in-rect size point')]
    point'))

(defn blinks? [field point] (> (get-in field point) 9))

(defn blinked-points [field points]
  (set (filter #(blinks? field %) points)))

(defn reset-blinked [field]
  (reduce (fn [acc point] (update-in acc point #(if (> % 9) 0 %)))
          field
          (indices (count field))))

(defn step [field]
  (loop [field (inc-all field)
         blinked #{}
         nexts (blinked-points field (indices (count field)))]
    (if (empty? nexts)
      [(reset-blinked field) blinked]
      (let [nxt (first nexts)
            surrounding (around (count field) nxt)
            field (inc-some field surrounding)
            more-blinked (blinked-points field surrounding)]
        (recur field
               (conj blinked nxt)
               (apply disj (into nexts more-blinked) nxt blinked))))))

(defn blink-log [field]
  (iterate (fn [[field]] (step field)) [field #{}]))

(def all-blink? (partial every? #(every? zero? %)))

(defn parse [path]
  (->> path
       slurp
       split-lines
       (mapv (partial mapv (comp read-string str)))))

(defn solve1 [field]
  (->> field
       blink-log
       (take 101)
       (map (comp count second))
       (apply +)))

(defn solve2 [field]
  (->> field
       blink-log
       (map first)
       (take-while (complement all-blink?))
       count))

(defmacro execute []
  (let [data (parse "./resources/data11.txt")]
    [(solve1 data) (solve2 data)]))
