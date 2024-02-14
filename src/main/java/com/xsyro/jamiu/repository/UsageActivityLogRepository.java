package com.xsyro.jamiu.repository;

import com.xsyro.jamiu.model.UsageActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
interface UsageActivityLogRepository extends JpaRepository<UsageActivityLog, Long> {
}
