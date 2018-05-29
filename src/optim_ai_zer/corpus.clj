(ns optim-ai-zer.corpus
  (:require [peco.core :refer [tokenizer]]))

(def tokenize (tokenizer [:lower-case :remove-numbers :porter-stem :remove-stop-words]))

(def text-0 "First, let's look at how simulated annealing works, and why it's good at finding solutions to the traveling salesman problem in particular. The simulated annealing algorithm was originally inspired from the process of annealing in metal work. Annealing involves heating and cooling a material to alter its physical properties due to the changes in its internal structure. As the metal cools its new structure becomes fixed, consequently causing the metal to retain its newly obtained properties. In simulated annealing we keep a temperature variable to simulate this heating process. We initially set it high and then allow it to slowly 'cool' as the algorithm runs. While this temperature variable is high the algorithm will be allowed, with more frequency, to accept solutions that are worse than our current solution. This gives the algorithm the ability to jump out of any local optimums it finds itself in early on in execution. As the temperature is reduced so is the chance of accepting worse solutions, therefore allowing the algorithm to gradually focus in on a area of the search space in which hopefully, a close to optimum solution can be found. This gradual 'cooling' process is what makes the simulated annealing algorithm remarkably effective at finding a close to optimum solution when dealing with large problems which contain numerous local optimums. The nature of the traveling salesman problem makes it a perfect example.")


