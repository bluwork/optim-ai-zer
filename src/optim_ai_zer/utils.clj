(ns optim-ai-zer.utils)

;; TODO Catch exception
(defn dot-product
  [vec1 vec2]
  (if (= (count vec1) (count vec2))
    (apply +(map #(* %1 %2) vec1 vec2 ))))

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
