package com.example.Blog_application.services.impl;

import com.example.Blog_application.exceptions.UnsupportedFileTypeException;
import com.example.Blog_application.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // Get File Name
        String name = file.getOriginalFilename();

        // this thing is not necessary, we are doing this to generate a random name for our file, to prevent multiple files of same name
        String randomID = UUID.randomUUID().toString();
        String substr = name.substring(name.lastIndexOf("."));
        String randomGeneratedFileName = null;
        if(substr.equalsIgnoreCase(".png") || substr.equalsIgnoreCase(".jpg") || substr.equalsIgnoreCase(".jpeg")){
            randomGeneratedFileName = randomID.concat(substr);

            // Make Full Name
            String filePath = path+ File.separator+randomGeneratedFileName; // here file separator simply means "/" so this line states that path/imageName.png like images/abc.png

            // create folder if not created
            File f = new File(path);
            if(!f.exists()){
                f.mkdir();
            }

            // Copy file data from the image(file) to the desired location
            Files.copy(file.getInputStream(), Paths.get(filePath));

            return randomGeneratedFileName;
        }
        else{
            throw new UnsupportedFileTypeException("This file is not supported, only .png, .jpg, .jpeg files are allowed !!");
        }

    }

    @Override
    public InputStream getResource(String path, String imageName) throws FileNotFoundException {
        String fullPath = path+File.separator+imageName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }

}
