package com.example.turibuildtest0405.dto.post;

import java.io.Serializable;

public class PostSearchDto implements Serializable {
    private Long postId;
    private String postImageUrl;
    private String content;
    private String postType;
    private String roadAddress;
    private String placeName;
    private double x;
    private double y;
    private int rating;

    private String nickname;
    private String profileImageUrl;

    public String getPostImageUrl() {
        return postImageUrl;
    }

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

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public void setRoadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
