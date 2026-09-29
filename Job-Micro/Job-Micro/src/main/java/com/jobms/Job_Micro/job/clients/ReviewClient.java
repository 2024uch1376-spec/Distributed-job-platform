package com.jobms.Job_Micro.job.clients;

import com.jobms.Job_Micro.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEW-SERVICE", url = "http://reviews-service:8093")
public interface ReviewClient {

    @GetMapping("/reviews")
     List<Review> getReviews(@RequestParam("companyId") Long companyId);
}