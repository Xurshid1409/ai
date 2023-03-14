package uz.edu.ai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.edu.ai.domain.News;
import uz.edu.ai.domain.Offer;

public interface OfferRepository extends JpaRepository<Offer, Integer> {


    @Query("select o from Offer o where o.status = ?1")
    Page<Offer> findAllByStatus(String status, Pageable pageable);
}
