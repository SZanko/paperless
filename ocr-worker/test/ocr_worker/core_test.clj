(ns ocr-worker.core-test
  (:require [clojure.test :refer :all]
            [ocr-worker.core :refer :all])
  (:import (java.awt.image BufferedImage)))

(deftest get-env-test
  (testing "Sets Env and tries to get it"
    (is (= "fallback" (get-env "TMP_ENV" "fallback")))
    (System/setProperty "TMP_ENV" "env")
    (is (= "env" (get-env "TMP_ENV" "fallback")))
    )
  (System/clearProperty "TMP_ENV")
  )

(deftest ocr-image-test
  (testing "Tries to run an ocr on an image"
    ;(is (= "Hello World\n" (get-pdf-content-from-disk "resources/HelloWorld.png" true)))
    (is (= "Hello World\n" (get-pdf-content-from-disk "resources/HelloWorld.pdf" false)))))


(deftest get-pdf-content-images-test
  (testing "Tries to get the image of a pdf"
    (is (= (instance? BufferedImage (get-pdf-content-as-images "resources/HelloWorld.pdf"))))))
