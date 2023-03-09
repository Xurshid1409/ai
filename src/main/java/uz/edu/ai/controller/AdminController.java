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
import uz.edu.ai.service.DocumentService;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final DocumentService documentService;

    @PostMapping(value = "createDocument", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createDocument(@RequestParam("file") MultipartFile file,
                                            @RequestParam("newsId") Integer newsId) {
        Result offer = documentService.upload(file, newsId);
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
