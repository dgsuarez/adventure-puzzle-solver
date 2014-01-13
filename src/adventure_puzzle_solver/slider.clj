(ns adventure-puzzle-solver.slider)

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

(defn free-cells [puzzle]
  (for [[x row] (map-indexed #(vector %1 %2) puzzle)
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
