(ns adventure-puzzle-solver.core)

(defn get-cell [puzzle coord]
  (let [[x y] coord]
    (get (get puzzle x) y)))

(defn assoc-cell [puzzle coord val]
  (let [[x y] coord]
    (assoc puzzle x (assoc (get puzzle x) y val))))

(defn swap-cells [puzzle coord1 coord2]
  (let [val1 (get-cell puzzle coord1)
        val2 (get-cell puzzle coord2)]
    (assoc-cell (assoc-cell puzzle coord1 val2) coord2 val1)))
