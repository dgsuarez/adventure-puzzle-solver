(ns adventure-puzzle-solver.bfser-test
  (:require [clojure.test :refer :all]
            [adventure-puzzle-solver.bfser :refer :all]
            [adventure-puzzle-solver.slider :refer :all]))

(def one-step [[1 2 3]
               [0 4 5]
               [7 8 9]])

(def solution [[1 2 3]
               [4 0 5]
               [7 8 9]])

(def initial [[4 1 3]
              [2 8 5]
              [7 0 9]])

(def solution-as-state (list (list solution)))

(def is-slider-solution? (make-solution solution))

(deftest when-getting-all-next-for-first-step
  (is (= 4 (count (all-next (list solution) possible-next-steps)))))

(deftest when-getting-more-next-already-there
  (is (= 3 (count (all-next (list solution one-step) possible-next-steps)))))

(deftest when-going-down-a-level
  (is (= 4 (count (take-step solution-as-state possible-next-steps)))))

(deftest lazily-get-all-solutions
  (is (= (list 1 2 2 2 2 3 3 3 3 3) (map count (take 10 (get-all-states solution-as-state possible-next-steps))))))

(deftest when-is-solution
  (is (= (list solution) (solve solution is-slider-solution? possible-next-steps))))

(deftest when-one-step-to-solution
  (is (= (list one-step solution) (solve one-step is-slider-solution? possible-next-steps))))

(deftest when-longer-steps-to-solution
 (solve initial is-slider-solution? possible-next-steps))
