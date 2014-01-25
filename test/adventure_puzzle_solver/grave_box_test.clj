(ns adventure-puzzle-solver.grave-box-test
  (:require [clojure.test :refer :all]
            [adventure-puzzle-solver.grave-box :refer :all]
            [adventure-puzzle-solver.best-first :refer :all]))

(def start [[0 0 0 0 0 0 0 0 0]
            [2 6 3 7 5 4 4 8 0]
            [0 0 0 0 0 0 0 0 0]])

(def end   [[0 0 0 0 0 0 0 0 0]
            [7 8 2 3 5 4 6 4 0]
            [0 0 0 0 0 0 0 0 0]])

(def small [[1 2 3 0]
            [4 5 0 0]
            [0 0 6 7]])

(deftest get-the-index-of-moveable-pairs-in-a-row
  (is (= '(0 1 4 5) (pairs-in-row [1 2 3 0 2 3 1]))))

(deftest get-all-the-moveable-pairs
  (is (= '([0 0] [0 1] [1 0] [2 2])
         (pairs small))))

(deftest get-a-new-pair-from-a-move
  (is (= [1 1] (new-pair [1 0] [0 1]))))

(deftest get-the-cells-in-a-pair
  (is (= (list [0 0] [0 1]) (pair-cells [0 0]))))

(deftest generate-a-single-next-step
  (is (= [1 0 2 3] 
         (get (move-cells small [[0 1] [0 2]] [[0 2] [0 3]]) 0))))

(deftest know-if-a-move-is-invalid
  (is (not (valid-move? small [[0 1] [0 2]] [[1 1] [1 2]]))))

(deftest know-if-a-move-is-valid
  (is (valid-move? small [[0 1] [0 2]] [[0 2] [0 3]])))

(deftest all-get-moving
  (is (= 5 (count (possible-next-steps small)))))

(deftest all-moving-with-full
  (is (= 15 (count (possible-next-steps start)))))

(deftest scorer-scores-right
  (is (= 6 (scorer start (list end)))))

(deftest solve-it
  (let [solution (solve start #{end} #(scorer end %) possible-next-steps)]
    (println solution)
    (println (count solution))))

