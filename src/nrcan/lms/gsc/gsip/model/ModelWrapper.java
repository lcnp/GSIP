package nrcan.lms.gsc.gsip.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.namespace.QName;


import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import nrcan.lms.gsc.gsip.Manager;
import nrcan.lms.gsc.gsip.conf.Configuration;
import nrcan.lms.gsc.gsip.vocabulary.SCHEMA;
import nrcan.lms.gsc.gsip.vocabulary.CGDN;



/**
 * Wraps a Jena model and provide utilities for FreeMarker to access underlying model
 * @author Eric Boisvert
 * Laboratoire de Cartographie Numérique et de Photogrammétrie
 * Commission géologique du Canada (c) 2018
 * Ressources naturelles Canada
 */

public class ModelWrapper {

	private Model model;
	private String locale = "en";
	private Resource contextResource;
	public static final String SCHEMAORG = "https://schema.org/";
	// I cannot use the real properties under Geosconnex here, they are converted to local
	public static final Property concretizedBy =  ResourceFactory.createProperty( System.getenv("GSIP_BASEURI")+"/id/onto/", "concretizedBy" );
	public static final Property concretizes =  ResourceFactory.createProperty( System.getenv("GSIP_BASEURI")+"/id/onto/", "concretizes" );
	public static final Property partOf =  ResourceFactory.createProperty( System.getenv("GSIP_BASEURI")+"/id/onto/", "partOf" );
	
	// this is not not a very good design for long term, but this codebase might not be maintained in the long term

	//TODO. I should get the default baseUri from context, not hardcoded
	public ModelWrapper(Model m,String contextResource)
	{
		this.model = m;
		this.contextResource = m.getResource(contextResource);
		/**
		Logger.getAnonymousLogger().log(Level.INFO, "Contexte : " + contextResource);
		model.write(System.out,"TURTLE");
		**/
	}
	
	public ModelWrapper(Model m,String contextResource,String locale)
	{
		this.model = m;
		this.contextResource = m.getResource(contextResource);
		// if locale is null, it's english, otherwise french is is starts with 'f'
		this.locale = locale == null?"en":(locale.toLowerCase().startsWith("f")?"fr":"en");
		Logger.getAnonymousLogger().log(Level.WARNING,"Language " + this.locale);
	}
	
	


	/**
	 * Get the URI of the context resource (the queried resource)
	 * @return
	 */
	
	public String getContextResourceUri()
	{
		return contextResource.getURI();
	}
	
	
	/**
	 * return the URI up to /id/ 
	 * @return
	 */
	public String getNonInfoUri()
	{
		int s = contextResource.toString().indexOf("/id/");
		return contextResource.toString().substring(0, s) + "/id/";
		
	}
	
	
	/**
	 * if convertion is on, the client has a local URI.  If this local uri is used in the namespace, it should be mapped to persistent
	 * @param ns
	 * @return
	 */
	public String getPrefix(String ns)
	{
		if (ns != null)
		{
			
			return model.getNsURIPrefix(ns);
		}
		else
			return "";
	}
	
	
	
	/** 
	 * The preferred label for the context resource
	 * will return the first label that matches the language, if none, will return the first that has no language attribute. If not available, will return whatever first label if finds. If none, return the default label
	 * @param language
	 * @param defaultLabel
	 * @return
	 */
	public String getPreferredLabel(String language,String defaultLabel)
	{
		return getPreferredLabel(contextResource,language,defaultLabel);
	}

	public String getPreferredLabel(String defaultLabel)
	{
		return getPreferredLabel(contextResource,locale,defaultLabel);
	}
	
	/**
	 * Return the preferred label for a specific resource expressed as a String
	 * will return the first label that matches the language, if none, will return the first that has no language attribute. If not available, will return whatever first label if finds
     * 
	 * 
	 * @param resource
	 * @param language
	 * @param defaultLabel
	 * @return
	 */
	/**public String getPreferredLabel(String resource,String language,String defaultLabel)
	{
		Resource res = model.getResource(getFullUri(resource));
		return getPreferredLabel(res,language,defaultLabel);
	} **/

