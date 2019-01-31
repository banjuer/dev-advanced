package designpattern.chain.web;

import designpattern.chain.entity.Request;
import designpattern.chain.entity.Response;
import lombok.AllArgsConstructor;

/**
 * 客户端发送接收消息
 */
@AllArgsConstructor
public class Client {

    String msg;

    public Response send() {
        System.out.println("client send msg:" + msg);
        return new Socket().post(new Request(msg));
    }

}
