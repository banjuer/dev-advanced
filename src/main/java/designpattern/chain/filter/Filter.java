package designpattern.chain.filter;

import designpattern.chain.entity.Request;
import designpattern.chain.entity.Response;

/**
 * 过滤器接口
 */
public interface Filter {

    void doFilter(Request req, Response res, FilterChain chain);

}