	/**
	 * Find a label for this language for this Resource, expressed as a Jena Resource.  If language is null or no label matches this language
	 * return the first one
	 * @param r
	 * @param language
	 * @param defaultLabel
	 * @return
	 */
	public String getPreferredLabel(Resource res,String language,String defaultLabel)
	{
		
		
		StmtIterator s = res.listProperties(RDFS.label);
		String atLeastThisOne = null;
		while(s.hasNext())
		{

			Statement st = s.next();
			if (atLeastThisOne == null)
				atLeastThisOne = st.getLiteral().getValue().toString();
			// is the language is null, we already found what we were looking for
			if (language == null) break;
			// got a language that matches 
			if (language.equals(st.getLanguage()))
				return st.getLiteral().getValue().toString();
		}
		
		if (atLeastThisOne == null)
			return defaultLabel;
		else
			return atLeastThisOne;
		
		
		
	}

	/**
	 * Get all the labels for this language
	 * @param lang language of the label
	 * @param includeUnderfined return those who have underfined language
	 * @return
	 */
	public List<String> getLabels(String lang,boolean includeUndefined)
	{
		return getLabels(this.contextResource,lang,includeUndefined);
	}

	/**
	 * 
	 * @param r context resource
	 * @param lang
	 * @param includeUnderfined
	 * @return
	 */
	public List<String> getLabels(Resource r,String lang,boolean includeUndefined)
	{
		List<String> labels = new ArrayList<String>();
			StmtIterator s =  r.listProperties(RDFS.label);
			while(s.hasNext())
			{
				Statement st = s.next();
			    if (lang == null)
					// we get them all
					{
						labels.add(st.getLiteral().getValue().toString());
					}
					else
					{
						if ((includeUndefined && (st.getLanguage() == null || st.getLanguage().trim().length() == 0)) || lang.equals(st.getLanguage()))
							labels.add(st.getLiteral().getValue().toString());

					}
			}
			return labels;

	}

	public String getJoinedLabels(Resource r,String lang,boolean includeUndefined,String sep)
	{
	
		List<String> lbl = getLabels(r,lang,includeUndefined);
		lbl.sort(new LauraLabelComparator());
		return	StringUtils.join(lbl,sep);
	}

	public String getJoinedLabels(String lang,boolean includeUndefined,String sep)
	{
		List<String> lbl = getLabels(lang,includeUndefined);
		lbl.sort(new LauraLabelComparator());
		return	StringUtils.join(lbl,sep);
	}
	

	private class LauraLabelComparator implements Comparator<String>
	{
		@Override
		public int compare(String s1,String s2)
		{
			// we don't care as long as the one with ":" is first
			if (s1 != null && s1.contains(":"))
				return -1;
			if (s2 != null && s2.contains(":"))
				return 1;
			if (s1 == null || s2 == null)
				return 0;
			return s1.compareTo(s2);
		}
	}

	/**
	 * get a list of Dataset, relevant for a single provider
	 * @param r
	 * @param p
	 * @return
	 */
	public List<Link> getDatasetForProvider(Resource r,Resource p,boolean isNir,String l)
	{
		
		return getRepresentationByProvider(r, p, isNir).
			stream().
			map(m -> getConcretizations(m)).
			flatMap(Set::stream).
			distinct().
			map(m-> new Link("",m.getURI(),getPreferredLabel(m, l, "N/A"))).
			collect(Collectors.toList());
		
		
	}

	public  List<Link> getDatasetForProvider(Resource p,boolean isNir,String l)
	{
		return getDatasetForProvider(contextResource,p,isNir,l);
	}

	/**
	 * By default, it this returns SubjectOf
	 * @param res
	 * @return
	 */

	

