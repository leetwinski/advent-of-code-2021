(ns advent-2021.task21
  (:require [clojure.edn :as edn]))

(defn turns [data]
  (map vector
       (take-nth 2 data)
       (take-nth 2 (rest data))))

(defn sumx [n] (long (/ (* n (inc n)) 2)))

(defn solve1 [[pos1 pos2]]
  (let [sums (map #(- (sumx (* 3 %)) (sumx (* 3 (dec %)))) (rest (range)))]
    (->> (turns sums)
         (reductions (partial mapv +) [pos1 pos2])
         rest
         (map (partial mapv #(let [x (rem % 10)]
                               (if (zero? x) 10 x))))
         (reductions (partial mapv +))
         (take-while #(every? (partial > 1000) %))
         ((fn [data]
            [(apply min (last data))
             (+ 3 (* 2 (* (count data) 3)))]))
         (apply *))))

(defn parse-data [path]
  (map (comp edn/read-string second)
       (re-seq #"Player \d starting position: (\d+)"
               (slurp path))))

(defmacro execute []
  [(solve1 (parse-data "./resources/data21.txt"))
   :unimplemented])
