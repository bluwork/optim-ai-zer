(ns optim-ai-zer.prep.corpus
  (:require [peco.core :refer [tokenizer]]
            [optim-ai-zer.prep.feed :as f]
            [optim-ai-zer.prep.optibase :as db]
            [optim-ai-zer.utils :as u]
            [criterium.core :as crit]
            [uncomplicate.neanderthal.core :refer :all]))

(def token-stem (tokenizer [:lower-case :porter-stem :remove-stop-words]))

(def token-w-o-stem (tokenizer [:lower-case :remove-stop-words]))

(defn get-frequencies
  [text]
  (frequencies (token-stem text)))

(defn pos-keywords
  "Return possible keywords from feed articles"
  [text times-repeated]
  (->> text
       (get-frequencies)
       (filter #(> (val %) times-repeated))
       (sort-by val >)))

(defn art-kwords
  [article]
  (pos-keywords (:content article) 2))

;; Pseudo code - values
;; TF(t) = (Number of times term t appears in a document) / (Total number of terms in the document)
;; IDF(t) = log_e(Total number of documents / Number of documents with term t in it).
;; Value = TF * IDF

;;(db/insert-articles (f/art-from :mit :science))

(defn all-unique
  "Return All Unique Keywords from corpus"
  [articles]
  (let [kw (map #(keys (art-kwords %)) articles)]
    (set (filter #(> (count %) 3) (apply concat kw)))))

(def tw (let [tw (all-unique (db/all-articles))]
                            tw))
(defn calculate-weight
  [inp]
  (let [input (into {} inp) ]
    (map (fn [x] (if (nil? (input x))
                   0.0
                   (float (/ (input x) (count tw))))) tw)))

(def tf (let [kw (map #(calculate-weight (art-kwords %)) (db/all-articles))]
          kw))

(defn get-matrix
  []
  (u/to-matrix tf))


