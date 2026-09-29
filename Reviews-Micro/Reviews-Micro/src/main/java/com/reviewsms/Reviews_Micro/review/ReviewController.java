package com.reviewsms.Reviews_Micro.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // ✅ FIXED: Empty @GetMapping handles GET /reviews?companyId=1
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
        return ResponseEntity.ok(reviewService.getAllReviews(companyId));
    }

    // POST /reviews?companyId=1
    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {
        boolean isAdded = reviewService.addReview(companyId, review);
        if (isAdded) {
            return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
    }

    // GET /reviews/{reviewId}
    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId) {
        Review review = reviewService.getReview(reviewId);
        if (review != null) return ResponseEntity.ok(review);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // PUT /reviews/{reviewId}
    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(
            @PathVariable Long reviewId,
            @RequestBody Review review) {
        boolean isUpdated = reviewService.updateReview(reviewId, review);
        if (isUpdated) return ResponseEntity.ok("Review updated successfully");
        return new ResponseEntity<>("Review not updated", HttpStatus.NOT_FOUND);
    }

    // DELETE /reviews/{reviewId}
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        boolean isDeleted = reviewService.deleteReview(reviewId);
        if (isDeleted) return ResponseEntity.ok("Review deleted successfully");
        return new ResponseEntity<>("Review not deleted", HttpStatus.NOT_FOUND);
    }
}