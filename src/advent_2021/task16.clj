(ns advent-2021.task16
  (:require [clojure.edn :as edn]
            [clojure.string :as cs]
            [clojure.pprint :refer [cl-format]]))

(declare parse-sequential)

(def ops {0 +
          1 *
          2 min
          3 max
          5 #(if (> %1 %2) 1 0)
          6 #(if (< %1 %2) 1 0)
          7 #(if (== %1 %2) 1 0)})

(defn to-bin [hex-string]
  (->> hex-string
       (map #(->> (str "0x" %)
                  edn/read-string
                  (cl-format nil "~4,'0b")))
       cs/join))

(defn read-bin [s]
  (edn/read-string (str "2r" s)))

(defn parse-header [s]  
  (let [[_ v t body] (re-find #"^(\d{3,3})(\d{3,3})(.*)$" s)
        version (read-bin v)
        id (read-bin t)]   
    [version id body]))

(defn parse-literal [s]
  (let [[version id body] (parse-header s)]
    (if (= id 4)
      (let [[num len] (let [[l [x]] (->> body
                                         (partition 5)
                                         (split-with (comp #{\1} first)))
                            len (* 5 (inc (count l)))]
                        [(read-bin (cs/join (apply concat (map rest (concat l [x])))))
                         len])]
        [{:v version :type :literal :val num} (subs body len)]))))

(defn parse-operator [s]
  (let [[version id [i :as body]] (parse-header s)]
    (when-not (= id 4)
      (if (= \0 (first body))
        (let [n (read-bin (subs body 1 16))
              more (subs body 16 (+ 16 n))
              inner (take-while identity (parse-sequential more))]
          [{:v version :type :operator :operator (ops id) :operands (mapv first inner)}
           (subs body (+ 16 n))])
        (let [n (read-bin (subs body 1 12))
              more (subs body 12)
              inner (take n (parse-sequential more))]
          [{:v version :type :operator :operator (ops id) :operands (mapv first inner)}
           (second (last inner))])))))

(defn parse-next [s]
  (when (and (seq s) (not (every? #{\0} s)))
    (or (parse-literal s)
        (parse-operator s))))

(defn parse-sequential [s]
  (rest (iterate #(parse-next (second %)) [nil s])))

(defn evaluate [x]
  (if (= (:type x) :literal)
    (:val x)
    (let [{:keys [operator operands]} x]
      (apply operator (map evaluate operands)))))

(defn sum-versions [prog]
  (->> prog
       (tree-seq coll? seq)
       (filter #(and (map-entry? %) (= :v (key %))))
       (map val)
       (apply +)))

(defmacro execute []
  (let [prog (->> (slurp "./resources/data16.txt")
                  cs/trim
                  to-bin
                  parse-next
                  first)]
    [(sum-versions prog)
     (evaluate prog)]))
