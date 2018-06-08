(ns ^{:author "BLu"
      :doc "Set of tools for extract kwords and prepare
            it for number crunching"}
    optim-ai-zer.prep.corpus
  (:require [peco.core :refer [tokenizer]]
            [optim-ai-zer.prep.feed :as f]
            [optim-ai-zer.prep.optibase :as db]
            [optim-ai-zer.utils :as u]))

(def ^:dynamic token-stem (tokenizer [:lower-case  :remove-stop-words :porter-stem]))

(def ^:dynamic corpus (db/all-articles))

(def ^:dynamic min-freq 2)



(defn tokenize-and-filter
  "Returns tokenized and filtered text"
  [text freq]
  (let [tokens (token-stem text) freqs (frequencies tokens)]
    (filter (complement nil?)(map #(if (>= (freqs %) freq) %) tokens))))


(defn filtered-corpus
  "Returns tokenized and filtered each document in corpus"
  [corpus]
  (map #(tokenize-and-filter (:content %) min-freq) corpus))

(def filtered (filtered-corpus corpus))

(defn tf
  "Return single term frequency in given doc"
  [term doc]
  (if (some #(= term %) doc)
    (double (/ ((frequencies doc) term) (count doc)))
    0.0))

(defn doc-with-term
  [term filtered-corpus]
  (apply + (map (fn [doc] (if (some #(= term %) (keys (frequencies doc)))
                            1
                            0)) filtered-corpus)))
(defn idf
  "Return idf value of single term in corpus"
  [term filtered-corpus]
  (Math/log (/ (count filtered-corpus) (doc-with-term term filtered-corpus))))

(defn corpus-uniques
  "Return unique keywords for given corpus"
  [filtered-corpus]
  (set (mapcat #(set %) filtered-corpus)))

;;(def uniques (corpus-uniques filtered))

(defn idf-uniques
  [uniques filtered-corpus]
  (apply hash-map (mapcat (fn [word] [word (idf word filtered-corpus)]) uniques)))

;;(time (def idfs (idf-uniques uniques filtered)))

(defn generate-dt-row
  [document uniques idfs]
    (map (fn [uniq-word] (if (some #(=  % uniq-word) document)
                         (* (tf uniq-word document) (idfs uniq-word))
                         0.0)) uniques))

(defn dt-m
  "Create document term matrix for each article using Neanderthal"
  [filtered-corpus uniques idfs]
  (u/dge-matrix (map #(generate-dt-row % uniques idfs) filtered-corpus)))

;;(time (def matrix (dt-m filtered uniques idfs)))

(defn doc-kw
  "Returns kwords for article"
  [doc]
  (keys (frequencies doc)))

(defn same-kwords
  [f s filtered-corpus]
  (let [docf (nth filtered-corpus f) docs(nth filtered-corpus s)]
    (filter (complement nil?)(map (fn [word] (if (some #(= word %) (doc-kw docs))
                                              word)) (doc-kw docf)))))

