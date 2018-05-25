(ns ^{:author "BLu"
      :doc "Genetic algorithm"}
    optim-ai-zer.gmo)

;; Solution - target chromosome
(def sol [1 1 0 1 0 0 1 1 1 0 ])

(def num-of-elite 1)

(def tournament-selection-size 4)

(def mutation-rate 0.1)


(defn sort-by-fitness
  [chromosomes]
  (sort-by :fitness #(> %1 %2) chromosomes))


(defn fitness
  [chromosome]
  (loop [len (count chromosome) fitness 0]
    (if (< len 1)
      fitness
      (recur (dec len)
             (if (= (nth chromosome (dec len)) (nth sol (dec len)))
               (inc fitness)
               fitness)))))

(defn generate-chromosome!
  [solution]
  (let [genes (vec (take (count solution) (repeatedly #(rand-int 2))))]
    {:fitness (fitness genes) :genes genes}))

;; Population initialization



(defn init-population!
  [size sol]
  (loop [pop-size size chromosomes []]
    (if (< pop-size 1)
      chromosomes 
      (recur (dec pop-size)
             (conj chromosomes (generate-chromosome! sol))))))


(defn cross-pop
  [population])


(defn mutate-pop
  [population])


(defn evolve-pop
  [population]
  ((comp mutate crossover) population))

(defn cross-chrom!
  [one two]
  (loop [accum 0 crossed-genes []]
    (if (> accum (dec (count one)))
      {:fitness (fitness crossed-genes) :genes crossed-genes}
      (recur (inc accum)
             (if (= (rand-int 2) 1)
                             (conj crossed-genes (nth one accum))
                             (conj crossed-genes (nth two accum)))))))

(defn mut-chrom!
  [chromosome]
  (loop [accum 0 mutated-genes []]
    (if (> accum (dec (count chromosome)))
      {:fitness (fitness mutated-genes) :genes mutated-genes}
      (recur (inc accum)
             (if (< (rand) mutation-rate)
               (conj mutated-genes 1)
               (conj mutated-genes (nth chromosome accum)))))))

(def chrom1 [1 1 0 1 0 0 1 1 0 1])
(def chrom2 [1 0 1 0 1 0 1 0 1 0])

(cross-chrom! chrom1 chrom2 )
(fitness chrom1)
(fitness chrom2)

(mut-chrom! chrom1)
