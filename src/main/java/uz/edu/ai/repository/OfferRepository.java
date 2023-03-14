package uz.edu.ai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.edu.ai.domain.Offer;
import uz.edu.ai.model.response.OfferProjection;
import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Integer> {

    @Query("select o from Offer o where o.status = ?1")
    Page<Offer> findAllByStatus(String status, Pageable pageable);

    @Query(nativeQuery = true, value = " select count(o.id), o.status from offer o group by o.status ")
    List<OfferProjection> countOfferByStatus();
}
