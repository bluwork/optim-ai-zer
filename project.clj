(defproject optim-ai-zer "0.1.1"
  :description "Corpus preparation, analyzing and finding best solution using
   optimization techniques"
  :url "https://github.com/masandcomm/optim-ai-zer"
  :scm {:name "git"
        :url "https://github.com/masandcomm/optim-ai-zer"}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [peco "0.1.5"]
                 [org.clojars.gnzh/feedparser-clj "0.6.0"]
                 [org.jsoup/jsoup "1.11.3"]
                 [clojure.jdbc/clojure.jdbc "0.4.0"]
                 [mysql/mysql-connector-java "5.1.39"]
                 [korma/korma "0.4.3"]
                 [migratus "1.0.6"]
                 [criterium "0.4.4"]
                 [uncomplicate/neanderthal "0.19.0"]]
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
          :output-path "docs/codox"}
  :profiles {:dev {:plugins [[lein-codox "0.9.0"]
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
