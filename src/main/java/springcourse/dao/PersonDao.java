package springcourse.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import springcourse.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Component
public class PersonDao {

    @PersistenceContext
    private EntityManager entityManager;


    public List<User> index() {
        return entityManager.createQuery("SELECT p FROM User p", User.class).getResultList();
    }

    public User show(int id) {
        TypedQuery<User> query = entityManager.createQuery("SELECT p FROM User p where p.id = : id", User.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findAny().orElse(null);
    }

    public void save(User user) {
        entityManager.persist(user);
    }

    public void update(int id, User updatedPerson) {
        User personToBeUpdated = show(id);

        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());

        entityManager.createQuery("update User p set p.name =: name, p.age =: age, p.email =: email where p.id=:id")
                .setParameter("id", id)
                .setParameter("name", updatedPerson.getName())
                .setParameter("age", updatedPerson.getAge())
                .setParameter("email", updatedPerson.getEmail()).executeUpdate();
    }

    public void delete(int id) {
        entityManager.createQuery("delete from User p where p.id=:id").setParameter("id", id).executeUpdate();
    }
}
