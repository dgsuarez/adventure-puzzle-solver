(ns adventure-puzzle-solver.backtracker-test
  (:require [clojure.test :refer :all]
            [adventure-puzzle-solver.backtracker :refer :all]
            [adventure-puzzle-solver.slider :refer :all]))


(def one-step [[1 2 3]
               [0 4 5]
               [6 7 8]])

(def solution [[1 2 3]
               [4 0 5]
               [6 7 8]])

(def initial [[4 1 3]
              [2 7 5]
              [6 0 8]])

(deftest when-is-solution
  (solve solution #{solution} possible-next-steps))

(deftest when-one-step-to-solution
 (count (solve one-step #{solution} possible-next-steps)))

(deftest when-longer-steps-to-solution
 (solve initial #{solution} possible-next-steps))
