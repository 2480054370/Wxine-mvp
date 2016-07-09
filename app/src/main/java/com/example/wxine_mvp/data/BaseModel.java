package com.example.wxine_mvp.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by zhoubing on 15/10/30.
 */
public abstract class BaseModel {

    @JSONField(serialize = false)
    public String getJson() {
        return JSON.toJSONString(this);
    }

}
