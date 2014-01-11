(ns adventure-puzzle-solver.slider-test
  (:require [clojure.test :refer :all]
            [adventure-puzzle-solver.slider :refer :all]))

(def puzzle [[1 2 3]
             [4 0 5]
             [7 8 9]])

(def one-in-next-step [[1 0 3]
                       [4 2 5]
                       [7 8 9]])

(deftest generating-next-steps
  (is (= (count (possible-next-steps puzzle)) 4))
  (is (contains? (possible-next-steps puzzle) one-in-next-step))
  (is (= (count (possible-next-steps one-in-next-step)) 3)))

(deftest swaping-cells
  (is (= (swap-cells puzzle [0 1] [1 1])
         [[1 0 3]
          [4 2 5]
          [7 8 9]])))

(deftest detect-free-cells
  (is (= (free-cells puzzle) (list [1 1]))))

(deftest is-solution 
  (let [solution? (make-solution puzzle)]
    (is (solution? puzzle))
    (is (not (solution? one-in-next-step)))))

