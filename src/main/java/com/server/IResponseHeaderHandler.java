package com.server;

public interface IResponseHeaderHandler {

    String createLine(RequestParams requestParams, ResponseParams responseParams);
}
