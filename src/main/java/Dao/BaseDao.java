package Dao;

import Entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BaseDao {
    //private static Configuration configuration = new Configuration().configure();
    @Autowired
    private SessionFactory sessionFactory; //= configuration.buildSessionFactory();
    public Session session;
    private Transaction transaction;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void init(){
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    public Boolean updateObj(Object o){
        session.update(o);

        return true;
    }

    public Boolean insertObj(Object o){
        session.save(o);

        return true;
    }

    public Object searchObjByID(Class c, String id){
        Object o = session.get(c, id);

        return o;
    }

    public Boolean delete(Object o, String id){
        o = session.get(o.getClass(), id);
        session.delete(o);

        return true;
    }

    public List<Object> custom(String hql){
        Query query = session.createQuery(hql);

        List<Object> objectList = query.list();

        return  objectList;
    }

    public void over(){
        try{
            transaction.commit();
        }finally {
            session.close();
        }
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
//        Student student = new Student();
//
//        student.setStudentID("0010");
//        student.setClassID("116030804");
//        student.setStudentName("小黑");
//        student.setStudentAge(18);
//
        BaseDao baseDao = new BaseDao();
//        baseDao.delete(student, "0010");

        String hql = "from Student where ClassID = 116030804 and StudentName like '%李%'  ";
        List<Object> objectList = baseDao.custom(hql);
        for(Object student : objectList){
            System.out.println(student);
        }
    }

}
