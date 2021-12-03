(ns advent-2021.task2
  (:require [clojure.edn :as edn]))

(defn get-cmds [path]
  (partition 2 (edn/read-string (str "[" (slurp path) "]"))))

(def actions-1 {'forward (fn [[x y] v] [(+ x v) y])
                'up      (fn [[x y] v] [x (- y v)])
                'down    (fn [[x y] v] [x (+ y v)])})

(def actions-2 {'forward (fn [[x y aim] v] [(+ x v) (+ y (* v aim)) aim])
                'up      (fn [[x y aim] v] [x y (- aim v)])
                'down    (fn [[x y aim] v] [x y (+ aim v)])})

(defn solve [action-appliers initial-state commands]
  (let [[x y] (reduce (fn [state [cmd v]]
                        ((action-appliers cmd) state v))
                      initial-state
                      commands)]
    (* x y)))

(defmacro execute []
  (let [cmds (get-cmds "./resources/data2.txt")]
    [(solve actions-1 [0 0] cmds)
     (solve actions-2 [0 0 0] cmds)]))
