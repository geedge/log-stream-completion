package cn.ac.iie.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;

/**
 * @author qidaijie
 */
public class SessionRecordLog {
    private long uid;
    private int policy_id;
    private long action;
    private int start_time;
    private int end_time;
    private long recv_time;
    private String trans_proto;
    private String app_proto;
    private int addr_type;
    private String server_ip;
    private String client_ip;
    private int server_port;
    private int client_port;
    private int service;
    private int entrance_id;
    private int device_id;
    private int Link_id;
    private String isp;
    private int encap_type;
    private int direction;
    private int stream_dir;
    private String cap_ip;
    private String addr_list;
    private String server_location;
    private String client_location;
    private String client_asn;
    private String server_asn;
    private String subscribe_id;
    private long con_duration_ms;
    private String url;
    private String host;
    private String domain;
    private String category;
    private String req_line;
    private String res_line;
    private String cookie;
    private String referer;
    private String user_agent;
    private String content_len;
    private String content_type;
    private String set_cookie;
    private String req_header;
    private String resp_header;
    private String req_body_key;
    private String req_body;
    private String res_body_key;
    private String resp_body;
    private String version;
    private String sni;
    private String san;
    private String cn;
    private int app_id;
    private int protocol_id;
    private long con_latency_ms;
    private int pinningst;
    private int intercept_state;
    private long ssl_server_side_latency;
    private long ssl_client_side_latency;
    private String ssl_server_side_version;
    private String ssl_client_side_version;
    private int ssl_cert_verify;
    private String stream_trace_id;
    private String ssl_error;
    private long c2s_pkt_num;
    private long S2c_pkt_num;
    private long c2s_byte_num;
    private long s2c_byte_num;
    private String nas_ip;
    private String framed_ip;
    private String account;
    private int packet_type;

