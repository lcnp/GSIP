package nrcan.lms.gsc.gsip.triple;


import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.rdf.model.Model;


public interface TripleStore {
	
	public Model getSparqlConstructModel(String sparql);
	public Model getSparqlDescribeModel(String describe);
	public Model describe(String resource);
	public boolean resourceExists(String resource);
	public void executeSelect(String select,SolutionHandler h,Model m);
	public void executeSelect(ParameterizedSparqlString select, SolutionHandler h,Model m);
	public void close();

}
