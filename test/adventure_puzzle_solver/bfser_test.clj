(ns adventure-puzzle-solver.bfser-test
  (:require [clojure.test :refer :all]
            [adventure-puzzle-solver.bfser :refer :all]
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

(def solution-as-state (list (list solution)))

(deftest when-getting-all-next-for-first-step
  (is (= 4 (count (all-next-for-path (list solution) (atom #{}) possible-next-steps)))))

(deftest when-getting-more-next-already-there
  (is (= 3 (count (all-next-for-path (list solution one-step) (atom #{one-step}) possible-next-steps)))))

(deftest when-going-down-a-level
  (is (= 4 (count (next-level solution-as-state (atom #{}) possible-next-steps)))))

(deftest lazily-get-all-solutions
  (is (= (list 1 2 2 2 2 3 3 3 3 3) (map count (take 10 (all-paths solution-as-state (atom #{}) possible-next-steps))))))

(deftest when-is-solution
  (is (= (list solution) (solve solution #{solution} possible-next-steps))))

(deftest when-one-step-to-solution
  (is (= (list one-step solution) (solve one-step #{solution} possible-next-steps))))

(deftest when-longer-steps-to-solution
 (is (= 6 (count (solve initial #{solution} possible-next-steps)))))
