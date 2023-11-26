package ru.lomov.zorika_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.zorika_backend.entities.AppUser;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findUserByEmail(String email);

    @Query("SELECT DISTINCT u FROM AppUser u WHERE u.fullName LIKE %:query% OR u.email LIKE %:query%")
    List<AppUser> searchUser(@Param("query") String query);
}
