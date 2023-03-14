package uz.edu.ai.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.edu.ai.constants.ResponseMessage;
import uz.edu.ai.model.Result;
import uz.edu.ai.model.request.NewsRequest;
import uz.edu.ai.service.DocumentService;
import uz.edu.ai.service.NewsService;
import uz.edu.ai.service.OfferService;
import java.util.List;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final DocumentService documentService;
    private final NewsService newsService;
    private final OfferService offerService;

    @PostMapping("createNews")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> createNews(@RequestBody NewsRequest request) {
        Result result = newsService.createNews(request);
        return ResponseEntity.status(result.getStatus() ? 201 : 400).body(result);
    }

    @PostMapping("updateNews/{newsId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> updateNews(@PathVariable Integer newsId, @RequestBody NewsRequest request) {
        Result result = newsService.updateNews(newsId, request);
        return ResponseEntity.status(result.getStatus() ? 200 : 400).body(result);
    }

    @GetMapping("{newsId}")
    public ResponseEntity<?> getNewsById(@PathVariable Integer newsId) {
        return ResponseEntity.ok(newsService.getNewsById(newsId));
    }

    @GetMapping("news")
    public ResponseEntity<?> getNews(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return ResponseEntity.ok(newsService.getNews(page, size));
    }

    @DeleteMapping("{newsId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> deleteNews(@PathVariable Integer newsId) {
        Result result = newsService.deleteNews(newsId);
        return ResponseEntity.status(result.getStatus() ? 200 : 400).body(result);
    }

    @PostMapping(value = "createDocument", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> createDocument(@RequestParam("files") List<MultipartFile> files,
                                            @RequestParam(value = "newsId", required = false) Integer newsId,
                                            @RequestParam(value = "memberId", required = false) Integer memberId) {
        Result offer = documentService.upload(files, newsId, memberId);
        return ResponseEntity.status(offer.getStatus() ? 200 : 400).body(offer);
    }

    @PatchMapping("sendAnswer")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> sendAnswer(@RequestParam(value = "offerId") Integer offerId,
                                          @RequestParam(value = "answer") String answer) {
        Result result = offerService.sendAnswer(offerId, answer);
        return ResponseEntity.status(result.getStatus() ? 200 : 400).body(result);
    }

    @GetMapping("offers")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> getOffers(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @RequestParam(value = "size", defaultValue = "20") Integer size,
                                       @RequestParam(value = "status") String status) {
        return ResponseEntity.ok(offerService.getOffers(page, size, status));
    }

    @GetMapping("offers/{offerId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> getOfferById(@PathVariable Integer offerId) {
        return ResponseEntity.ok(offerService.getOfferById(offerId));
    }

    @GetMapping("offers/statisticsByStatus")
//    @PreAuthorize("hasRole('ADMIN')")
//    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> statisticsByStatus() {
        return ResponseEntity.ok(offerService.statisticsByStatus());
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
