package com.my_first_project.SCM.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.my_first_project.SCM.entities.Contacts;
import com.my_first_project.SCM.entities.User;

public interface ContactService {
    // save contacts
    Contacts save(Contacts contacts);

    // update contacts
    Contacts update(Contacts contacts);

    // get Contacts
    List<Contacts> getAll();

    // contact by id
    Contacts getById(String id);

    // delete contacts
    void delete(String id);

    // search contact
    Page<Contacts> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);

    Page<Contacts> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);

    Page<Contacts> searchByPhone(String phoneNumberKeyword, int size, int page, String sortBy, String order,
            User user);

    // get contacts by userId
    List<Contacts> getByUserId(String userId);

    Page<Contacts> getByUser(User user, int page, int size, String sortField, String sortDirection);

}
