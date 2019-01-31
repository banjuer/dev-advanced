package designpattern.chain.web;

import designpattern.chain.entity.Request;
import designpattern.chain.entity.Response;

/**
 * 处理消息
 */
public class Server {

    public void deal(Request req, Response res) {
        System.out.println("server receive filtered msg:" + req.getMsg());
        res.setMsg("msg deal done");
        System.out.println("server send res msg:" + res.getMsg());
    }

}
