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
      (sort-by-fitness chromosomes) 
      (recur (dec pop-size)
             (conj chromosomes (generate-chromosome! sol))))))


(defn mutate-pop
  [population]
  (loop [accum 0 mutated-pop []]
    (if (> accum (dec (count population)))
      mutated-pop
      (recur (inc accum)
             (if (< accum num-of-elite)
               (conj mutated-pop (nth population accum))
               (conj mutated-pop (mut-chrom! (nth population accum))))))))

(defn mut-chrom!
  [chromosome]
  (loop [accum 0 mutated-genes []]
    (if (> accum (dec (count (:genes chromosome))))
      {:fitness (fitness mutated-genes) :genes mutated-genes}
      (recur (inc accum)
             (if (< (rand) mutation-rate)
               (conj mutated-genes 1)
               (conj mutated-genes (nth (:genes chromosome) accum)))))))

(defn cross-pop
  [population]
  (loop [accum 0 crossed-pop []]
    (if (> accum (dec (count population)))
      crossed-pop
      (recur (inc accum)
             (if (< accum num-of-elite)
               (conj crossed-pop (nth population accum))
               (conj crossed-pop (cross-chrom! (first (select-tournament-pop population)) (first (select-tournament-pop population)) )))))))

(defn evolve-pop
  [population]
  (sort-by-fitness ((comp mutate-pop cross-pop) population)))

(evolve-pop  (init-population! 10 sol))

(defn cross-chrom!
  [one two]
  (loop [accum 0 crossed-genes []]
    (if (> accum (dec (count (:genes one))))
      {:fitness (fitness crossed-genes) :genes crossed-genes}
      (recur (inc accum)
             (if (= (rand-int 2) 1)
                             (conj crossed-genes (nth (:genes one) accum))
                             (conj crossed-genes (nth (:genes two) accum)))))))



(defn select-tournament-pop
  [population]
  (let [tp-size tournament-selection-size ]
    (loop [counter tp-size tour-pop []]
      (if (< counter 1)
        (sort-by-fitness tour-pop)
        (recur (dec counter)
               (conj tour-pop (nth population (rand-int (count population)) )))))))

(select-tournament-pop (init-population! 10 sol))






(cross-chrom! (first (select-tournament-pop (init-population! 10 sol))) (first (select-tournament-pop (init-population! 10 sol))))

(mut-chrom! {:genes chrom1})


(defn find-solution
  []
  (loop [population (init-population! 10 sol) generation 0]
    (if (or (> generation 100000) (= (:fitness (first population)) 10))
      (str "Solution is find in generation: " generation)
      (recur (evolve-pop population) (inc generation)))))


(find-solution)