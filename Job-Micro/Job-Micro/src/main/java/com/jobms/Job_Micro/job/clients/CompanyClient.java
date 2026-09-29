package com.jobms.Job_Micro.job.clients;

import com.jobms.Job_Micro.job.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COMPANY-SERVICE", url = "${company-service.url:http://company-service:8091}")
public interface CompanyClient {

    @GetMapping("/companies/{id}")
     Company getCompany(@PathVariable("id") Long id);


}
