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
@Entity
@Table(name = "user_have_voucher")
@NamedQueries({
    @NamedQuery(name = "UserHaveVoucher.findAll", query = "SELECT u FROM UserHaveVoucher u"),
    @NamedQuery(name = "UserHaveVoucher.findByQuantity", query = "SELECT u FROM UserHaveVoucher u WHERE u.quantity = :quantity"),
    @NamedQuery(name = "UserHaveVoucher.findByUserId", query = "SELECT u FROM UserHaveVoucher u WHERE u.userHaveVoucherPK.userId = :userId"),
    @NamedQuery(name = "UserHaveVoucher.findByVoucherId", query = "SELECT u FROM UserHaveVoucher u WHERE u.userHaveVoucherPK.voucherId = :voucherId")})
public class UserHaveVoucher implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserHaveVoucherPK userHaveVoucherPK;
    @Column(name = "quantity")
    private Integer quantity;
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "voucher_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Voucher voucher;

    public UserHaveVoucher() {
    }

    public UserHaveVoucher(UserHaveVoucherPK userHaveVoucherPK) {
        this.userHaveVoucherPK = userHaveVoucherPK;
    }

    public UserHaveVoucher(long userId, long voucherId) {
        this.userHaveVoucherPK = new UserHaveVoucherPK(userId, voucherId);
    }

    public UserHaveVoucherPK getUserHaveVoucherPK() {
        return userHaveVoucherPK;
    }

    public void setUserHaveVoucherPK(UserHaveVoucherPK userHaveVoucherPK) {
        this.userHaveVoucherPK = userHaveVoucherPK;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userHaveVoucherPK != null ? userHaveVoucherPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserHaveVoucher)) {
            return false;
        }
        UserHaveVoucher other = (UserHaveVoucher) object;
        if ((this.userHaveVoucherPK == null && other.userHaveVoucherPK != null) || (this.userHaveVoucherPK != null && !this.userHaveVoucherPK.equals(other.userHaveVoucherPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hbt.QuanLyGiaoHangBackend.pojo.UserHaveVoucher[ userHaveVoucherPK=" + userHaveVoucherPK + " ]";
    }
    
}
