package swagasoft.spring.security.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import swagasoft.spring.security.spring.security.model.Tutorial;

import java.util.List;


@Service
public interface TutorialRepository extends JpaRepository <Tutorial, Long>{
    List<Tutorial> findByPublished(boolean published);
    List<Tutorial> findTitleContainingString(String title);
}
