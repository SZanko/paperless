(ns ocr-worker.core-test
  (:require [clojure.test :refer :all]
            [ocr-worker.core :refer :all]
            []))


(deftest ocr-image-test
  (testing "Tries to run an ocr on an image"
    (is (= "Hello World"))))