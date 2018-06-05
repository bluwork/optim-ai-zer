(ns ^{:author "BLu"
      :doc "entry point for optim-ai-zer
           Contains func which serve data to world"}
    optim-ai-zer.core
  (:require [optim-ai-zer.algos.simann :as sim]
            [optim-ai-zer.prep.corpus :as co]
            [optim-ai-zer.prep.feed :as f]
            [optim-ai-zer.prep.optibase :as db]
            [clojure.set :as s]))

(defn -main
  "Entry point"
  []
  (println "Hello! Please use REPL for now :)"))

(defn articles-to-db
  [provider topic]
  (db/insert-articles (f/art-from provider topic)))

(defn best-tour
  ([] (best-tour sim/cities 5000 0.003))
  ([cities temp cooling-rate]
   (sim/simulate-annealing! cities temp cooling-rate)))

(defn similar-articles
  [art-num tf-m]
  (sim/sim-ann-articles! art-num tf-m 10000 0.002))

(defn article-map
  [art-list corpus]
  (map #(nth corpus %) art-list))

(defn analyze
  [art-num corpus tf-m]
  (map #(co/kwords (:content %)) (map #(nth corpus %) similar-articles(art-num tf-m))))

(defn kw
  [num corpus]
  (let [art-kw (co/kwords (:content (nth corpus num)))]
    art-kw))

(defn same-kws
  [art1 art2]
  (s/intersection (set (kw art1)) (set (kw  art2))))
