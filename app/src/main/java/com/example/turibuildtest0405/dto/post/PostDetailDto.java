package com.example.turibuildtest0405.dto.post;


import com.example.turibuildtest0405.dto.comment.PostCommentDto;

import java.io.Serializable;
import java.util.List;

public class PostDetailDto implements Serializable {
    private String nickname;
    private String profileImageUrl;
    private String content;
    private String postType;
    private String postImageUrl;
    private int rating;
    List<PostCommentDto> commentList;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<PostCommentDto> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<PostCommentDto> commentList) {
        this.commentList = commentList;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContent() {
        return content;
    }

    public String getPostType() {
        return postType;
    }
}
