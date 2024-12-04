(ns ocr-worker.core
  (:gen-class)
  (:require [clj-ocr.core :as ocr]
            [clj-ocr.utils :as ocr-utils]
            [clojure.java.io :as io])
  (:import (javax.imageio ImageIO)))


(defn get-pdf-content
  "Run an Ocr scan on a file"
  [^String filename]
  (ocr/do-ocr (ImageIO/read (io/file filename))
              (ocr/set-language "eng")))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (ocr-utils/get-lang-data "eng")
  (->>
    (get-pdf-content "resources/HelloWorld.pdf")
    (println)))
