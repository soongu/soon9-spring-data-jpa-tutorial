package kr.co.kokono.datajpa.repository;

import kr.co.kokono.datajpa.dto.MemberDto;
import kr.co.kokono.datajpa.entity.Member;
import kr.co.kokono.datajpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;
    @Autowired
    EntityManager em;

    @Test
    public void testMember() throws Exception {
        //given
        Member member = new Member("kura");
        //when
        Member savedMember = memberRepository.save(member);
        Member foundMember = memberRepository.findById(savedMember.getId()).get();

        //then
        assertThat(foundMember.getName()).isEqualTo(member.getName());
    }

    @Test
    public void basicCRUD() throws Exception {
        //given
        Member member1 = new Member("nako");
        Member member2 = new Member("kura");
        //when
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        List<Member> members = memberRepository.findAll();
        //then
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        assertThat(members.size()).isEqualTo(2);
        assertThat(memberRepository.count()).isEqualTo(2);

        memberRepository.delete(member1);
        assertThat(memberRepository.count()).isEqualTo(1);
    }

    @Test
    public void findNameAndAgeGreaterThanTest() throws Exception {
        //given
        Member member1 = new Member("aaa", 10);
        Member member2 = new Member("bbb", 20);

        //when
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> findMember = memberRepository.findByNameAndAgeGreaterThan("bbb", 15);
        //then
        assertThat(findMember.get(0).getName()).isEqualTo("bbb");
        assertThat(findMember.get(0).getAge()).isEqualTo(20);
    }

    @Test
    public void testQuery() throws Exception {
        //given
        Member member1 = new Member("aaa", 10);
        Member member2 = new Member("bbb", 20);

        //when
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findUser("aaa", 10);

        //then
        assertThat(result.get(0)).isEqualTo(member1);
    }

    @Test
    public void testGetUserNameList() throws Exception {
        //given
        Member member1 = new Member("aaa", 10);
        Member member2 = new Member("bbb", 20);
        Member member3 = new Member("ccc", 30);
        Member member4 = new Member("ddd", 20);

        //when
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        List<String> usernameList = memberRepository.findUsernameList();

        //then
        usernameList.forEach(System.out::println);
    }

    @Test
    public void testMemberDto() throws Exception {
        //given
        Member member1 = new Member("nako");
        Member member2 = new Member("kura");

        Team team = new Team("IZONE");

        teamRepository.save(team);

        member1.setTeam(team);
        member2.setTeam(team);
        //when
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<MemberDto> memberDto = memberRepository.findMemberDto();

        //then
        memberDto.forEach(System.out::println);
    }

    @Test
    public void findByNames() throws Exception {
        //given
        Member member1 = new Member("nako");
        Member member2 = new Member("kura");

        Team team = new Team("IZONE");

        teamRepository.save(team);

        member1.setTeam(team);
        member2.setTeam(team);
        //when
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> names = memberRepository.findByNames(Arrays.asList("nako", "kura"));

        //then
        names.forEach(System.out::println);
    }

    @Test
    public void paging() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));
        memberRepository.save(new Member("member6", 10));
        memberRepository.save(new Member("member7", 10));

        int age = 10;

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "name"));

        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        //변환
        Page<MemberDto> toMap = page.map(m -> new MemberDto(m.getId(), m.getName(), m.getTeam().getName()));
        //then
        List<Member> members = page.getContent();
        long totalCount = page.getTotalElements();



        for (Member member : members) {
            System.out.println("member = " + member);
        }
        System.out.println("totalCount = " + totalCount);

        assertThat(members.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(7);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void bulkUpdate() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        //when
        int resultCount = memberRepository.bulkAgePlus(20);

        //then
        assertThat(resultCount).isEqualTo(3);

    }
    
    @Test
    public void findMemberLazy() throws Exception {
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);
        
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush(); em.clear();

        //when
        List<Member> members = memberRepository.findAll();
//        List<Member> members = memberRepository.findMemberFetchJoin();

        //then
        for (Member member : members) {
            System.out.println("member.getName() = " + member.getName());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }
    }

    @Test
    public void testCustom() throws Exception {
        //given
        List<Member> memberCustom = memberRepository.findMemberCustom();
        //when

        //then
    }

    @Test
    public void JpaEventBaseEntity() throws Exception {
        //given
        Member member = new Member("member1");
        memberRepository.save(member); //@PrePersist
        Thread.sleep(100);
        member.setName("member2");
        em.flush(); //@PreUpdate
        em.clear();
        //when
        Member findMember = memberRepository.findById(member.getId()).get();
        //then
        System.out.println("findMember.createdDate = " +
                findMember.getCreatedDate());
        System.out.println("findMember.updatedDate = " +
                findMember.getLastModifiedDate());
    }

}