(ns r2rd.core
  (:use
   [compojure.core :only [defroutes POST GET]]
   [clostache.parser :only [render-resource]])
  (:require [ring.adapter.jetty :as jetty])
  (:import
   (java.io ByteArrayOutputStream StringReader )
   (com.avengerpenguin.r2r JenaModelSource Mapper NTriplesOutput Repository)
   (com.hp.hpl.jena.rdf.model ModelFactory)))


(defn convertModel
  "Takes a Jena Model and returns the data mapped onto the Schema.org
  vocabulary. The return value will be a string N3 serialisation of
  the output. Uses the r2r library to do the actual mapping."
  [model]
  (let [
        source (new JenaModelSource model)
        stream (new ByteArrayOutputStream)
        out (new NTriplesOutput stream)
        mappings (Repository/createFileOrUriRepository "mappings.ttl")
        vocabulary "@prefix schema: <http://schema.org/> .
(
    schema:name,
    schema:description,
    schema:thumbnailUrl,
    schema:image,
    schema:actor
)" ]
    (Mapper/transform source out mappings vocabulary)
    (new String (.toByteArray stream))))


(defn convertString
  "Takes a string containing a Turtle serialisation of an RDF graph,
  creates a Jena Model from it and then hands off to convertModel to
  do the actual mapping."
  [rdfstring]
  (let [
        model (ModelFactory/createDefaultModel)
        reader (new StringReader rdfstring)
        ]
    (.read model reader "" "TURTLE")
    (convertModel model)))


(defn homePage []
  (render-resource "templates/home.mustache" {}))


(defroutes convertHttp
  "POST Turtle data here to get back equivalent N3 data in the Schema.org
  vocabulary."
  (GET "/" [] (homePage))
  (POST "/" {body :body} (convertString (slurp body))))


(defn -main
  "App entrypoint, which launches Jetty and bootstraps the web app.
  Use the PORT environment variable to force a bind to a port other
  than 5000."
  []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "5000"))]
    (jetty/run-jetty convertHttp {:port port})))
