(ns optim-ai-zer.algos.hill
  (:require [optim-ai-zer.utils :as u]))

(def cities [{:name "Paris" :x 60 :y 200} {:name "Lyon" :x 180 :y 200} {:name "La Rochelle" :x 80 :y 180}
             {:name "Bordeaux" :x 140 :y 180} {:name "Lenz" :x 20 :y 160} {:name "Nice" :x 100 :y 160}
             {:name "Lille" :x 200 :y 160} {:name "Rennes" :x 140 :y 140} {:name "Brest" :x 40 :y 120}
             {:name "Toulon" :x 100 :y 120} {:name "Nancy" :x 180 :y 100} {:name "Calais" :x 60 :y 80}])

;; 1. Generate random solution

;; 2. Calculate cost of generated solution - here that is tour total distance

;; 3. Generate a random neighboring solution

;; 4. Calculate the new solution cost

;; 5. Compare them - in this case lower is better

;; 6. Repeat some time - in this case best result is with 1000

;; 7. Get best solution


(defn ttd
  "Calculate tour total distance"
  [tour]
  (loop [accum 0 distance 0]
    (if (= accum (count tour))
      distance
      (recur (inc accum) (if (= accum  (dec (count tour)))
                           (+ distance (u/euclid-dist (nth tour accum) (first tour)))
                           (+ distance (u/euclid-dist (nth tour accum) (nth tour (inc accum)))))))))

(defn climb!
  ([cities] (climb! cities 1000))
  ([cities repetitions]
  (let [init-sol cities best-solution (atom cities)]
    (loop [counter repetitions solution init-sol]
      (if (< (ttd solution) (ttd (deref best-solution))) (reset! best-solution solution))
      (if (< counter 1)
        (str "Final solution distance: " (ttd (deref best-solution)) " Tour: " (deref best-solution) )
        (recur (dec counter) (let [new-solution (u/r-swap-val! solution)]
                                             (if (< (ttd new-solution) (ttd solution))
                                               new-solution
                                               solution))))))))


