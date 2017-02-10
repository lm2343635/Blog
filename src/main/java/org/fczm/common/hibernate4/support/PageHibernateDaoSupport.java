package org.fczm.common.hibernate4.support;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


public abstract class PageHibernateDaoSupport<T extends Serializable> extends HibernateDaoSupport implements CrudDao<T> {

    @Autowired
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    private Class<T> clazz;

    protected final void setClass(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public final T get(String id) {
        return getHibernateTemplate().get(clazz, id);
    }

    public String save(T entity) {
        String id = (String) getHibernateTemplate().save(entity);
        getHibernateTemplate().flush();
        return id;
    }

    public void update(T entity) {
        getHibernateTemplate().update(entity);
        getHibernateTemplate().flush();
    }

    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
        getHibernateTemplate().flush();
    }

    public void delete(String id) {
        delete(get(id));
    }

    public List<T> findAll() {
        return (List<T>) getHibernateTemplate().find("from " + clazz.getName());
    }

    public List<T> findAll(String orderby, boolean desc) {
        String hql = "from " + clazz.getName() + " order by " + orderby;
        if (desc) {
            hql += " desc";
        }
        return (List<T>) getHibernateTemplate().find(hql);
    }

    /**
     * 使用hql语句进行分页查询
     *
     * @param hql      需要查询的hql语句
     * @param offset   第一条记录索引
     * @param pageSize 每页需要显示的记录数
     * @return 当前页的所有记录
     */
    public List findByPage(final String hql, final int offset, final int pageSize) {
        List list = getHibernateTemplate().execute(new HibernateCallback<List>() {
            public List doInHibernate(Session session) throws HibernateException {
                List result = session.createQuery(hql)
                        .setFirstResult(offset)
                        .setMaxResults(pageSize)
                        .list();
                return result;
            }
        });
        return list;
    }

    /**
     * 使用hql语句进行分页查询
     *
     * @param hql      需要查询的hql语句
     * @param value    如果hql有一个参数需要传入，value就是传入hql语句的参数
     * @param offset   第一条记录索引
     * @param pageSize 每页需要显示的记录数
     * @return 当前页的所有记录
     */
    public List findByPage(final String hql, final Object value, final int offset, final int pageSize) {
        //通过一个HibernateCallback对象来执行查询
        List list = getHibernateTemplate().execute(new HibernateCallback<List>() {
            //实现HibernateCallback接口必须实现的方法
            public List doInHibernate(Session session) throws HibernateException {
                //执行Hibernate分页查询
                List result = session.createQuery(hql)
                        //为hql语句传入参数
                        .setParameter(0, value)
                        .setFirstResult(offset)
                        .setMaxResults(pageSize)
                        .list();
                return result;
            }
        });
        return list;
    }

    /**
     * 使用hql语句进行分页查询
     *
     * @param hql      需要查询的hql语句
     * @param values   如果hql有多个个参数需要传入，values就是传入hql的参数数组
     * @param offset   第一条记录索引
     * @param pageSize 每页需要显示的记录数
     * @return 当前页的所有记录
     */
    public List findByPage(final String hql, final List<Object> values, final int offset, final int pageSize) {
        //通过一个HibernateCallback对象来执行查询
        List list = getHibernateTemplate().execute(new HibernateCallback<List>() {
            //实现HibernateCallback接口必须实现的方法
            public List doInHibernate(Session session) throws HibernateException {
                //执行Hibernate分页查询
                Query query = session.createQuery(hql);
                //为hql语句传入参数
                for (int i = 0; i < values.size(); i++) {
                    query.setParameter(i, values.get(i));
                }
                List result = query.setFirstResult(offset).setMaxResults(pageSize).list();
                return result;
            }
        });
        return list;
    }

}