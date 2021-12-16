(ns advent-2021.task15
  (:require [clojure.string :as cs]
            [clojure.edn :as edn]))

(defn parse [path]
  (->> path
       slurp
       cs/split-lines
       (mapv #(mapv (comp edn/read-string str) %))))

(defn min-path [field]
  (let [bound (dec (count field))
        rng (range bound -1 -1)
        table (reduce (fn [res [y x :as point]]
                        (let [v (get-in field point)]
                          (cond
                            (and (= bound x) (= bound y)) (assoc res point v)
                            (= bound x) (assoc res point (+ v (res [(inc y) x])))
                            (= bound y) (assoc res point (+ v (res [y (inc x)])))
                            :else (assoc res point (+ v
                                                      (min (res [(inc y) x])
                                                           (res [y (inc x)])))))))
                      {} (for [y rng x rng] [y x]))]
    (- (table [0 0])
       (get-in field [0 0]))))

(defmacro execute []
  (let [field (parse "./resources/data15.txt")]
    [(min-path field) :unimplemented]))

;; (defn inc-row [row]
;;   (map #(if (= % 9) 1 (inc %)) row))

;; (defn inc-grid [grid]
;;   (map inc-row grid))

;; (defn replicate-h [grid]
;;   (apply map #(reduce into [] %&)
;;          (take 5 (iterate inc-grid grid))))

;; (defn replicate-v [grid]
;;   (let [row (replicate-h grid)]
;;     (mapv vec (apply concat (take 5 (iterate inc-grid row))))))



