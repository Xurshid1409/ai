package uz.edu.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.edu.ai.domain.Document;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

    @Query("select d from Document d where d.news.id = ?1")
    List<Document> findAllByNews_Id(Integer newsId);
}
