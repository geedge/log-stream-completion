package cn.ac.iie.bean;


/**
 * Radius 日志
 *
 * @author qidaijie
 */
public class RadiusSessionRecordLog extends PublicSessionRecordLog {


//TODO RADIUS协议属性  4

    private int radius_session_timeout;
    private int radius_idle_timeout;
    private int radius_acct_status_type;
    private int radius_acct_terminate_cause;

    public int getRadius_session_timeout() {
        return radius_session_timeout;
    }

    public void setRadius_session_timeout(int radius_session_timeout) {
        this.radius_session_timeout = radius_session_timeout;
    }

    public int getRadius_idle_timeout() {
        return radius_idle_timeout;
    }

    public void setRadius_idle_timeout(int radius_idle_timeout) {
        this.radius_idle_timeout = radius_idle_timeout;
    }

    public int getRadius_acct_status_type() {
        return radius_acct_status_type;
    }

    public void setRadius_acct_status_type(int radius_acct_status_type) {
        this.radius_acct_status_type = radius_acct_status_type;
    }

    public int getRadius_acct_terminate_cause() {
        return radius_acct_terminate_cause;
    }

    public void setRadius_acct_terminate_cause(int radius_acct_terminate_cause) {
        this.radius_acct_terminate_cause = radius_acct_terminate_cause;
    }

    private int radius_packet_type;

    private String radius_nas_ip;
    private String radius_framed_ip;
    private String radius_account;


    public int getRadius_packet_type() {
        return radius_packet_type;
    }

    public void setRadius_packet_type(int radius_packet_type) {
        this.radius_packet_type = radius_packet_type;
    }

    public String getRadius_nas_ip() {
        return radius_nas_ip;
    }

    public void setRadius_nas_ip(String radius_nas_ip) {
        this.radius_nas_ip = radius_nas_ip;
    }

    public String getRadius_framed_ip() {
        return radius_framed_ip;
    }

    public void setRadius_framed_ip(String radius_framed_ip) {
        this.radius_framed_ip = radius_framed_ip;
    }

    public String getRadius_account() {
        return radius_account;
    }

    public void setRadius_account(String radius_account) {
        this.radius_account = radius_account;
    }
}
