package com.example.turibuildtest0405.dto.comment;

import java.io.Serializable;

public class PostCommentDto implements Serializable {
    private String profileImageUrl;
    private String nickname;
    private String content;

    public PostCommentDto(String profileImageUrl, String nickname, String content) {
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.content = content;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
