package com.jrails.modules.orm.hibernate;

import com.jrails.modules.orm.hibernate.GenericEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.hibernate.SessionFactory;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-23 12:18:26
 */
@Service("entityManager")
public class DefaultEntityManager extends GenericEntityManager {
    @Override
    @Autowired
    public void setSessionFactory(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}