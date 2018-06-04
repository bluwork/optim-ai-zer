(ns ^{:author "BLu"
      :doc "Set of tools for extract kwords and prepare
            it for number crunching"}
    optim-ai-zer.prep.corpus
  (:require [peco.core :refer [tokenizer]]
            [optim-ai-zer.prep.feed :as f]
            [optim-ai-zer.prep.optibase :as db]
            [optim-ai-zer.utils :as u]))

(def token-stem (tokenizer [:lower-case :porter-stem :remove-stop-words]))

(defn get-frequencies
  "Returns kwords and number of repetition for each
  for given text"
  [text]
  (frequencies (token-stem text)))



(defn pos-keywords
  "Returns possible terms from feed articles
  for given frequency"
  [text times-repeated]
  (->> text
       (get-frequencies)
       (filter #(> (val %) times-repeated))
       (sort-by val >)))
(defn kwords
  [text]
  (keys (pos-keywords text 1)))
(defn art-kwords
  "Returns all tokenized terms from article"
  [article]
  (pos-keywords (:content article) 1))

(defn all-unique
  "Return all unique kwords from whole corpus"
  [corpus]
  (let [kw (map #(keys (art-kwords %)) corpus)]
    (set (filter #(> (count %) 3) (apply concat kw)))))

(defn calculate-weight
  "Returns seq of floats which represent
  weight of term in inp(uted text)"
  [inp uniqs]
  (let [input (into {} inp) ]
    (map (fn [x] (if (nil? (input x))
                   0.0
                   (float (/ (input x) (count uniqs))))) uniqs)))
(defn to-bin
  "For some given vector vol returns seq of nums
  every 0 stay 0 , every num bigger than 0 become 1"
  [vol]
  (map (fn [x]  (map #(if (> % 0) 1 0) x)) vol))

(defn source-tf
  "Returns seq of lists with numerically crunched articles -
  ready for matrix"
  [corpus]
  (let [uniqs (all-unique corpus)]
  (map #(calculate-weight (art-kwords %) uniqs) corpus)))

(defn tf-m
  "Create term frequency matrix for each article using Neanderthal"
  ([] (tf-m (db/all-articles)))
  ([corpus] (u/dge-matrix (source-tf corpus))))

(defn bin-m
  "Create bin matrix from given source (list of crunched articles)"
  ([] (bin-m (db/all-articles)))
  ([corpus] (u/dge-matrix (to-bin (source-tf corpus)))))

(defn df-m
  "Returns transposed matrix from given input"
  [tf-m]
  (u/transpose (tf-m)))

(defn train-d
  "Return indices for training and test data. Not yet implemented"
  [percent]
  (let [articles (db/all-articles) train-size (/ (* percent (count articles)) 100)]))
    

