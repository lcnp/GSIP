package nrcan.lms.gsc.gsip;

import org.apache.http.HttpStatus;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
        return Response.ok("<html><head><title>GSIP</title><body><h1>I'm here</h1></body></html>").type(MediaType.TEXT_HTML_TYPE).build();
    }

}
