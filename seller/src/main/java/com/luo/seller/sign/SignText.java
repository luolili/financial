package com.luo.seller.sign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luo.util.JsonUtil;

/**
 * 签名明文
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public interface SignText {
    default String toText() {
        return JsonUtil.toJson(this);
    }
}
