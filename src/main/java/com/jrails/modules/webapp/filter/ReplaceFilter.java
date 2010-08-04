package com.jrails.modules.webapp.filter;

import com.jrails.modules.webapp.http.CharArrayResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-14 14:39:14
 * <p/>
 * Filter that replaces all occurrences of a given string with a
 * replacement.
 * This is an abstract class: you <I>must</I> override the getTargetString
 * and getReplacementString methods in a subclass.
 * The first of these methods specifies the string in the response
 * that should be replaced. The second of these specifies the string
 * that should replace each occurrence of the target string.
 */
public abstract class ReplaceFilter implements Filter {
    private FilterConfig config;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //CharArrayResponseWrapper responseWrapper = new CharArrayResponseWrapper((HttpServletResponse) response);
        CharArrayResponseWrapper responseWrapper = new CharArrayResponseWrapper((HttpServletResponse) response);
        // Invoke resource, accumulating output in the wrapper.
        chain.doFilter(request, responseWrapper);
        // Turn entire output into one big String.
        String responseString = responseWrapper.toString();
        // In output, replace all occurrences of target string with replacement
        // string.
        responseString = this.doReplace(responseString, request, response);
        // Update the Content-Length header.
        updateHeaders(response, responseString);

        OutputStreamWriter ow = new OutputStreamWriter(response.getOutputStream(),"utf-8");
        ow.write(responseString);
        ow.flush();
        ow.close();
    }

    protected abstract String doReplace(String responseString, ServletRequest request, ServletResponse response);

    /**
     * Store the FilterConfig object in case subclasses want it.
     */
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    protected FilterConfig getFilterConfig() {
        return (config);
    }

    public void destroy() {
    }

    /**
     * The string that needs replacement.
     * Override this method in your subclass.
     */
    public abstract String getTargetString();

    /**
     * The string that replaces the target. Override this method in
     * your  subclass.
     */
    public abstract String getReplacementString();

    /**
     * Updates the response headers. This simple version just sets
     * the Content-Length header, assuming that we are using a
     * character set that uses 1 byte per character.
     * For other character sets, override this method to use
     * different logic or to give up on persistent HTTP connections.
     * In this latter case, have this method set the Connection header
     * to "close".
     */
    public void updateHeaders(ServletResponse response, String responseString) {
        try {
            response.setContentLength(responseString.getBytes("utf-8").length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
