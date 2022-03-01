/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.smart.util;

import com.smart.contant.CommonConstants;
import com.smart.contant.enums.SmartExceptionEnum;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author smart
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private Integer code;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private T data;

    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> R<T> ok() {
        return restResult(null, CommonConstants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, CommonConstants.FAIL, null);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, CommonConstants.FAIL, null);
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, CommonConstants.FAIL, msg);
    }

    // 异常信息
    public static <T> R<T> error(SmartExceptionEnum em) {
        return new R<T>(em.getCode(), em.getMsg());
    }

    public static <T> R<T> error(Integer code, String msg) {
        return new R<T>(code, msg);
    }


    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

}
