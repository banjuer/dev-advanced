package practice.designpattern.chain.web;

import practice.designpattern.chain.entity.Request;
import practice.designpattern.chain.entity.Response;

/**
 * 建立服务端与客户端连接通道
 */
public class Socket {

    /**
     * 服务端进程
     */
    Tomcat tomcat = new Tomcat();

    public Response post(Request req) {
        Response res = new Response("");
        tomcat.doRequest(req, res);
        return res;
    }

}
