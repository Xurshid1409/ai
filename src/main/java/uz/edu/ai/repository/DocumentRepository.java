package uz.edu.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.edu.ai.domain.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
}
