(ns optim-ai-zer.core
  (:require [optim-ai-zer.algos.simann :as sim]
            [optim-ai-zer.prep.corpus :as co]
            [optim-ai-zer.prep.feed :as f]
            [optim-ai-zer.prep.optibase :as db]
            [clojure.set :as s]))

(defn simann
  [art-num]
  (let [m (co/tf-m)]
    (with-out-str ( sim/sim-ann-articles! art-num m 10000 0.002))))

(defn -main
  "Entry point"
  []
  (println "Hello! Please use REPL for now :)"))

;(db/insert-articles (f/art-from :mit :robotics))
;(simulate-annealing! cities 5000 0.003)

;;(def m (co/tf-m))

;;(def corpus (db/all-articles))
;;(def uniqes (co/all-unique corpus))

(defn analyze
  [art-num corpus tf-m]
  (map #(co/kwords (:content %)) (map #(nth corpus %) (sim/sim-ann-articles! art-num tf-m 10000 0.002))))

;;(map #(:link %) (map #(nth corpus  %) (sim/sim-ann-articles! 0 m 10000 0.002)))

(defn kw
  [num corpus]
  (let [art-kw (co/kwords (:content (nth corpus num)))]
    art-kw))
;;(time (climb! cities 5000)

;;(analyze 0)
(defn same-kws
  [art1 art2]
  (s/intersection (set (kw art1)) (set (kw  art2))))
;;(same-kws 0 67)
