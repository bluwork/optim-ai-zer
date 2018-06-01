(ns optim-ai-zer.utils
  (:require [uncomplicate.neanderthal
             [native :refer :all]
             [core :refer :all]]
            [criterium.core :refer :all]))

;; TODO Catch exception
(defn dot-product
  [vec1 vec2]
  (if (= (count vec1) (count vec2))
    (apply + (map #(* %1 %2) vec1 vec2 ))))

(defn magnitude
  [vec1]
  (Math/sqrt (apply + (map #(* % %) vec1))))


(defn cosine-similarity
  [vec1 vec2]
  (/ (dot-product vec1 vec2) (* (magnitude vec1) (magnitude vec2))))

(defn cosine-distance
  [vec1 vec2]
  (- 1 (cosine-similarity vec1 vec2)))

(defn euclidian-dist
  "Calculate Euclidian distance between two points"
  [pt1 pt2]
  (Math/sqrt (+ (Math/pow (- (:x pt1) (:x pt2)) 2) (Math/pow (- (:y pt1) (:y pt2)) 2))))

(defn r-swap-val!
  "Swap two random values in given vector"
  [vec1]
  (loop [pos1 (rand-int (count vec1)) pos2 (rand-int (count vec1))]
    (if (= pos1 pos2)
      (recur pos1 (rand-int (count vec1)))
      (assoc (assoc vec1 pos1 (nth vec1 pos2)) pos2 (nth vec1 pos1)))))

(def a (dge 2 3 [1 2 3 4 5 6]))
(def b (dge 3 2 [1 3 5 7 9 11]))

; Not working on Mac
; java.lang.NoClassDefFoundError: Could not initialize class uncomplicate.neanderthal.internal.host.CBLAS
; - let's try on Linux
;(mm a b)

(defn quick-bench!
  [entry]
  (with-progress-reporting (quick-bench entry)))

