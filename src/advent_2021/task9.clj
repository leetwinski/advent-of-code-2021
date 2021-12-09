(ns advent-2021.task9
  (:require [clojure.edn :refer [read-string]]
            [clojure.string :refer [split-lines]]))

(defn parse [path]
  (mapv #(mapv (comp read-string str) %)
        (split-lines (slurp path))))

(defn indices [data]
  (mapcat (fn [y] (map (partial vector y)
                       (range (count (first data)))))
          (range (count data))))

(defn val-at [field]
  #(get-in field % 10))

(defn low-point? [[y x] field]
  (let [[middle & others] (map (val-at field)
                               [[y x]
                                [(dec y) x]
                                [(inc y) x]
                                [y (dec x)]
                                [y (inc x)]])]
    (every? #(< middle %) others)))

(defn increasing [starting-at direction field]
  (let [get-val (val-at field)
        delta (case direction
                :left [0 -1]
                :right [0 1]
                :up [-1 0]
                :down [1 0])]
    (->> starting-at
         (iterate (partial mapv + delta))
         (take-while #(< (get-val %) 9))
         (partition 2 1)
         (take-while (fn [[a b]] (< (get-val a) (get-val b))))
         (reduce into #{}))))

(defn cross [starting-at field]
  (reduce into #{} (map #(increasing starting-at % field)
                        [:up :down :left :right])))

(defn basin [low-point field]
  (letfn [(rec [visited [x & xs :as remaining]]
            (when (seq remaining)
              (lazy-seq (cons x (rec (conj visited x)
                                     (concat (remove (conj visited x) (cross x field))
                                             xs))))))]
    (rec #{} [low-point])))

(defn basins [field]
  (->> field
       indices
       (filter #(low-point? % field))
       (map #(basin % field))
       (map #(zipmap % (repeat (set %))))
       (apply merge-with into)
       vals
       distinct))

(defn low-points [field]
  (->> field
       indices
       (filter #(low-point? % field))
       (map (val-at field))))

(defn solve1 [field]
  (let [lp (low-points field)]
    (apply + (count lp) lp)))

(defn solve2 [field]
  (->> field
       basins
       (map count)
       (sort >)
       (take 3)
       (apply *)))

(defmacro execute []
  (let [field (parse "./resources/data9.txt")]
    [(solve1 field)
     (solve2 field)]))