(ns ocr-worker.core
  (:gen-class)
  (:require [clj-ocr.core :as ocr]
            [clj-ocr.utils :as ocr-utils]
            [clojure.java.io :as io]
            [pdfboxing.text :as pdf-text]
            [pdfboxing.common :as pdf-common]
            [minio :as minio]
            [clojure.data.json :as json]
            [langohr.core :as rmq]
            [langohr.queue :as lhq]
            [langohr.channel   :as lch]
            [langohr.consumers :as lc]
            [langohr.basic     :as lb]
            )
  (:import (io.minio GetObjectArgs GetObjectResponse)
           (java.io ByteArrayInputStream ByteArrayOutputStream File FileInputStream FileOutputStream)
           (java.nio.file Files)
           (java.lang String)
           (java.nio.file.attribute FileAttribute)
           (javax.imageio ImageIO)
           (org.apache.pdfbox.pdmodel PDDocument)
           (org.apache.pdfbox.text PDFTextStripper)
           (org.apache.pdfbox.rendering ImageType PDFRenderer)
           (java.awt.image BufferedImage)
           (pdfboxing.common PDFDocument)))

(defn get-env
  "Retrieve an environment variable by name, or return the default value if not set."
  [env-name default-value]
  (or (System/getenv env-name) (System/getProperty env-name) default-value))

(def rabbitmq-default-user (get-env "RABBITMQ_DEFAULT_USER" "rabbitmq_user"))
(def rabbitmq-default-passwd (get-env "RABBITMQ_DEFAULT_PASS" "rabbitmq_passwd"))
(def rabbitmq-default-host (get-env "RABBITMQ_DEFAULT_HOST" "localhost"))
(def rabbitmq-queue-input "files.input")
(def rabbitmq-queue-output "files.content")
(def elasticsearch-default-user (get-env "ELASTICSEARCH_DEFAULT_USER" "elasticsearchUser"))
(def elasticsearch-default-passwd (get-env "ELASTICSEARCH_DEFAULT_PASS" "elastic_passwd"))
(def elasticsearch-default-host (get-env "ELASTICSEARCH_DEFAULT_HOST" "http://localhost:9200"))
(def minio-endpoint (get-env "minioEndpoint" "http://localhost:9000"))
(def minio-access-key (get-env "minioAccessKey" "accessKey"))
(def minio-secret-key (get-env "minioSecretKey" "secretKey"))
(def minio-bucket "default")
(def minio-connection (minio/connect minio-endpoint minio-access-key minio-secret-key))


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

(defn get-pdf-content-from-disk
  "Run an OCR scan on a file which is on the disk"
  ([^String filename-on-disk]
   (get-pdf-content-from-disk filename-on-disk true))      ; Default behavior: don't delete file
  ([^String filename-on-disk delete-after?]
   (try
     (->> (if (pdf-common/is-pdf? filename-on-disk)
            ;; Get a sequence of images for PDFs
            (get-pdf-content-as-images filename-on-disk)
            ;; Read a single image for non-PDFs
            [(ImageIO/read filename-on-disk)])              ; Wrap in a sequence for consistent handling
          (map #(ocr/do-ocr % (ocr/set-language "eng")))    ; Process each image
          (apply str))                                      ; Combine OCR results into a single string
     (finally
       (when (and delete-after? (.exists (io/file filename-on-disk)))
         (.delete (io/file filename-on-disk)))))))

(defn download-file-to-tmp
  "Downloads the file temporary"
  [filename]
  (let [
        temp-path (File/createTempFile (subs filename 0 (.lastIndexOf filename "."))
                                       (subs filename (.lastIndexOf filename ".")))
        temp-file (.getAbsoluteFile temp-path)
        ]
    (minio/download-object minio-connection minio-bucket filename temp-file)
    (println temp-file)
    (str temp-file)
    ))


(defn create-output-json
  "create a json with the filename and content of the file"
  [filename content]
  (json/write-str {:text content :minioFilename filename})
  )

(defn sent-content-back
  "Sents the Content Back through rabbitmq"
  [filename content]
  (println filename content)
  (let [conn (rmq/connect {:host rabbitmq-default-host, :port 5672, :username rabbitmq-default-user, :vhost "/", :password rabbitmq-default-passwd}) ;; RabbitMQ connection URI
        channel (rmq/create-channel conn)
        queue rabbitmq-queue-output] ;; Define the queue name
    (try
      ;(lhq/declare channel queue {:durable true})
      (lb/publish channel "" queue (create-output-json filename content))
      (println (str "Message published to queue '" queue "': " (create-output-json filename content)))

    ;; Cleanup resources
      (finally
        (.close channel)
        (.close conn))
    ))
  )

(defn receive-filename-send-content-back
  "Callback function to handle received messages."
  [channel metadata payload]
  (let [filename (String. payload "UTF-8")]
    (println "Received message:" filename)
    (->> (download-file-to-tmp filename)
         (get-pdf-content-from-disk)
         (sent-content-back filename)
         )
  ))

(defn start-listening
  "Connect to RabbitMQ and listen to a queue indefinitely."
  []
  (let [conn    (rmq/connect {:host rabbitmq-default-host, :port 5672, :username rabbitmq-default-user, :vhost "/", :password rabbitmq-default-passwd}) ;; RabbitMQ connection URI
        channel (rmq/create-channel conn)
        queue   rabbitmq-queue-input] ;; Define the queue name
    ;; Declare the queue (idempotent, won't recreate if it exists)
    ;(lhq/declare channel queue {:durable true})
    ;; Start consuming messages from the queue
    (lc/subscribe channel queue receive-filename-send-content-back {:auto-ack true})
    (println (str "Listening for messages on queue '" queue "'..."))
    ;; Prevent the program from exiting
    (Thread/sleep Long/MAX_VALUE)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  ;(ocr-utils/get-lang-data "eng")
  (println minio-access-key minio-secret-key)
  (start-listening)
  ;(sent-content-back "Hello World" "Penis")
  ;(->>
    ;(str "Hello World")
    ;(minio/get-object minio-connection minio-bucket "9138730-FH_Campus_Bestaetigung_1.pdf")
    ;(pdf-text/extract "resources/HelloWorld.pdf")
    ;(download-file-to-tmp "9138730-FH_Campus_Bestaetigung_1.pdf")
    ;(get-pdf-content-from-disk)
    ;(println))
  )
