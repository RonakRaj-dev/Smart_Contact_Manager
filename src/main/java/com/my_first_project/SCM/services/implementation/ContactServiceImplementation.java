package com.my_first_project.SCM.services.implementation;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.my_first_project.SCM.entities.Contacts;
import com.my_first_project.SCM.entities.User;
import com.my_first_project.SCM.helper.ResourceNotFoundException;
import com.my_first_project.SCM.repositories.ContactRepo;
import com.my_first_project.SCM.services.ContactService;

@Service
public class ContactServiceImplementation implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contacts save(Contacts contacts) {
        String contactId = UUID.randomUUID().toString();

        contacts.setId(contactId);
        return contactRepo.save(contacts);
    }

    @Override
    public Contacts update(Contacts contacts) {
        var contactOld = contactRepo.findById(contacts.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found in this id ! " + contacts.getId()));
        contactOld.setName(contacts.getName());
        contactOld.setEmail(contacts.getEmail());
        contactOld.setPhone(contacts.getPhone());
        contactOld.setAddress(contacts.getAddress());
        contactOld.setDescription(contacts.getDescription());
        contactOld.setFavourite(contacts.isFavourite());
        contactOld.setLinkedInLink(contacts.getLinkedInLink());
        contactOld.setTwitterLink(contacts.getTwitterLink());
        contactOld.setCloudinaryImagePublicId(contacts.getCloudinaryImagePublicId());
        contactOld.setPicture(contacts.getPicture());

        return contactRepo.save(contactOld);

    }

    @Override
    public List<Contacts> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contacts getById(String id) {
        return contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found in this id ! " + id));
    }

    @Override
    public void delete(String id) {
        var contact = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found in this id ! " + id));
        contactRepo.delete(contact);
    }

    @Override
    public List<Contacts> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contacts> getByUser(User user, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);

        return contactRepo.findByUser(user, pageable);
    }

    @Override
    public Page<Contacts> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    @Override
    public Page<Contacts> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,
            User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndEmailContaining(user, emailKeyword, pageable);
    }

    @Override
    public Page<Contacts> searchByPhone(String phoneKeyword, int size, int page, String sortBy, String order,
            User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndPhoneContaining(user, phoneKeyword, pageable);
    }

}
