package kr.co.kokono.datajpa.repository;

import kr.co.kokono.datajpa.dto.MemberDto;
import kr.co.kokono.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByNameAndAgeGreaterThan(String name, int age);

    @Query("select m from Member m where m.name = :name and m.age = :age")
    List<Member> findUser(String name, int age);

    @Query("select m.name from Member m")
    List<String> findUsernameList();

    @Query("select new kr.co.kokono.datajpa.dto.MemberDto(m.id, m.name, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.name in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) //이게 있어야 executeUpdate가 나감, clear옵션은 영속성컨텍스트를 클리어함
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"}) //fetch join 추가하는 방법
    List<Member> findAll();
}
