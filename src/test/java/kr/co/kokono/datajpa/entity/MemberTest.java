package kr.co.kokono.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @Autowired
    EntityManager em;

    @Test
    public void testEntity() throws Exception {
        //given
        Team teamA = new Team("IZONE");
        Team teamB = new Team("HKT48");

        //when
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("nako", 20, teamA);
        Member member2 = new Member("kura", 22, teamA);
        Member member3 = new Member("애긔", 24, teamB);
        Member member4 = new Member("고즤", 25, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        //초기화
        em.flush();
        em.clear();

        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        //then
        members.forEach(m -> {
            System.out.println("## member = " + m);
            System.out.println("## ->> team = " + m.getTeam().getName());
        });
    }
}