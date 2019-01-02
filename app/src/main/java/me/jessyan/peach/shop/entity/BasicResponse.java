package me.jessyan.peach.shop.entity;

import com.google.gson.annotations.SerializedName;

import me.jessyan.peach.shop.netconfig.Optional;

/**
 * author Created by He on 2017/7/13.
 */

public class BasicResponse<T> {

    @SerializedName("msg")
    private String msg;
    @SerializedName("code")
    private int code;
    @SerializedName("returnModel")
    private T data;

    @Override
    public String toString() {
        return "BasicResponse{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Optional<T> transform() {
        return new Optional<>(getData());
    }
}
