(ns advent-2021.task5-test
  (:require [advent-2021.task5 :as sut]
            [clojure.test :as t :refer [deftest testing is]]))

(def data (sut/parse-data
           "0,9 -> 5,9
            8,0 -> 0,8
            9,4 -> 3,4
            2,2 -> 2,1
            7,0 -> 7,4
            6,4 -> 2,0
            0,9 -> 2,9
            3,4 -> 1,4
            0,0 -> 8,8
            5,5 -> 8,2"))

(deftest task5-1-sample-input-test
  (testing "5-1 passes on sample data"
    (is (= 5 (sut/solve false data)))))

(deftest task5-2-sample-input-test
  (testing "5-2 passes on sample data"
    (is (= 12 (sut/solve true data)))))



