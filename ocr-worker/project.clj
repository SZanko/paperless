(defproject ocr-worker "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.novemberain/langohr "5.5.0"]
                 [clojurewerkz/elastisch "3.0.1"]
                 [minio-clj "0.2.2"]
                 [pdfboxing "0.1.14"]
                 [net.sourceforge.tess4j/tess4j "5.13.0"]]
  :main ^:skip-aot ocr-worker.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
