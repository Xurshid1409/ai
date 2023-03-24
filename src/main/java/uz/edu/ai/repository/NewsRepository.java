package uz.edu.ai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.edu.ai.domain.News;

public interface NewsRepository extends JpaRepository<News, Integer> {

    @Query(nativeQuery = true, value = "select * from news n where n.is_public = true order by n.news_date desc ")
    Page<News> findAllPublicNews(Pageable pageable);

    @Query(nativeQuery = true, value = "select * from news n order by n.news_date desc ")
    Page<News> findAllNews(Pageable pageable);
}
