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

}