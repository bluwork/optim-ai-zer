(ns optim-ai-zer.charter
  (:require [com.hypirion.clj-xchart :as c]
            [optim-ai-zer.prep.optibase :as db]))

(defn show-kwords
  [data]
  (c/view
   (c/category-chart {"Similar same kwords count" (second data)} )))

(let [reper-data {:doc1 25 "Doc2" 25 "Doc3" 25 "Doc4" 25}
      similar-data {"Doc1" 15 "Doc2" 20 "Doc3" 4 "Doc4" 18 "Doc5" 24}]
  (show-kwords [reper-data similar-data]))

(defn show-corpus-length
  []
  (let [articles (db/all-articles)]
    (c/view
     (c/category-chart {"Text length in corpus"
                        (eval (art-lengths))}))))

(defn art-lengths
  []
  (let [articles (db/all-articles)]
    (into (sorted-map) (map (fn [article] [(str (:id article)) (count (:content article)) ]) articles))))

(show-corpus-length)

(defn show-result
  [result]
  (c/view
   (c/category-chart
    {"Similar docs kwords" (eval (into (sorted-map) (map (fn [doc] [(str (:art-num doc)) (:art-kwords doc)]) result))) })))
