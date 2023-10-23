package study.datajpa.entity;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본생성자 필수 필요. private  X, proxy 관련때문에
@Entity
@Getter @Setter  // setter는 만들지 말자
@ToString(of ={"id", "username", "age"}) // team 쓰면안됨.. 연관관계필드는 출력 주의
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private int age;

    private String username;


    @ManyToOne  // (fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;  //외래키 보관, 연관관계 주인

    public Member(String username) { // 생성자보다 비즈니스에 맞는 메서드 생성하기
        this.username = username;
    }

    public Member( String username,int age, Team team) {
        this.age = age;
        this.username = username;
        if(team != null){
            changeTeam(team);
        }
    }

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this); // 주의 !! 이거 넣어줘야됨.
    }


}
