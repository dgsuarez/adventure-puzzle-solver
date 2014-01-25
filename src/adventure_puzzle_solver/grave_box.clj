(ns adventure-puzzle-solver.grave-box
  (:require [clojure.set]
            [adventure-puzzle-solver.core :refer :all]))

(defn pairs-in-row [row]
  (let [idx-pairs (map-indexed vector (map vector row (rest row)))
        filtered (remove #(some #{0} (last %)) idx-pairs)]
    (map first filtered)))

(defn pairs [puzzle]
  (mapcat identity (map-indexed #(map vector (repeat %1) (pairs-in-row %2)) puzzle)))

(defn new-pair [pair move]
  (vec (map + pair move)))

(defn pair-cells [pair]
  [pair [(first pair) (+ 1 (last pair))]])

(defn cell-values [puzzle cells]
  (map #(get-cell puzzle %) cells))

(defn move-cells [puzzle from to]
  (let [new-froms (map vector from '(0 0))
        new-tos (map vector to (cell-values puzzle from))
        new-to-set (concat new-froms new-tos)]
    (reduce #(apply assoc-cell %1 %2) puzzle new-to-set)))

(defn valid-move? [puzzle from to]
  (let [unexplored (clojure.set/difference (set to) (set from))]
    (and 
      (not (some #{0} (cell-values puzzle from))) 
      (every? #{0} (cell-values puzzle unexplored)))))

(defn possible-next-steps [puzzle]
  (remove nil? (for [move #{[-1 0] [1 0] [0 1] [0 -1]}
                     pair (pairs puzzle)
                     :let [from (pair-cells pair)
                           to (pair-cells (new-pair pair move))]
                     :when (valid-move? puzzle from to)]
                 (move-cells puzzle from to))))

(defn scorer [solution current]
  (- (count (first solution)) (count (filter identity (map = (get solution 1) (get (first current) 1))))))
