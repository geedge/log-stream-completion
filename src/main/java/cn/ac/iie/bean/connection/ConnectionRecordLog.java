package cn.ac.iie.bean.connection;

import cn.ac.iie.bean.PublicSessionRecordLog;

/**
 * 除radius之外
 *
 * @author qidaijie
 */
public class ConnectionRecordLog extends PublicSessionRecordLog {


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

//TODO MAIL协议属性    9

    private String mail_protocol_type;
    private String mail_sender;
    private String mail_receiver;
    private String mail_subject;
    private String mail_content;
    private String mail_attachment_name;
    private String mail_attachment_content;
    private String mail_eml_file;
    private String mail_snapshot;

//TODO 3DNS协议属性    18

    private int dns_message_id;
    private Integer dns_qr;
    private Integer dns_opcode;
    private int dns_aa;
    private int dns_tc;
    private int dns_rd;
    private int dns_ra;
    private int dns_rcode;
    private int dns_qdcount;
    private int dns_ancount;
    private int dns_nscount;
    private int dns_arcount;
    private String dns_qname;
    private int dns_qtype;
    private int dns_qclass;
    private String dns_cname;
    private int dns_sub;
    private String dns_rr;

//TODO SSL协议属性   13


    private Integer ssl_pinningst;
    private Integer ssl_intercept_state;
    private int ssl_server_side_latency;
    private int ssl_client_side_latency;
    private Integer ssl_cert_verify;
    private int ssl_con_latency_ms;
    private String ssl_version;
    private String ssl_sni;
    private String ssl_san;
    private String ssl_cn;
    private String ssl_server_side_version;
    private String ssl_client_side_version;
    private String ssl_error;

    //TODO FTP协议属性  2
    private String ftp_url;
    private String ftp_content;


    //TODO BGP协议属性  3
    private int bgp_type;
    private String bgp_as_num;
    private String bgp_route;

    //TODO VOIP协议属性  4
    private String voip_calling_account;
    private String voip_called_account;
    private String voip_calling_number;
    private String voip_called_number;

    //TODO STREAMING_MEDIA协议属性  2
    private String streaming_media_url;
    private String streaming_media_protocol;


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


    public Integer getSsl_pinningst() {
        return ssl_pinningst;
    }

    public void setSsl_pinningst(Integer ssl_pinningst) {
        this.ssl_pinningst = ssl_pinningst;
    }

    public Integer getSsl_intercept_state() {
        return ssl_intercept_state;
    }

