(ns ^{:author "BLu"
      :doc "Genetic algorithm"} optim-ai-zer.algos.genalg)


(defn by-fitness
  [chromosomes]
  (sort-by :fitness #(> %1 %2) chromosomes))


(defn fitness
  [chromosome sol]
  (loop [len (count chromosome) fitness 0]
    (if (< len 1)
      fitness
      (recur (dec len)
             (if (= (nth chromosome (dec len)) (nth sol (dec len)))
               (inc fitness)
               fitness)))))

(defn gen-chrom!
  [sol]
  (let [genes (vec (take (count sol) (repeatedly #(rand-int 2))))]
    {:fitness (fitness genes sol) :genes genes}))

;; Population initialization

(defn init-population!
  [size sol]
  (loop [pop-size size chromosomes []]
    (if (< pop-size 1)
      (by-fitness chromosomes) 
      (recur (dec pop-size)
             (conj chromosomes (gen-chrom! sol))))))

(defn mut-chrom!
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
  [one two sol]
  (loop [accum 0 crossed-genes []]
    (if (> accum (dec (count (:genes one))))
      {:fitness (fitness crossed-genes sol) :genes crossed-genes}
      (recur (inc accum)
             (if (= (rand-int 2) 1)
               (conj crossed-genes (nth (:genes one) accum))
               (conj crossed-genes (nth (:genes two) accum)))))))

(defn tournament-pop
  ([popul] (tournament-pop popul 3))
  ([popul ts-size]
   (loop [counter ts-size tour-pop []]
      (if (< counter 1)
        (first (by-fitness tour-pop))
        (recur (dec counter)
               (conj tour-pop (nth popul (rand-int (count popul)) )))))))

(defn cross-pop
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
  [popul sol]
  (by-fitness (mutate-pop (cross-pop popul sol) sol)))

(defn find-solution
  [popul sol]
  (loop [p popul generation 0]
    (if (or (= (:fitness (first p)) 10) (> generation 9999))
      (if (< generation 10000)
        (str "Solution is found in generation: "  generation " Data: " (first p))
        (str "No solution found. Best result (match) " (first p)))
      (recur (evolve-pop p sol) (inc generation)))))


;(let [sol [1 1 0 1 0 0 1 1 1 0]] (find-solution (init-population! 15 sol) sol))

(def art-sol (corpus/get-some-kwords))
