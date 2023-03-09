package uz.edu.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.edu.ai.domain.News;

public interface NewsRepository extends JpaRepository<News, Integer> {
}
