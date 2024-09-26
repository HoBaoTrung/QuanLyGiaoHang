/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hbt.QuanLyGiaoHangBackend.pojo;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

/**
 *
 * @author ASUS
 */
@Data
@Entity
@Table(name = "shipper")
@NamedQueries({
    @NamedQuery(name = "Shipper.findAll", query = "SELECT s FROM Shipper s"),
    @NamedQuery(name = "Shipper.findById", query = "SELECT s FROM Shipper s WHERE s.id = :id"),
    @NamedQuery(name = "Shipper.findByCmnd", query = "SELECT s FROM Shipper s WHERE s.cmnd = :cmnd"),
    @NamedQuery(name = "Shipper.findByActive", query = "SELECT s FROM Shipper s WHERE s.active = :active")})
public class Shipper implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "CMND")
    private String cmnd;
    @Column(name = "active")
    private Boolean active;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipper")
    @JsonIgnore
    private Set<DauGia> dauGiaSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipper")
    @JsonIgnore
    private Set<Rate> rateSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipperId")
    @JsonIgnore
    private Set<Comment> commentSet;

    public Shipper() {
    }

    public Shipper(Long id) {
        this.id = id;
    }

    public Shipper(Long id, String cmnd) {
        this.id = id;
        this.cmnd = cmnd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<DauGia> getDauGiaSet() {
        return dauGiaSet;
    }

    public void setDauGiaSet(Set<DauGia> dauGiaSet) {
        this.dauGiaSet = dauGiaSet;
    }

    public Set<Rate> getRateSet() {
        return rateSet;
    }

    public void setRateSet(Set<Rate> rateSet) {
        this.rateSet = rateSet;
    }

    public Set<Comment> getCommentSet() {
        return commentSet;
    }

    public void setCommentSet(Set<Comment> commentSet) {
        this.commentSet = commentSet;
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
        if (!(object instanceof Shipper)) {
            return false;
        }
        Shipper other = (Shipper) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hbt.QuanLyGiaoHangBackend.pojo.Shipper[ id=" + id + " ]";
    }
    
}
