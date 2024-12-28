package com.noisy.rrssProject.repository;

import com.noisy.rrssProject.model.entity.Community;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    Optional<Community> findByName(String name);

    @Transactional
    void deleteByName(String name);

}
