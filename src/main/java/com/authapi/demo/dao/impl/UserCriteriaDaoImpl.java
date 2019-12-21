package com.authapi.demo.dao.impl;

import com.authapi.demo.dao.UserCriteriaDao;
import com.authapi.demo.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userCriteriaDao")
public class UserCriteriaDaoImpl extends AbstractDao<Long, User> implements UserCriteriaDao {

    private String activeField = "active";

    @Override
    public List<User> getUsers() {
        Criteria criteria = createEntityCriteria(true, activeField);
        List<User> users = criteria.list();
         return users;
    }

	public User findByUsername(String username) {
		Criteria criteria = createEntityCriteria(true, activeField);
		criteria.add(Restrictions.eq("username", username));
		return (User) criteria.uniqueResult();
	}
}
