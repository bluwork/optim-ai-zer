# optim-AI-zer

- Recommender system, RSS Feed corpus preparator
- More on [optim-AI-zer wiki](https://github.com/masandcomm/optim-ai-zer/wiki)



## Installation

  - Install [Intel MKL](https://software.intel.com/en-us/mkl).
  - Clone and lein install [feedparser-clj](https://github.com/scsibug/feedparser-clj).
  - Create user and database in MySQL server  or edit migratus and korma data (first is in project.clj, second in prep/optima.clj).
  - Run lein migratus migrate.

## Usage

For now, you can use it from REPL.
* to add articles to db, from core.clj call (article-to-db provider topic), e.g.
``` clojure
(article-to-db :reuters :science)
```
* you can add, or change, or remove topics by editing rss-links in prep.feed.clj following the pattern {:provider {:topic "link"}}, e.g.
``` clojure
{:bbc {:science "https://feeds.bbci.co.uk/news/science_and_environment/rss.xml"}}
```
* after adding data to db, create document term matrix using prep.corpus function dtf-m
``` clojure
(require '[optim-ai-zer.prep.corpus :refer :all])
(def matrix (dtf-m))
```
 * note * call again if you change articles in database
Then from core call, eg:
``` clojure
(similar-articles 0 matrix)
```
where 0 is first article from corpus.

## Future improvements
- GUI
- rss links in database - editable from GUI
...

## License

Copyright © 2018 BLu

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
