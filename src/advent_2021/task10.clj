(ns advent-2021.task10
  (:require [clojure.string :refer [split-lines]]))

(let [opening "[{(<"
      closing "]})>"]
  (def opening? (set opening))
  (def closing? (set closing))
  (def opening-by-closing (zipmap closing opening)))

(def err-price {\) 3 \] 57 \} 1197 \> 25137})

(def completion-price {\( 1 \[ 2 \{ 3 \< 4})

(defn estimate-completion [incomplete]
  (->> incomplete
       reverse
       (map completion-price)
       (reduce #(+ %2 (* % 5)) 0)))

(def parse (comp split-lines slurp))

(defn middle [data] (nth data (quot (count data) 2)))

(defn process [line]
  (reduce (fn [{:keys [stack]} x]
            (cond (opening? x) {:stack (conj stack x)}
                  (= (opening-by-closing x) (peek stack)) {:stack (pop stack)}
                  :else (reduced {:err x :stack stack})))
          {:stack []}
          line))

(defmacro execute []
  (let [processed (map process (parse "./resources/data10.txt"))
        [errors incompletes] ((juxt keep remove) :err processed)
        res1 (apply + (map err-price errors))
        res2 (middle (sort (map (comp estimate-completion :stack) incompletes)))]
    [res1 res2]))
