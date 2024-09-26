/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hbt.QuanLyGiaoHangBackend.pojo;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 *
 * @author ASUS
 */
@Embeddable
public class UserHaveVoucherPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "user_id")
    private long userId;
    @Basic(optional = false)
    @Column(name = "voucher_id")
    private long voucherId;

    public UserHaveVoucherPK() {
    }

    public UserHaveVoucherPK(long userId, long voucherId) {
        this.userId = userId;
        this.voucherId = voucherId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) voucherId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserHaveVoucherPK)) {
            return false;
        }
        UserHaveVoucherPK other = (UserHaveVoucherPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.voucherId != other.voucherId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hbt.QuanLyGiaoHangBackend.pojo.UserHaveVoucherPK[ userId=" + userId + ", voucherId=" + voucherId + " ]";
    }
    
}
