package com.jobms.Job_Micro.job;

import com.jobms.Job_Micro.job.clients.CompanyClient;
import com.jobms.Job_Micro.job.clients.ReviewClient;
import com.jobms.Job_Micro.job.dto.JobDTO;
import com.jobms.Job_Micro.job.external.Company;
import com.jobms.Job_Micro.job.external.Review;
import com.jobms.Job_Micro.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;
    int attempt = 0;

    public JobServiceImpl(JobRepository jobRepository,
                          CompanyClient companyClient,
                          ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    // Choose @CircuitBreaker, @Retry, or @RateLimiter depending on what you are testing:
    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    // @Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    // @RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempt: " + ++attempt);
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Fallback must match the exact return type: List<JobDTO>
    public List<JobDTO> companyBreakerFallback(Exception e) {
        System.out.println("Fallback triggered due to: " + e.getMessage());
        List<JobDTO> fallbackList = new ArrayList<>();
        JobDTO dummy = new JobDTO();
        dummy.setId(0L);
        dummy.setTitle("Service Temporarily Unavailable");
        dummy.setDescription("Company or Review service is currently down. Please try again later.");
        fallbackList.add(dummy);
        return fallbackList;
    }

    private JobDTO convertToDto(Job job) {
        Company company = null;
        List<Review> reviews = Collections.emptyList();

        if (job.getCompanyId() != null) {
            // Let Feign exceptions propagate so Resilience4j can track service downtime
            company = companyClient.getCompany(job.getCompanyId());
            reviews = reviewClient.getReviews(job.getCompanyId());
        }

        return JobMapper.mapToJobWithCompanyDTO(job, company, reviews);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return (job != null) ? convertToDto(job) : null;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            if (updatedJob.getCompanyId() != null) {
                job.setCompanyId(updatedJob.getCompanyId());
            }
            jobRepository.save(job);
            return true;
        }
        return false;
    }
}