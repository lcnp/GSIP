# Jan 2026 Model

## types

```
<id/mo/0003847526> a gxc:mo;
<info/mo/0003847526> a gxc:UIC.
<dat/mo/0003847526/nrcan-rncan/csv>  a gxc:FIC;
[] a gxc:RIC
```



## /id/ links 

``` 
<id/mo/0003847526>
    a gxc:mo;
    gxp:subjectOf <dat/mo/0003847526/nrcan-rncan/csv> ;
    gxp:subjectOf <info/mo/0003847526> ;
    gxp:subjectOf [
        a gxc:FIC;
    ]
```


### FIC

```
[
        a gxc:FIC;
        dct:format "text/html" ;
        dct:conformsTo <http://xmlns.earthresourceml.org/earthresourceml-lite/2.0> ; 
        gxp:encodes <info/mo/nrcan-rncan> ;
        gxp:representedBy [
            a gxc:RIC ;
            schema:provider <https://nrcan.gc.ca/gsc> ; 
            schema:url "https://geoconnex.ca/htmldoc/mo/nrcan-rncan/30001473"
        ]

```

The provider is used to group the panels

``` 
<dat/mo/0003847526/nrcan-rncan/csv>
    a gxc:FIC;
    dct:format "text/csv" ;
    dct:conformsTo <http://xmlns.earthresourceml.org/earthresourceml-lite/2.0> ; 
    gxp:encodes <info/mo/nrcan-rncan> ;
    gxp:representedBy [
        a gxc:RIC ;
        gxp:preferred "true"^^xsd:boolean;
        schema:provider <https://nrcan.gc.ca/gsc> ; 
        schema:url "https://geoconnex.ca/gsc-node/geoserver/erml/ows?service=WFS&version=1.1.0&request=GetFeature&typeName=erml%3AMineralOccurrenceView&outputFormat=csv&maxFeatures=10&CQL_FILTER=identifier='30001473'"
    ], [
        a gxc:RIC ;
        schema:provider <https://nrcan.gc.ca/gsc> ; 
        schema:url "https://geoconnex.ca/gsc-node/geoserver/erml/ows?service=WFS&version=1.1.0&request=GetFeature&typeName=erml%3AMineralOccurrenceView&outputFormat=csv&maxFeatures=10&CQL_FILTER=identifier='30001473'"
    ] .
``` 

a /id/ is a subject of a FIC

```

<id/mo/0003847526>
    a gxc:mo;
    gxp:subjectOf <dat/mo/0003847526/nrcan-rncan/csv> ;
# add subject of to /info/ 
    gxp:subjectOf <info/mo/0003847526> ;
    gxp:subjectOf [
        a gxc:FIC;   
        gxp:encodes <info/mo/nrcan-rncan> ;
        gxp:representedBy [
            a gxc:RIC ;
            schema:provider <https://nrcan.gc.ca/gsc> ; 
            schema:url "https://geoconnex.ca/htmldoc/mo/nrcan-rncan/30001473"
        ]

```

a /info/ is encodedBy a FIC

```
<info/mo/nrcan-rncan>
    a gxc:UICs;
    gxp:partOf <info/mo/cgdn-rdgc>;
    gxp:encodedBy [
        a gxc:FICs ;
        dct:format "application/gml+xml;subtype=erml";
# add conforms to: should be URL not string in ""
        dct:conformsTo <http://xmlns.earthresourceml.org/earthresourceml-lite/2.0> ; 
        rdfs:label "CGDN NRCan Mineral Occurrence dataset in ERML"@en, "RDGC jeu de données d'indices minéralisés RNCan en ERML"@fr ;
# remove schema name
#      schema:name "CGDN NRCan Mineral Occurrence dataset in ERML"@en, "RDGC jeu de données d'indices minéralisés RNCan en ERML"@fr  ; 
# add representedBy relation and RIC class
        gxp:representedBy [
            a gxc:RIC ;
# move provider to here
            schema:provider <https://nrcan.gc.ca/gsc> ; 
            schema:url "https://geoconnex.ca/gsc-node/geoserver/erml/ows?service=WFS&version=1.1.0&request=GetFeature&typeName=erml%3AMineralOccurrenceView&outputFormat=gml32&SORTBY=identifier"
       ]
``` 




