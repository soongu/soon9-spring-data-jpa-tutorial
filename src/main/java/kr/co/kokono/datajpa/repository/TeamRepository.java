package kr.co.kokono.datajpa.repository;

import kr.co.kokono.datajpa.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
