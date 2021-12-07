(ns advent-2021.core
  (:require [advent-2021.task1 :as task1]
            [advent-2021.task2 :as task2]
            [advent-2021.task3 :as task3]
            [advent-2021.task4 :as task4]
            [advent-2021.task5 :as task5]
            [advent-2021.task7 :as task7]))

(defn -main []
  (println "task1:" (task1/execute))
  (println "task2:" (task2/execute))
  (println "task3:" (task3/execute))
  (println "task4:" (task4/execute))
  (println "task5:" (task5/execute))
  (println "task7:" (task7/execute)))
