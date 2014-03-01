(ns adventure-puzzle-solver.seventh-cake
  (:require [clojure.set]
            [adventure-puzzle-solver.core :refer :all]))

(defn get-starting-piece-number [cake]
  (->> cake
       flatten
       (apply max)
       inc))

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

(defn full-piece? [{spec :spec}]
  (every? zero? (vals spec)))

(defn solution? [{piece-num :piece-num} state]
  (every? #(>= % piece-num) (flatten (:cake state))))

(defn update-spec [{spec :spec} state]
  (if (full-piece? state)
    (merge state {:spec spec :piece-num (inc (:piece-num state))})
    state))

(defn get-next-states [original-state state]
  (let [state (update-spec original-state state)]
    (map #(update-position % state) (valid-next state))))

(defn get-spec [pieces cake]
  (into {} (map #(vector (first %) (-> % last count (/ pieces))) 
                (group-by identity (flatten cake)))))

(defn base-state [pieces cake]
  {:cake cake 
   :spec (get-spec pieces cake)
   :piece-num (get-starting-piece-number cake)})

(defn build-initial-state [pieces cake]
  (update-position '(0 0) (base-state pieces cake)))

(defn build-solution-checker [pieces cake]
  (partial solution? (base-state pieces cake)))

(defn build-get-next-states [pieces cake]
  (partial get-next-states (base-state pieces cake)))

