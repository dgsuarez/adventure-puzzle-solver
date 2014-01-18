(ns adventure-puzzle-solver.bfser)

(defn in? [seq elm]  
  (some #(= elm %) seq))

(defn all-next [current get-next-states]
  (let [next-states (get-next-states (first current))]
    (for [s next-states
          :when (not (in? current s))] 
      (conj current s))))

(defn take-step [explored get-next-states]
  (mapcat #(all-next % get-next-states) explored))

(defn get-all-states [explored get-next-states]
  (lazy-cat explored (get-all-states (take-step explored get-next-states) get-next-states)))
            
(defn solve [state solution? get-next-states]
  (let [initial (list (list state))]
    (reverse (first (filter #(solution? (first %)) (get-all-states initial get-next-states))))))

