(ns optim-ai-zer.gmo-test
  (:require [clojure.test :refer :all]
            [optim-ai-zer.gmo :refer :all]))

(deftest a-test
  (testing "Check fitness function."
    (is (= (recalculate-fitness [1 1 0 1 0 0 1 1 1 0]) 10))))
