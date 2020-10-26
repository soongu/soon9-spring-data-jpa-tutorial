package kr.co.kokono.datajpa.repository;

import kr.co.kokono.datajpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() throws Exception {
        //given
        Member member = new Member("nako");
        //when
        Member savedMember = memberJpaRepository.save(member);
        Member foundMember = memberJpaRepository.find(savedMember.getId());
        //then
        assertThat(foundMember.getName()).isEqualTo(member.getName());
        assertThat(foundMember).isEqualTo(member);
    }




    @Test
    public void updateTest() throws Exception {
        //given
        Member member = new Member("nako");
        //when
        memberJpaRepository.save(member);

        Optional<Member> findMember = memberJpaRepository.findById(member.getId());

        //수정
        findMember.get().setName("나자");

        //then
    }

    @Test
    public void findByNameAndAgeGreaterThanTest() throws Exception {
        //given
        Member member1 = new Member("aaa", 10);
        Member member2 = new Member("bbb", 20);

        //when
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        List<Member> findMember = memberJpaRepository.findByNameAndAgeGreaterThan("bbb", 15);
        //then
        assertThat(findMember.get(0).getName()).isEqualTo("bbb");
        assertThat(findMember.get(0).getAge()).isEqualTo(20);
    }

    @Test
    public void paging() throws Exception {
        //given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));
        memberJpaRepository.save(new Member("member6", 10));
        memberJpaRepository.save(new Member("member7", 10));

        int age = 10;
        int offset = 0;
        int limit = 3;

        //when
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        //then
        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(7);
    }

    @Test
    public void bulkUpdate() throws Exception {
        //given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 19));
        memberJpaRepository.save(new Member("member3", 20));
        memberJpaRepository.save(new Member("member4", 21));
        memberJpaRepository.save(new Member("member5", 40));
        memberJpaRepository.save(new Member("member6", 50));
        memberJpaRepository.save(new Member("member7", 30));

        //when
        int resultCount = memberJpaRepository.bulkAgePlus(20);

        //then
        assertThat(resultCount).isEqualTo(5);

    }

}