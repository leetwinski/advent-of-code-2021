(ns advent-2021.core
  (:require [advent-2021.task1 :as task1]
            [advent-2021.task2 :as task2]
            [advent-2021.task3 :as task3]))

(defn -main []
  (println "task1:" (task1/execute))
  (println "task2:" (task2/execute))
  (println "task3:" (task3/execute)))
