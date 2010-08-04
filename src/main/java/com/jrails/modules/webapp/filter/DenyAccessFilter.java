package com.jrails.modules.webapp.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-14 14:58:28
 *
 * Deny access if the request comes from a banned site or is referred here by a banned site.
 */
public class DenyAccessFilter implements Filter {
    private HashSet<String> bannedSiteTable;

    /**
     * Deny access if the request comes from a banned site or is referred here
     * by a banned site.
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {
        System.out.println("Within BannedAccessFilter:Filtering the Request...");
        HttpServletRequest req = (HttpServletRequest) request;
        String requestingHost = req.getRemoteHost();
        String referringHost = getReferringHost(req.getHeader("Referer"));
        String bannedSite = null;
        boolean isBanned = false;
        if (bannedSiteTable.contains(requestingHost)) {
            bannedSite = requestingHost;
            isBanned = true;
        } else if (bannedSiteTable.contains(referringHost)) {
            bannedSite = referringHost;
            isBanned = true;
        }
        if (isBanned) {
            showWarning(response, bannedSite);
        } else {
            chain.doFilter(request, response);
        }
        System.out.println("Within BannedAccessFilter:Filtering the Response...");
    }

    /**
     * Create a table of banned sites based on initialization parameters.
     * Remember that version 2.3 of the servlet API mandates the use of the
     * Java  2 Platform. Thus, it is safe to use HashSet (which determines
     * whether a given key exists) rather than the clumsier Hashtable
     * (which has a value for each key).
     */

    public void init(FilterConfig config) throws ServletException {
        bannedSiteTable = new HashSet<String>();
        String bannedSites = config.getInitParameter("bannedSites");
        // Default token set: white space.
        StringTokenizer tok = new StringTokenizer(bannedSites);
        while (tok.hasMoreTokens()) {
            String bannedSite = tok.nextToken();
            bannedSiteTable.add(bannedSite);
            System.out.println("Banned " + bannedSite);
        }
    }

    public void destroy() {
    }

    private String getReferringHost(String refererringURLString) {
        try {
            URL referringURL = new URL(refererringURLString);
            return (referringURL.getHost());
        } catch (MalformedURLException mue) { // Malformed or null
            return (null);
        }
    }

    // Replacement response that is returned to users
    // who are from or referred here by a banned site.
    private void showWarning(ServletResponse response, String bannedSite)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 "
                + "Transitional//EN\">\n";
        out.println(docType + "<HTML>\n"
                + "<HEAD><TITLE>Access Prohibited</TITLE></HEAD>\n"
                + "<BODY BGCOLOR=\"WHITE\">\n" + "<H1>Access Prohibited</H1>\n"
                + "Sorry, access from or via " + bannedSite + "\n"
                + "is not allowed.\n" + "</BODY></HTML>");
    }
}