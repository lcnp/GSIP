package nrcan.lms.gsc.gsip.triple;
/**
 
   _    ___ _  _ ___ 
 | |  / __| \| | _ \
 | |_| (__| .` |  _/
 |____\___|_|\_|_|  
                    
                    
 */


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;


public class RemoteStore extends TripleStoreImpl {
	// default enpoint
	public static final String defaultSparqlEndpoint = "http://localhost:8080/fuseki/gsip";
	private String sparqlRepo;
	
	public RemoteStore(String store)
	{
		this.sparqlRepo = store;
	}
	
	// perform a sparql query on a data store
	@Override
	public Model getSparqlConstructModel(String sparql)
	{
		 Query query = QueryFactory.create(sparql);
			  try ( RDFConnection conn = RDFConnectionFactory.connect(sparqlRepo) ) {
		          return  conn.queryConstruct(query);
		        }
		 catch(Exception ex)
		 {
			 Logger.getAnonymousLogger().log(Level.SEVERE, "failed to execute [\n" + sparql + "]\n from " + sparqlRepo ,ex);
			 return null;
		 }
		
			 
	}

	public Model getSparqlDescribeModel(String describe)
	{
		
		Query query = QueryFactory.create(describe);
			  try ( RDFConnection conn = RDFConnectionFactory.connect(sparqlRepo) ) {
		          return  conn.queryDescribe(query);
		        }
		 catch(Exception ex)
		 {
			 Logger.getAnonymousLogger().log(Level.SEVERE, "failed to execute [\n" + describe + "]\n from " + sparqlRepo ,ex);
			 return null;
		 }
		
	}

	@Override
	public void executeSelect(String select,SolutionHandler h) {
		Query qry = QueryFactory.create(select);
    	QueryExecution qe = QueryExecution.service(this.sparqlRepo,select);
    	ResultSet rs = qe.execSelect();
		if (h.init())
		{
			while(rs.hasNext())
			{
				if (!h.read(rs.next())) break;
			}
		}

		h.end();
		rs.close();
		
	}

	
		

}
