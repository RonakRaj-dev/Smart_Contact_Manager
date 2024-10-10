package com.my_first_project.SCM.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Contacts {

    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String picture;
    @Column(length = 1000)
    private String description;
    private boolean favourite=false;
    
    private String websiteLink;
    // private List<SocialLink> socialLinks = new ArrayList<>()

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "contacts", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLinks> links = new ArrayList<>();
}
