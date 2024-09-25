#describes a non info
PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>
PREFIX dct: <http://purl.org/dc/terms/>
PREFIX schema: <https://schema.org/>
PREFIX cgdn: <https://geoconnex.ca/id/onto/> 
CONSTRUCT {
    <${resource?replace(' ','%20')}> ?p ?o. # all resources from any property
    ?o ?p2 ?o2. # all predicate-of an any object selected above
    <${resource?replace(' ','%20')}> ?p3 ?l. 
    ?o2 rdfs:label ?l2.
    ?o schema:geo ?g.
    ?g ?pg ?pp.
    }
WHERE {<${resource?replace(' ','%20')}> ?p ?o. ?o ?p2 ?o2. <${resource?replace(' ','%20')}> ?p3 ?l. 
 OPTIONAL {?o2 rdfs:label ?l2.}. 
 OPTIONAL {?o schema:geo ?g. ?g ?pg ?pp}. 

 FILTER (isLiteral(?l))}
