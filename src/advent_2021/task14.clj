(ns advent-2021.task14
  (:require [clojure.string :as cs]))

(defn parse-rule [s]
  (let [[k [v]] (cs/split s #" -> ")]
    [(seq k) v]))

(defn parse [input]
  (let [[[a] [_ & b]] (split-with seq (cs/split-lines input))]
    {:tpl a :rules (into {} (map parse-rule) b)}))

(def produces
  (memoize (fn [pair rules steps]
             (when-let [[[a b] x] (and (pos? steps) (find rules pair))]
               (merge-with +
                           {x 1}
                           (produces [a x] rules (dec steps))
                           (produces [x b] rules (dec steps)))))))

(defn count-production [rules tpl steps]
  (->> tpl
       (partition 2 1)
       (map #(produces % rules steps))
       (apply merge-with + (frequencies tpl))
       vals
       sort
       ((juxt last first))
       (apply -)))

(defmacro execute []
  (let [{:keys [tpl rules]} (parse (slurp "./resources/data14.txt"))
        prods (partial count-production rules tpl)]
    (mapv prods [10 40])))
