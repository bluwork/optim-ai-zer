(ns ^{:author "BLu"
      :doc "Set of tools for extract kwords and prepare
            it for number crunching"}
    optim-ai-zer.prep.corpus
  (:require [peco.core :refer [tokenizer]]
            [optim-ai-zer.prep.feed :as f]
            [optim-ai-zer.prep.optibase :as db]
            [optim-ai-zer.utils :as u]))

(def token-stem (tokenizer [:lower-case  :remove-stop-words :porter-stem]))

(def corpus (db/all-articles))

(def unique-words (atom #{}))



(def pref-frequency 1)


(defn tokenize
  "Returns tokenized text (input is :content value from article)"
  [text]
  (token-stem text))

(defn single-article-kwords
  "Returns keywords with frequency for given tokenized text"
  [tokenized-text freq]
  (into {} (filter #(> (val %) freq) (frequencies tokenized-text))))



(defn corpus-uniques
  "Return unique keywords for given corpus"
  [corpus]
  (set (apply concat (map (fn [x]  (keys (single-article-kwords (tokenize (:content  x)) pref-frequency))) corpus))))

(defn remember-uniques
  [corpus]
  (let [cu (corpus-uniques corpus)]
    (reset! unique-words (vec cu))))
(remember-uniques corpus)
(count @unique-words)


(single-article-kwords (tokenize (:content (nth corpus 150))) pref-frequency)

(defn generate-dt-row
  [document]
  (let [uniq-num (count @unique-words)]
    (map (fn [uniq-word] (if (some #(= (key %) uniq-word) document)
                         (float (/ (document uniq-word) uniq-num))
                         0.0)) @unique-words)))

(defn prepare-docs-for-dtm
  ([] (prepare-docs-for-dtm (db/all-articles)))
  ([corpus] (map #(single-article-kwords (tokenize (:content %)) pref-frequency) corpus)))

(defn source-tf
  "Returns seq of lists with numerically crunched articles -
  ready for matrix"
  [prepared-docs]
  (map #(generate-dt-row  %) prepared-docs))

(defn dt-m
  "Create document term matrix for each article using Neanderthal"
  ([] (dt-m (prepare-docs-for-dtm)))
  ([docs] (u/dge-matrix (source-tf docs))))

(defn train-d
  "Return indices for training and test data. Not yet implemented"
  [percent]
  (let [articles (db/all-articles) train-size (/ (* percent (count articles)) 100)]))
    
(defn tf
  "Return single term frequency in given doc"
  [term doc]
  (if (some #(= term %) doc)
    (double (/ ((frequencies doc) term) (count doc)))
    0.0))

(defn term-count
  "Return count of single term in document"
  [term doc]
  (if (some #(= term %) doc)
    ((frequencies doc) term)
    0))

(defn idf
  "Return idf value of single term in corpus"
  [term docs]
  (Math/log (/ (count docs) (apply + (map #(term-count term %) docs)))))

(defn tf-idf
  "Return tf-idf value for single term in corpus"
  [term doc docs]
  (* (tf term doc) (idf term docs)))

(defn source-tf-idf
  [tokenized-corpus]
  (let [z tokenized-corpus]
    (map (fn [y] (map (fn [x] (tf-idf x y z)) y)) z)))

(defn tf-idf-matrix
  "Create document term matrix for each article using Neanderthal"
  [prepared-docs] (u/dge-matrix (source-tf-idf prepared-docs)))

(defn kwords-for
  [index]
  (into {} (single-article-kwords (tokenize (:content (nth corpus index))) pref-frequency)))

(defn same-kwords
  [f s]
  (filter (complement nil?) (map (fn [x] (if (some #(=  % x) (keys (kwords-for s)))
                  x)) (keys (kwords-for f)))))
