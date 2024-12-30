package com.my_first_project.SCM.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageSevice {

    String uploadImage(MultipartFile contactImage, String filename);

    String getUrlFromPublicId(String publicid);
}
