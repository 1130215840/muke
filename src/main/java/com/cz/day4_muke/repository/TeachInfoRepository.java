package com.cz.day4_muke.repository;

import com.cz.day4_muke.domain.TeachInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TeachInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeachInfoRepository extends JpaRepository<TeachInfo, Long> {

}
