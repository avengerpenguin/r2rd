(ns r2rd.mappings
  (:require [clj-turtle.core :refer :all]))


(defns r2r "http://www4.wiwiss.fu-berlin.de/bizer/r2r/")
(defns mp "http://rossfenning.co.uk/mappings#")


(defn make-mapping [name source target]
  (rdf
   (mp name) (a) (r2r :PropertyMapping)
   (mp name) (r2r :prefixDefinitions) (literal
                                       "schema: <http://schema.org/>")
   (mp name) (r2r :prefixDefinitions) (literal
                                       "dbpedia-owl:	<http://dbpedia.org/ontology/>")
   (mp name) (r2r :sourcePattern) (literal source)
   (mp name) (r2r :targetPattern) (literal target)))


(def mappings
  (str
   (make-mapping "nameMapping"
                 "?SUBJ rdfs:label ?o"
                 "?SUBJ schema:name ?o")
   (make-mapping "descriptionMapping"
                 "?SUBJ rdfs:comment ?o"
                 "?SUBJ schema:description ?o")
   (make-mapping "thumbnailMapping"
                 "?SUBJ dbpedia-owl:thumbnail ?o"
                 "?SUBJ schema:thumbnailUrl ?o")))

