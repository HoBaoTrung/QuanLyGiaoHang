/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hbt.QuanLyGiaoHangBackend.pojo;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "rate")
@NamedQueries({
    @NamedQuery(name = "Rate.findAll", query = "SELECT r FROM Rate r"),
    @NamedQuery(name = "Rate.findByPoint", query = "SELECT r FROM Rate r WHERE r.point = :point"),
    @NamedQuery(name = "Rate.findByUserId", query = "SELECT r FROM Rate r WHERE r.id.userId = :userId"),
    @NamedQuery(name = "Rate.findByShipperId", query = "SELECT r FROM Rate r WHERE r.id.shipperId = :shipperId")})
public class Rate implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RatePK id;
    @Basic(optional = false)
    @Column(name = "point")
    private int point;
    @JoinColumn(name = "shipper_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Shipper shipper;
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Rate() {
    }

    public Rate(RatePK ratePK) {
        this.id = ratePK;
    }

    public Rate(RatePK ratePK, int point) {
        this.id = ratePK;
        this.point = point;
    }

    public Rate(long userId, long shipperId) {
        this.id = new RatePK(userId, shipperId);
    }

    public RatePK getRatePK() {
        return id;
    }

    public void setRatePK(RatePK ratePK) {
        this.id = ratePK;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Shipper getShipper() {
        return shipper;
    }

    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(object instanceof Rate)) {
            return false;
        }
        Rate other = (Rate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hbt.QuanLyGiaoHangBackend.pojo.Rate[ ratePK=" + id + " ]";
    }
    
}
