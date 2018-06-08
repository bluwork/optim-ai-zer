(ns ^{:author "BLu"
      :doc "Simulated Annealing algorithm.
            Strength is that it avoids getting caught at local max
            local-max - solution that is better than any others nearby, but
            isn't the very best."}
    optim-ai-zer.algos.simann
  (:require [optim-ai-zer.utils :as u]
            [optim-ai-zer.prep.corpus :as corpus]
            [optim-ai-zer.prep.optibase :as db]
            [uncomplicate.neanderthal.core :refer :all]))

;; TSP Traveling Salesman problem

(def cities [{:name "Paris" :x 60 :y 200} {:name "Lyon" :x 180 :y 200} {:name "La Rochelle" :x 80 :y 180}
             {:name "Bordeaux" :x 140 :y 180} {:name "Lenz" :x 20 :y 160} {:name "Nice" :x 100 :y 160}
             {:name "Lille" :x 200 :y 160} {:name "Rennes" :x 140 :y 140} {:name "Brest" :x 40 :y 120}
             {:name "Toulon" :x 100 :y 120} {:name "Nancy" :x 180 :y 100} {:name "Calais" :x 60 :y 80}])

(defn acceptance-probability
  "Returns probability based on distances and temperature."
  [curr-dist new-dist temp]
  (if (< new-dist curr-dist)
    1.0
    (Math/exp (/ (- curr-dist new-dist) temp))))

(defn ttd
  "Calculate tour total distance for Traveling Salesman Problem."
  [tour]
  (loop [accum 0 distance 0]
    (if (= accum (count tour))
      distance
      (recur (inc accum) (if (= accum  (dec (count tour)))
                           (+ distance (u/euclid-dist (nth tour accum) (first tour)))
                           (+ distance (u/euclid-dist (nth tour accum) (nth tour (inc accum)))))))))


(defn simulate-annealing!
  "Try to determine tour with lowest distance between cities
  best solution (b-sol) is lowest solution in this case
  default temp 10000 cooling-rate 0.003"
  ([cities] (simulate-annealing! cities 10000 0.003))
  ([cities init-temp cooling-rate]
   (let [init-sol cities b-sol (atom cities)]
     (loop [temp init-temp sol init-sol]
       (if (< (ttd sol) (ttd (deref b-sol))) (reset! b-sol sol))
       (if (< temp 1)
         (str "Final solution distance: " (ttd (deref b-sol)) "Tour: " (deref b-sol) )
         (recur (* temp (- 1 cooling-rate)) (let [new-sol (u/r-swap-val! sol)]
                                              (if (> (acceptance-probability (ttd sol) (ttd new-sol) temp) (rand))
                                                new-sol
                                                sol))))))))

(defn calculate-cost
  "Calculate cost and return old or new solution based on
   acceptance probability"
  [matrix reper sol temp]
  (let [next-sol (u/rand-w-o-num! (mrows matrix) reper)]
    (if (> (acceptance-probability (u/vec-dist matrix reper sol) (u/vec-dist matrix reper next-sol) temp) (rand))
      next-sol
      sol)))

(defn check-best
  [matrix reper sol best]
  (if (< (u/vec-dist matrix reper sol) (u/vec-dist matrix reper (last @best)))
    (swap! best conj sol)))

(defn sim-ann-articles!
  "Iterative process for article recommendation system"
  [reper matrix init-temp cr]
  (let [best (atom [(rand-int (mrows matrix))])]
    (loop [temp init-temp sol (inc reper)]
      (check-best matrix reper sol best)
      (if (< temp 1)
        (reverse @best)
        (recur (* temp (- 1 cr)) (calculate-cost matrix reper sol temp) )))))

