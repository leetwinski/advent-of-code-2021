(ns advent-2021.task9
  (:require [clojure.edn :refer [read-string]]
            [clojure.string :refer [split-lines]]))

(def boundary 9)

(defn parse [path]
  (mapv #(mapv (comp read-string str) %)
        (split-lines (slurp path))))

(defn cross [point]
  (mapv #(mapv + point %)
        [[0 -1] [0 1] [-1 0] [1 0]]))

(defn indices [[line :as data]]
  (let [idx (comp range count)]
    (for [y (idx data) x (idx line)] [y x])))

(defn ascending-around [op]
  (fn [point val-at]
    (op #(< (val-at point) (val-at %) boundary)
        (cross point))))

(defn basin [low-point val-at]
  (let [get-candidates (ascending-around filterv)]
    (loop [res [] [point & points :as remaining] [low-point]]
      (if (seq remaining)
        (recur (conj res point)
               (into (get-candidates point val-at) points))
        res))))

(defn solve1 [low-points val-at]
  (apply + (map (comp inc val-at) low-points)))

(defn solve2 [low-points val-at]
  (->> low-points
       (map #(basin % val-at))
       (map #(zipmap % (repeat (set %))))
       (apply merge-with into)
       vals
       distinct       
       (map count)
       (sort >)
       (take 3)
       (apply *)))

(defmacro execute []
  (let [field (parse "./resources/data9.txt")
        low-point? (ascending-around every?)
        val-at #(get-in field % boundary)
        low (filter #(low-point? % val-at) (indices field))]
    ((juxt solve1 solve2) low val-at)))
