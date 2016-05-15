package org.fczm.blog.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fczm.blog.bean.TypeBean;
import org.fczm.blog.domain.Type;
import org.fczm.blog.service.TypeManager;
import org.fczm.blog.service.util.ManagerTemplate;

public class TypeManagerImpl extends ManagerTemplate implements TypeManager {

	@Override
	public String addType(String tname) {
		Type type=new Type();
		type.setTname(tname);
		type.setDate(new Date());
		type.setCount(0);
		return typeDao.save(type);
	}

	@Override
	public List<TypeBean> getAll() {
		List<TypeBean> types=new ArrayList<>();
		for(Type type: typeDao.findAll("date", false)) {
			types.add(new TypeBean(type));
		}
		return types;
	}

	@Override
	public void modifyType(String tid, String tname) {
		Type type=typeDao.get(tid);
		type.setTname(tname);
		typeDao.update(type);
	}

	@Override
	public void removeType(String tid) {
		typeDao.delete(tid);
	}

}
