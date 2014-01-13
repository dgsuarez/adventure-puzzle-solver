(ns adventure-puzzle-solver.backtracker)

(defn _solve [checked state solution? get-next-states]
  (let [new-checked (conj checked state)
        next-states (get-next-states state)]
    (cond 
      (empty? next-states) nil
      (contains? checked state) nil
      (solution? state) (list state)
      :else (cons state 
                  (first (drop-while nil? (map #(_solve new-checked % solution? get-next-states) next-states)))))))

(defn solve [state solution? get-next-states]
  (_solve #{} state solution? get-next-states))

