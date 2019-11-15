package cn.ac.iie.bean.proxy;


import cn.ac.iie.bean.PublicSessionRecordLog;

/**
 * @author qidaijie
 */
public class ProxySessionRecordLog extends PublicSessionRecordLog {


//TODO HTTP协议属性  21

    private String http_url;
    private String http_host;
    private String http_domain;
    private String http_request_line;
    private String http_response_line;
    private String http_request_header;
    private String http_response_header;
    private String http_request_body;
    private String http_response_body;
    private String http_request_body_key;
    private String http_response_body_key;
    private int http_proxy_flag;
    private int http_sequence;
    private String http_snapshot;
    private String http_cookie;
    private String http_referer;
    private String http_user_agent;
    private String http_content_length;
    private String http_content_type;
    private String http_set_cookie;
    private String http_version;

    public String getHttp_url() {
        return http_url;
    }

    public void setHttp_url(String http_url) {
        this.http_url = http_url;
    }

    public String getHttp_host() {
        return http_host;
    }

    public void setHttp_host(String http_host) {
        this.http_host = http_host;
    }

    public String getHttp_domain() {
        return http_domain;
    }

    public void setHttp_domain(String http_domain) {
        this.http_domain = http_domain;
    }

    public String getHttp_request_line() {
        return http_request_line;
    }

    public void setHttp_request_line(String http_request_line) {
        this.http_request_line = http_request_line;
    }

    public String getHttp_response_line() {
        return http_response_line;
    }

    public void setHttp_response_line(String http_response_line) {
        this.http_response_line = http_response_line;
    }

    public String getHttp_request_header() {
        return http_request_header;
    }

    public void setHttp_request_header(String http_request_header) {
        this.http_request_header = http_request_header;
    }

    public String getHttp_response_header() {
        return http_response_header;
    }

    public void setHttp_response_header(String http_response_header) {
        this.http_response_header = http_response_header;
    }

    public String getHttp_request_body() {
        return http_request_body;
    }

    public void setHttp_request_body(String http_request_body) {
        this.http_request_body = http_request_body;
    }

    public String getHttp_response_body() {
        return http_response_body;
    }

    public void setHttp_response_body(String http_response_body) {
        this.http_response_body = http_response_body;
    }

    public String getHttp_request_body_key() {
        return http_request_body_key;
    }

    public void setHttp_request_body_key(String http_request_body_key) {
        this.http_request_body_key = http_request_body_key;
    }

    public String getHttp_response_body_key() {
        return http_response_body_key;
    }

    public void setHttp_response_body_key(String http_response_body_key) {
        this.http_response_body_key = http_response_body_key;
    }

    public int getHttp_proxy_flag() {
        return http_proxy_flag;
    }

    public void setHttp_proxy_flag(int http_proxy_flag) {
        this.http_proxy_flag = http_proxy_flag;
    }

    public int getHttp_sequence() {
        return http_sequence;
    }

    public void setHttp_sequence(int http_sequence) {
        this.http_sequence = http_sequence;
    }

    public String getHttp_snapshot() {
        return http_snapshot;
    }

    public void setHttp_snapshot(String http_snapshot) {
        this.http_snapshot = http_snapshot;
    }

    public String getHttp_cookie() {
        return http_cookie;
    }

    public void setHttp_cookie(String http_cookie) {
        this.http_cookie = http_cookie;
    }

    public String getHttp_referer() {
        return http_referer;
    }

    public void setHttp_referer(String http_referer) {
        this.http_referer = http_referer;
    }

    public String getHttp_user_agent() {
        return http_user_agent;
    }

    public void setHttp_user_agent(String http_user_agent) {
        this.http_user_agent = http_user_agent;
    }

    public String getHttp_content_length() {
        return http_content_length;
    }

    public void setHttp_content_length(String http_content_length) {
        this.http_content_length = http_content_length;
    }

    public String getHttp_content_type() {
        return http_content_type;
    }

    public void setHttp_content_type(String http_content_type) {
        this.http_content_type = http_content_type;
    }

    public String getHttp_set_cookie() {
        return http_set_cookie;
    }

    public void setHttp_set_cookie(String http_set_cookie) {
        this.http_set_cookie = http_set_cookie;
    }

    public String getHttp_version() {
        return http_version;
    }

    public void setHttp_version(String http_version) {
        this.http_version = http_version;
    }
}
