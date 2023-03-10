package uz.edu.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.edu.ai.constants.ResponseMessage;
import uz.edu.ai.domain.News;
import uz.edu.ai.model.Result;
import uz.edu.ai.model.request.NewsRequest;
import uz.edu.ai.model.response.DocumentResponse;
import uz.edu.ai.model.response.NewsResponse;
import uz.edu.ai.repository.NewsRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final DocumentService documentService;

    @Transactional
    public Result createNews(NewsRequest request) {
        try {
            News news = new News();
            news.setTitleUZ(request.getTitleUZ());
            news.setTitleRU(request.getTitleRU());
            news.setTitleEN(request.getTitleEN());
            news.setTextUZ(request.getTextUZ());
            news.setTextRU(request.getTextRU());
            news.setTextEN(request.getTextEN());
            newsRepository.save(news);
            return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true, news.getId());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
        }
    }
    @Transactional
    public Result updateNews(Integer newsId, NewsRequest request) {
        try {
            News news = newsRepository.findById(newsId).get();
            news.setTitleUZ(request.getTitleUZ());
            news.setTitleRU(request.getTitleRU());
            news.setTitleEN(request.getTitleEN());
            news.setTextUZ(request.getTextUZ());
            news.setTextRU(request.getTextRU());
            news.setTextEN(request.getTextEN());
            newsRepository.save(news);
            return new Result(ResponseMessage.SUCCESSFULLY_UPDATE.getMessage(), true);
        } catch (Exception e) {
            return new Result(ResponseMessage.ERROR_UPDATE.getMessage(), false);
        }
    }

    @Transactional(readOnly = true)
    public Optional<NewsResponse> getNewsById(Integer newsId) {
        Optional<News> news = newsRepository.findById(newsId);
        List<DocumentResponse> documentResponses = news.get().getDocuments().stream().map(DocumentResponse::new).toList();
        return Optional.of(new NewsResponse(news.get(), documentResponses));
    }

    @Transactional(readOnly = true)
    public Page<NewsResponse> getNews(int page, int size) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return newsRepository.findAll(pageable).map(e ->
                new NewsResponse(e, e.getDocuments().stream().map(DocumentResponse::new).toList()));
    }

    @Transactional
    public Result deleteNews(Integer newsId) {
        News news = newsRepository.findById(newsId).get();
        try {
            news.getDocuments().forEach(d -> {
                documentService.deleteFile(d.getFileName());
            });
            documentService.deleteDocuments(news.getDocuments());
            newsRepository.delete(news);
            return new Result(ResponseMessage.SUCCESSFULLY_DELETED.getMessage(), true);
        } catch (Exception e) {
            return new Result(ResponseMessage.ERROR_DELETED.getMessage(), false);
        }
    }
}
