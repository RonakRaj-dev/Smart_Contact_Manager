package com.my_first_project.SCM.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.my_first_project.SCM.entities.Contacts;
import com.my_first_project.SCM.entities.User;
import com.my_first_project.SCM.forms.ContactForm;
import com.my_first_project.SCM.forms.ContactSearchForm;
import com.my_first_project.SCM.helper.AppConstants;
import com.my_first_project.SCM.helper.Helper;
import com.my_first_project.SCM.helper.Message;
import com.my_first_project.SCM.helper.MessageType;
import com.my_first_project.SCM.services.ContactService;
import com.my_first_project.SCM.services.ImageSevice;
import com.my_first_project.SCM.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageSevice imageSevice;

    @RequestMapping("/add")
    // add contact page
    public String addContactView(Model model) {

        ContactForm contactForm = new ContactForm();

        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    @PostMapping("/add")
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession httpSession) {

        // validate the user
        if (result.hasErrors()) {

            httpSession.setAttribute("message",
                    Message.builder()
                            .content("Please correct the following errors ! ")
                            .type(MessageType.red)
                            .build());

            return "user/add_contact";
        }

        // process the form data
        String username = Helper.getEmailOfLoggedInUser(authentication);

        // form -> contact
        User user = userService.getUserByEmail(username);

        // process the contact picture

        Contacts contacts = new Contacts();

        contacts.setName(contactForm.getName());
        contacts.setEmail(contactForm.getEmail());
        contacts.setPhone(contactForm.getPhoneNumber());
        contacts.setAddress(contactForm.getAddress());
        contacts.setDescription(contactForm.getDescription());
        contacts.setUser(user);
        contacts.setLinkedInLink(contactForm.getLinkedInLink());
        contacts.setTwitterLink(contactForm.getTwitterLink());

        contacts.setFavourite(contactForm.isFavourite());

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileURl = imageSevice.uploadImage(contactForm.getContactImage(), filename);
            contacts.setPicture(fileURl);
            contacts.setCloudinaryImagePublicId(filename);
        }

        contactService.save(contacts);
        System.out.println(contactForm);

        // set the contact picture url

        httpSession.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .type(MessageType.green)
                        .build());

        return "redirect:/user/contacts/add";
    }

    // VIEW CONTACTS

    @RequestMapping
    public String viewContacts(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
            Authentication authentication) {

        // load all your user contacts

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        Page<Contacts> pageContact = contactService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/contacts";
    }

    // new search handler

    @RequestMapping("/search")
    public String searchHandler(

            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contacts> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhone(contactSearchForm.getValue(), size, page, sortBy,
                    direction, user);
        }

        logger.info("pageContact {}", pageContact);

        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        logger.info("User: {}", user);
        logger.info("PageContact: {}", pageContact);
        logger.info("Field: {}, Value: {}", contactSearchForm.getField(), contactSearchForm.getValue());

        return "user/search";
    }

    // delete contact

    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") String contactId, HttpSession httpSession) {
        contactService.delete(contactId);
        logger.info("Contact with id {} deleted", contactId);
        httpSession.setAttribute("message", Message.builder()
                .content("Contact deleted successfully")
                .type(MessageType.green)
                .build());
        return "redirect:/user/contacts";
    }

    // update contact view

    @GetMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable("contactId") String contactId, Model model) {

        var contact = contactService.getById(contactId);

        ContactForm contactForm = new ContactForm();

        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhone());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setTwitterLink(contact.getTwitterLink());
        contactForm.setFavourite(contact.isFavourite());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";

    }

    @PostMapping("/update/{contactId}")
    public String updateContact(@PathVariable("contactId") String contactId,
            @Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult, HttpSession httpSession,
            Model model) {
        // update contact

        if (bindingResult.hasErrors()) {
            httpSession.setAttribute("message", Message.builder()
                    .content("Please correct the following errors !")
                    .type(MessageType.red)
                    .build());
            return "user/update_contact_view";
        }

        var contact = contactService.getById(contactId);
        contact.setId(contactId);
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhone(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setTwitterLink(contactForm.getTwitterLink());
        contact.setFavourite(contactForm.isFavourite());

        // process image:

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String imageUrl = imageSevice.uploadImage(contactForm.getContactImage(), filename);
            contact.setCloudinaryImagePublicId(filename);
            contact.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);
        } else {
            logger.info("No image uploaded");
        }

        var updatedContact = contactService.update(contact);
        logger.info("Updated contact: {}", updatedContact);
        model.addAttribute("message", Message.builder()
                .content("Contact updated successfully")
                .type(MessageType.blue)
                .build());

        return "redirect:/user/contacts";
    }
}
