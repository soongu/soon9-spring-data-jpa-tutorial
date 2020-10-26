package kr.co.kokono.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"team"})
//@NamedQuery(
//        name = "Member.findByName",
//        query = "select m from Member m where m.name = :name"
//)
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, int age, Team team) {
        this.name = name;
        this.age = age;
        if (team != null) changeTeam(team);
    }

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
