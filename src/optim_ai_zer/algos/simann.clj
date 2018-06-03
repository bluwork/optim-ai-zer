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

;; 1. Generate random solution

;; 2. Calculate cost of generated solution using some cost function defined ?!

;; 3. Generate a random neighboring solution

;; 4. Calculate the new solution's cost

;; 5. Compare them:
;; if cost-new < cost-old  -  move to new solution
;; if cost-new > cost-old - maybe move to the new solution

;; 6. Repeat from 3 to 5 until an acceptable solution is found or maximum numbers
;;    of iterations is reached

;; Very important parameter - temperature

;; TSP Traveling Salesman problem

(def cities [{:name "Paris" :x 60 :y 200} {:name "Lyon" :x 180 :y 200} {:name "La Rochelle" :x 80 :y 180}
             {:name "Bordeaux" :x 140 :y 180} {:name "Lenz" :x 20 :y 160} {:name "Nice" :x 100 :y 160}
             {:name "Lille" :x 200 :y 160} {:name "Rennes" :x 140 :y 140} {:name "Brest" :x 40 :y 120}
             {:name "Toulon" :x 100 :y 120} {:name "Nancy" :x 180 :y 100} {:name "Calais" :x 60 :y 80}])

(defn acceptance-probability
  [curr-dist new-dist temp]
  (if (< new-dist curr-dist)
    1.0
    (Math/exp (/ (- curr-dist new-dist) temp))))

(defn ttd
  "Calculate tour total distance"
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
(simulate-annealing! cities 5000 0.003)

(defn calculate-cost
  [matrix reper sol temp]
  (let [next-sol (u/rand-w-o-num! (mrows matrix) reper)]
    (if (> (acceptance-probability (u/vec-dist matrix reper sol) (u/vec-dist matrix reper next-sol) temp) (rand))
      next-sol
      sol)))

(defn sim-ann-articles!
  [reper matrix init-temp cr]
  (let [best (atom [(dec (mrows matrix))])]
    (loop [temp init-temp sol (inc reper)]
      (if (< (u/vec-dist matrix reper sol) (u/vec-dist matrix reper (last @best))) (swap! best conj sol))
      (if (< temp 1)
        (reverse @best)
        (recur (* temp (- 1 cr)) (calculate-cost matrix reper sol temp) )))))

(def m (corpus/tf-m))  
(sim-ann-articles! 0 m 10000 0.002)

(def corpus (db/all-articles))
(map #(:title %) (map #(nth corpus  %) (sim-ann-articles! 54 m 10000 0.003)))
