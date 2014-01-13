(ns adventure-puzzle-solver.backtracker-test
  (:require [clojure.test :refer :all]
            [adventure-puzzle-solver.backtracker :refer :all]
            [adventure-puzzle-solver.slider :refer :all]))

(def initial [[4 3 1]
              [0 2 5]
              [7 8 9]])

(def one-step [[1 2 3]
               [0 4 5]
               [6 7 8]])

(def solution [[1 2 3]
               [4 0 5]
               [7 8 9]])

(def is-slider-solution? (make-solution solution))

(deftest when-is-solution
  (solve solution is-slider-solution? possible-next-steps))

(deftest when-one-step-to-solution
 (count (solve one-step is-slider-solution? possible-next-steps)))

;Blows up the stack :(
;(deftest when-longer-steps-to-solution
 ;(solve initial is-slider-solution? possible-next-steps))
