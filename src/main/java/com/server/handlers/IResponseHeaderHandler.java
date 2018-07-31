package com.server.handlers;

import com.server.RequestParams;
import com.server.ResponseParams;

public interface IResponseHeaderHandler {

    String createLine(RequestParams requestParams, ResponseParams responseParams);
}
