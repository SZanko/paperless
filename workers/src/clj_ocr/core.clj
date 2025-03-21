; epl license scottxtra https://github.com/ScottXTra/clj-ocr
(ns clj-ocr.core
  (:require clj-ocr.utils
            [clojure.java.io :as io]
            [pdfboxing.text :as pdf])
  (:import
    [net.sourceforge.tess4j  ITesseract Tesseract]
    [javax.imageio ImageIO])

  (:gen-class))

(def instance
  (new Tesseract))
;; param 1 -> buffered image ( to read a file from disk to buffered image is (ImageIO/read (clojure.java.io/file "pic.png")) )
;; param 2 -> Tesseract instance
;; returns -> text from the image
(defn do-ocr [bi tess-instance]
  (.doOCR tess-instance bi))

(defn set-language
  [lang]
  (let [new-lang-ins instance]
    (.setLanguage new-lang-ins lang) new-lang-ins))