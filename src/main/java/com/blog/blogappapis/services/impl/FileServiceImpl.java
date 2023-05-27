package com.blog.blogappapis.services.impl;

import com.blog.blogappapis.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        String name = file.getOriginalFilename();

        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        String filePath = path + File.separator + fileName;

        File f = new File(filePath);
        if (!f.exists()){
            f.getParentFile().mkdirs(); // Créer les dossiers parents si nécessaire
            f.createNewFile(); // Créer le fichier
            f.setReadable(true); // Définir les autorisations pour le fichier créé
            f.setWritable(true);
            Files.copy(file.getInputStream(), Paths.get(filePath));
        } else {
            throw new FileAlreadyExistsException("Le fichier " + fileName + " existe déjà");
        }

        return fileName;
    }


    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }
}
