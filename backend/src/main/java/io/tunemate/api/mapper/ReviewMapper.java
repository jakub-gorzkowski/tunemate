package io.tunemate.api.mapper;

import io.tunemate.api.dto.ReviewDto;
import io.tunemate.api.model.Review;

import static io.tunemate.api.mapper.ReleaseMapper.mapToReleaseDto;
import static io.tunemate.api.mapper.UserMapper.mapToUserDto;

public class ReviewMapper {
    public static ReviewDto mapToReviewDto(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .releaseDto(mapToReleaseDto(review.getRelease()))
                .userDto(mapToUserDto(review.getUser()))
                .build();
    }

    public static Review mapFromReviewDto(ReviewDto reviewDto) {
        return Review.builder()
                .id(reviewDto.getId())
                .content(reviewDto.getContent())
                .build();
    }
}
