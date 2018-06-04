(ns optim-ai-zer.utils-test
  (:require [clojure.test :refer :all]
            [optim-ai-zer.utils :refer :all]))

(deftest dot-prod-test
  (is (= 14 (dot-prod [1 2 3] [1 2 3]))))

(deftest magnitude-test
  (is (#(and (> 9.53) (< 9.54) %) (magnitude [1 2 3 4 5 6]))))

(deftest euclid-test
  (let [pt1 {:x 120 :y 130} pt2 {:x 120 :y 170}]
    (is (= 40.0 (euclid-dist pt1 pt2 )))))

(deftest cosine-test
  (let [u [1 2 3] v [1 2 3]]
    (is (= 1.0 (cos-sim u v)))
    (is (= 0.0 (cos-dist u v)))))

(deftest ned-test
  (let [u [100 200 200] v [100 200 300]]
    (is (= 100.0 (ned u v)))))

(deftest test-r-swap
  (let [u [1 2 3 4 5]]
    (is ((complement =) u (r-swap-val! u)))))

(deftest test-rand-w-o
  (let [range 10  excl 8]
    (is ((complement =) excl (rand-w-o-num! range excl)))))  
