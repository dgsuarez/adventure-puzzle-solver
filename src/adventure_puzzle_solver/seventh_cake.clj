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

(defn done? [{:keys [cake]}]
  (every? #(>= % 3) (flatten cake)))

(defn update-spec [original-spec state]
  (if (full-piece? state)
    (merge state {:spec original-spec :piece-num (inc (:piece-num state))})
    state))

(defn rec-split-cake [original-spec state]
  (if (done? state) 
    state
    (first (remove nil? (map #(rec-split-cake original-spec %) 
                             (chunks-from (update-spec original-spec state)))))))

(defn split-cake [state]
  (rec-split-cake (:spec state) (update-position '(0 0) state)))