	/**
	 * Get a list of representations (subjectOf resources) for this resource
	 * @param res data resource, can be a blank node
	 * @return
	 */
	private List<Resource> getRepresentations(Resource res,Property p)
	{
		//Logger.getAnonymousLogger().log(Level.INFO,"subjectOf "+ res.getURI());
		StmtIterator statements = res.listProperties(p);
		List<Resource> subjectOf = new ArrayList<Resource>();
		while(statements.hasNext())
		{
			Statement s = statements.next();
			subjectOf.add(s.getResource());
		
		}
		//System.out.println("Got representations");
		// add other Infosets related to current infoset
		//extractConcretizations(subjectOf);
		return subjectOf;

	}


	public List<Link> getInfosetLinks(Resource ds)
	{
		//System.out.println("looking dataset for " + ds.getURI());
		//Resource ds = model.getResource(dataset);
		Set<Resource> infosets = getConcretizations(ds);
		//System.out.println("we have " + infosets.size() + " infosets");
		List<Link> links = new ArrayList<>();
		for(Resource r:infosets)
		{
			Link infoSetLink = new Link("Dataset", r.getURI(), this.getLastPart(r.getURI()));
			links.add(infoSetLink);
		}
		return links;

	}

	/**
	 * Add the concretizations of a resource to an existing list of datasets
	 * see issue #12.
	 */
	private Set<Resource> getConcretizations(Resource dataset)
	{
		// we assume 
		Set<Resource> infosets = new HashSet<Resource>();
		// for each element in the currentList (they are Datasets)
		// I check if it has a concretize to find an Info
	
			
			StmtIterator concretizesItr = dataset.listProperties(concretizes);
			while(concretizesItr.hasNext())
			{
				
				//get a resource, but can be something else, will only keep going if it's a
			
				Resource infoset = getResourceFromItr(concretizesItr.next());
				if (infoset == null) continue;
				infosets.add(infoset);
				// this guys should have a 'partOf'
				//StmtIterator partOfItr = infoset.listProperties(partOf);
				//while(partOfItr.hasNext())
				//{
					
					// this is the related Infoset
				//	Resource relatedInfoSet = getResourceFromItr(partOfItr.next());
				//	if (relatedInfoSet == null) continue;
				//	infosets.add(relatedInfoSet);
					
				//}
			}
		
			return infosets;
	}

	/**
	 * return the Resource only if it's a Resource
	 */
	private static Resource getResourceFromItr(Statement s)
	{
		
			RDFNode n = s.getObject();
	
			if (n.isResource())
				return n.asResource();
			else return null;
		
	}
	/**
	 * return all providers for a given resource
	 * @param res a Resource that has a linkage to a Dataset, either though subjectOf or concretizedBy
	 * @return
	 */
	public List<Resource> getAllProviders(Resource res,boolean isNir)
	{
		
		return getRepresentations(res,getDsProperty(isNir)).stream().map(r -> getProviders(r)).flatMap(List::stream).distinct().collect(Collectors.toList());
		
	}


	/**
	 * return the correct property depending if the resource is a real thing or an info.
	 * 
	 * @param isNir
	 * @return
	 */
	private Property getDsProperty(boolean isNir)
	{
		return isNir?SCHEMA.subjectOf:concretizedBy;
	}

	private String dumpStatement(Statement stmt)
	{
		StringBuilder sb = new StringBuilder();
		Resource  subject   = stmt.getSubject();     // get the subject
		Property  predicate = stmt.getPredicate();   // get the predicate
		RDFNode   object    = stmt.getObject();      // get the object
		sb.append(subject.toString());
		sb.append(" " + predicate.toString() + " ");
    if (object instanceof Resource) {
		sb.append(object.toString());
    } else {
        // object is a literal
        sb.append(" \"" + object.toString() + "\"");
    }

	return sb.toString();

	}

