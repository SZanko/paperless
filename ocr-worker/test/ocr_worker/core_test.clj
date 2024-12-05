(ns ocr-worker.core-test
  (:require [clojure.test :refer :all]
            [ocr-worker.core :refer :all])
  (:import (java.awt.image BufferedImage)))



(deftest ocr-image-test
  (testing "Tries to run an ocr on an image"
    (is (= "Hello World\n" (get-pdf-content "resources/HelloWorld.png")))
    (is (= "Hello World\n" (get-pdf-content "resources/HelloWorld.pdf")))))


(deftest get-pdf-content-images-test
  (testing "Tries to get the image of a pdf"
    (is (= (instance? BufferedImage (get-pdf-content-as-images "resources/HelloWorld.pdf"))))))
