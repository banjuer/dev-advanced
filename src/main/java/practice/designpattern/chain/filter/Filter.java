package practice.designpattern.chain.filter;

import practice.designpattern.chain.entity.Request;
import practice.designpattern.chain.entity.Response;

/**
 * 过滤器接口
 */
public interface Filter {

    void doFilter(Request req, Response res, FilterChain chain);

}
