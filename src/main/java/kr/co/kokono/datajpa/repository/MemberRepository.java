package kr.co.kokono.datajpa.repository;

import kr.co.kokono.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
