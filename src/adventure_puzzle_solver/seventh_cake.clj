(ns adventure-puzzle-solver.seventh-cake
  (:require [clojure.set]
            [adventure-puzzle-solver.core :refer :all]))

(defn update-position [pos state]
  (let [{:keys [cake piece-num spec]} state]
    (merge state {:cake (assoc-in cake pos piece-num)
                  :last-pos pos
                  :spec (update-in spec [(get-in cake pos)] dec)})))

(defn valid-val? [val]
  (not (or (nil? val) (zero? val))))

(defn valid-next [{:keys [last-pos spec cake]}]
  (filter #(valid-val? (spec (get-in cake %)))
          (for [coord [[-1 0] [0 -1] [1 0] [0 1]]] 
            (map + last-pos coord))))

(defn chunks-from [state]
  (map #(update-position % state) (valid-next state)))

(defn full-piece? [{:keys [spec]}]
  (every? zero? (vals spec)))

(defn done? [n state]
  (every? #(>= % n) (flatten (:cake state))))

(defn update-spec [original-spec state]
  (if (full-piece? state)
    (merge state {:spec original-spec :piece-num (inc (:piece-num state))})
    state))

(defn rec-split-cake [original-state state]
  (if (done? (:piece-num original-state) state) 
    state
    (some identity (map #(rec-split-cake original-state %) 
                        (chunks-from (update-spec (:spec original-state) state))))))

(defn split-cake [state]
  (rec-split-cake state (update-position '(0 0) state)))

