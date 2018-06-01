(ns optim-ai-zer.prep.corpus
  (:require [peco.core :refer [tokenizer]]
            [optim-ai-zer.prep.feed :as f]
            [optim-ai-zer.prep.optibase :as db]
            [criterium.core :as crit]))

(def tokenize (tokenizer [:lower-case :remove-numbers :porter-stem :remove-stop-words]))

(def rss-links {:bbc {:science  "https://feeds.bbci.co.uk/news/science_and_environment/rss.xml"
                    :technology "https://feeds.bbci.co.uk/news/technology/rss.xml"}
              :reuters {:science "http://feeds.reuters.com/reuters/scienceNews"
                        :technology "http://feeds.reuters.com/reuters/technologyNews"}
              :mit {:ml "https://news.mit.edu/rss/topic/machine-learning"
                    :robotics "https://news.mit.edu/rss/topic/robotics"
                    :science "https://news.mit.edu/rss/topic/science-technology-and-society"}
              :fast-ml {:all "http://fastml.com/atom.xml"}
              :ai-trends {:all "https://aitrends.com/feed/"}})

(defn get-frequencies
  [text]
  (frequencies (tokenize text)))

(defn pos-keywords
  "Return possible keywords from feed articles"
  [text times-repeated]
  (->> text
       (get-frequencies)
       (filter #(> (val %) times-repeated))
       (sort-by val >)
       keys))

(defn art-kwords
  [article]
  (pos-keywords (:content article) 3))

(defn all-kwords
  [articles]
  (map (fn [x] {:word x}) (set (apply concat (map art-kwords articles)))))

;(db/insert-articles (f/articles ((comp :science :mit) rss-links) :mit))

;; TODO Not finished 
(defn tf
  [articles]
  (let [relevant-kwords (all-kwords articles)]
    (loop [counter (count articles) tfm []]
      (if (< counter 1)
        :true
        (recur (dec counter) tf)))))

;(crit/with-progress-reporting (crit/quick-bench (all-kwords (db/all-articles))))
