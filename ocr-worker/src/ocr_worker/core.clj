(ns ocr-worker.core
  (:gen-class)
  (:require [clj-ocr.core :as ocr]
            [clj-ocr.utils :as ocr-utils]
            [clojure.java.io :as io]
            [pdfboxing.text :as pdf-text]
            [pdfboxing.common :as pdf-common])
  (:import (java.io File)
           (javax.imageio ImageIO)
           (org.apache.pdfbox.pdmodel PDDocument)
           (org.apache.pdfbox.text PDFTextStripper)
           (org.apache.pdfbox.rendering ImageType PDFRenderer)
           (java.awt.image BufferedImage)
           (pdfboxing.common PDFDocument)))

(defn get-pdf-content-as-images
  "Get content of the pdf as image"
  [^String filename]
  (with-open [^PDFDocument doc (pdf-common/obtain-document filename)]
    (let [renderer (PDFRenderer. doc)
          num-pages (.getNumberOfPages doc)]
      (doall
        (mapv (fn [page-index]
                (.renderImageWithDPI renderer page-index 300 ImageType/RGB))
              (range num-pages))))))


(defn get-pdf-content
  "Run an OCR scan on a file"
  [^String filename]
  (let [ocrfile (io/file filename)]
    (->> (if (pdf-common/is-pdf? ocrfile)
           ;; Get a sequence of images for PDFs
           (get-pdf-content-as-images ocrfile)
           ;; Read a single image for non-PDFs
           [(ImageIO/read ocrfile)]) ; Wrap in a sequence for consistent handling
         (map #(ocr/do-ocr % (ocr/set-language "eng"))) ; Process each image
         (apply str)))) ; Combine OCR results into a single string



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  ;(ocr-utils/get-lang-data "eng")
  (->>
    (pdf-text/extract "resources/HelloWorld.pdf")
    ;(get-pdf-content "resources/HelloWorld.png")
    (println)))
