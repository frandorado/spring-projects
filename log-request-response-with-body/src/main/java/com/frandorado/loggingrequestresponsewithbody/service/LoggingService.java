package com.frandorado.loggingrequestresponsewithbody.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoggingService {
    
    void logRequest(HttpServletRequest httpServletRequest, Object body);
    
    void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body);
}
