(ns optim-ai-zer.prep.feed
  (:import [org.jsoup Jsoup])
  (:require [clojure.java.io :as io]
            [feedparser-clj.core :as feedp]
            [clojure.string :as cstr]))

(def rss-links {:bbc {:science  "https://feeds.bbci.co.uk/news/science_and_environment/rss.xml"
                      :technology "https://feeds.bbci.co.uk/news/technology/rss.xml"}
                :reuters {:science "http://feeds.reuters.com/reuters/scienceNews"
                          :technology "http://feeds.reuters.com/reuters/technologyNews"}
                :mit {:ml "https://news.mit.edu/rss/topic/machine-learning"
                      :robotics "https://news.mit.edu/rss/topic/robotics"
                      :science "https://news.mit.edu/rss/topic/science-technology-and-society"}
                :fast-ml {:all "http://fastml.com/atom.xml"}
                :ai-trends {:all "https://aitrends.com/feed/"}})
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

(defn art-from
  "Returns all articles from selected rss-feed"
  [provider topic]
  (let [feed (rss-feed ((comp topic provider) rss-links))]
    (map #(article % provider) (links feed))))

