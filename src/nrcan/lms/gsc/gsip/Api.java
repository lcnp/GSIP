package nrcan.lms.gsc.gsip;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.apache.http.HttpStatus;
import org.apache.jena.query.QuerySolution;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nrcan.lms.gsc.gsip.triple.SolutionHandler;
import nrcan.lms.gsc.gsip.triple.TripleStore;

/**
 * implement various API utilities
 * @author eboisver
 *
 */
@Path("api")
public class Api {
    @Path("health")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getHealth()
    {
        return Response.ok(healthRepost()).type(MediaType.TEXT_HTML_TYPE).build();
    }


    /**
     * Get the ontology (all the owl things) and get a few things for each things
     * @return
     */
    private String healthRepost()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><title>GSIP</title><body>");
        String repo = System.getenv("GSIP_TRIPLESTORE");
        if (repo.startsWith("webapp:"))
            sb.append("<h1> Local "+repo + "</h1>");
            else
            sb.append("<h1>Remote triple store</h1>");
        sb.append("<h2>Ontology</h2>");
        sb.append("<table>");
        serializeSelect("PREFIX owl: <http://www.w3.org/2002/07/owl#> \n SELECT ?resource ?owltype WHERE {?resource a ?owltype. ?owltype a owl:Class. FILTER regex(str(?resource), \"/id/\").} LIMIT 50",sb);
        

        sb.append("</table>");




        sb.append("</body></html>");
        return sb.toString();

    }

    private void serializeSelect(String selectSparql,StringBuilder sb)
    {
        TripleStore j = Manager.getInstance().getTripleStore();
            SelectToHtml sh = new SelectToHtml(sb);
            j.executeSelect(selectSparql, sh);


    }

    public class SelectToHtml implements SolutionHandler 
    {

        private StringBuilder out;
        private List<String> vars = null;
        public SelectToHtml(StringBuilder out)
        {
            this.out = out;
        }
        @Override
        public boolean init() {
            return true;
           
        }

        @Override
        public boolean read(QuerySolution qs) {
            // if we don't have a list of variable yet, just get it from the first solution

        if (vars == null)
        {
            Iterator<String> v = qs.varNames();
            vars = Lists.newArrayList(v);
            // generate a header
            out.append("<tr>");
            vars.forEach(m->out.append("<th>" + m + "</th>"));
            out.append("</tr>");

        }
           out.append("<tr>");
           vars.forEach(m->out.append("<td>" + qs.get(m).toString() + "</td>"));
           out.append("</tr>");
           return true;
           
        }

        @Override
        public void end() {
            
        }
        
    }


}
