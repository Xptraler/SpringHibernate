package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.CacheableResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public void addCar(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }


    @Override
    public User getUser(String model, int series) {
        Car car = (Car) sessionFactory.getCurrentSession().
                createQuery("from Car c where c.model = :model and c.series = :series")
                .setParameter("model", model).setParameter("series", series).setMaxResults(1).uniqueResult();

        User user = (User) sessionFactory.getCurrentSession().createQuery("from User where car = :car")
                .setParameter("car", car).setMaxResults(1).uniqueResult();
        return user;


    }

}



