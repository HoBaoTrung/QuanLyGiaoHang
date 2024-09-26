/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hbt.QuanLyGiaoHangBackend.pojo;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 *
 * @author ASUS
 */
@Embeddable
public class DauGiaPK implements Serializable {

//    @Basic(optional = false)
//    @Column(name = "product_id")
    private long productId;
//    @Basic(optional = false)
//    @Column(name = "shipper_id")
    private long shipperId;

    public DauGiaPK() {
    }

    public DauGiaPK(long productId, long shipperId) {
        this.productId = productId;
        this.shipperId = shipperId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getShipperId() {
        return shipperId;
    }

    public void setShipperId(long shipperId) {
        this.shipperId = shipperId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) productId;
        hash += (int) shipperId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DauGiaPK)) {
            return false;
        }
        DauGiaPK other = (DauGiaPK) object;
        if (this.productId != other.productId) {
            return false;
        }
        if (this.shipperId != other.shipperId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hbt.QuanLyGiaoHangBackend.pojo.DauGiaPK[ productId=" + productId + ", shipperId=" + shipperId + " ]";
    }
    
}
