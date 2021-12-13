(ns advent-2021.task13
  (:require [clojure.string :refer [split-lines join]]
            [clojure.edn :refer [read-string]]))

(defn field->str [field] (join \newline (map join field)))

(def transpose (partial apply map vector))

(defn count-dots [field]
  (count (filter #{\#} (apply concat field))))

(defn make-field [[height width]]
  (vec (repeat height (vec (repeat width \.)))))

(defn fold-val [a b]
  (if (or (= \# a) (= \# b)) \# \.))

(defn fold-line [at line]
  (let [[l [_ & r]] (split-at at line)
        [short long] (sort-by count [l (reverse r)])
        diff (- (count long) (count short))]
    (concat (take diff long)
            (map fold-val (drop diff long) short))))

(defn fold-by-axis [field [axis n]]
  (let [proc (if (= axis "x") identity transpose)]
    (->> field
         proc
         (map #(fold-line n %))
         proc)))

(defn field-size [points]
  (mapv inc (reduce (fn [[a b] [c d]] [(max a c) (max b d)])
                    [0 0]
                    points)))

(defn parse [path]
  (let [[l [_ & r]] (split-with seq (split-lines (slurp path)))
        points (map #(let [[a b] (read-string (str "[" % "]"))]
                       [b a]) l)
        field (make-field (field-size points))
        folds (map #(let [[_ axis n] (re-matches #".*(x|y)=(\d+)" %)]
                      [axis (read-string n)])
                   r)]
    {:field (reduce (fn [acc point] (assoc-in acc point \#))
                    field points)
     :folds folds}))

(def solve1 (comp count-dots fold-by-axis))

(defn solve2 [field folds]
  (field->str (reduce fold-by-axis field folds)))

(defmacro execute []
  (let [{:keys [folds field]} (parse "./resources/data13.txt")]
    [(solve1 field (first folds))
     (solve2 field folds)]))
