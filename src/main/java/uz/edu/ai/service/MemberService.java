package uz.edu.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.edu.ai.constants.ResponseMessage;
import uz.edu.ai.domain.Member;
import uz.edu.ai.model.Result;
import uz.edu.ai.model.request.MemberRequest;
import uz.edu.ai.model.response.DocumentResponse;
import uz.edu.ai.model.response.MemberResponse;
import uz.edu.ai.repository.MemberRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final DocumentService documentService;

    @Transactional
    public Result createMember(MemberRequest request) {
        try {
            Member member = new Member();
            member.setFullName(request.getFullName());
            member.setWorkPlace(request.getWorkPlace());
            memberRepository.save(member);
            return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true, member.getId());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
        }
    }
    @Transactional
    public Result updateMember(Integer memberId, MemberRequest request) {
        try {
            Member member = memberRepository.findById(memberId).get();
            member.setFullName(request.getFullName());
            member.setWorkPlace(request.getWorkPlace());
            memberRepository.save(member);
            member.getDocuments().forEach(document -> documentService.deleteFile(document.getFileName()));
            documentService.deleteDocuments(member.getDocuments());
            memberRepository.save(member);
            return new Result(ResponseMessage.SUCCESSFULLY_UPDATE.getMessage(), true);
        } catch (Exception e) {
            return new Result(ResponseMessage.ERROR_UPDATE.getMessage(), false);
        }
    }

    @Transactional(readOnly = true)
    public Optional<MemberResponse> getMemberById(Integer memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        List<DocumentResponse> documentResponses = member.get().getDocuments().stream().map(DocumentResponse::new).toList();
        return Optional.of(new MemberResponse(member.get(), documentResponses));
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> getMembers() {
        return memberRepository.findAll().stream().map(e ->
                new MemberResponse(e, e.getDocuments().stream().map(DocumentResponse::new).toList())).toList();
    }

}
