package nrcan.lms.gsc.gsip.triple;

import java.util.logging.Level;
import java.util.logging.Logger;


import org.apache.jena.rdf.model.Model;

import nrcan.lms.gsc.gsip.model.ModelUtil;

public abstract class TripleStoreImpl implements TripleStore {

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * describe a resource (DESCRIBE)
	 * @param resource
	 * @return
	 */
	public Model describe(String resource)
	{
		return getSparqlDescribeModel("DESCRIBE " + ModelUtil.formatResource(resource));
	}
	
	public boolean resourceExists(String resource)
	{
		Model mdl = describe(resource);
		
		
		return !mdl.isEmpty();
	}

	
	
	
	

}