	/**
	 * return all providers for the context resource
	 * @return
	 */
	public List<Resource> getAllProviders(boolean isNir)
	{
		return getRepresentations(isNir).stream().map(r -> getProviders(r)).flatMap(List::stream).distinct().collect(Collectors.toList());
	}

	/**
	 * Get all the representations of the context resource that have this provider
	 * @param provider
	 * @return
	 */
	public List<Resource> getRepresentationByProvider(Resource provider,boolean isNir)
	{


		return getRepresentations(isNir).stream().filter(m -> getProviders(m).contains(provider)).collect(Collectors.toList());
	}

	/**
	 * The all the reprensentation of a specific context resource that has this provider
	 * @param context
	 * @param provider
	 * @return
	 */
	public List<Resource> getRepresentationByProvider(Resource context,Resource provider,boolean isNir)
	{
		return getRepresentations(context,getDsProperty(isNir)).stream().filter(m -> getProviders(m).contains(provider)).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param res a Dataset Resource
	 * @return
	 */
	public List<Resource> getProviders(Resource res)
	{
		StmtIterator statements = res.listProperties(SCHEMA.provider);
		List<Resource> subjectOf = new ArrayList<Resource>();
		while(statements.hasNext())
		{
			Statement s = statements.next();
			subjectOf.add(s.getResource());
		}
		return subjectOf;
	}
	
	/**
	 * 
	 * @param res a Dataset
	 * @return
	 */
	public String getProvider(Resource res)
	{
		Resource p = res.getPropertyResourceValue(SCHEMA.provider);
		if (p != null)
			return p.toString();
		else
			return "N/A";
	}
	
	
	
	/**
	 * Get a list of representations (subjectOf resources) for the context resource
	 * @return
	 */
	public List<Resource> getRepresentations(boolean isNir)
	{
		//Logger.getAnonymousLogger().log(Level.INFO, contextResource.getURI());
		return getRepresentations(this.contextResource.getURI(),isNir);
	}

	/**
	 * Get a list of representations for a resource, expressed as a string
	 * @param res
	 * @param isNir is this a /id/ ?
	 * @return
	 */
	public List<Resource> getRepresentations(String res,boolean isNir)
	{
		if (res == null) return getRepresentations(isNir);
		Resource r = model.getResource(getFullUri(res));
		return getRepresentations(r,getDsProperty(isNir));
	}
	
	/**
	 * Get the URI with an extra format override (?f=) parameter.  Will add it to the URL 
	 * takes care of existing parameters.  Returns self if none is found.
	 * @param r
	 * @param mime
	 * @return
	 */
	public String getFormatOverride(String r,String mime)
	{
		Configuration c = Manager.getInstance().getConfiguration();		
			String format = c.getFormatFromMimeType(mime);
			if (format != null)
				try {
					return appendFormat(r,"f",format);
				} catch (URISyntaxException e) {
					Logger.getAnonymousLogger().log(Level.WARNING, "problem with format for mime [" + mime + "] for resource " + r, e);
					return r;
				}
			else
			return r;
	}
	
	/**
	 * Get all the association grouped according to Aggregation.  This is called by GetRelevantLinkBy* public method.
	 * @param r
	 * @param a
	 * @return
	 */
	private Map<String,List<Link>> getRelevantLinkByGroup(Resource r,Aggregation a)
	{
		Map<String,List<Link>> out = new Hashtable<String,List<Link>>();
		List<Link> links = getRelevantLinks(r);
		List<Link> newCollection;
		for(Link l:links)
		{
			if (!out.containsKey(a.getKey(l)))
			{
				newCollection = new ArrayList<Link>();
				out.put(a.getKey(l),newCollection );
			}
			else
				newCollection = out.get(a.getKey(l));
			newCollection.add(l);
		}
		return out;
		
	}
	
	/**
	 * return a list of links, group by properties . Hash keys are properties, values are resources
	 * @param r
	 * @return
	 */
	public Map<String,List<Link>> getRelevantLinkByProperty(Resource r)
	{
		try {
		return getRelevantLinkByGroup(r,new Aggregation() {
			public String getKey(Link l) {return l.getLabel();}}
			);
		}
		catch(Exception e)
		{
			Logger.getAnonymousLogger().log(Level.SEVERE, "Problem getting relevant links by properties", e);
			return new Hashtable<String,List<Link>>(); // empty list
		}
	}
	
	/**
	 * return a list of links, group by resource.  Hash keys are resources, values are properties
	 * @param r
	 * @return
	 */
	public Map<String,List<Link>> getRelevantLinkByResource(Resource r)
	{
		try {
		return getRelevantLinkByGroup(r,new Aggregation() {
			public String getKey(Link l) {return l.getUrl();}}
			);
		}
		catch(Exception e)
		{
			Logger.getAnonymousLogger().log(Level.SEVERE, "Problem getting relevant links by resource", e);
			return new Hashtable<String,List<Link>>(); // empty list
		}

	}
	
	/** 
	 * Same as getRelevantLinkByResource(Resource r), but for context resource
	 * @return
	 */
	public Map<String,List<Link>> getRelevantLinkByResource()
	{
		return getRelevantLinkByResource(this.contextResource);
	}
	
	/**
	 *  Same as getRelevantLinkByProperty(Resource r), but for context resource
	 * @return
	 */
	public Map<String,List<Link>> getRelevantLinkByProperty()
	{
		return getRelevantLinkByProperty(this.contextResource);
	}
	
	
	/**
	 * Get a list of relevant links, not grouped, for a Resource.  Used by other GetRelevantLink*
	 * Get all the links that are not RDFS/RDF/OWL or DCTerms
	 * @param r
	 * @return
	 */
	public List<Link> getRelevantLinks(Resource r)
	{
		List<Link> links = new ArrayList<Link>();
		StmtIterator i = r.listProperties();
		while(i.hasNext())
		{
			Statement statement = i.next();
			Property p = statement.getPredicate();
			String ns = p.getNameSpace();
		 // skip RDFS, RDF, OWL and DCT
			if (RDFS.getURI().equals(ns)) continue;
			if (RDF.getURI().equals(ns)) continue;
			if (OWL.getURI().equals(ns)) continue;
			if (SCHEMA.getURI().equals(ns)) continue;
			if (sameResource(p, concretizedBy)) continue;
			//if (DCTerms.getURI().equals(ns)) continue;
			// if we're here, we're good
			// the object must be a resource
			if (statement.getObject().isResource())
			{
				String l = getPreferredLabel(statement.getResource(),locale, this.getLastPart(statement.getResource().getURI()));
				links.add(new Link(p.getLocalName(),statement.getResource().getURI(),l));
			}

		}
		return links;
	}

	
	
	/**
	 * Get relevant links (See getRelevantLinks(Resource)
	 * @return
	 */
	public List<Link> getRelevantLinks()
	{
		return getRelevantLinks(this.contextResource);
	}

	/** get the supported dct formats for a /data/ 
	 * 
	 * @param r
	 * @return
	 */
	public List<String> getFormats(Resource r)
	{
		List<String> formats = new ArrayList<String>();
		StmtIterator i = r.listProperties(DCTerms.format);
		while(i.hasNext())
		{
			Statement s = i.next();
			formats.add(s.getString());
		}
		return formats;

	}
	
	/**
	 * appends the f= parameters to an existing URI
	 * @param url
	 * @param key
	 * @param value
	 * @return
	 * @throws URISyntaxException
	 */
	public String appendFormat(String url,String key,String value) throws URISyntaxException
	{
		URI u = new URIBuilder(url).addParameter(key, value).build();
		return u.toString();
	}

	/**
	 * check if a resource is a prefixed reference (geo:Thing as opposed to <http://geo.com/Thing>)
	 * @param resource
	 * @return
	 */
	public boolean isPrefixed(String resource)
	{
		if (resource.startsWith("<") && resource.endsWith(">")) return false;
		QName name = QName.valueOf(resource);
		// check if the prefix exist
		String uri = model.getNsPrefixURI(name.getPrefix());
		return (uri != null && uri.length() > 0) ;
		
		
	}
	 
	/**
	 * convert a prefixed resource to a fully qualified resource
	 * assumes we are passing a prefix:value , if not, just return the value as-is
	 * @param prefixedResource
	 * @return
	 */
	public String getFullUri(String prefixedResource)
	{
	//	Logger.getAnonymousLogger().log(Level.INFO, "prefixed:" + prefixedResource);
		QName name = QName.valueOf(prefixedResource);
		//TODO: not sure this always work
		if (prefixedResource.equals(name.getLocalPart())) 
			return prefixedResource; // there's no prefix
		String uri = model.getNsPrefixURI(name.getPrefix());
		if (uri != null && uri.length() >0 )
			return uri + name.getLocalPart();
		else
			return prefixedResource;
			
		
	}
	
	
	/** return the complete context model in requested encoding
	 * This is generally used to embed JSON-LD into a HTML document.
	 * @param format
	 * @return jena supported 
	 */
	public String encode(String format)
	{
		Lang l = getLang(format);
		if (l != null)
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			RDFDataMgr.write(baos, model, l);
			return baos.toString(Charset.forName("UTF-8"));
		}
		
		else
			return format + " is Invalid \n use TTL,NT,JSON-LD,RDF/XML-ABBREV,RDF/XML,N3 or RDF/JSON";
	}
	
