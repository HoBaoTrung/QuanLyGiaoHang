/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hbt.QuanLyGiaoHangBackend.pojo;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "provinces")
@NamedQueries({
    @NamedQuery(name = "Provinces.findAll", query = "SELECT p FROM Provinces p"),
    @NamedQuery(name = "Provinces.findById", query = "SELECT p FROM Provinces p WHERE p.id = :id"),
    @NamedQuery(name = "Provinces.findByProvinceName", query = "SELECT p FROM Provinces p WHERE p.provinceName = :provinceName")})
public class Provinces implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "province_name")
    private String provinceName;
    @OneToMany(mappedBy = "provinceId")
    @JsonIgnore
    private Set<Districts> districtsSet;

    public Provinces() {
    }

    public Provinces(Integer id) {
        this.id = id;
    }

    public Provinces(Integer id, String provinceName) {
        this.id = id;
        this.provinceName = provinceName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Set<Districts> getDistrictsSet() {
        return districtsSet;
    }

    public void setDistrictsSet(Set<Districts> districtsSet) {
        this.districtsSet = districtsSet;
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
        if (!(object instanceof Provinces)) {
            return false;
        }
        Provinces other = (Provinces) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hbt.QuanLyGiaoHangBackend.pojo.Provinces[ id=" + id + " ]";
    }
    
}
