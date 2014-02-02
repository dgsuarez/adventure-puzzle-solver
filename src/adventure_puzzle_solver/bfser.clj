(ns adventure-puzzle-solver.bfser)

(defn all-next-for-path [path seen get-next-states]
  (for [s (get-next-states (first path)) :when (not (contains? @seen s))] 
    (do 
      (swap! seen conj s)
      (conj path s))))

(defn next-level [paths seen get-next-states]
  (mapcat #(all-next-for-path % seen get-next-states) paths))

(defn all-paths [paths seen get-next-states]
  (lazy-cat paths (all-paths (next-level paths seen get-next-states) seen get-next-states)))
            
(defn solve [state solution? get-next-states]
  (let [initial (list (list state))
        seen (atom #{})]
    (reverse (first (filter #(solution? (first %)) (all-paths initial seen get-next-states))))))

