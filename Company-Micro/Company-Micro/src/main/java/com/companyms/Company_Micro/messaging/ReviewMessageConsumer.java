package com.companyms.Company_Micro.messaging;

import com.companyms.Company_Micro.company.CompanyService;
import com.companyms.Company_Micro.dto.ReviewMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {

    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage) {
        System.out.println("Received message for company ID: " + reviewMessage.getCompanyId());
        companyService.updateCompanyRating(reviewMessage);
    }
}