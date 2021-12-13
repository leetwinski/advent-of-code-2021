(ns advent-2021.task12
  (:require [clojure.string :refer [split-lines split lower-case]]))

(defn parse [data]
  (let [lines (split-lines data)
        a->b (map #(split % #"-") lines)
        b->a (map (comp vec rseq) a->b)
        p (group-by first (concat a->b b->a))]    
    (dissoc (reduce-kv (fn [acc k v] (assoc acc k (remove #{"start"} (map second v))))
                       {} p)
            "end")))

(defn is-lower? [s]
  (and (not= "end" s)
       (not= "start"s)
       (= s (lower-case s))))

(defn paths-from [ways visited location]
  (let [nexts (remove visited (ways location))]
    (if (= "end" location)
      1
      (if (empty? nexts)
        0
        (apply + (map (fn [x] (paths-from
                               ways
                               (if (is-lower? x)
                                 (conj visited x)
                                 visited)
                               x))
                      nexts))))))

(defmacro execute []
  (let [ways (parse (slurp "./resources/data12.txt"))]
    [(paths-from ways #{} "start")
     :unimpemented]))
