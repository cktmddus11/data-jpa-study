package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Team;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Rollback(false)
@Transactional
@SpringBootTest
class TeamJpaRepositoryTest {

    @Autowired
    private TeamJpaRepository teamJpaRepository;

    @Test
    void save() {
        Team team = new Team("팀1");
        teamJpaRepository.save(team);
    }

    @Test
    void delete() {
    }

    @Test
    void find() {
    }

    @Test
    void findAll() {
        Team team = new Team("팀1");
        Team team2 = new Team("팀2");

        teamJpaRepository.save(team);
        teamJpaRepository.save(team2);

        List<Team> findTeams = teamJpaRepository.findAll();
        System.out.println("findTeams = "+findTeams);

        Team findTeam1 = teamJpaRepository.findById(team.getId()).get();
        Team findTeam2 = teamJpaRepository.findById(team2.getId()).get();

        Assertions.assertThat(findTeam1).isEqualTo(team);
        Assertions.assertThat(findTeam2).isEqualTo(team2);

        findTeam1.setName("팀333");
    }

    @Test
    void findById() {
    }

    @Test
    void count() {
    }
}