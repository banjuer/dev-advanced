package designpattern.chain.web;

import designpattern.chain.filter.FilterChain;
import designpattern.chain.filter.HTMLFilter;
import designpattern.chain.entity.Request;
import designpattern.chain.entity.Response;
import designpattern.chain.filter.SensitiveFilter;

public class Tomcat {
    FilterChain chain;
    public Tomcat() {
        init();
    }

    private void init() {
        chain = new FilterChain().add(new HTMLFilter()).add(new SensitiveFilter());
    }

    public void doRequest(Request req, Response res) {
        chain.doFilter(req, res, chain);
    }

}
