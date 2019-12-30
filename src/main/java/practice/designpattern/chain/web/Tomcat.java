package practice.designpattern.chain.web;

import practice.designpattern.chain.filter.FilterChain;
import practice.designpattern.chain.filter.HTMLFilter;
import practice.designpattern.chain.entity.Request;
import practice.designpattern.chain.entity.Response;
import practice.designpattern.chain.filter.SensitiveFilter;

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
