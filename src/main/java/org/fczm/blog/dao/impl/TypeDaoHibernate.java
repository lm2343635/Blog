package org.fczm.blog.dao.impl;

import org.fczm.blog.dao.TypeDao;
import org.fczm.blog.domain.Type;
import org.fczm.common.hibernate4.support.PageHibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class TypeDaoHibernate extends PageHibernateDaoSupport<Type> implements TypeDao {

	public TypeDaoHibernate() {
		super();
		setClass(Type.class);
	}
}
