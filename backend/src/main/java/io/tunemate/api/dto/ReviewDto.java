package io.tunemate.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDto {
    private Long id;
    private String content;
    private ReleaseDto releaseDto;
    private UserDto userDto;
}
