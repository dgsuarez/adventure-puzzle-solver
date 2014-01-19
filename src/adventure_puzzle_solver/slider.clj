(ns adventure-puzzle-solver.slider
  (:require [adventure-puzzle-solver.core :refer :all]))

(defn free-cells [puzzle]
  (for [[x row] (map-indexed vector puzzle)
        y (range (count row))
        :when (= 0 (get-cell puzzle [x y]))]
    [x y]))

(defn possible-next-steps
  "generates the possible next steps for a given puzzle" 
  [puzzle]
  (let [moves #{[-1 0] [1 0] [0 1] [0 -1]}
        [x y] (first (free-cells puzzle))
        new-coords (map (fn [[dx dy]] 
                          [(+ x dx) (+ y dy)]) moves)]
    (set (for [[nx ny] new-coords 
               :when (get-cell puzzle [nx ny])] 
           (swap-cells puzzle [x y] [nx ny])))))

(defn make-solution [puzzle]
  #(= puzzle %))
