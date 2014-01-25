(ns adventure-puzzle-solver.best-first-test
  (:require [clojure.test :refer :all]
            [adventure-puzzle-solver.slider :refer :all]
            [clojure.data.priority-map :refer :all]
            [adventure-puzzle-solver.best-first :refer :all]))


(def one-step [[1 2 3]
               [0 4 5]
               [6 7 8]])

(def solution [[1 2 3]
               [4 0 5]
               [6 7 8]])

(def initial [[4 1 3]
              [2 7 5]
              [6 0 8]])

(defn qish-scorer [solution]
  (count solution))

(def is-slider-solution? (make-solution solution))

(deftest adding-states-to-the-queue 
  (is (= 4 (count (explore-next-state (list solution) (priority-map) (atom #{}) qish-scorer possible-next-steps)))))

(deftest get-a-solution
  (is (= 6 (count (solve initial is-slider-solution? qish-scorer possible-next-steps)))))
