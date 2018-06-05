# optim-AI-zer

- Recommender system, RSS Feed corpus preparator

Theory:
- Stochastic optimization
- Solve problems where outcomes depends on the combination of many variables.
  Problems can have many possible solutions.
- Optimization finds the best solution by trying many different solutions and
  scoring them to determine quality of each.

Implementation - in Clojure:
- RSS Feed data collector - optim-ai-zer.prep.feed
- Corpus preparator - optim-ai-zer.prep.corpus


# The Cost Function

- Return value that represent how bad solution is.
- Most difficult thing to determine.



# Hill Climbing

Theory:
- Starts with a random solution
- Looks ath the set of neighboring solutions for those that are better (cost
  function for that solution is lower).

Implementation:
- Algorithm code in optim-ai-zer.algos.hill

# Simulated Annealing

Theory:
- optimization method inspired by physics
- starts with a random solution
- uses variable representing the temperature which start high and gradually gets
  lower.
- cost is calculated before and after the change, and the costs are compared:
  * new cost is lower - new solution becomes the current solution - similar to
    hill climbing
  * new cost is higher - new solution can still become the current solution with
    a certain probability.
- temperature: willingness to accept a worse solution
- Formula:
  p=e^((-highcost-lowcost)/temperature)
  * when starts, temperature is very high, exponent is near 0, probability is
    almost 1
  * when temperature decreases, the difference between the high cost and the low
    cost becomes more important: bigger diff - lower probability

Implementation:
- Algorithm code in optim-ai-zer.algos.simann.clj

# Genetic Algorithms

Theory:
- inspired by nature
- population
- in each step, the cost function for the entire population is calculated to get
  a ranked list of solutions
- next generation - new population created after the solutions is ranked
- elitism - process of adding top solutions in the current population to the new
  population
  * rest of the new population consist of completely new solutions that are
    created by modifying the best solutions.
- two ways:
  * mutation - small, simple, random change to existing solution
  * crossover (breeding) - taking two of the best solutions and combining them

Implementation:
- Algorithm code in optim-ai-zer.algos.genalg.clj

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

## Options


## Examples

## Future improvements
- GUI
- rss links in database - editable from GUI
...

## License

Copyright © 2018 BLu

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
