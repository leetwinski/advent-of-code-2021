(ns advent-2021.task6)

(defn parse-data [path]
  (clojure.edn/read-string (str "[" (slurp path) "]")))

(defn nxt [data-freqs]
  (letfn [(freq [k] (data-freqs k 0))]
    {0 (freq 1)
     1 (freq 2)
     2 (freq 3)
     3 (freq 4)
     4 (freq 5)
     5 (freq 6)
     6 (+ (freq 7) (freq 0))
     7 (freq 8)
     8 (freq 0)}))

(defn day-value [seed day]
  (apply + (vals (nth (iterate nxt (frequencies seed)) day))))

(defmacro execute []
  (let [data (parse-data "./resources/data6.txt")]
    [(day-value data 80)
     (day-value data 256)]))

