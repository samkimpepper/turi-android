package com.example.turibuildtest0405.dto.post;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PostRequestDto {

    public static class Create {
        private String content;
        private String postType;
        private String roadAddress;
        private String jibunAddress;
        private String placeName;
        private float x;
        private float y;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPostType() {
            return postType;
        }

        public void setPostType(String postType) {
            this.postType = postType;
        }

        public String getRoadAddress() {
            return roadAddress;
        }

        public void setRoadAddress(String roadAddress) {
            this.roadAddress = roadAddress;
        }

        public String getJibunAddress() {
            return jibunAddress;
        }

        public void setJibunAddress(String jibunAddress) {
            this.jibunAddress = jibunAddress;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }

    public static class Search {
        private String keyword;
        private String postType;

        public Search(String keyword, String postType) {
            this.keyword = keyword;
            this.postType = postType;
        }
    }
}
