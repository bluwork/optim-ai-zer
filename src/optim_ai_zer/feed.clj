(ns optim-ai-zer.feed
  (:require [clojure.xml :as parser]
            [clojure.java.io :as io]
            [optim-ai-zer.stemming :as st]))

(defrecord Feed [title link description language copyright pub-date])
(defrecord FeedMessage [title description link author guid])
(spit "blubber.txt" "testing doc writing")
(slurp "blubber.txt")

(slurp "http://clojuredocs.org")
(slurp "http://www.example.com")
(slurp "https://feeds.bbci.co.uk/news/rss.xml")

(parser/parse (slurp "https://feeds.bbci.co.uk/news/rss.xml"))
(parser/parse (io/input-stream "https://feeds.bbci.co.uk/news/science_and_environment/rss.xml"))

(st/get-frequencies "If it looks like a duck, swims like a duck, and quacks like a duck, then it probably is a duck. ")
(spit "file.txt" (st/get-frequencies "If it looks like a duck, swims like a duck, and quacks like a duck, then it probably is a duck. "))
(slurp "file.txt")
(spit "only-xml.txt"(slurp "https://feeds.bbci.co.uk/news/rss.xml"))
