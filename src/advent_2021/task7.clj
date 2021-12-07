(ns advent-2021.task7
  (:require [clojure.edn :refer [read-string]]))

(defn parse-data [path]
  (read-string (str "[" (slurp path) "]")))

(defn reposition-cost [steps-to-cost to positions]
  (->> positions
       (map #(steps-to-cost (Math/abs (- % to))))
       (apply +)))

(defn sum-upto [n]
  (/ (* n (inc n)) 2))

;; Naive solution. It completes in reasonable time, but is still inefficient

;; (defn solve [step-to-cost-fn data]
;;   (let [cost-fn #(reposition-cost step-to-cost-fn % data)]
;;     (->> (range (apply min data) (inc (apply max data)))
;;          (map cost-fn)
;;          (apply min))))

;; (solve identity data1)
;; (solve sum-upto data1)
;; -------------------------------------------------------------------------

(defn median [data]
  (let [data-sorted (sort data)
        len (count data-sorted)]
    (if (odd? len)
      (nth data-sorted (/ (dec len) 2))
      (Math/round (/ (+ (nth data-sorted (/ len 2))
                        (nth data-sorted (dec (/ len 2))))
                     2.0)))))

(defn avg [data]
  (long (/ (apply + data) (double (count data)))))

(defn solve [step-to-cost find-horizontal data]
  (reposition-cost step-to-cost (find-horizontal data) data))

(defmacro execute []
  (let [data (parse-data "./resources/data6.txt")]
    [(solve identity median data)
     (solve sum-upto avg data)]))
