package org.fczm.blog.dao.impl;

import org.fczm.blog.dao.TypeDao;
import org.fczm.blog.domain.Type;
import org.fczm.common.hibernate3.support.PageHibernateDaoSupport;

public class TypeDaoHibernate extends PageHibernateDaoSupport<Type> implements TypeDao {

	public TypeDaoHibernate() {
		super();
		setClass(Type.class);
	}
}
