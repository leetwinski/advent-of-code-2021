(ns advent-2021.core
  (:require [advent-2021.task1 :as task1]
            [advent-2021.task2 :as task2]
            [advent-2021.task3 :as task3]
            [advent-2021.task4 :as task4]
            [advent-2021.task5 :as task5]
            [advent-2021.task6 :as task6]
            [advent-2021.task7 :as task7]
            [advent-2021.task8 :as task8]
            [advent-2021.task9 :as task9]
            [advent-2021.task10 :as task10]
            [advent-2021.task11 :as task11]
            [advent-2021.task13 :as task13]))



(defn -main []
  (println "task1:" (task1/execute))
  (println "task2:" (task2/execute))
  (println "task3:" (task3/execute))
  (println "task4:" (task4/execute))
  (println "task5:" (task5/execute))
  (println "task6:" (task6/execute))
  (println "task7:" (task7/execute))
  (println "task8:" (task8/execute))
  (println "task9:" (task9/execute))
  (println "task10:" (task10/execute))
  (println "task11:" (task11/execute))
  (let [[res1 res2] (task13/execute)]
    (println "task13: [")
    (println res1)
    (println res2)
    (println "]")))
