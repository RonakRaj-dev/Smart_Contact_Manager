package com.my_first_project.SCM.services.implementation;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.my_first_project.SCM.helper.AppConstants;
import com.my_first_project.SCM.services.ImageSevice;

@Service
public class ImageServiceImplementation implements ImageSevice {

    private Cloudinary cloudinary;

    public ImageServiceImplementation(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }
    

    @Override
    public String uploadImage(MultipartFile contactImage, String filename) {

        try {
            byte[] data= new byte[contactImage.getInputStream().available()];

            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id", filename));

            return this.getUrlFromPublicId(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public String getUrlFromPublicId(String publicid) {
        return cloudinary.url().transformation(
            new Transformation<>().width(AppConstants.CONTACT_IMAGE_WIDTH).height(AppConstants.CONTACT_IMAGE_HEIGHT).crop(AppConstants.CONTACT_IMAGE_CROP)
        ).generate(publicid);   
    }



    
}
