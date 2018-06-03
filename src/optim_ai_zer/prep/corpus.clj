(ns ^{:author "BLu"
      :doc "Set of tools for extract kwords and prepare
            it for number crunching"}
    optim-ai-zer.prep.corpus
  (:require [peco.core :refer [tokenizer]]
            [optim-ai-zer.prep.feed :as f]
            [optim-ai-zer.prep.optibase :as db]
            [optim-ai-zer.utils :as u]
            [criterium.core :as crit]))

(def token-stem (tokenizer [:lower-case :porter-stem :remove-stop-words]))

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

;; (db/insert-articles (f/art-from :reuters :science))
(defn idf-m
  []
  )
(defn all-unique
  "Return all unique kwords from corpus"
  [corpus]
  (let [kw (map #(keys (art-kwords %)) corpus)]
    (set (filter #(> (count %) 3) (apply concat kw)))))

(defn calculate-weight
  [inp uniqs]
  (let [input (into {} inp) ]
    (map (fn [x] (if (nil? (input x))
                   0.0
                   (float (/ (input x) (count uniqs))))) uniqs)))

(defn source-tf
  [corpus]
  (let [uniqs (all-unique corpus)]
  (map #(calculate-weight (art-kwords %) uniqs) corpus)))

(defn tf-m
  ([] (tf-m (db/all-articles)))
  ([corpus] (u/dge-matrix (source-tf corpus))))


