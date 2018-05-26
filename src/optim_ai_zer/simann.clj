(ns ^{:author "BLu"
      :doc "Simulated Annealing algorithm.
            Strength is that it avoids getting caught at local max
            local-max - solution that is better than any others nearby, but
            isn't the very best."}
    optim-ai-zer.simann)

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

;; TSP The Salesman problem


(def cities [{:name "Paris" :x 60 :y 200} {:name "Lyon" :x 180 :y 200} {:name "La Rochelle" :x 80 :y 180}
             {:name "Bordeaux" :x 140 :y 180} {:name "Lenz" :x 20 :y 160} {:name "Nice" :x 100 :y 160}
             {:name "Lille" :x 200 :y 160} {:name "Rennes" :x 140 :y 140} {:name "Brest" :x 40 :y 120}
             {:name "Toulon" :x 100 :y 120} {:name "Nancy" :x 180 :y 100} {:name "Calais" :x 60 :y 80}])

(def temp 100000)


(def cooling-rate 0.003)


(defn dist-cities
  "Calculate Euclidian distance between two cities"
  [ct1 ct2]
  (Math/sqrt (+ (Math/pow (- (:x ct1) (:x ct2)) 2) (Math/pow (- (:y ct1) (:y ct2)) 2))))

(dist-cities (nth cities 0) (nth cities 11))

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
                           (+ distance (dist-cities (nth tour accum) (first tour)))
                           (+ distance (dist-cities (nth tour accum) (nth tour (inc accum)))))))))

(ttd cities)

(defn swap-cities!
  "Swap two random cities"
  [cities]
  (loop [pos1 (rand-int (count cities)) pos2 (rand-int (count cities))]
    (if (= pos1 pos2)
      (recur pos1 (rand-int (count cities)))
      (assoc (assoc cities pos1 (nth cities pos2)) pos2 (nth cities pos1)))))


(defn simulate-annealing!
  [cities init-temp cooling-rate]
  (let [init-sol cities best-solution (atom cities)]
    (loop [temp init-temp solution init-sol]
      (println (ttd solution))
      (if (< (ttd solution) (ttd (deref best-solution))) (reset! best-solution solution))
      (if (< temp 1)
        (str "Final solution distance: " (ttd (deref best-solution)) "Tour: " (deref best-solution) )
        (recur (* temp (- 1 cooling-rate)) (let [new-solution (swap-cities! solution)]
                                             (if (> (acceptance-probability (ttd solution) (ttd new-solution) temp) (rand))
                                               new-solution
                                               solution)))))))



(time (simulate-annealing! (shuffle cities) 100000 0.003))
