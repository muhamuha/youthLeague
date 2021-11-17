package com.wzxc.common.utils.camunda;

import javax.servlet.http.HttpServletRequest;

public class RequestPathUtils {

    public String getRequestUrl(HttpServletRequest request){
        String Turl = request.getServerName() + ":" + request.getServerPort();
//        String url = request.getRequestURI().replace("/muhamuha", "");
        if(request.getScheme().equals("https")){
            return "https://" + Turl;
        }
        return "http://" + Turl;
    }
}
