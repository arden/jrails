package com.jrails.modules.webapp.http;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-14 14:35:58
 * A response wrapper that takes everything the client would normally
 * output and saves it in one big character array.
 */
public class CharArrayResponseWrapper extends HttpServletResponseWrapper {
    private CharArrayWriter charWriter;

    /**
     * Initializes wrapper.
     * <p/>
     * First, this constructor calls the parent constructor. That call
     * is crucial so that the response is stored and thus setHeader, *setStatus, addCookie, and so forth work normally.
     * <p/>
     * Second, this constructor creates a CharArrayWriter that will
     * be used to accumulate the response.
     */
    public CharArrayResponseWrapper(HttpServletResponse response) {
        super(response);
        charWriter = new CharArrayWriter();
    }

    /**
     * When servlets or JSP pages ask for the Writer, don't give them
     * the real one. Instead, give them a version that writes into
     * the character array.
     * The filter needs to send the contents of the array to the
     * client (perhaps after modifying it).
     */
    public PrintWriter getWriter() {
        return (new PrintWriter(charWriter));
    }

    /**
     * Get a String representation of the entire buffer.
     * <p/>
     * Be sure <B>not</B> to call this method multiple times on the same
     * wrapper. The API for CharArrayWriter does not guarantee that it
     * "remembers" the previous value, so the call is likely to make
     * a new String every time.
     */
    public String toString() {
        return (charWriter.toString());
    }

    /**
     * Get the underlying character array.
     */
    public char[] toCharArray() {
        return (charWriter.toCharArray());
    }
}
