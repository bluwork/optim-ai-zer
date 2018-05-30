(ns optim-ai-zer.feed
  (:import [org.jsoup Jsoup])
  (:require [clojure.java.io :as io]
            [optim-ai-zer.corpus :as corpus]
            [feedparser-clj.core :as feedp]
            [clojure.string :as cstr]))


(def selector-bbc "[property=articleBody] p")
(def selector-reuters "p")

(defn selector
  [provider]
  (cond 
    (= provider :reuters) "p"
    (= provider :bbc) "[property=articleBody] p"
    :else "p"))

(defn feed
  [address]
  (feedp/parse-feed (io/input-stream address)))



(defn links
  [feed]
  (map #(:link %) (:entries feed)))

(defn random-link!
  [feed]
  (let [links (map #(:link %) (:entries feed))]
    (nth links (rand-int (count links)))))

(defn get-doc
  [link]
  (.get (Jsoup/connect link)))

(defn get-title
  [link]
  (.title (get-doc link)))

(defn get-body
  [link]
  (.body (get-doc link)))

(defn article-body
  [link selector]
  (.text (.select (get-body link) selector)))


(corpus/get-frequencies (article-body (random-link! reuters-science-feed) selector-reuters))

(defn r-article!
  [link provider]
  (let [feed (feed link)]
    (article-body (random-link! feed) (selector provider))))
