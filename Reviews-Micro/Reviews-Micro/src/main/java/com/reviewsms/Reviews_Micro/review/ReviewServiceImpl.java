package com.reviewsms.Reviews_Micro.review;

import com.reviewsms.Reviews_Micro.messaging.ReviewMessageProducer;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    // 1. Declare the producer
    private final ReviewMessageProducer reviewMessageProducer;

    // 2. Inject it via the constructor
    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMessageProducer reviewMessageProducer) {
        this.reviewRepository = reviewRepository;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        if (companyId != null && review != null) {
            review.setCompanyId(companyId);
            Review savedReview = reviewRepository.save(review);

            // 3. Trigger the RabbitMQ message after a successful save
            reviewMessageProducer.sendMessage(savedReview);

            return true;
        }
        return false;
    }

    @Override
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null && updatedReview != null) {
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            Review savedReview = reviewRepository.save(review);

            // 4. Trigger the RabbitMQ message after a successful update
            reviewMessageProducer.sendMessage(savedReview);

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            reviewRepository.delete(review);
            return true;
        }
        return false;
    }
}