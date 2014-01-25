(ns adventure-puzzle-solver.bfser)

(defn all-next [current seen get-next-states]
  (for [s (get-next-states (first current))
    :when (not (contains? @seen s))] 
    (do 
      (swap! seen conj s)
      (conj current s))))

(defn take-step [explored seen get-next-states]
  (mapcat #(all-next % seen get-next-states) explored))

(defn get-all-states [explored seen get-next-states]
  (lazy-cat explored (get-all-states (take-step explored seen get-next-states) seen get-next-states)))
            
(defn solve [state solution? get-next-states]
  (let [initial (list (list state))
        seen (atom #{})]
    (reverse (first (filter #(solution? (first %)) (get-all-states initial seen get-next-states))))))

