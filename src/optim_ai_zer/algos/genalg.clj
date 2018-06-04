(ns ^{:author "BLu"
      :doc "Genetic algorithm"}
    optim-ai-zer.algos.genalg
  (:require [optim-ai-zer.prep.corpus :as corpus]
            [optim-ai-zer.utils :as u]
            [uncomplicate.neanderthal.core :refer :all]))

(defn by-fitness
  "Sort chromosomes (individuals) by fitness of genes"
  [chromosomes]
  (sort-by :fitness #(> %1 %2) chromosomes))

(defn fitness
  "Calculate fitness for chromosome related to solution"
  [chromosome sol]
  (loop [len (count chromosome) fitness 0]
    (if (< len 1)
      (/ fitness (count chromosome))
      (recur (dec len)
             (if (= (nth chromosome (dec len)) (nth sol (dec len)))
               (inc fitness)
               fitness)))))

(defn gen-chrom!
  "Generate initial chromosomes for init pop"
  [sol]
  (let [genes (vec (take (count sol) (repeatedly #(rand-int 2))))]
    {:fitness (fitness genes sol) :genes genes}))

(defn init-population!
  "Create initial population"
  [size sol]
  (loop [pop-size size chromosomes []]
    (if (< pop-size 1)
      (by-fitness chromosomes) 
      (recur (dec pop-size)
             (conj chromosomes (gen-chrom! sol))))))

(defn mut-chrom!
  "Mutate chromosomes for given mutation rate."
  ([chromosome sol] (mut-chrom! chromosome sol 0.01))
  ([chromosome sol mutation-rate]
  (loop [accum 0 mutated-genes []]
    (if (> accum (dec (count (:genes chromosome))))
      {:fitness (fitness mutated-genes sol) :genes mutated-genes}
      (recur (inc accum)
             (if (< (rand) mutation-rate)
               (conj mutated-genes 1)
               (conj mutated-genes (nth (:genes chromosome) accum))))))))

(defn mutate-pop
  "Mutate population"
  ([population sol] (mutate-pop population sol 1))
  ([population sol num-of-elite]
  (loop [accum 0 mutated-pop []]
    (if (> accum (dec (count population)))
      mutated-pop
      (recur (inc accum)
             (if (< accum num-of-elite)
               (conj mutated-pop (nth population accum))
               (conj mutated-pop (mut-chrom! (nth population accum) sol))))))))

(defn cross-chrom!
  "Crossover genes from two chromosomes"
  [one two sol]
  (loop [accum 0 crossed-genes []]
    (if (> accum (dec (count (:genes one))))
      {:fitness (fitness crossed-genes sol) :genes crossed-genes}
      (recur (inc accum)
             (if (= (rand-int 2) 1)
               (conj crossed-genes (nth (:genes one) accum))
               (conj crossed-genes (nth (:genes two) accum)))))))

(defn tournament-pop
  "Returns tournament population for crossovering general population"
  ([popul] (tournament-pop popul 3))
  ([popul ts-size]
   (loop [counter ts-size tour-pop []]
      (if (< counter 1)
        (first (by-fitness tour-pop))
        (recur (dec counter)
               (conj tour-pop (nth popul (rand-int (count popul)) )))))))

(defn cross-pop
  "Crossover population"
  ([popul sol ] (cross-pop popul sol 1))
  ([popul sol num-of-elite]
  (loop [accum 0 crossed-pop []]
    (if (> accum (dec (count popul)))
      crossed-pop
      (recur (inc accum)
             (if (< accum num-of-elite)
               (conj crossed-pop (nth popul accum))
               (conj crossed-pop (cross-chrom! (tournament-pop popul) (tournament-pop popul) sol))))))))

(defn evolve-pop
  "First crossover population, then mutate population"
  [popul sol]
  (by-fitness (mutate-pop (cross-pop popul sol) sol)))

(defn find-solution
  "Iterative process, magic happens here :)"
  [popul sol]
  (loop [p popul generation 0]
    (if (or (= (:fitness (first p)) 1) (> generation 9999))
      (if (< generation 10000)
        (str "Solution is found in generation: "  generation " Data: " (first p))
        (str "No solution found. Best result (match) " (first p)))
      (recur (evolve-pop p sol) (inc generation)))))

(let [sol [1 1 0 1 0 0 1 1 1 0]] (find-solution (init-population! 15 sol) sol))

;(def art-sol (corpus/get-some-kwords))
;; (def bm (corpus/bin-m))
;; (def tf (corpus/tf-m))
;; (map #(println (u/n-cos-sim (row bm 0) (row bm %))) (range 1 61))

;;(find-solution (init-population! 5 (into [] (row bm 0))) (into [] (row bm 0)))


;; Implementation using natives

(defn n-init-pop
  [])

(defn n-gen-chrom!
  [])

(defn n-mutate-chrom!
  [])

(defn n-cross-chrom!
  [])

(defn n-mutate-pop!
  [])

(defn n-cross-pop!
  [])

(defn n-evolve-pop!
  [])

(defn n-tournament-pop
  [])

(defn n-fitness
  [])
