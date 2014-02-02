(ns adventure-puzzle-solver.slider
  (:require [adventure-puzzle-solver.core :refer :all]))

(defn swap-cells [puzzle coord1 coord2]
  (let [val1 (get-in puzzle coord1)
        val2 (get-in puzzle coord2)]
    (assoc-in (assoc-in puzzle coord1 val2) coord2 val1)))

(defn free-cell [puzzle]
  (first (for [[x row] (map-indexed vector puzzle)
               y (range (count row))
               :when (= 0 (get-in puzzle [x y]))]
           [x y])))

(defn possible-next-steps [puzzle]
  (let [moves #{[-1 0] [1 0] [0 1] [0 -1]}
        [x y] (free-cell puzzle)
        new-coords (map (fn [[dx dy]] 
                          [(+ x dx) (+ y dy)]) moves)]
    (for [[nx ny] new-coords 
          :when (get-in puzzle [nx ny])] 
      (swap-cells puzzle [x y] [nx ny]))))

(defn compare-states [a b]
  (- (count (flatten a)) (count (filter identity (map = (flatten a) (flatten b))))))

(defn scorer [solution current]
  [(compare-states solution (first current)) (count current)])
