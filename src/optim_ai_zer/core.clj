(ns ^{:author "BLu"
      :doc "entry point for optim-ai-zer
           Contains func which serve data to world"}
    optim-ai-zer.core
  (:require [optim-ai-zer.algos.simann :as sim]
            [optim-ai-zer.prep.corpus :as co]
            [optim-ai-zer.prep.feed :as f]
            [optim-ai-zer.prep.optibase :as db]
            [optim-ai-zer.charter :as charts]
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
  (sim/sim-ann-articles! art-num tf-m 10000 0.003))

(defn article-map
  [art-list corpus]
  (map #(nth corpus %) art-list))

(defn show-data
  [art-num type]
  (let [indices (similar-articles-by-article art-num co/matrix) docs (db/all-articles)]
    (cond
      (= type :graph)(take 5 (map (fn [x] {:art-num x
                                            :art-kwords (count (co/same-kwords art-num x co/filtered))}) indices))
      (= type :title)(take 5 (map #(type (nth docs %)) indices))
      (= type :link) (take 5 (map #(type (nth docs %)) indices))
      (= type :content) (take 5 (map #(type (nth docs %)) indices))

      :else (take 10 (map (fn [x]  (nth docs x)) indices)))))

;;(charts/show-result (show-data 0 :graph))

;;(show-data 0 :title)
;;(show-data 0 :link)
;;(show-data 0 :content)


