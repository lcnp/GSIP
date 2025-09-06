package nrcan.lms.gsc.gsip;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.apache.jena.query.QuerySolution;

import freemarker.template.TemplateException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import nrcan.lms.gsc.gsip.conf.Configuration;
import nrcan.lms.gsc.gsip.data.MatchType.MimeType;
import nrcan.lms.gsc.gsip.template.TemplateManager;
import nrcan.lms.gsc.gsip.triple.SolutionHandler;
import nrcan.lms.gsc.gsip.triple.TripleStore;
import nrcan.lms.gsc.gsip.util.MediaTypeUtil;


/**
 * The dat route essentially return a 307 with a new address found in the registry, or a 404 if not found.
 */

@Path("dat/{seg:.*}")
public class Dat {
    @Context UriInfo uriInfo;
	@GET
    public Response getDat() {
   
        // the /dat/ only supports one format per resource, so there are no such things as content negotiation here.
        // we just look for the resource in the triple store and return a 307 redirect to the resource URL
        
        //String path = uriInfo.getPath();
        //String pattern = getPattern(uriInfo);
        //convert the local URL into a global URL to search in the triple store
        // replace baseuri with persistenuri
        String baseUri = Configuration.getInstance().getParameterAsString("GSIP_BASEURI", "http://localhost:8080/gsip");
        String persistentUri = Configuration.getInstance().getParameterAsString("persistentUri", "https://geoconnex.ca");
        
        String searchUri = uriInfo.getAbsolutePath().toString().replace(baseUri, persistentUri); 
        Logger.getAnonymousLogger().log(Level.INFO, "Converted URI" + searchUri);
        // create a SPARQL query to get local uri to redirect to
        ReadUltimateUrl handler = new ReadUltimateUrl();
        Map<String,Object> params = Map.of("resource", searchUri);
        String query;
        try {
            query = TemplateManager.getInstance().transform(params, "_dat_location.flt");
        } catch (IOException | TemplateException e) {
            // TODO Auto-generated catch block
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error transforming template", e);
            return Response.status(500).entity("Internal server error").type(MediaType.TEXT_PLAIN).build();
        }
        TripleStore j = Manager.getInstance().getTripleStore();
        j.executeSelect(query, handler);
        if (handler.getUrl() != null)
        {
            // found a URL to redirect to
            return Response.status(307).header("Location", handler.getUrl()).build();
        }
        else
        {
            // not found
            return Response.status(404).entity("Not found").type(MediaType.TEXT_PLAIN).build();
        }
    }

    public class ReadUltimateUrl implements SolutionHandler
    {
        private String url = null;
        public String getUrl() { return url; }

        @Override
        public boolean init() {
            return true;
        }

        @Override
        public boolean read(QuerySolution qs) {
            if (qs.contains("u"))
            {
                url = qs.get("u").toString();
                return false; // stop processing
            }
            return true; // continue processing
        }

        @Override
        public void end() {
            // Does nothing
        }

    }

    public static String getPattern(UriInfo info)
	{
		StringBuilder b = new StringBuilder();
		boolean gotData = false;
		for(PathSegment s:info.getPathSegments())
		{
			if ("dat".equals(s.getPath())) 
				{
				gotData = true;
				continue;
				}
			// it's not data here, so check if we got data yet
			if (!gotData) continue;
			// we have something here after data
			if (b.length() > 0) b.append("/");
			b.append(s.getPath());
		}
		return b.toString();
		}
	
}
