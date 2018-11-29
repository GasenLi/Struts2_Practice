package Dao;

import Entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class BaseDao {
    private static Configuration configuration = new Configuration().configure();
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public BaseDao(){
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    public String updateObj(Object o){
        session.update(o);

        over();
        return "true";
    }

    public String insertObj(Object o){
        session.save(o);

        over();
        return "true";
    }

    public Object searchObjByID(Object o, String id){
        o = session.get(o.getClass(), id);

        over();
        return o;
    }


    public String delete(Object o, String id){
        o = session.get(o.getClass(), id);
        session.delete(o);

        over();
        return "true";
    }

    public List<Object> custom(String hql){
        Query query = session.createQuery(hql);

        List<Object> objectList = query.list();

        over();
        return  objectList;
    }

    private void over(){
        transaction.commit();

        session.close();
        sessionFactory.close();
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
