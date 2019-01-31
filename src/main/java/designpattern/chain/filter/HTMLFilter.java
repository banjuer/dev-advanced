package designpattern.chain.filter;

import designpattern.chain.entity.Request;
import designpattern.chain.entity.Response;

/**
 * 前端代码过滤器
 */
public class HTMLFilter implements Filter {
    @Override
    public void doFilter(Request req, Response res, FilterChain chain) {
        // 处理信息
        req.setMsg(req.getMsg().replace("<", ""));
        System.out.println("req msg after " + this.getClass().getSimpleName() + ":" + req.getMsg());
        // 交棒
        chain.doFilter(req, res, chain);
        // 处理响应
        System.out.println("res msg " + this.getClass().getSimpleName() + " : " + res.getMsg());
    }
}
