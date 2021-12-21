(ns advent-2021.task20
  (:require [clojure.edn :as edn]
            [clojure.string :as cs]))

(defn parse-alg [s] (vec (keep #{\. \#} s)))

(defn parse-field [s] (mapv vec s))

(defn read-bin [s] (edn/read-string (apply str "2r" s)))

(defn get-enhanced-px [field alg [y x] fst]
  (let [default (if fst \. (alg 0))
        px-vals (for [dy [-1 0 1] dx [-1 0 1]]
                  (get-in field [(+ dy y) (+ dx x)] default))]
    (alg (read-bin (map {\. \0 \# \1} px-vals)))))

(defn step [field alg fst]
  (let [width (count (first field))
        pixels (for [y (range -2 (+ 2 (count field)))
                     x (range -2 (+ 2 width))]
                 [y x])
        px-vals (pmap #(get-enhanced-px field alg % fst) pixels)]
    (mapv vec (partition (+ 4 width) px-vals))))

(defn step-val [field alg steps-count]
  (->> [field true]
       (iterate (fn [[f s]] [(step f alg s) (not s)]))
       (#(nth % steps-count))
       first
       (apply concat)
       (filter #{\#})
       count))

(defn parse-data [path]
  (let [data (slurp path)
        [a _ & more] (cs/split-lines data)]
    {:alg (parse-alg a)
     :field (parse-field more)}))

(defmacro execute []
  (let [{:keys [alg field]} (parse-data "./resources/data20.txt")]
    ;; naive solution. it completes, but obviously is suboptimal. i will get back to it later
    [(step-val field alg 2)
     (step-val field alg 50)
     ]))

