package com.example.turibuildtest0405.activity.adapter;

import androidx.annotation.Nullable;

import com.example.turibuildtest0405.dto.kakaomap.MapSearchKeywordResult;
import com.example.turibuildtest0405.dto.post.PostSearchDto;

public interface DetailClickCallbackListener {
    void callback(MapSearchKeywordResult.Place place);

}
