package com.xsyro.jamiu.repository;

import com.xsyro.jamiu.model.UsageActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface UsageActivityLogRepository extends JpaRepository<UsageActivityLog, Long> {

    List<UsageActivityLog> findAllByCustomerId(Integer customerId);

    Long countByCustomerId(Long customerId);

    Long countByCustomerIdAndRequestTimeBetween(Long customerId, Date from, Date to);


}