    public SessionRecordLog() {
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(int policy_id) {
        this.policy_id = policy_id;
    }

    public long getAction() {
        return action;
    }

    public void setAction(long action) {
        this.action = action;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public String getSsl_error() {
        return ssl_error;
    }

    public void setSsl_error(String ssl_error) {
        this.ssl_error = ssl_error;
    }

    public String getApp_proto() {
        return app_proto;
    }

    public void setApp_proto(String app_proto) {
        this.app_proto = app_proto;
    }

    public long getRecv_time() {
        return recv_time;
    }

    public void setRecv_time(long recv_time) {
        this.recv_time = recv_time;
    }

    public String getTrans_proto() {
        return trans_proto;
    }

    public void setTrans_proto(String trans_proto) {
        this.trans_proto = trans_proto;
    }

    public int getAddr_type() {
        return addr_type;
    }

    public void setAddr_type(int addr_type) {
        this.addr_type = addr_type;
    }

    public String getServer_ip() {
        return server_ip;
    }

    public void setServer_ip(String server_ip) {
        this.server_ip = server_ip;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public int getServer_port() {
        return server_port;
    }

    public void setServer_port(int server_port) {
        this.server_port = server_port;
    }

    public int getClient_port() {
        return client_port;
    }

    public void setClient_port(int client_port) {
        this.client_port = client_port;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getEntrance_id() {
        return entrance_id;
    }

    public void setEntrance_id(int entrance_id) {
        this.entrance_id = entrance_id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getLink_id() {
        return Link_id;
    }

    public void setLink_id(int link_id) {
        Link_id = link_id;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public int getEncap_type() {
        return encap_type;
    }

    public void setEncap_type(int encap_type) {
        this.encap_type = encap_type;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getStream_dir() {
        return stream_dir;
    }

    public void setStream_dir(int stream_dir) {
        this.stream_dir = stream_dir;
    }

    public String getCap_ip() {
        return cap_ip;
    }

    public void setCap_ip(String cap_ip) {
        this.cap_ip = cap_ip;
    }

    public String getAddr_list() {
        return addr_list;
    }

    public void setAddr_list(String addr_list) {
        this.addr_list = addr_list;
    }

    public String getServer_location() {
        return server_location;
    }

    public void setServer_location(String server_location) {
        this.server_location = server_location;
    }

    public String getClient_location() {
        return client_location;
    }

    public void setClient_location(String client_location) {
        this.client_location = client_location;
    }

    public String getClient_asn() {
        return client_asn;
    }

    public void setClient_asn(String client_asn) {
        this.client_asn = client_asn;
    }

    public String getServer_asn() {
        return server_asn;
    }

    public void setServer_asn(String server_asn) {
        this.server_asn = server_asn;
    }

    public String getSubscribe_id() {
        return subscribe_id;
    }

    public void setSubscribe_id(String subscribe_id) {
        this.subscribe_id = subscribe_id;
    }

    public long getCon_duration_ms() {
        return con_duration_ms;
    }

    public void setCon_duration_ms(long con_duration_ms) {
        this.con_duration_ms = con_duration_ms;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReq_line() {
        return req_line;
    }

    public void setReq_line(String req_line) {
        this.req_line = req_line;
    }

    public String getRes_line() {
        return res_line;
    }

    public void setRes_line(String res_line) {
        this.res_line = res_line;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }

    public String getContent_len() {
        return content_len;
    }

    public void setContent_len(String content_len) {
        this.content_len = content_len;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getSet_cookie() {
        return set_cookie;
    }

    public void setSet_cookie(String set_cookie) {
        this.set_cookie = set_cookie;
    }

    public String getReq_header() {
        return req_header;
    }

    public void setReq_header(String req_header) {
        this.req_header = req_header;
    }

    public String getResp_header() {
        return resp_header;
    }

    public void setResp_header(String resp_header) {
        this.resp_header = resp_header;
    }

    public String getReq_body_key() {
        return req_body_key;
    }

    public void setReq_body_key(String req_body_key) {
        this.req_body_key = req_body_key;
    }

    public String getReq_body() {
        return req_body;
    }

    public void setReq_body(String req_body) {
        this.req_body = req_body;
    }

    public String getRes_body_key() {
        return res_body_key;
    }

    public void setRes_body_key(String res_body_key) {
        this.res_body_key = res_body_key;
    }

    public String getResp_body() {
        return resp_body;
    }

    public void setResp_body(String resp_body) {
        this.resp_body = resp_body;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSni() {
        return sni;
    }

    public void setSni(String sni) {
        this.sni = sni;
    }

    public String getSan() {
        return san;
    }

    public void setSan(String san) {
        this.san = san;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public int getProtocol_id() {
        return protocol_id;
    }

    public void setProtocol_id(int protocol_id) {
        this.protocol_id = protocol_id;
    }

    public int getIntercept_state() {
        return intercept_state;
    }

    public void setIntercept_state(int intercept_state) {
        this.intercept_state = intercept_state;
    }

    public long getSsl_server_side_latency() {
        return ssl_server_side_latency;
    }

    public void setSsl_server_side_latency(long ssl_server_side_latency) {
        this.ssl_server_side_latency = ssl_server_side_latency;
    }

    public long getSsl_client_side_latency() {
        return ssl_client_side_latency;
    }

    public void setSsl_client_side_latency(long ssl_client_side_latency) {
        this.ssl_client_side_latency = ssl_client_side_latency;
    }

    public String getSsl_server_side_version() {
        return ssl_server_side_version;
    }

    public void setSsl_server_side_version(String ssl_server_side_version) {
        this.ssl_server_side_version = ssl_server_side_version;
    }

    public String getSsl_client_side_version() {
        return ssl_client_side_version;
    }

    public void setSsl_client_side_version(String ssl_client_side_version) {
        this.ssl_client_side_version = ssl_client_side_version;
    }

    public int getSsl_cert_verify() {
        return ssl_cert_verify;
    }

    public void setSsl_cert_verify(int ssl_cert_verify) {
        this.ssl_cert_verify = ssl_cert_verify;
    }

    public String getStream_trace_id() {
        return stream_trace_id;
    }

    public void setStream_trace_id(String stream_trace_id) {
        this.stream_trace_id = stream_trace_id;
    }

    public long getCon_latency_ms() {
        return con_latency_ms;
    }

    public void setCon_latency_ms(long con_latency_ms) {
        this.con_latency_ms = con_latency_ms;
    }

    public int getPinningst() {
        return pinningst;
    }

    public void setPinningst(int pinningst) {
        this.pinningst = pinningst;
    }


    public long getC2s_pkt_num() {
        return c2s_pkt_num;
    }

    public void setC2s_pkt_num(long c2s_pkt_num) {
        this.c2s_pkt_num = c2s_pkt_num;
    }

    public long getS2c_pkt_num() {
        return S2c_pkt_num;
    }

    public void setS2c_pkt_num(long s2c_pkt_num) {
        S2c_pkt_num = s2c_pkt_num;
    }

    public long getC2s_byte_num() {
        return c2s_byte_num;
    }

    public void setC2s_byte_num(long c2s_byte_num) {
        this.c2s_byte_num = c2s_byte_num;
    }

    public long getS2c_byte_num() {
        return s2c_byte_num;
    }

    public void setS2c_byte_num(long s2c_byte_num) {
        this.s2c_byte_num = s2c_byte_num;
    }

    public String getNas_ip() {
        return nas_ip;
    }

    public void setNas_ip(String nas_ip) {
        this.nas_ip = nas_ip;
    }

    public String getFramed_ip() {
        return framed_ip;
    }

    public void setFramed_ip(String framed_ip) {
        this.framed_ip = framed_ip;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getPacket_type() {
        return packet_type;
    }

    public void setPacket_type(int packet_type) {
        this.packet_type = packet_type;
    }
}
