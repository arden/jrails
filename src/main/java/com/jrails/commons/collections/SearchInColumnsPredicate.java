package com.jrails.commons.collections;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 列表搜索匹配类
 *
 * @author arden
 */
public class SearchInColumnsPredicate implements Predicate {
    private static final Log logger = LogFactory.getLog(SearchInColumnsPredicate.class);

    private String searchIn[];
    private String searchCondition;
    
    public SearchInColumnsPredicate(String searchIn[], String searchCondition) {
        if (searchIn == null) {
            searchIn = new String[0];
        }
        if (StringUtils.isEmpty(searchCondition)) {
            searchCondition = "";
        }
        this.searchIn = searchIn;
        this.searchCondition = searchCondition;
    }

    public boolean evaluate(Object bean) {
        boolean match = false;
        if (this.searchIn != null) {
            for (String property : searchIn) {
                try {
                    Object value = PropertyUtils.getProperty(bean, property);
                    if (value != null) {
                        String valueOf = String.valueOf(value);
                        match = this.doSearch(valueOf, searchCondition);
                        if (match) {
                            break;
                        }
                    }
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(SearchInColumnsPredicate.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(SearchInColumnsPredicate.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(SearchInColumnsPredicate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return match;
    }

  public boolean doSearch(String value, String search) {
    return false;
  }
}