	public Model getModel()
	{
		return model;
	}
	
	/**
	 * get a resource from the model
	 * @param res
	 * @return
	 */
	public Resource getResource(String res)
	{
		return model.getResource(res);
	}
	
	/**
	 * get a resource value from a property
	 * @param context resource to get a resource from 
	 * @param property
	 * @return
	 */
	public List<Resource> getPropertyResource(String context,String property)
	{
		
		Property p = model.createProperty(property);
		if (context == null)
			return getAllResource(this.contextResource.listProperties(p));
		else
		{
		Resource r = getResource(context);
		if (r == null)
			return null;
		else
			return getAllResource(r.listProperties(p));
		
		}
	}
	
	private List<Resource> getAllResource(StmtIterator it)
	{
		List<Resource> listResource = new ArrayList<Resource>();
		while (it.hasNext())
		{
			RDFNode n = it.next().getObject();
			if (n.isResource())
				listResource.add(n.asResource());
		}
		return listResource;
	}
	
	/**
	 * Get a resource from a property of the context resource
	 * @param property
	 * @return
	 */
	public List<Resource> getPropertyResource(String property)
	{
		return getPropertyResource(null,property);
	}
	
	/**
	 * Get the Jena language from a string representation
	 * @param s
	 * @return
	 */
	private static Lang getLang(String s)
	{
		if ("TTL".equalsIgnoreCase(s)) return Lang.TURTLE;
		if ("NT".equalsIgnoreCase(s)) return Lang.NT;
		if ("JSON-LD".equalsIgnoreCase(s)) return Lang.JSONLD;
		if ("RDF-XML-ABBREV".equalsIgnoreCase(s)) return Lang.RDFTHRIFT;
		if ("RDF/XML".equalsIgnoreCase(s)) return Lang.RDFXML;
		if ("N3".equalsIgnoreCase(s)) return Lang.N3;
		if ("RDF/JSON".equalsIgnoreCase(s)) return Lang.RDFJSON;
		return null;
	}
	
