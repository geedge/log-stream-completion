package cn.ac.iie.bean;


/**
 * 公共类
 *
 * @author qidaijie
 */
public class PublicSessionRecordLog {

//TODO 基础属性   40     int22

    private int common_service;
    private int common_direction;
    private long common_recv_time;
    private int common_address_type;
    private int common_policy_id;
    private int common_action;
    private int common_client_port;
    private int common_entrance_id;
    private int common_link_id;
    private int common_encapsulation;
    private int common_server_port;
    private int common_app_id;
    private int common_protocol_id;
    private int common_c2s_pkt_num;
    private int common_s2c_pkt_num;
    private long common_c2s_byte_num;
    private long common_s2c_byte_num;
    private int common_start_time;
    private int common_end_time;
    private int common_con_duration_ms;
    private int common_stream_dir;
    private int common_has_dup_traffic;
    private long common_log_id;

    private String common_schema_type;

    private String common_l4_protocol;
    private String common_user_tags;
    private String common_user_region;
    private String common_client_ip;
    private String common_device_id;
    private String common_isp;
    private String common_sled_ip;
    private String common_client_location;
    private String common_client_asn;
    private String common_subscriber_id;
    private String common_server_ip;
    private String common_server_location;
    private String common_server_asn;
    private String common_app_label;
    private String common_address_list;
    private String common_stream_error;
    private String common_stream_trace_id;

    public int getCommon_service() {
        return common_service;
    }

    public void setCommon_service(int common_service) {
        this.common_service = common_service;
    }

    public int getCommon_direction() {
        return common_direction;
    }

    public void setCommon_direction(int common_direction) {
        this.common_direction = common_direction;
    }

    public long getCommon_recv_time() {
        return common_recv_time;
    }

    public void setCommon_recv_time(long common_recv_time) {
        this.common_recv_time = common_recv_time;
    }

    public int getCommon_address_type() {
        return common_address_type;
    }

    public void setCommon_address_type(int common_address_type) {
        this.common_address_type = common_address_type;
    }

    public int getCommon_policy_id() {
        return common_policy_id;
    }

    public void setCommon_policy_id(int common_policy_id) {
        this.common_policy_id = common_policy_id;
    }

    public int getCommon_action() {
        return common_action;
    }

    public void setCommon_action(int common_action) {
        this.common_action = common_action;
    }

    public int getCommon_client_port() {
        return common_client_port;
    }

    public void setCommon_client_port(int common_client_port) {
        this.common_client_port = common_client_port;
    }

    public int getCommon_entrance_id() {
        return common_entrance_id;
    }

    public void setCommon_entrance_id(int common_entrance_id) {
        this.common_entrance_id = common_entrance_id;
    }

    public int getCommon_link_id() {
        return common_link_id;
    }

    public void setCommon_link_id(int common_link_id) {
        this.common_link_id = common_link_id;
    }

    public int getCommon_encapsulation() {
        return common_encapsulation;
    }

    public void setCommon_encapsulation(int common_encapsulation) {
        this.common_encapsulation = common_encapsulation;
    }

    public int getCommon_server_port() {
        return common_server_port;
    }

    public void setCommon_server_port(int common_server_port) {
        this.common_server_port = common_server_port;
    }

    public int getCommon_app_id() {
        return common_app_id;
    }

    public void setCommon_app_id(int common_app_id) {
        this.common_app_id = common_app_id;
    }

    public int getCommon_protocol_id() {
        return common_protocol_id;
    }

    public void setCommon_protocol_id(int common_protocol_id) {
        this.common_protocol_id = common_protocol_id;
    }

    public int getCommon_c2s_pkt_num() {
        return common_c2s_pkt_num;
    }

    public void setCommon_c2s_pkt_num(int common_c2s_pkt_num) {
        this.common_c2s_pkt_num = common_c2s_pkt_num;
    }

    public int getCommon_s2c_pkt_num() {
        return common_s2c_pkt_num;
    }

    public void setCommon_s2c_pkt_num(int common_s2c_pkt_num) {
        this.common_s2c_pkt_num = common_s2c_pkt_num;
    }

    public long getCommon_c2s_byte_num() {
        return common_c2s_byte_num;
    }

    public void setCommon_c2s_byte_num(long common_c2s_byte_num) {
        this.common_c2s_byte_num = common_c2s_byte_num;
    }

    public long getCommon_s2c_byte_num() {
        return common_s2c_byte_num;
    }

