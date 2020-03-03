package pl.tarr1.spring_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tarr1.spring_app.model.User;

@Repository         // klasa implementująca zapytania SQL
public interface UserRepository extends JpaRepository<User, Long> {
// public interface UserRepository extends JpaRepository<TYP OBIEKTU ORM, TYP KLUCZA GŁÓWNEGO> {
// ten interfejs pozwala wykonywać polecenia dla tabelki user poprzez wywoływanie metod w Javie

    // SELECT * FROM user WHERE email = ?
    User findFirstByEmail(String email);
}
