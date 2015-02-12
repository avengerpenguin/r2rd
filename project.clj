(defproject r2rd "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :description "Web app wrapping r2r library."
  :url "http://example.com/FIXME"
  :license {:name "GPL v3"
            :url "http://www.gnu.org/copyleft/gpl.html"}
  :plugins [[lein-test-out "0.3.1"]]
  :dependencies [
                 [org.clojure/clojure "1.6.0"]
                 [ring/ring-core "1.3.1"]
                 [compojure "1.1.5"]
                 [ring/ring-jetty-adapter "1.3.1"]
                 [com.avengerpenguin/r2r_2.11.1 "0.0.0"]
                 [com.hp.hpl.jena/jena "2.6.2"]
                 [org.slf4j/slf4j-api "1.6.2"]
                 [org.slf4j/slf4j-log4j12 "1.6.2"]
                 [clj-turtle "0.1.3"]
                 [de.ubercode.clostache/clostache "1.4.0"]
                 [clj-http "1.0.1"]
                 ]
  :profiles {:uberjar {:aot :all}}
  :main r2rd.core)
