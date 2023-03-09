package uz.edu.ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.storage.location}")
    private Path location;

    @Override
    public void init() {
        try {
            Files.createFile(location);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public String upload(MultipartFile file) {
        if (!file.isEmpty()) {
//            String random = RandomStringUtils.random(5, true, true);
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//            String fullFileName = key + "-" + random + fileName;
            try {
                Files.copy(file.getInputStream(), this.location.resolve(Objects.requireNonNull(fileName)), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }
            return fileName;
        }
        return null;
    }

    @Override
    public Resource download(String filename) {
        try {
            Path path = location.resolve(filename);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(String filename) {
        try {
            File file = new File(location + "/" + filename);
            file.delete();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleted: " + e.getMessage());
        }
    }
}
