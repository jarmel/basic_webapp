package com.jarmel.basic.webapp.entity.security;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_access_log")
public class UserAccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private Integer user_id;

    @Column(name = "remote_ip_address")
    private String remoteIpAddress;

    @Column(name = "login_access_dt_tm")
    private Date loginAccessDate;

    public UserAccessLog(Integer user_id, String remoteIpAddress) {
        this.user_id = user_id;
        this.remoteIpAddress = remoteIpAddress;
        this.setLoginAccessDate(new Date());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getRemoteIpAddress() {
        return remoteIpAddress;
    }

    public void setRemoteIpAddress(String remoteIpAddress) {
        this.remoteIpAddress = remoteIpAddress;
    }

    public Date getLoginAccessDate() {
        return loginAccessDate;
    }

    public void setLoginAccessDate(Date loginAccessDate) {
        this.loginAccessDate = loginAccessDate;
    }

    @Override
    public String toString() {
        return "UserAccessLog{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", remoteIpAddress='" + remoteIpAddress + '\'' +
                ", loginAccessDate=" + loginAccessDate +
                '}';
    }
}