    public void setCommon_s2c_byte_num(long common_s2c_byte_num) {
        this.common_s2c_byte_num = common_s2c_byte_num;
    }

    public int getCommon_start_time() {
        return common_start_time;
    }

    public void setCommon_start_time(int common_start_time) {
        this.common_start_time = common_start_time;
    }

    public int getCommon_end_time() {
        return common_end_time;
    }

    public void setCommon_end_time(int common_end_time) {
        this.common_end_time = common_end_time;
    }

    public int getCommon_con_duration_ms() {
        return common_con_duration_ms;
    }

    public void setCommon_con_duration_ms(int common_con_duration_ms) {
        this.common_con_duration_ms = common_con_duration_ms;
    }

    public int getCommon_stream_dir() {
        return common_stream_dir;
    }

    public void setCommon_stream_dir(int common_stream_dir) {
        this.common_stream_dir = common_stream_dir;
    }

    public int getCommon_has_dup_traffic() {
        return common_has_dup_traffic;
    }

    public void setCommon_has_dup_traffic(int common_has_dup_traffic) {
        this.common_has_dup_traffic = common_has_dup_traffic;
    }

    public String getCommon_schema_type() {
        return common_schema_type;
    }

    public void setCommon_schema_type(String common_schema_type) {
        this.common_schema_type = common_schema_type;
    }

    public long getCommon_log_id() {
        return common_log_id;
    }

    public void setCommon_log_id(long common_log_id) {
        this.common_log_id = common_log_id;
    }

    public String getCommon_l4_protocol() {
        return common_l4_protocol;
    }

    public void setCommon_l4_protocol(String common_l4_protocol) {
        this.common_l4_protocol = common_l4_protocol;
    }

    public String getCommon_user_tags() {
        return common_user_tags;
    }

    public void setCommon_user_tags(String common_user_tags) {
        this.common_user_tags = common_user_tags;
    }

    public String getCommon_user_region() {
        return common_user_region;
    }

    public void setCommon_user_region(String common_user_region) {
        this.common_user_region = common_user_region;
    }

    public String getCommon_client_ip() {
        return common_client_ip;
    }

    public void setCommon_client_ip(String common_client_ip) {
        this.common_client_ip = common_client_ip;
    }

    public String getCommon_device_id() {
        return common_device_id;
    }

    public void setCommon_device_id(String common_device_id) {
        this.common_device_id = common_device_id;
    }

    public String getCommon_isp() {
        return common_isp;
    }

    public void setCommon_isp(String common_isp) {
        this.common_isp = common_isp;
    }

    public String getCommon_sled_ip() {
        return common_sled_ip;
    }

    public void setCommon_sled_ip(String common_sled_ip) {
        this.common_sled_ip = common_sled_ip;
    }

    public String getCommon_client_location() {
        return common_client_location;
    }

    public void setCommon_client_location(String common_client_location) {
        this.common_client_location = common_client_location;
    }

    public String getCommon_client_asn() {
        return common_client_asn;
    }

    public void setCommon_client_asn(String common_client_asn) {
        this.common_client_asn = common_client_asn;
    }

    public String getCommon_subscriber_id() {
        return common_subscriber_id;
    }

    public void setCommon_subscriber_id(String common_subscriber_id) {
        this.common_subscriber_id = common_subscriber_id;
    }

    public String getCommon_server_ip() {
        return common_server_ip;
    }

    public void setCommon_server_ip(String common_server_ip) {
        this.common_server_ip = common_server_ip;
    }

    public String getCommon_server_location() {
        return common_server_location;
    }

    public void setCommon_server_location(String common_server_location) {
        this.common_server_location = common_server_location;
    }

    public String getCommon_server_asn() {
        return common_server_asn;
    }

    public void setCommon_server_asn(String common_server_asn) {
        this.common_server_asn = common_server_asn;
    }

    public String getCommon_app_label() {
        return common_app_label;
    }

    public void setCommon_app_label(String common_app_label) {
        this.common_app_label = common_app_label;
    }

    public String getCommon_address_list() {
        return common_address_list;
    }

    public void setCommon_address_list(String common_address_list) {
        this.common_address_list = common_address_list;
    }

    public String getCommon_stream_error() {
        return common_stream_error;
    }

    public void setCommon_stream_error(String common_stream_error) {
        this.common_stream_error = common_stream_error;
    }

    public String getCommon_stream_trace_id() {
        return common_stream_trace_id;
    }

    public void setCommon_stream_trace_id(String common_stream_trace_id) {
        this.common_stream_trace_id = common_stream_trace_id;
    }
}
