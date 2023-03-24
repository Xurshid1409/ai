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
import uz.edu.ai.domain.Offer;
import uz.edu.ai.model.Result;
import uz.edu.ai.model.request.OfferRequest;
import uz.edu.ai.model.response.OfferProjection;
import uz.edu.ai.model.response.OfferResponse;
import uz.edu.ai.repository.OfferRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final EmailService emailService;

    @Transactional
    public Result createOffer(OfferRequest request) {
        try {
            Offer offer = new Offer();
            offer.setPhoneNumber(request.getPhoneNumber());
            offer.setEmail(request.getEmail());
            offer.setText(request.getText());
            offer.setFullName(request.getFullName());
            offerRepository.save(offer);
            return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
        }
    }
    @Transactional
    public Result sendAnswer(Integer offerId, String text) {
        try {
            Offer offer = offerRepository.findById(offerId).get();
            offer.setAnswer(text);
            offer.setStatus("Javob berildi");
            offer.setModifiedDate(LocalDateTime.now());
            offerRepository.save(offer);
            emailService.sendEmail(offer.getEmail(), text);
            return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR.getMessage(), false);
        }
    }

    @Transactional(readOnly = true)
    public Page<OfferResponse> getOffers(int page, int size, String status) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return offerRepository.findAllByStatus(status, pageable).map(OfferResponse::new);
    }

    @Transactional(readOnly = true)
    public Optional<OfferResponse> getOfferById(Integer offerId) {
        Optional<Offer> offer = offerRepository.findById(offerId);
        return Optional.of(new OfferResponse(offer.get()));
    }

    @Transactional(readOnly = true)
    public Map<String, Integer> statisticsByStatus() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Yangi", 0);
        map.put("Javob berildi", 0);
        map.put("Jami", 0);
        List<OfferProjection> offerProjections = offerRepository.countOfferByStatus();
        offerProjections.forEach(offerProjection -> {
            map.put(offerProjection.getStatus(), offerProjection.getCount());
        });
        int sum = map.values().stream().mapToInt(d -> d).sum();
        map.put("Jami", sum);
        return map;
    }


}
