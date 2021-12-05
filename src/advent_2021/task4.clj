(ns advent-2021.task4)

(defn parse [path]  
  (with-open [rdr (clojure.java.io/reader path)]   
    (let [[nums & card-data] (mapv #(clojure.edn/read-string (str "[" % "]"))
                                   (filter seq (line-seq rdr)))]
      {:nums nums :cards (partition 5 card-data)})))

(defn heads-seq [data]
  (->> data
       vec
       (iterate pop)
       (take-while seq)
       reverse))

(defn columns [card]
  (apply map vector card))

(defn winning? [called-set card]
  (boolean (some (partial every? called-set)
                 (concat card (columns card)))))

(defn postprocess [called-set last-called card]
  (->> card
       (apply concat)
       (remove called-set)
       (apply +)
       (* last-called)))

(defn partition-win-lose [called-set cards]
  (let [res (group-by #(winning? called-set %) cards)]
    [(res true) (res false)]))

(defn solve1 [num-vec cards]
  (some (fn [called]
          (let [called-set (set called)]
            (when-let [winning (some #(when (winning? called-set %) %)
                                     cards)]
              (postprocess called-set (peek called) winning))))
        (heads-seq num-vec)))

(defn solve2 [num-vec cards] 
  (let [next-win-lose (fn [[_ losing] nums-vec]
                        (let [called-set (set nums-vec)
                              [w l] (partition-win-lose called-set losing)]
                          [w l (peek nums-vec) called-set]))
        [winning _ last-called called-set] (->> num-vec
                                                heads-seq
                                                (reductions next-win-lose [[] cards 0 #{}])
                                                (drop-while (comp seq second))
                                                first)]
    (postprocess called-set last-called (last winning))))

(defmacro execute []
  (let [data (parse "./resources/data4.txt")
        {:keys [nums cards]} data]
    [(solve1 nums cards) (solve2 nums cards)]))
