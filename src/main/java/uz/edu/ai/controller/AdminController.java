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
import uz.edu.ai.model.request.MemberRequest;
import uz.edu.ai.model.request.NewsRequest;
import uz.edu.ai.service.DocumentService;
import uz.edu.ai.service.MemberService;
import uz.edu.ai.service.NewsService;
import uz.edu.ai.service.OfferService;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final DocumentService documentService;
    private final NewsService newsService;
    private final OfferService offerService;
    private final MemberService memberService;

    @PostMapping("createNews")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> createNews(@RequestBody NewsRequest request) {
        Result result = newsService.createNews(request);
        return ResponseEntity.status(result.getStatus() ? 201 : 400).body(result);
    }

    @PutMapping("updateNews/{newsId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> updateNews(@PathVariable Integer newsId, @RequestBody NewsRequest request) {
        Result result = newsService.updateNews(newsId, request);
        return ResponseEntity.status(result.getStatus() ? 200 : 400).body(result);
    }

    @GetMapping("news/{newsId}")
    public ResponseEntity<?> getNewsById(@PathVariable Integer newsId) {
        return ResponseEntity.ok(newsService.getNewsById(newsId));
    }

    @GetMapping("news")
    public ResponseEntity<?> getNews(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return ResponseEntity.ok(newsService.getNews(page, size));
    }

    @GetMapping("publicNews")
    public ResponseEntity<?> getNewsIsPublic(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                             @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return ResponseEntity.ok(newsService.getNewsPublic(page, size));
    }

    @PatchMapping("setNewsIsPublic/{newsId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> setNewsIsPublic(@PathVariable Integer newsId,
                                             @RequestParam(value = "isPublic") Boolean isPublic) {
        return ResponseEntity.ok(newsService.setNewsIsPublic(newsId, isPublic));
    }

    @DeleteMapping("news/{newsId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> deleteNews(@PathVariable Integer newsId) {
        Result result = newsService.deleteNews(newsId);
        return ResponseEntity.status(result.getStatus() ? 200 : 400).body(result);
    }

    @PostMapping("createMember")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> createMember(@RequestBody MemberRequest request) {
        Result result = memberService.createMember(request);
        return ResponseEntity.status(result.getStatus() ? 201 : 400).body(result);
    }

    @PutMapping("updateMember/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> updateMember(@PathVariable Integer memberId, @RequestBody MemberRequest request) {
        Result result = memberService.updateMember(memberId, request);
        return ResponseEntity.status(result.getStatus() ? 200 : 400).body(result);
    }

    @GetMapping("members")
    public ResponseEntity<?> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }

    @GetMapping("members/{memberId}")
    public ResponseEntity<?> getMemberById(@PathVariable Integer memberId) {
        return ResponseEntity.ok(memberService.getMemberById(memberId));
    }

    @DeleteMapping("members/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable Integer memberId) {
        return ResponseEntity.ok(memberService.deleteMember(memberId));
    }

    @PostMapping(value = "createDocument", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> createDocument(@RequestParam("files") List<MultipartFile> files,
                                            @RequestParam(value = "newsId", required = false) Integer newsId,
                                            @RequestParam(value = "memberId", required = false) Integer memberId) {
        if (newsId != null) {
            Result result = documentService.upload(files, newsId);
            return ResponseEntity.status(result.getStatus() ? 200 : 400).body(result);
        }
        Result result = documentService.uploadPhoto(files, memberId);
        return ResponseEntity.status(result.getStatus() ? 200 : 400).body(result);
    }

    @PostMapping(value = "uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
            Result result = documentService.uploadImage(file);
            return ResponseEntity.status(result.getStatus() ? 200 : 400).body(result);
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

    @DeleteMapping("offers/{offerId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> deleteOffer(@PathVariable Integer offerId) {
        return ResponseEntity.ok(offerService.deleteOffer(offerId));
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
          /*  contentType = "application/octet-stream";
            headers.add("content-disposition", "inline; filename=" + resource.getFilename());
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).
                    headers(headers)
                    .body(resource);*/

            MediaType contType = null;
            headers.add("content-disposition", "inline;filename=" + resource.getFilename());
            if (Objects.requireNonNull(resource.getFilename()).endsWith("pdf")) {
                headers.setContentType(MediaType.parseMediaType("application/pdf"));
                headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
                contType = MediaType.APPLICATION_PDF;
            } else if (Objects.requireNonNull(resource.getFilename()).endsWith("jpg")){
                contType = MediaType.IMAGE_JPEG;

            }else if (Objects.requireNonNull(resource.getFilename()).endsWith("png")){
                contType = MediaType.IMAGE_PNG;

            }else if (Objects.requireNonNull(resource.getFilename()).endsWith("jpeg")){
                contType = MediaType.IMAGE_JPEG;

            }
            return ResponseEntity.ok().contentType(contType).
                    headers(headers)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.ok(new Result(ResponseMessage.ERROR.getMessage(), false));
        }
    }
}
