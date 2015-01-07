(ns r2rd.core
    (:use compojure.core)
    (:require [ring.adapter.jetty :as jetty]))

(import 'java.io.ByteArrayOutputStream)
(import 'com.avengerpenguin.r2r.NTriplesOutput)
(import 'java.io.StringReader)


(defn convertModel [model]
  (let [
    source (new com.avengerpenguin.r2r.JenaModelSource model)
    stream (new ByteArrayOutputStream)
    out (new NTriplesOutput stream)
    mappings (com.avengerpenguin.r2r.Repository/createFileOrUriRepository "mappings.ttl")
    vocabulary "@prefix schema: <http://schema.org/> .
(
    schema:name,
    schema:description,
    schema:thumbnailUrl,
    schema:image,
    schema:actor
)" ]
    (com.avengerpenguin.r2r.Mapper/transform source out mappings vocabulary)
    (new java.lang.String (.toByteArray stream))))


(defn convertString [rdfstring]
  (let [
    model (com.hp.hpl.jena.rdf.model.ModelFactory/createDefaultModel)
    reader (new StringReader rdfstring)
    ]
    (.read model reader "" "TURTLE")
    (convertModel model)))


(defroutes convertHttp
  (POST "/" {body :body} (convertString (slurp body))))


(defn -main []
    (let [port (Integer/parseInt (get (System/getenv) "PORT" "5000"))]
    (jetty/run-jetty convertHttp {:port port})))
