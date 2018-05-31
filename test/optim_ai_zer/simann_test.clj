(ns optim-ai-zer.simann-test
  (:require [clojure.test :refer :all]
            [optim-ai-zer.algos.simann :refer :all]))

(deftest test-ap
  "Test acceptance probability"
  (is (= (acceptance-probability 100 20 1000) 1.0))
  (is (< (acceptance-probability 20 100 100) 0.5)))
