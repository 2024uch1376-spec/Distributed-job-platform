package com.reviewsms.Reviews_Micro.messaging;

import com.reviewsms.Reviews_Micro.dto.ReviewMessage;
import com.reviewsms.Reviews_Micro.review.Review;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Review review) {
        ReviewMessage reviewMessage = new ReviewMessage();
        reviewMessage.setId(review.getId());
        reviewMessage.setTitle(review.getTitle());
        reviewMessage.setDescription(review.getDescription());
        reviewMessage.setRating(review.getRating());
        reviewMessage.setCompanyId(review.getCompanyId());

        // Send to the queue defined in RabbitMQConfig
        rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);
    }
}