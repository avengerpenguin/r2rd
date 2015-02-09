(ns r2rd.homepage
  (:require [clojure.test :refer :all]
            [r2rd.core :refer :all]
            [clj-http.client :as client]))

(use 'clojure.test.junit)

(use-fixtures
  :once
  (fn [f]
    (let [server (ring.adapter.jetty/run-jetty
                  convertHttp
                  {:port 1234 :join? false})]
      (try
        (f)
        (finally
          (.stop server))))))

(require '[clj-http.client :as client])

(deftest should_work
  (def resp (client/get "http://localhost:1234"))
  (testing "Expecting 200 on home page."
    (is (= (:status resp) 200))))
