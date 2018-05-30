(ns optim-ai-zer.corpus
  (:require [peco.core :refer [tokenizer]]
            [optim-ai-zer.feed :as f]))

(def tokenize (tokenizer [:lower-case :remove-numbers :porter-stem :remove-stop-words]))

(def rss-links {:bbc {:science  "https://feeds.bbci.co.uk/news/science_and_environment/rss.xml"
                    :technology "https://feeds.bbci.co.uk/news/technology/rss.xml"}
              :reuters {:science "http://feeds.reuters.com/reuters/scienceNews"
                        :technology "http://feeds.reuters.com/reuters/technologyNews"}
              :mit {:ml "https://news.mit.edu/rss/topic/machine-learning"
                    :robotics "https://news.mit.edu/rss/topic/robotics"
                    :science "https://news.mit.edu/rss/topic/science-technology-and-society"}
              :fast-ml {:all "http://fastml.com/atom.xml"}
              :ai-trends {:all "https://aitrends.com/feed/"}}})

(defn get-frequencies
  [text]
  (frequencies (tokenize text)))

(defn pos-keywords
  "Return possible keywords from feed articles"
  [text repetitive]
  (->> text
       (get-frequencies)
       (filter #(> (val %) repetitive))
       (sort-by val >)))
  ;(sort-by val > (filter #(> (val %) repetition) (get-frequencies text))))

(pos-keywords (f/r-article! ((comp :science :bbc) rss-links) :bbc) 2)


(pos-keywords (f/r-article! ((comp :ml :mit) rss-links) :mit) 2)

(pos-keywords (f/r-article! ((comp :all :ai-trends) rss-links) :ai-trends) 3)

(pos-keywords (f/r-article! ((comp :all :fast-ml) rss-links) :fast-ml) 1)


