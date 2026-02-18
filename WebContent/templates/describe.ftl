#describes a non info
PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>
PREFIX schema: <https://schema.org/>
PREFIX gxp: <https://geoconnex.ca/id/prp/>
PREFIX schema: <https://schema.org/> 
CONSTRUCT {
    <${resource?replace(' ','%20')}> ?p ?o. # all resources from any property
    ?o ?p2 ?o2. # all predicate-of an any object selected above
    <${resource?replace(' ','%20')}> ?p3 ?l. 
    ?o2 rdfs:label ?l2.
    ?o schema:geo ?g.
    ?g ?pg ?pp.
    ?o gxp:subjectOf ?fic.
    ?fic ?pfic ?ofic.
    ?ogic gxp:representedBy ?prov.
    ?prov ?pprov ?oprov.
    ?oprov rdfs:label ?provlabel.
    }
WHERE {<${resource?replace(' ','%20')}> ?p ?o. ?o ?p2 ?o2. <${resource?replace(' ','%20')}> ?p3 ?l. 
 OPTIONAL {?o2 rdfs:label ?l2.}. 
 OPTIONAL {?o schema:geo ?g. ?g ?pg ?pp}. 
 OPTIONAL {?o gxp:subjectOf ?fic. ?fic ?pfic ?ofic}

 OPTIONAL {?ogic gxp:representedBy ?prov. ?prov ?pprov ?oprov}
 OPTIONAL {?oprov rdfs:label ?provlabel}

 FILTER (isLiteral(?l))}
