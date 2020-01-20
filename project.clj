(defproject optim-ai-zer "0.1.1"
  
  :description "Corpus preparation, analyzing and finding best solution using
   optimization techniques"
  
  :url "https://github.com/masandcomm/optim-ai-zer"
  
  :scm {:name "git"
        :url "https://github.com/masandcomm/optim-ai-zer"}
  
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [peco "0.1.6"]
                 [org.clojars.gnzh/feedparser-clj "0.6.1"]
                 [org.jsoup/jsoup "1.12.1"]
                 [clojure.jdbc/clojure.jdbc "0.4.0"]
                 [mysql/mysql-connector-java "5.1.48"]
                 [korma/korma "0.4.3"]
                 [migratus "1.2.7"]
                 [criterium "0.4.5"]
                 [uncomplicate/neanderthal "0.26.1"]
                 [com.hypirion/clj-xchart "0.2.0"]
                 [hiccup "2.0.0-alpha2"]]

:main optim-ai-zer.core

:codox {:metadata {:doc/format :markdown}
          :src-dir-uri "https://github.com/masandcomm/optim-ai-zer/blob/master/"
          :src-linenum-anchor-prefix "L"
          :namespaces [optim-ai-zer.core
                       optim-ai-zer.algos.genalg
                       optim-ai-zer.algos.simann
                       optim-ai-zer.algos.hill
                       optim-ai-zer.prep.feed
                       optim-ai-zer.prep.corpus
                       optim-ai-zer.utils]
          :output-path "docs"}

  :profiles {:dev {:plugins [[lein-codox "0.10.7"]
                             [migratus-lein "0.4.1"]]
                   #_:global-vars #_{*warn-on-reflection* true}}}

  :migratus {:store :database
             :migration-dir "migrations"
             :db {:classname "com.mysql.cj.jdbc.Driver"
                  :subprotocol "mysql"
                  :subname "//localhost:3306/optima"
                  :user "optima"
                  :password "optima1"}}

  :exclusions [[org.jcuda/jcuda-natives :classifier "apple-x86_64"]
               [org.jcuda/jcublas-natives :classifier "apple-x86_64"]])

