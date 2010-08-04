/* --------------------------------------
 * CREATED ON 2007-11-26 15:48:04
 *
 * MSN ardenemily@msn.com
 * QQ 83058327（太阳里的雪）
 * MOBILE 13590309275
 * BLOG http://www.caojianghua.com
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.modules.orm.hibernate;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Hibernate Dao帮助类
 * 
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
public class HibernateHelper {
    public static void checkWriteOperationAllowed(HibernateTemplate template, Session session) throws InvalidDataAccessApiUsageException {
        if (template.isCheckWriteOperations() && template.getFlushMode() != HibernateTemplate.FLUSH_EAGER &&
                session.getFlushMode().lessThan(FlushMode.COMMIT)) {
            throw new InvalidDataAccessApiUsageException(
                    "Write operations are not allowed in read-only mode (FlushMode.NEVER/MANUAL): " +
                    "Turn your Session into FlushMode.COMMIT/AUTO or remove 'readOnly' marker from transaction definition.");
        }
    }
}