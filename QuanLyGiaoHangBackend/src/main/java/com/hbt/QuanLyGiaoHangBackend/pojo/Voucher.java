/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hbt.QuanLyGiaoHangBackend.pojo;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "voucher")
@NamedQueries({
    @NamedQuery(name = "Voucher.findAll", query = "SELECT v FROM Voucher v"),
    @NamedQuery(name = "Voucher.findById", query = "SELECT v FROM Voucher v WHERE v.id = :id"),
    @NamedQuery(name = "Voucher.findByName", query = "SELECT v FROM Voucher v WHERE v.name = :name"),
    @NamedQuery(name = "Voucher.findByValue", query = "SELECT v FROM Voucher v WHERE v.value = :value"),
    @NamedQuery(name = "Voucher.findByExpirationDate", query = "SELECT v FROM Voucher v WHERE v.expirationDate = :expirationDate")})
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "value")
    private double value;
    @Basic(optional = false)
    @Column(name = "measurement")
    private String measurement;
    @Basic(optional = false)
    @Column(name = "expiration_date")
    @Temporal(TemporalType.DATE)
    private Date expirationDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voucher")
    @JsonIgnore
    private Set<UserHaveVoucher> userHaveVoucherSet;

    public Voucher() {
    }

    public Voucher(Long id) {
        this.id = id;
    }

    public Voucher(Long id, String name, double value, Date expirationDate) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.expirationDate = expirationDate;
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
        if (!(object instanceof Voucher)) {
            return false;
        }
        Voucher other = (Voucher) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hbt.QuanLyGiaoHangBackend.pojo.Voucher[ id=" + id + " ]";
    }
    
}
