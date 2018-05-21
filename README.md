# optim-ai-zer

- Stochastic optimization
- Solve problems where outcomes depends on the combination of many variables.
  Problems can have many possible solutions.
- Optimization finds the best solution by trying many different solutions and
  scoring them to determine quality of each.

# The Cost Function

- Return value that represent how bad solution is.
- Most difficult thing to determine.

# Random Searching

# Hill Climbing

- Starts with a random solution
- Looks ath the set of neighboring solutions for those that are better (cost
  function for that solution is lower).

# Simulated Annealing
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

# Genetic Algorithms
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
# Installation

Download from http://example.com/FIXME.

## Usage

## Options

FIXME: listing of options this app accepts.

## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2018 BLu

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
