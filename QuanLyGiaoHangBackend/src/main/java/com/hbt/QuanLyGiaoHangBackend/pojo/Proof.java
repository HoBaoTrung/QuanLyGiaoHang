/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hbt.QuanLyGiaoHangBackend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "proof")
@Data
public class Proof implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "product_id")
    private Long id;

    @Column(name = "image", nullable = false, length = 255)
    private String image;

    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    public Proof() {}
    public Proof(String image, Product product) {

        this.image = image;
        this.product = product;
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
        if (!(object instanceof Proof)) {
            return false;
        }
        Proof other = (Proof) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hbt.QuanLyGiaoHangBackend.pojo.Category[ id=" + id + " ]";
    }
    
}
