package com.spyair.blog.po;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "t_sign")
public class Sign {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    private String dqrq;

    @Column(nullable = false)
    private String yxbz;

    public String getDqrq() {
        return dqrq;
    }

    public void setDqrq(String dqrq) {
        this.dqrq = dqrq;
    }

    @ManyToOne
    private User user;

    public String getYxbz() {
        return yxbz;
    }

    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Sign{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", dqrq='" + dqrq + '\'' +
                ", yxbz='" + yxbz + '\'' +
                ", user=" + user +
                '}';
    }
}
