(ns adventure-puzzle-solver.best-first
  (:require [clojure.data.priority-map :refer :all]))

(defn make-entry [state solution scorer]
  (let [solution (conj solution state)]
    [solution (scorer solution)]))

(defn explore-next-state [solution queue seen scorer get-next-states]
  (reduce #(do (swap! seen conj %2)
               (conj %1 (make-entry %2 solution scorer)))
          queue 
          (remove #(contains? @seen %) (get-next-states (first solution)))))

(defn solve [initial solution? scorer get-next-states] 
  (let [seen (atom #{})
        initial (list initial)
        queue (priority-map initial (scorer initial))]
    (loop [queue queue]
      (let [current (-> queue peek first)]
        (if (solution? (first current))
          (reverse current)
          (recur (explore-next-state current (pop queue) seen scorer get-next-states)))))))

