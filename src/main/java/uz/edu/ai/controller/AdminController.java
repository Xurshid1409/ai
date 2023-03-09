package uz.edu.ai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.edu.ai.constants.ResponseMessage;
import uz.edu.ai.model.Result;
import uz.edu.ai.model.request.NewsRequest;
import uz.edu.ai.service.DocumentService;
import uz.edu.ai.service.NewsService;
import java.util.List;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final DocumentService documentService;
    private final NewsService newsService;

    @PostMapping("createNews")
    public ResponseEntity<?> createNews(@RequestBody NewsRequest request) {
        Result result = newsService.createNews(request);
        return ResponseEntity.status(result.getStatus() ? 201 : 400).body(result);
    }

    @PostMapping("updateNews/{newsId}")
    public ResponseEntity<?> updateNews(@PathVariable Integer newsId, @RequestBody NewsRequest request) {
        Result result = newsService.updateNews(newsId, request);
        return ResponseEntity.status(result.getStatus() ? 200 : 400).body(result);
    }

    @GetMapping("{newsId}")
    public ResponseEntity<?> getNewsById(@PathVariable Integer newsId) {
        return ResponseEntity.ok(newsService.getNewsById(newsId));
    }

    @GetMapping
    public ResponseEntity<?> getNews(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return ResponseEntity.ok(newsService.getNews(page, size));
    }

    @PostMapping(value = "createDocument", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createDocument(@RequestParam("files") List<MultipartFile> files,
                                            @RequestParam("newsId") Integer newsId) {
        Result offer = documentService.upload(files, newsId);
        return ResponseEntity.status(offer.getStatus() ? 200 : 400).body(offer);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadDiploma(@PathVariable String fileName) {
        String contentType;
        try {
            Resource resource = documentService.download(fileName);
            HttpHeaders headers = new HttpHeaders();
            contentType = "application/octet-stream";
            headers.add("content-disposition", "attachment; filename=" + resource.getFilename());
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).
                    headers(headers)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.ok(new Result(ResponseMessage.ERROR.getMessage(), false));
        }
    }
}
