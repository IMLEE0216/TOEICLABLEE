package toeicLab.toeicLab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toeicLab.toeicLab.domain.RC;
import toeicLab.toeicLab.domain.SPK;

public interface SPKRepository  extends JpaRepository<SPK, Long> {
}
