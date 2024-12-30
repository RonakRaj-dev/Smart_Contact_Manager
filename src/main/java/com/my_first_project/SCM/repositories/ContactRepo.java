package com.my_first_project.SCM.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.my_first_project.SCM.entities.Contacts;
import com.my_first_project.SCM.entities.User;

public interface ContactRepo extends JpaRepository<Contacts, String> {
    // find the contacts by user

    // custom finder method
    Page<Contacts> findByUser(User user, Pageable pageable);


    @Query("SELECT c FROM Contacts c WHERE c.user.id = :userId")
    List<Contacts> findByUserId(@Param("userId") String userId);


    Page<Contacts> findByUserAndNameContaining(User user, String nameKeyword, Pageable pageable);
    Page<Contacts> findByUserAndEmailContaining(User user, String emailKeyword, Pageable pageable);
    Page<Contacts> findByUserAndPhoneContaining(User user, String phoneKeyword, Pageable pageable);

}
