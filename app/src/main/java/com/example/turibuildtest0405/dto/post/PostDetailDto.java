package com.example.turibuildtest0405.dto.post;


import com.example.turibuildtest0405.dto.comment.PostCommentDto;

import java.io.Serializable;
import java.util.List;

public class PostDetailDto implements Serializable {
    private String nickname;
    private String content;
    private String postType;
    List<PostCommentDto> commentList;

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
