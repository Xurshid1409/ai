package uz.edu.ai.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public void init();
    public String upload(MultipartFile file);
    public Resource download(String filename);
    public void delete(String filename);
}
