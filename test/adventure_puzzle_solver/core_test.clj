(ns adventure-puzzle-solver.core-test
  (:require [clojure.test :refer :all]
            [adventure-puzzle-solver.core :refer :all]))

(def puzzle [[1 2 3]
             [4 0 5]
             [7 8 9]])

(deftest swaping-cells
  (is (= (swap-cells puzzle [0 1] [1 1])
         [[1 0 3]
          [4 2 5]
          [7 8 9]])))

