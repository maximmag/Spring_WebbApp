package magerramov.services;

import magerramov.dao.UserDao;
import magerramov.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    @Autowired
    private UserDao usersDao;

    public UserService() {
    }

    public void deleteUsers() {
        usersDao.deleteAll();
    }

    public User findUser(int id) {
        return usersDao.findById(id);
    }

    public boolean UserFromLogin(String login) {
        return usersDao.findUserFromLogin(login);
    }
    public String getPassUser(String login){
        return usersDao.findPassrFromLogin(login);
    }

    public boolean saveUser(User user) {
        return usersDao.save(user);
    }



    public void deleteUser(String login) {
        usersDao.delete(login);
    }

    public void updateUserPass(String login, String pass) {
        usersDao.updatePass(login, pass);
    }

    public void updateUserLogin(String oldLogin, String newLass) {
        usersDao.updateLogin(oldLogin,newLass);
    }

}
