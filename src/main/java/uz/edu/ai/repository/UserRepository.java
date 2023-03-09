package uz.edu.ai.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.edu.ai.domain.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.phoneNumber = ?1")
    @EntityGraph(attributePaths = "role")
    Optional<User> findUserByPhoneNumber(String phoneNumber);


    @Query("select u from User u where u.phoneNumber = ?1 or u.pinfl = ?2")
    Optional<User> findUserByPhoneNumberOrPinfl(String phoneNumber, String pinfl);

    Optional<User> findUserByPinfl(String pinfl);
}
