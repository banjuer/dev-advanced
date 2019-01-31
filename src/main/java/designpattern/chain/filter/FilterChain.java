package designpattern.chain.filter;

import designpattern.chain.entity.Request;
import designpattern.chain.entity.Response;
import designpattern.chain.web.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 过滤链
 * 聚集所有过滤器, 链接作用
 */
public class FilterChain implements Filter {

    private List<Filter> filters = Collections.synchronizedList(new ArrayList<>());

    private AtomicInteger index = new AtomicInteger(0);

    public FilterChain add(Filter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public void doFilter(Request req, Response res, FilterChain chain) {
        int curIndex = index.get();
        if (curIndex == filters.size()) {
            index.set(0);
            new Server().deal(req, res);
            return;
        }
        Filter curFilter = filters.get(index.get());
        index.incrementAndGet();
        curFilter.doFilter(req, res, chain);
    }

}
