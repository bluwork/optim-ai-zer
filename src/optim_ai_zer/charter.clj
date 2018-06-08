(ns optim-ai-zer.charter
  (:require [com.hypirion.clj-xchart :as c]
            [optim-ai-zer.prep.optibase :as db]))


(defn art-lengths
  []
  (let [articles (db/all-articles)]
    (into (sorted-map) (map (fn [article] [(str (:id article)) (count (:content article)) ]) articles))))

(defn show-corpus-length
  []
  (let [articles (db/all-articles)]
    (c/view
     (c/category-chart {"Text length in corpus"
                        (eval (art-lengths))}))))
(defn show-result
  [result]
  (c/view
   (c/category-chart
    {"Similar docs kwords" (eval (into (sorted-map) (map (fn [doc] [(str (:art-num doc)) (:art-kwords doc)]) result))) })))

