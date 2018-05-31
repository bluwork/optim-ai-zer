(ns optim-ai-zer.utils-test
  (:require [clojure.test :refer :all]
            [optim-ai-zer.utils :refer :all]))

(deftest dot-prod-test
  (is (= 14 (dot-product [1 2 3] [1 2 3]))))

(deftest magnitude-test
  (is (#(and (> 9.53) (< 9.54) %) (magnitude [1 2 3 4 5 6]))))

(deftest euclid-test
  (let [pt1 {:x 120 :y 130} pt2 {:x 120 :y 170}]
    (is (= 40.0 (euclidian-dist pt1 pt2 )))))

(deftest cosine-test
  (let [v1 [1 2 3] v2 [1 2 3]]
    (is (= 1.0 (cosine-similarity v1 v2)))
    (is (= 0.0 (cosine-distance v1 v2)))))
