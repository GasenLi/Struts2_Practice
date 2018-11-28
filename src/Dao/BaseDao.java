package Dao;

import Entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class BaseDao {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public BaseDao(){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    public void updateObj(Object o){
        session.update(o);

        over();
    }

    public void insertObj(Object o){
        session.save(o);

        over();
    }

    public Object searchObj(Object o, String id) throws IllegalAccessException, InstantiationException {
        o = session.get(o.getClass(), id);

        over();
        return o;
    }

    public void delete(Object o, String id){
        o = session.get(o.getClass(), id);
        session.delete(o);

        over();
    }

    private void over(){
        transaction.commit();

        session.close();
        sessionFactory.close();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Student student = new Student();

        student.setStudentID("0010");
        student.setClassID("116030804");
        student.setStudentName("小黑");
        student.setStudentAge(18);

        BaseDao baseDao = new BaseDao();
        baseDao.delete(student, "0010");
    }

}