(def text-1 "Migration

There is of course no real difference between evolving 10 completely separate islands in parallel and running the same single-population evolution 10 times in a row, other than how the computing resources are utilised. In practice the populations are not kept permanently isolated from each other and there are occasional opportunities for individuals to migrate between islands.

In nature external species have been introduced to foreign ecosystems in several ways. In an ice age the waters that previously separated two land masses might freeze providing a route for land animals to migrate to previously unreachable places. Microorganisms and insects have often strayed beyond their usual environment by hitching a ride with larger species.

The effect of introducing a foreign species to a new environment can vary. The new species might be ill-adapted to its new surroundings and quickly perish. Alternatively, a lack of natural predators may cause it to flourish, often to the detriment of indigenous species. One such example is the introduction of rabbits to Australia. Australia was a land without rabbits until the arrival of European settlers. An Englishman named Thomas Austin released 24 rabbits into the wild of Victoria in October 1859 with the intention of hunting them. If rabbits are famous for one thing it is for reproducing prodigiously. The mild winters allowed year-round breeding and the absence of any natural rabbit predators, such as foxes, allowed the Australian rabbit population to explode unchecked. Within 10 years an annual cull of two million rabbits was having no noticeable effect on rabbit numbers and the habitats of some native animals were being destroyed by the floppy-eared pests. Today there are hundreds of millions of rabbits in Australia, despite efforts to reduce the population, and the name of Thomas Austin is widely cursed for his catastrophic lack of foresight.

While such invasions of separate species provide a useful analogy for what can happen when we introduce migration into island model evolutionary algorithms, we are specifically interested in the effects of migration involving genetically different members of the same species. This is because, in our simplified model of evolution, all individuals are compatible and can reproduce. The island model of evolution provides the isolation necessary for diversity to thrive while still providing opportunities for diverse individuals to be combined to produce potentially fitter offspring.

In an island model, the isolation of the separate populations often leads to different traits originating on different islands. Migration brings these diverse individuals together occasionally to see what happens when they are combined. Remember that, even if the immigrants are weak, cross-over can result in offspring that are fitter than either of their parents. In this way, the introduction to the population of new genetic building blocks may result in evolutionary progress even if the immigrants themselves are not viable in the new population.")

(def text-2 "Chinese navy researchers have revealed how they plan to hunt submarines using ship-launched uncrewed air vehicles (UAVs).

The plan, developed by the naval academy in Dalian, China, is to choose the best hunting pattern for a drone using the power of the genetic algorithm - a search engine that evolves an optimum solution by discarding feeble offspring and breeding the best to make ever stronger ones.

The route evolved would make the best use of fuel, cater for air and sea threats and work with dropped sonar buoys. Presumably this could come in handy in some future international dispute over Taiwan. But why tell your adversary - who can now evolve counter measures?

  You'd imagine that how a military hunts submarines might be a secret. In WWII, for instance, the airborne hunt for submarines with positional info gleaned from Bletchley Park's Enigma decrypts was pivotal in winning the battle of the Atlantic. But we learned about that much later. Bletchley was famously Churchill's goose that laid the golden eggs but never cackled.

Why the department of underwater weaponry and chemical defence at the academy has revealed its cunning UAV plan - and published it in the journal Advanced Materials Research - is somewhat baffling.

It's not the first time this has happened. In 2010 Chinese researchers published a treatise on how to hack and trip large chunks of the US electricity grid. This led (after much initial disbelieving spluttering) to much angry rhetoric from aggrieved US commentators, not least the Department of Homeland Security.

Perhaps the frenetic, breakneck pace of China's scientific publishing machine is outstripping the nation's ability to work out what it should and should not publish?")

(def text-3 "
Hill Climbing
Definition - What does Hill Climbing mean?

Hill climbing is a mathematical optimization heuristic method used for solving computationally challenging problems that have multiple solutions. It is an iterative method belonging to the local search family which starts with a random solution and then iteratively improves that solution one element at a time until it arrives at a more or less optimized solution.
Techopedia explains Hill Climbing

  Hill climbing is an optimization technique that is used to find a \"local optimum\" solution to a computational problem. It starts off with a solution that is very poor compared to the optimal solution and then iteratively improves from there. It does this by generating \"neighbor\" solutions which are relatively a step better than the current solution, picks the best and then repeats the process until it arrives at the most optimal solution because it can no longer find any improvements.

Variants:

    Simple — The first closest node or solution to be found is chosen.
    Steepest ascent — All available successor solutions are considered and then the closest one is selected.
    Stochastic — A neighbor solution is selected at random, and it is then decided whether or not to move on to that solution based on the amount of improvement over the current node.

Hill climbing is done iteratively — it goes through an entire procedure and the final solution is stored. If a different iteration finds a better final solution, the stored solution or state is replaced. This is also called shotgun hill climbing, as it simply tries out different paths until it hits the best one, just like how a shotgun is inaccurate but may still hit its target because of the wide spread of projectiles. This works very well in many cases because at it turns out, it is better to spend CPU resources exploring different paths than carefully optimizing from an initial condition.
")
(def text-4 "Clojure is a dynamic, general-purpose programming language, combining the approachability and interactive development of a scripting language with an efficient and robust infrastructure for multithreaded programming. Clojure is a compiled language, yet remains completely dynamic – every feature supported by Clojure is supported at runtime. Clojure provides easy access to the Java frameworks, with optional type hints and type inference, to ensure that calls to Java can avoid reflection.

Clojure is a dialect of Lisp, and shares with Lisp the code-as-data philosophy and a powerful macro system. Clojure is predominantly a functional programming language, and features a rich set of immutable, persistent data structures. When mutable state is needed, Clojure offers a software transactional memory system and reactive Agent system that ensure clean, correct, multithreaded designs.

I hope you find Clojure's combination of facilities elegant, powerful, practical and fun to use.

Rich Hickey
author of Clojure and CTO Cognitect")

(frequencies (tokenize text-0))

(defn get-frequencies
  [text]
  (frequencies (tokenize text)))
(get-frequencies text-0)
(count (filter #(> % 2) (vals (get-frequencies text-0))))
(keys (filter #(> % 2) (vals (get-frequencies text-0))))
(keys (get-frequencies text-0))

(defn pos-keywords
  [text repetition]
  (->> text
       (get-frequencies)
       (filter #(> (val %) repetition))
       (sort-by val >)))
  ;(sort-by val > (filter #(> (val %) repetition) (get-frequencies text))))

(pos-keywords text-0 2)
(pos-keywords text-1 2)
(pos-keywords text-2 1)
(pos-keywords text-3 1)
(pos-keywords text-4 1)
