package com.hzg.sys;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/4/12.
 */
@Entity(name = "hzg_sys_company")
public class Company implements Serializable {

    private static final long serialVersionUID = 345435245233218L;

    public Company(){
        super();
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id", length = 11)
    private Integer id;

    @Column(name="name",length=30)
    private String name;

    @Column(name="phone",length=16)
    private String phone;

    @Column(name="address",length=60)
    private String address;

    @Column(name="chargerId",length=11)
    private Integer chargerId;

    @Column(name="inputDate")
    private Timestamp inputDate;

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", chargerId=" + chargerId +
                ", inputDate=" + inputDate +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getChargerId() {
        return chargerId;
    }

    public void setChargerId(Integer chargerId) {
        this.chargerId = chargerId;
    }

    public Timestamp getInputDate() {
        return inputDate;
    }

    public void setInputDate(Timestamp inputDate) {
        this.inputDate = inputDate;
    }
}