package com.spyair.blog.util;

import javax.servlet.http.HttpServletRequest;

public class StringUtilx {

    public static String encodeFileName(HttpServletRequest request, String pFileName) throws Exception {
        String filename = null;
        String agent = request.getHeader("USER-AGENT");
        if (null != agent) {
            if (-1 != agent.indexOf("Firefox")) {
                filename = "=?UTF-8?B?" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(pFileName.getBytes("UTF-8")))) + "?=";
            } else if (-1 != agent.indexOf("Chrome")) {
                filename = new String(pFileName.getBytes(), "ISO8859-1");
            } else {
                filename = java.net.URLEncoder.encode(pFileName, "UTF-8");
                filename = filename.replace("+", "%20");
            }
        } else {
            filename = pFileName;
        }
        return filename;
    }
}
