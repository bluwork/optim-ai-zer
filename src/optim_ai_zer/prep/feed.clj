(ns optim-ai-zer.prep.feed
  (:import [org.jsoup Jsoup])
  (:require [clojure.java.io :as io]
            [feedparser-clj.core :as feedp]
            [clojure.string :as cstr]))


(defn selector
  "Returns string of params for filtering text"
  [provider]
  (cond 
    (= provider :reuters) "p"
    (= provider :bbc) "[property=articleBody] p"
    :else "p"))

(defn rss-feed
  "Returns clojure-like map of rss-feed"
  [feed-link]
  (feedp/parse-feed (io/input-stream feed-link)))


(defn links
  "Returns all links from current rss feed"
  [feed]
  (map #(:link %) (:entries feed)))

(defn get-doc
  "Returns page from web"
  [article-link]
  (.get (Jsoup/connect article-link)))


(defn article
  "Returns map which represents one article - title and content"
  [article-link provider]
  (let [doc (get-doc article-link)]
    {:title (.title doc) :content (.text (.select (.body doc) (selector provider)))}))

(defn articles
  "Returns all articles from selected rss-feed"
  [feed-link provider]
  (let [feed (rss-feed feed-link)]
    (map #(article % provider) (links feed))))
