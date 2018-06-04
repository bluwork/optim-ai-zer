(ns optim-ai-zer.utils
  (:require [uncomplicate.neanderthal
             [native :refer :all]
             [core :refer :all]
             [linalg :refer :all]]
            [criterium.core :refer :all]))

;; TODO Catch exception
(defn dot-prod
  "Returns dot product of 2 vecs"
  [u v]
  (if (= (count u) (count v))
    (apply + (map #(* %1 %2) u v))))

(defn magnitude
  "Returns magnitude of vec"
  [u]
  (Math/sqrt (apply + (map #(* % %) u))))

(defn cos-sim
  "Returns cosine similarity for 2 vecs"
  [u v]
  (/ (dot-prod u v)  (magnitude u) (magnitude v)))

(defn n-cos-sim
  "Return cosine similarity between 2 vecs"
  [u v]
  (/ (dot u v) (nrm2 u) (nrm2 v)))

(defn cos-dist
  "Returns cosine distance between two vecs"
  [u v]
  (- 1 (cos-sim u v)))

(defn n-cos-dist
  "Return cosine distance between 2 clo-like seqs"
  [dv1 dv2]
  (let [dvu (dv dv1) dvv (dv dv2)]
    (- 1 (n-cos-sim dvu dvv))))

(defn euclid-dist
  "Calculate Euclidian distance between two points
  given in 2 maps {:x x :y y}"
  [pt1 pt2]
  (Math/sqrt (+ (Math/pow (- (:x pt1) (:x pt2)) 2) (Math/pow (- (:y pt1) (:y pt2)) 2))))

(defn ned
  "Calculate Euclidian Distance between two vectors
   using native Neanderthal lib"
  [clu clv]
  (let [u (dv clu) v (dv clv)]
    (nrm2 (axpy -1 v u))))

(defn r-swap-val!
  "Swap two random positions (sp and fp) in given vector u"
  [u]
  (let [size (count u)]
    (loop [sp (rand-int size) fp (rand-int size)]
      (if (= sp fp)
        (recur sp (rand-int size))
        (assoc (assoc u sp (nth u fp)) fp (nth u sp))))))

(defn dge-matrix
  [source]
  (dge (count source) (count (first source)) source))

(defn vec-dist
  "Returns cosine distance between 2 rows
   a and b in matrix m"
  [m a b]
  (n-cos-dist (row m a) (row m b)))

(defn rand-w-o-num!
  "Returns random number from given range
  except excluded number"
  [range exclude]
  (loop [rand-num (rand-int range)]
    (if (= rand-num exclude)
      (recur (rand-int range))
      rand-num)))
(defn transpose
  [tf-m]
  (trans tf-m))
