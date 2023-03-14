package uz.edu.ai.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.edu.ai.constants.ResponseMessage;
import uz.edu.ai.domain.Document;
import uz.edu.ai.domain.Member;
import uz.edu.ai.domain.News;
import uz.edu.ai.model.Result;
import uz.edu.ai.repository.DocumentRepository;
import uz.edu.ai.repository.MemberRepository;
import uz.edu.ai.repository.NewsRepository;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class DocumentService {

    @Value("${file.storage.location}")
    private Path location;
    private final NewsRepository newsRepository;
    private final MemberRepository memberRepository;
    private final DocumentRepository documentRepository;

    @Transactional
    public Result upload(List<MultipartFile> files, Integer newsId, Integer memberId) {

        News news;
        Member member;
        if (newsId != null) {
            news = newsRepository.findById(newsId).get();
        } else {
            news = null;
        }
        if (memberId != null) {
            member = memberRepository.findById(memberId).get();
        } else {
            member = null;
        }

        List<Document> documents = new ArrayList<>();
        try {
            files.forEach(file -> {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                Integer name = ThreadLocalRandom.current().nextInt(99999999, 1000000000);
                String s = FilenameUtils.getExtension(fileName);
                String fullFileName = name + "." + s;
                try {
                    Files.copy(file.getInputStream(), this.location.resolve(Objects.requireNonNull(fullFileName)), StandardCopyOption.REPLACE_EXISTING);
                    String currentUrl = getCurrentUrl(fullFileName);
                    Document document = new Document();
                    if (news != null) {
                        document.setNews(news);
                    }
                    if (member != null) {
                        document.setNews(news);
                    }
                    document.setFileUrl(currentUrl);
                    document.setFileName(fullFileName);
                    documents.add(document);
                } catch (Exception e) {
                    throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
                }
            });
            documentRepository.saveAll(documents);
            return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR.getMessage(), false);
        }
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

    public void deleteFile(String filename) {
        try {
            File file = new File(location + "/" + filename);
            file.delete();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleted: " + e.getMessage());
        }
    }

    public void deleteDocuments(List<Document> documents) {
        try {
           documentRepository.deleteAll(documents);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleted: " + e.getMessage());
        }
    }

    private String getCurrentUrl(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/admin/download/").path(fileName).toUriString();
    }
}