    public void setSsl_intercept_state(Integer ssl_intercept_state) {
        this.ssl_intercept_state = ssl_intercept_state;
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

    public Integer getSsl_cert_verify() {
        return ssl_cert_verify;
    }

    public void setSsl_cert_verify(Integer ssl_cert_verify) {
        this.ssl_cert_verify = ssl_cert_verify;
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

    public String getMail_protocol_type() {
        return mail_protocol_type;
    }

    public void setMail_protocol_type(String mail_protocol_type) {
        this.mail_protocol_type = mail_protocol_type;
    }

    public String getMail_sender() {
        return mail_sender;
    }

    public void setMail_sender(String mail_sender) {
        this.mail_sender = mail_sender;
    }

    public String getMail_receiver() {
        return mail_receiver;
    }

    public void setMail_receiver(String mail_receiver) {
        this.mail_receiver = mail_receiver;
    }

    public String getMail_subject() {
        return mail_subject;
    }

    public void setMail_subject(String mail_subject) {
        this.mail_subject = mail_subject;
    }

    public String getMail_content() {
        return mail_content;
    }

    public void setMail_content(String mail_content) {
        this.mail_content = mail_content;
    }

    public String getMail_attachment_name() {
        return mail_attachment_name;
    }

    public void setMail_attachment_name(String mail_attachment_name) {
        this.mail_attachment_name = mail_attachment_name;
    }

    public String getMail_attachment_content() {
        return mail_attachment_content;
    }

    public void setMail_attachment_content(String mail_attachment_content) {
        this.mail_attachment_content = mail_attachment_content;
    }

    public String getMail_eml_file() {
        return mail_eml_file;
    }

    public void setMail_eml_file(String mail_eml_file) {
        this.mail_eml_file = mail_eml_file;
    }

    public String getMail_snapshot() {
        return mail_snapshot;
    }

    public void setMail_snapshot(String mail_snapshot) {
        this.mail_snapshot = mail_snapshot;
    }

    public int getDns_message_id() {
        return dns_message_id;
    }

    public void setDns_message_id(int dns_message_id) {
        this.dns_message_id = dns_message_id;
    }

    public Integer getDns_qr() {
        return dns_qr;
    }

    public void setDns_qr(Integer dns_qr) {
        this.dns_qr = dns_qr;
    }

    public Integer getDns_opcode() {
        return dns_opcode;
    }

    public void setDns_opcode(Integer dns_opcode) {
        this.dns_opcode = dns_opcode;
    }

    public int getDns_aa() {
        return dns_aa;
    }

    public void setDns_aa(int dns_aa) {
        this.dns_aa = dns_aa;
    }

    public int getDns_tc() {
        return dns_tc;
    }

    public void setDns_tc(int dns_tc) {
        this.dns_tc = dns_tc;
    }

    public int getDns_rd() {
        return dns_rd;
    }

    public void setDns_rd(int dns_rd) {
        this.dns_rd = dns_rd;
    }

    public int getDns_ra() {
        return dns_ra;
    }

    public void setDns_ra(int dns_ra) {
        this.dns_ra = dns_ra;
    }

    public int getDns_rcode() {
        return dns_rcode;
    }

    public void setDns_rcode(int dns_rcode) {
        this.dns_rcode = dns_rcode;
    }

    public int getDns_qdcount() {
        return dns_qdcount;
    }

    public void setDns_qdcount(int dns_qdcount) {
        this.dns_qdcount = dns_qdcount;
    }

    public int getDns_ancount() {
        return dns_ancount;
    }

    public void setDns_ancount(int dns_ancount) {
        this.dns_ancount = dns_ancount;
    }

    public int getDns_nscount() {
        return dns_nscount;
    }

    public void setDns_nscount(int dns_nscount) {
        this.dns_nscount = dns_nscount;
    }

    public int getDns_arcount() {
        return dns_arcount;
    }

    public void setDns_arcount(int dns_arcount) {
        this.dns_arcount = dns_arcount;
    }

    public String getDns_qname() {
        return dns_qname;
    }

    public void setDns_qname(String dns_qname) {
        this.dns_qname = dns_qname;
    }

    public int getDns_qtype() {
        return dns_qtype;
    }

    public void setDns_qtype(int dns_qtype) {
        this.dns_qtype = dns_qtype;
    }

    public int getDns_qclass() {
        return dns_qclass;
    }

    public void setDns_qclass(int dns_qclass) {
        this.dns_qclass = dns_qclass;
    }

    public String getDns_cname() {
        return dns_cname;
    }

    public void setDns_cname(String dns_cname) {
        this.dns_cname = dns_cname;
    }

    public int getDns_sub() {
        return dns_sub;
    }

    public void setDns_sub(int dns_sub) {
        this.dns_sub = dns_sub;
    }

    public String getDns_rr() {
        return dns_rr;
    }

    public void setDns_rr(String dns_rr) {
        this.dns_rr = dns_rr;
    }


    public int getSsl_server_side_latency() {
        return ssl_server_side_latency;
    }

    public void setSsl_server_side_latency(int ssl_server_side_latency) {
        this.ssl_server_side_latency = ssl_server_side_latency;
    }

    public int getSsl_client_side_latency() {
        return ssl_client_side_latency;
    }

    public void setSsl_client_side_latency(int ssl_client_side_latency) {
        this.ssl_client_side_latency = ssl_client_side_latency;
    }

    public int getSsl_con_latency_ms() {
        return ssl_con_latency_ms;
    }

    public void setSsl_con_latency_ms(int ssl_con_latency_ms) {
        this.ssl_con_latency_ms = ssl_con_latency_ms;
    }

    public String getSsl_version() {
        return ssl_version;
    }

    public void setSsl_version(String ssl_version) {
        this.ssl_version = ssl_version;
    }

    public String getSsl_sni() {
        return ssl_sni;
    }

    public void setSsl_sni(String ssl_sni) {
        this.ssl_sni = ssl_sni;
    }

    public String getSsl_san() {
        return ssl_san;
    }

    public void setSsl_san(String ssl_san) {
        this.ssl_san = ssl_san;
    }

    public String getSsl_cn() {
        return ssl_cn;
    }

    public void setSsl_cn(String ssl_cn) {
        this.ssl_cn = ssl_cn;
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

    public String getSsl_error() {
        return ssl_error;
    }

    public void setSsl_error(String ssl_error) {
        this.ssl_error = ssl_error;
    }

    public String getFtp_url() {
        return ftp_url;
    }

    public void setFtp_url(String ftp_url) {
        this.ftp_url = ftp_url;
    }

    public String getFtp_content() {
        return ftp_content;
    }

    public void setFtp_content(String ftp_content) {
        this.ftp_content = ftp_content;
    }

    public int getBgp_type() {
        return bgp_type;
    }

    public void setBgp_type(int bgp_type) {
        this.bgp_type = bgp_type;
    }

    public String getBgp_as_num() {
        return bgp_as_num;
    }

    public void setBgp_as_num(String bgp_as_num) {
        this.bgp_as_num = bgp_as_num;
    }

    public String getBgp_route() {
        return bgp_route;
    }

    public void setBgp_route(String bgp_route) {
        this.bgp_route = bgp_route;
    }

    public String getVoip_calling_account() {
        return voip_calling_account;
    }

    public void setVoip_calling_account(String voip_calling_account) {
        this.voip_calling_account = voip_calling_account;
    }

    public String getVoip_called_account() {
        return voip_called_account;
    }

    public void setVoip_called_account(String voip_called_account) {
        this.voip_called_account = voip_called_account;
    }

    public String getVoip_calling_number() {
        return voip_calling_number;
    }

    public void setVoip_calling_number(String voip_calling_number) {
        this.voip_calling_number = voip_calling_number;
    }

    public String getVoip_called_number() {
        return voip_called_number;
    }

    public void setVoip_called_number(String voip_called_number) {
        this.voip_called_number = voip_called_number;
    }


    public String getStreaming_media_url() {
        return streaming_media_url;
    }

    public void setStreaming_media_url(String streaming_media_url) {
        this.streaming_media_url = streaming_media_url;
    }

    public String getStreaming_media_protocol() {
        return streaming_media_protocol;
    }

    public void setStreaming_media_protocol(String streaming_media_protocol) {
        this.streaming_media_protocol = streaming_media_protocol;
    }
}
