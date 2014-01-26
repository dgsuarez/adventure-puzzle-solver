(ns adventure-puzzle-solver.slider-test
  (:require [clojure.test :refer :all]
            [adventure-puzzle-solver.slider :refer :all]
            [adventure-puzzle-solver.best-first :as best-first]
            [adventure-puzzle-solver.bfser :as bfser]))

(def puzzle [[1 2 3]
             [4 0 5]
             [6 7 8]])

(def one-in-next-step [[1 0 3]
                       [4 2 5]
                       [6 7 8]])

(def weird [[4 1 0] 
            [2 3 5] 
            [7 8 9]])

(def initial [[4 1 3]
              [2 7 5]
              [6 0 8]])

(deftest with-once-failing
  (is (every? identity (flatten (seq (possible-next-steps weird))))))

(deftest generating-next-steps
  (is (= (count (possible-next-steps puzzle)) 4))
  (is (contains? (possible-next-steps puzzle) one-in-next-step))
  (is (= (count (possible-next-steps one-in-next-step)) 3)))

(deftest detect-free-cells
  (is (= (free-cells puzzle) (list [1 1]))))

(deftest is-solution 
  (let [solution? (make-solution puzzle)]
    (is (solution? puzzle))
    (is (not (solution? one-in-next-step)))))

(deftest scores-right
  (is (= [2 1] (scorer puzzle (list one-in-next-step)))))

(deftest solve-it
  (let [best-first-sol (time (best-first/solve initial #{puzzle} #(scorer puzzle %) possible-next-steps))
        bfser-sol (time (bfser/solve initial #{puzzle} possible-next-steps))]
    (is (= bfser-sol best-first-sol))))
