/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hbt.QuanLyGiaoHangBackend.pojo;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "districts")
@NamedQueries({
    @NamedQuery(name = "Districts.findAll", query = "SELECT d FROM Districts d"),
    @NamedQuery(name = "Districts.findById", query = "SELECT d FROM Districts d WHERE d.id = :id"),
    @NamedQuery(name = "Districts.findByDistrictName", query = "SELECT d FROM Districts d WHERE d.districtName = :districtName")})
public class Districts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "district_name")
    private String districtName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fromDistrictId")
    @JsonIgnore
    private Set<Product> productSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "toDistrictId")
    @JsonIgnore
    private Set<Product> productSet1;
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    @ManyToOne
    private Provinces provinceId;

    public Districts() {
    }

    public Districts(Integer id) {
        this.id = id;
    }

    public Districts(Integer id, String districtName) {
        this.id = id;
        this.districtName = districtName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    public Set<Product> getProductSet1() {
        return productSet1;
    }

    public void setProductSet1(Set<Product> productSet1) {
        this.productSet1 = productSet1;
    }

    public Provinces getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Provinces provinceId) {
        this.provinceId = provinceId;
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
        if (!(object instanceof Districts)) {
            return false;
        }
        Districts other = (Districts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hbt.QuanLyGiaoHangBackend.pojo.Districts[ id=" + id + " ]";
    }
    
}