	/** get the list of types for a resource (rdf:type)
	 * 
	 * @param r
	 * @return
	 */
	public String getTypeLabel(Resource r)
	{
		// TODO: create a function that only return leaf types
		StringBuilder sb = new StringBuilder();
		StmtIterator i = r.listProperties(RDF.type);
		while(i.hasNext())
		{
			String nextElement = null;
			Statement s = i.next();
			try {
			Resource typeResource = s.getResource();
			if (typeResource.isAnon()) continue;
			// try to find a label for the type in the request language, if not, just use the typename
			nextElement = this.getPreferredLabel(typeResource, locale, getLastPart(typeResource.getURI()));
			}
			catch(Exception ex)
			{
				nextElement = "ERR";
			}
			
			if (sb.length() > 0) sb.append(",");
			sb.append(nextElement);
			
		}
		
		return sb.toString();
	
		
		
	}

	/**
	 * helper function to return a string in the preferred language
	 * @param en
	 * @param fr
	 * @return
	 */
	public String getLocText(String en,String fr)
	{
		return locale.equals("fr")?fr:en;
		
	}
	
	/**
	 * convenience function to return the individual name at the end of a URI
	 * eg: For https//geoconnex.ca/id/aquifers/Richelieu, will return "Richelieu".
	 * useful whene there are no rdfs:label
	 * @param url
	 * @return
	 */
	public String getLastPart(String url)
	{
		if (url == null) return "";
		String[] parts = url.split(":|/|#");
		return parts[parts.length-1];
	}
	
	
	/**
	 * get the type list in the form type > type > type
	 * @return
	 */
	public String getTypeLabel()
	{
		return getTypeLabel(this.contextResource);
	}
	
	
	/**
	 * For some reason, getPropertyResourceValue does not work with literal (?) in a blank node (?)
	 * @param p
	 * @return
	 */
	private String getLiteralPropertyValue(Resource res,Property p)
	{
		Statement stmt = res.getProperty(p);
		if (stmt == null)
			return null;
		else
			return stmt.getObject().asLiteral().toString();
		
	}
	/**
	 * Get the URLs for the remote resource (we assume this is a data node)
	 * @param res. data resource. can be a blank node
	 * @param useResourceUri.  if url is missing and the resource is not a blank node, use the resource URL
	 * @return
	 */
	public List<Link> getUrls(Resource res,boolean useResourceUri)
	{
		List<Link> urls = new ArrayList<Link>();
		String url = getLiteralPropertyValue(res,SCHEMA.url);
		
		// if the url list is empty and we are allowed to use the resource uri, do so
		if (url==null)
		{
			if (useResourceUri && res.isURIResource())
			{
			// loop in all the mime types 
			for(String f:getFormats(res))
				{
				Link l = new Link(f,getFormatOverride(res.getURI(),f),"");
				l.setMimeType(f);
				urls.add(l);
				}
			}
		}
			else
			{
				// we expect a literal
				// if there is only 1 format, 
				List<String> formats = getFormats(res);
				if (formats.size() < 2)
				{
					Link l = new Link(formats.size() == 0?"":formats.get(0),url,"");
					l.setMimeType(formats.size() == 0?"":formats.get(0));
					urls.add(l);
					
				}
				else
				for(String f:getFormats(res))
				{
					Link l = new Link(f,getFormatOverride(url,f),"");
					l.setMimeType(f);
					urls.add(l);
				}
			}
		return urls;
	}
	
	public String getConformsTo(Resource res)
	{
		
		Resource c = res.getPropertyResourceValue(DCTerms.conformsTo);
		if (c != null)
			return c.toString();
		else
			return "N/A";
	}
	
	public void close()
	{
		this.model.close();
	}

	public static boolean sameResource(Resource r1,Resource r2)
	{
		if (r1 == null && r2 == null) return true;
		if (r1 == null || r2 == null) return false;
		//TODO: I just do this in case namespace or localname are null (possible ?)
		try{
		return (r1.getNameSpace().equals(r2.getNameSpace()) && r1.getLocalName().equals(r2.getLocalName()));
		}
		catch(Exception ex)
		{
			return false;
		}
	}

}
