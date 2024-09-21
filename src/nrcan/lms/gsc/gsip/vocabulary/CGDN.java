package nrcan.lms.gsc.gsip.vocabulary;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;


public class CGDN {
	// code base on package org.apache.jena.vocabulary;
    // GSIP_BASEURI=http://localhost:8080/gsip
	//public static final String uri="http://localhost:8080/gsip/id/onto/";
    //public static final String uri= System.getenv("GSIP_BASEURI")+"/id/onto/";
    public static final String uri="https://geoconnex.ca/id/onto/";
	 protected static final Resource resource( String local )
     { return ResourceFactory.createResource( uri + local ); }

 protected static final Property property( String local )
     { return ResourceFactory.createProperty( uri, local ); }
 
 public static final Property partOf = Init.partOf();
 public static final Property hasPart = Init.hasPart();
 public static final Property concretizes = Init.concretizes();
 public static final Property concretizedBy = Init.concretizedBy();

 
 public static class Init {
     
     public static Property partOf()        	{ return property( "partOf"); }
     public static Property hasPart()         	{ return property( "hasPart"); }
     public static Property concretizes()			{ return property("concretizes");}
     public static Property concretizedBy() 				{ return property("concretizedBy");}

     
 }

 public static String getURI() {return uri;}

}
