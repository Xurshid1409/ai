package uz.edu.ai.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.edu.ai.constants.ResponseMessage;
import uz.edu.ai.domain.Document;
import uz.edu.ai.domain.News;
import uz.edu.ai.model.Result;
import uz.edu.ai.repository.DocumentRepository;
import uz.edu.ai.repository.NewsRepository;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class DocumentService {

    @Value("${file.storage.location}")
    private Path location;
    private final NewsRepository newsRepository;
    private final DocumentRepository documentRepository;

    @Transactional
    public Result upload(MultipartFile file, Integer newsId) {

        News news = newsRepository.findById(newsId).get();

        if (!file.isEmpty()) {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                Integer name = ThreadLocalRandom.current().nextInt(99999999, 1000000000);
                String s = FilenameUtils.getExtension(fileName);
                String fullFileName = name + "." + s;
                try {
                    Files.copy(file.getInputStream(), this.location.resolve(Objects.requireNonNull(fullFileName)), StandardCopyOption.REPLACE_EXISTING);
                    String currentUrl = getCurrentUrl(fullFileName);
                    Document document = new Document();
                    document.setNews(news);
                    document.setFileUrl(currentUrl);
                    documentRepository.save(document);
                    return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
                } catch (Exception e) {
                    throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
                }
        }
        return new Result(ResponseMessage.ERROR.getMessage(), false);
    }

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

    private String getCurrentUrl(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/news/download/").path(fileName).toUriString();
    }
}

