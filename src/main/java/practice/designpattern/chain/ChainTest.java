package practice.designpattern.chain;

import practice.designpattern.chain.entity.Response;
import practice.designpattern.chain.web.Client;

public class ChainTest {

    public static void main(String[] args) {

        String orginMsg = "hello xjp!  <script></script>";

        Response res = new Client(orginMsg).send();
        System.out.println(res.getMsg());
    }

}
