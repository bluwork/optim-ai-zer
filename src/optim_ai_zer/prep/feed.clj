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
                :ai-trends {:all "https://aitrends.com/feed/"}
                :sci-daily {:science "https://www.sciencedaily.com/rss/top/science.xml"
                            :technology "https://www.sciencedaily.com/rss/top/technology.xml"}
                :verge {:all "https://www.theverge.com/rss/index.xml"}})
(defn selector
  "Returns string of params for filtering text"
  [provider]
  (cond
    (= provider :mit) ".field-name-field-article-content p"
    (= provider :reuters) ".body_1gnLA p:not(.content_27_rw)"
    (= provider :bbc) "[property=articleBody] p"
    (= provider :verge) ".c-entry-content p"
    (= provider :ai-trends) ".td-post-content p"
    (= provider :sci-daily) "#text p"
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
  (let [doc (get-doc article-link) title (.title doc) content (.text (.select (.body doc) (selector provider)))]
    (if (> (count content) 300)
      {:title title :link article-link :content content})))

(defn art-from
  "Returns all articles from selected rss-feed"
  [provider topic]
  (let [feed (rss-feed ((comp topic provider) rss-links))]
    (filter (complement nil?) (map #(article % provider) (links feed)))))

