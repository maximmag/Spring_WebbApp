package magerramov.dao;


import magerramov.models.User;
import magerramov.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDao {

    public void deleteAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery("DELETE FROM User");
        int result = query.executeUpdate();
        tx1.commit();
        session.close();
    }

    public User findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }

    public boolean save(User user) {

        boolean bool = true;
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery("FROM User");
        List<User> users = query.getResultList();
        for (User u : users) {
            if (u.getLogin().equals(user.getLogin())){
                bool = false;
            }
        }
        if (bool) {
            session.save(user);
        }
        tx1.commit();
        session.close();

        return bool;

    }

    public Boolean findUserFromLogin (String login) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery("FROM User WHERE login = :login");
        query.setParameter("login", login);
        List<User> users = query.getResultList();
        tx1.commit();
        session.close();
        if (users.size() == 0){
            return false;
        }
        return true;
    }

    public String findPassrFromLogin (String login) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery("FROM User WHERE login = :login");
        query.setParameter("login", login);
        List<User> users = query.getResultList();
        tx1.commit();
        session.close();
        return users.get(0).getPass();
    }


    public void updatePass(String login, String pass) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery("UPDATE User SET pass = :pass WHERE login = :login");
        query.setParameter("login", login);
        query.setParameter("pass", pass);
        int result = query.executeUpdate();
        tx1.commit();
        session.close();
    }

    public void updateLogin(String oldLogin, String newLogin) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery("UPDATE User SET login = :newLogin WHERE login = :oldLogin");
        query.setParameter("oldLogin", oldLogin);
        query.setParameter("newLogin", newLogin);
        int result = query.executeUpdate();
        tx1.commit();
        session.close();
    }

    public void delete(String login) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery("DELETE FROM User WHERE login = :login");
        query.setParameter("login", login);
        int result = query.executeUpdate();
        tx1.commit();
        session.close();
    }


}
