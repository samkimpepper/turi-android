package com.example.turibuildtest0405.dto.post;

public class PostSearchDto {
    private Long postId;
    //private String postImageUrl;
    private String content;
    private String postType;
    private String roadAddress;
    private String placeName;
    private float x;
    private float y;

    public Long getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

    public String getPostType() {
        return postType;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public String getPlaceName() {
        return placeName;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
