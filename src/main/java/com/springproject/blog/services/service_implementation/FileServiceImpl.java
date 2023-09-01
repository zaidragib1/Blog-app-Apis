package com.springproject.blog.services.service_implementation;

import com.springproject.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService{


    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //filename
        String name = file.getOriginalFilename();

        // Fullpath
        String filePath = path + File.separator + name;

        //create folder if not created
        File f = new File(path);

        if(!f.exists()){
            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return name;

    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        String fullpath = path + File.separator + fileName;

        InputStream inputStream = new FileInputStream(fullpath);

        return inputStream;
    }
}
