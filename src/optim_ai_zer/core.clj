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
  (println (str "Hello! Please use REPL for now :)")))

(defn articles-to-db
  [provider topic]
  (db/insert-articles (f/art-from provider topic)))

(defn best-tour
  ([] (best-tour sim/cities 5000 0.003))
  ([cities temp cooling-rate]
   (sim/simulate-annealing! cities temp cooling-rate)))

(defn similar-articles-by-article
  [art-num tf-m]
  (sim/sim-ann-articles! art-num tf-m 5000 0.003))

(defn article-map
  [art-list corpus]
  (map #(nth corpus %) art-list))

(defn similar-articles-by-words
  [kwords dt-m]
  (sim/sim-by-kwords! kwords dt-m 10000 0.002))

;;(time (def dtm (co/dt-m)))

(defn show-data
  [art-num dtm]
  (let [indices (similar-articles-by-article art-num dtm) docs (db/all-articles)]
   (take 5 (map (fn [x] {:art-num x
                        :length (count (:content (nth docs x)))
                        :same-kwords (let [sk (co/same-kwords art-num x)]
                                       [(count sk) sk])
                        ;;:kwords (keys (co/kwords-for x))
                         }) indices))))
;;(show-data 1)

