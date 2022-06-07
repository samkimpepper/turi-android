package com.example.turibuildtest0405.dto.user;

/*
* Response
* 로그인 후 응답으로 받아서 SharedPreferences에 저장
* (로컬 스토리지 같은 용도)
* */

public class UserInfoDto {
    private String nickname;
    private String email;
    private String profileImageUrl;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
