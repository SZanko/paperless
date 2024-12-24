(defproject ocr-worker "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.novemberain/langohr "5.5.0"]
                 [clojurewerkz/elastisch "3.0.1"]
                 [io.minio/minio "8.5.12"]
                 [pdfboxing "0.1.14"]
                 [org.clojure/data.json "2.5.1"]
                 [org.slf4j/slf4j-simple "2.0.16"]
                 [cc.qbits/spandex "0.8.2"]
                 [net.sourceforge.tess4j/tess4j "5.13.0"]]
  :main ^:skip-aot ocr-worker.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
