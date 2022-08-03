package com.example.rbac.config;

public class RespResult<T> {
  private int code;
  private String message;
  private T data;

  public RespResult() {}

  public RespResult(int code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public static <T> RespResult<T> success(T data) {
    RespResult<T> resultData = new RespResult<>();
    resultData.setCode(200);
    resultData.setMessage("");
    resultData.setData(data);
    return resultData;
  }

  public int getCode() {
    return this.code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return this.data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
