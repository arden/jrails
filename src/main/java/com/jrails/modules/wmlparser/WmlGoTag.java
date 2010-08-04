package com.jrails.modules.wmlparser;

import org.htmlparser.tags.CompositeTag;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-20 16:49:14
 */
public class WmlGoTag extends CompositeTag {
    private static final String[] mIds = new String[]{"GO"};
    private static final String[] mEndTagEnders = new String[]{"ANCHOR"};

    public String[] getIds() {
        return (mIds);
    }

    public String[] getEnders() {
        return (mIds);
    }

    public String[] getEndTagEnders() {
        return (mEndTagEnders);
    }

    public String getLink() {
        return super.getAttribute("href");
    }

    public String getMethod() {
        return super.getAttribute("method");
    }
}
