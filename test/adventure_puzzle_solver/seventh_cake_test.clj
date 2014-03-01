(ns adventure-puzzle-solver.seventh-cake-test
  (:require [clojure.test :refer :all]
            [adventure-puzzle-solver.seventh-cake :refer :all]
            [adventure-puzzle-solver.best-first :as best-first]))

(def cake [[1 2 2 1 2 1]
           [0 2 1 1 2 1] 
           [0 2 2 0 1 0]
           [1 1 2 0 1 2]
           [2 2 1 0 1 2]])

(def solution [[3 8 8 8 7 6] 
               [3 3 3 8 7 6] 
               [4 4 3 8 7 6] 
               [4 4 4 7 7 6] 
               [5 5 5 5 5 6]])

(def spec {0 1, 1 2, 2 2})

(def state {:last-pos '(0 0)
            :cake cake
            :spec spec
            :piece-num 3})

(deftest it-should-get-valid-next-chunks
  (is (= '((1 0) (0 1)) (valid-next state)))
  (is (= '() (valid-next (assoc-in state [:spec] {}))))
  (is (= '((1 0)) (valid-next (assoc-in state [:spec] {0 1})))))

(deftest it-gets-a-single-chunk-for-a-piece
  (let [state (first (chunks-from state))]
    (is (= (:spec state) {0 0, 1 2, 2 2}))
    (is (= (:last-pos state) [1 0]))
    (is (= (get-in (:cake state) [1 0]) 3))))

(deftest it-knows-if-a-piece-is-full 
  (is (full-piece? {:spec {1 0}}))
  (is (not (full-piece? {:spec {1 2}}))))

(deftest it-finds-a-solution 
  (is (= solution (solve {:spec spec, :cake cake}))))

