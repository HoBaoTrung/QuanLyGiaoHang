/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hbt.QuanLyGiaoHangBackend.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "dau_gia")
@NamedQueries({
    @NamedQuery(name = "DauGia.findAll", query = "SELECT d FROM DauGia d"),
    @NamedQuery(name = "DauGia.findByProductId", query = "SELECT d FROM DauGia d WHERE d.id.productId = :productId"),
    @NamedQuery(name = "DauGia.findByShipperId", query = "SELECT d FROM DauGia d WHERE d.id.shipperId = :shipperId"),
    @NamedQuery(name = "DauGia.findByPrice", query = "SELECT d FROM DauGia d WHERE d.price = :price"),
    @NamedQuery(name = "DauGia.findBySelected", query = "SELECT d FROM DauGia d WHERE d.selected = :selected")})
public class DauGia implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DauGiaPK id;
    @Basic(optional = false)
    @Column(name = "price")
    private double price;
    @Basic(optional = false)
    @Column(name = "selected")
    private boolean selected;
    @JoinColumn(name = "productId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonIgnore
    private Product product;
    @JoinColumn(name = "shipperId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Shipper shipper;

    public DauGia() {
    }

    public DauGia(DauGiaPK dauGiaPK) {
        this.id = dauGiaPK;
    }

    public DauGia(DauGiaPK dauGiaPK, double price, boolean selected) {
        this.id = dauGiaPK;
        this.price = price;
        this.selected = selected;
    }

    public DauGia(long productId, long shipperId) {
        this.id = new DauGiaPK(productId, shipperId);
    }

    public DauGiaPK getDauGiaPK() {
        return id;
    }

    public void setDauGiaPK(DauGiaPK dauGiaPK) {
        this.id = dauGiaPK;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Shipper getShipper() {
        return shipper;
    }

    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DauGia)) {
            return false;
        }
        DauGia other = (DauGia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hbt.QuanLyGiaoHangBackend.pojo.DauGia[ dauGiaPK=" + id + " ]";
    }
    
}
