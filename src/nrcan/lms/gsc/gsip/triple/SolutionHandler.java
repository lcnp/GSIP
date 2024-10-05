package nrcan.lms.gsc.gsip.triple;

import org.apache.jena.query.QuerySolution;

public interface SolutionHandler
{

    public boolean init();
    public boolean read(QuerySolution qs);
    public void end();
}
