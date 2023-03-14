package uz.edu.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.edu.ai.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
