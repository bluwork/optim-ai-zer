(ns ^{:author "BLu"
      :doc "GMO - genetically modified objects"}
    optim-ai-zer.gmo)

(defn selection [population]
  (fittest population))

(defn crossover [])

(defn mutation [population]
  (let [mut-point (rand-int (:gene-length (first population)))]
    ))

(defn fittest
  [population]
  (loop [cnt (count population) max-fit 0]
    (if (< cnt 1)
      "finished"
      (recur (dec cnt)))))

(defn second-fittest
  [])
(selection )
(take 10 (repeatedly #(rand-int 42)))

(defn gen-individuals
  "Param population size"
  [pop-size genes-len]
  (loop [pop-size pop-size individuals []]
    (if (< pop-size 1)
      individuals
      (recur (dec pop-size) (conj individuals {:fitness 0 :genes (take genes-len (repeatedly #(rand-int 2)) )})))))
(gen-individuals 3 10)

(:genes (first (gen-individuals 3 10)))
