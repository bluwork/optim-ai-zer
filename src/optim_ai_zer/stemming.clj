(ns optim-ai-zer.stemming
  (:require [peco.core :refer [tokenizer]]))

(def tokenize (tokenizer [:lower-case :remove-numbers :porter-stem :remove-stop-words]))

(def text-0 "First, let's look at how simulated annealing works, and why it's good at finding solutions to the traveling salesman problem in particular. The simulated annealing algorithm was originally inspired from the process of annealing in metal work. Annealing involves heating and cooling a material to alter its physical properties due to the changes in its internal structure. As the metal cools its new structure becomes fixed, consequently causing the metal to retain its newly obtained properties. In simulated annealing we keep a temperature variable to simulate this heating process. We initially set it high and then allow it to slowly 'cool' as the algorithm runs. While this temperature variable is high the algorithm will be allowed, with more frequency, to accept solutions that are worse than our current solution. This gives the algorithm the ability to jump out of any local optimums it finds itself in early on in execution. As the temperature is reduced so is the chance of accepting worse solutions, therefore allowing the algorithm to gradually focus in on a area of the search space in which hopefully, a close to optimum solution can be found. This gradual 'cooling' process is what makes the simulated annealing algorithm remarkably effective at finding a close to optimum solution when dealing with large problems which contain numerous local optimums. The nature of the traveling salesman problem makes it a perfect example.")

(frequencies (tokenize text-0))

(defn get-frequencies
  [text]
  (frequencies (tokenize text)))
