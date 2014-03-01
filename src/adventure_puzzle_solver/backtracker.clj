(ns adventure-puzzle-solver.backtracker)

(defn solve [state solution? get-next-states]
  (if (solution? state)
    [state]
    (if-let [states (some identity (map #(solve % solution? get-next-states) (get-next-states state)))]
      (conj states state))))
