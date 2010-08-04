package com.jrails.modules.webapp.filter;

import com.jrails.modules.webapp.http.CharArrayResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-14 14:45:04
 *
 * Filter that compresses output with gzip (assuming that browser supports gzip).
 */
public class CompressionFilter implements Filter {
    private FilterConfig config;

     /**
      * If browser does not support gzip, invoke resource normally. If browser
      * <I>does</I> support gzip, set the Content-Encoding response header and
      * invoke resource with a wrapped response that collects all the output.
      * Extract the output and write it into a gzipped byte array. Finally, write
      * that array to the client's output stream.
      */
     public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (!isGzipSupported(req)) {
            // Invoke resource normally.
            chain.doFilter(req, res);
        } else {
            // Tell browser we are sending it gzipped data.
            res.setHeader("Content-Encoding", "gzip");
            // Invoke resource, accumulating output in the wrapper.
            CharArrayResponseWrapper responseWrapper = new CharArrayResponseWrapper(res);
            chain.doFilter(req, responseWrapper);
            // Get character array representing output.
            char[] responseChars = responseWrapper.toCharArray();
            // Make a writer that compresses data and puts it into a byte array.
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            GZIPOutputStream zipOut = new GZIPOutputStream(byteStream);
            OutputStreamWriter tempOut = new OutputStreamWriter(zipOut);
            // Compress original output and put it into byte array.
            tempOut.write(responseChars);
            // Gzip streams must be explicitly closed.
            tempOut.close();
            // Update the Content-Length header.
            res.setContentLength(byteStream.size());
            // Send compressed result to local.
            OutputStream realOut = res.getOutputStream();
            byteStream.writeTo(realOut);
        }
     }

     /**
      * Store the FilterConfig object in case subclasses want it.
      */
     public void init(FilterConfig config) throws ServletException {
        this.config = config;
     }

     protected FilterConfig getFilterConfig() {
        return (config);
     }

     public void destroy() {}

     private boolean isGzipSupported(HttpServletRequest req) {
        String browserEncodings = req.getHeader("Accept-Encoding");
        return ((browserEncodings != null) && (browserEncodings.indexOf("gzip") != -1));
     }
}
