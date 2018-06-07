(ns optim-ai-zer.prep.optibase
  (:require [korma.core :refer :all]
            [korma.db :refer :all]))

(defdb db (mysql {:host "localhost"
                  :port 3306
                  :db "optima"
                  :user "optima"
                  :password "optima1"}))

(defentity article
  (entity-fields :id :title :link :content))

(defn insert-articles
  "Insert one or more articles in db"
  [articles]
  (insert article (values articles)))

(defn all-articles
  "Returns all articles from db"
  []
  (select article))

