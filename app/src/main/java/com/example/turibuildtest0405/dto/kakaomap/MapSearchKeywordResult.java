package com.example.turibuildtest0405.dto.kakaomap;

import java.io.Serializable;
import java.util.List;

public class MapSearchKeywordResult {
    private PlaceMeta meta;
    private List<Place> documents;

    public PlaceMeta getMeta() {
        return meta;
    }

    public List<Place> getDocuments() {
        return documents;
    }

    public void setMeta(PlaceMeta meta) {
        this.meta = meta;
    }

    public void setDocuments(List<Place> documents) {
        this.documents = documents;
    }

    public static class PlaceMeta {
        private int total_count;
        private int pageable_count;
        private Boolean is_end;
        private RegionInfo same_name;

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public int getPageable_count() {
            return pageable_count;
        }

        public void setPageable_count(int pageable_count) {
            this.pageable_count = pageable_count;
        }

        public Boolean getIs_end() {
            return is_end;
        }

        public void setIs_end(Boolean is_end) {
            this.is_end = is_end;
        }

        public RegionInfo getSame_name() {
            return same_name;
        }

        public void setSame_name(RegionInfo same_name) {
            this.same_name = same_name;
        }
    }

    public static class Place implements Serializable {
        private String id;
        private String place_name;
        private String category_name;
        private String phone;
        private String address_name;
        private String road_address_name;
        private String x;
        private String y;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlace_name() {
            return place_name;
        }

        public void setPlace_name(String place_name) {
            this.place_name = place_name;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress_name() {
            return address_name;
        }

        public void setAddress_name(String address_name) {
            this.address_name = address_name;
        }

        public String getRoad_address_name() {
            return road_address_name;
        }

        public void setRoad_address_name(String road_address_name) {
            this.road_address_name = road_address_name;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }
    }

    public static class RegionInfo {
        private String[] region;
        private String keyword;
        private String selected_region;

        public String[] getRegion() {
            return region;
        }

        public void setRegion(String[] region) {
            this.region = region;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getSelected_region() {
            return selected_region;
        }

        public void setSelected_region(String selected_region) {
            this.selected_region = selected_region;
        }
    }
}





