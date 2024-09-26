/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hbt.QuanLyGiaoHangBackend.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

/**
 * @author ASUS
 */
@Entity
@Data
@Table(name = "product")
@NamedQueries({
        @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
        @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
        @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name"),
        @NamedQuery(name = "Product.findByQuantity", query = "SELECT p FROM Product p WHERE p.quantity = :quantity"),
        @NamedQuery(name = "Product.findByDeliveryAddress", query = "SELECT p FROM Product p WHERE p.deliveryAddress = :deliveryAddress"),
        @NamedQuery(name = "Product.findByReceiverAddress", query = "SELECT p FROM Product p WHERE p.receiverAddress = :receiverAddress"),
        @NamedQuery(name = "Product.findBySenderPhone", query = "SELECT p FROM Product p WHERE p.senderPhone = :senderPhone"),
        @NamedQuery(name = "Product.findByReceiverPhone", query = "SELECT p FROM Product p WHERE p.receiverPhone = :receiverPhone"),
        @NamedQuery(name = "Product.findByNote", query = "SELECT p FROM Product p WHERE p.note = :note"),
        @NamedQuery(name = "Product.findByImage", query = "SELECT p FROM Product p WHERE p.image = :image"),
        @NamedQuery(name = "Product.findByCreatedDate", query = "SELECT p FROM Product p WHERE p.createdDate = :createdDate"),
        @NamedQuery(name = "Product.findByPaymentDate", query = "SELECT p FROM Product p WHERE p.paymentDate = :paymentDate")})
public class Product implements Serializable {

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
    @Column(name = "quantity")
    private int quantity;
    @Basic(optional = false)
    @Column(name = "delivery_address")
    private String deliveryAddress;
    @Basic(optional = false)
    @Column(name = "receiver_address")
    private String receiverAddress;
    @Basic(optional = false)
    @Column(name = "sender_phone")
    private String senderPhone;
    @Basic(optional = false)
    @Column(name = "receiver_phone")
    private String receiverPhone;
    @Column(name = "note")
    private String note;
    @Column(name = "image")
    private String image;

    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    @JoinTable(name = "product_cate", joinColumns = {
            @JoinColumn(name = "product_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "category_id", referencedColumnName = "id")})
    @ManyToMany
    private Set<Category> categorySet;
    @JoinColumn(name = "from_district_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Districts fromDistrictId;
    @JoinColumn(name = "to_district_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Districts toDistrictId;
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Service serviceId;

    @Basic(optional = false)
    @Column(name = "sender_name")
    private String senderName;

    @Basic(optional = false)
    @Column(name = "receiver_name")
    private String receiverName;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Set<DauGia> dauGiaSet;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Proof proof;

    public Product() {
    }

    public Product(Long id) {
        this.id = id;
    }

    public Product(Long id, String name, int quantity, String deliveryAddress, String receiverAddress, String senderPhone, String receiverPhone, Date createdDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.deliveryAddress = deliveryAddress;
        this.receiverAddress = receiverAddress;
        this.senderPhone = senderPhone;
        this.receiverPhone = receiverPhone;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate() {
        // Lấy ngày giờ hiện tại theo múi giờ hệ thống
        LocalDateTime localDateTime = LocalDateTime.now();

        // Chuyển LocalDateTime sang ZonedDateTime
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        // Chuyển ZonedDateTime sang java.util.Date
        Date date = Date.from(zonedDateTime.toInstant());
        this.createdDate = date;

    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate() {
        // Lấy ngày giờ hiện tại theo múi giờ hệ thống
        LocalDateTime localDateTime = LocalDateTime.now();

        // Chuyển LocalDateTime sang ZonedDateTime
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        // Chuyển ZonedDateTime sang java.util.Date
        Date date = Date.from(zonedDateTime.toInstant());
        this.paymentDate = date;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }

    public Districts getFromDistrictId() {
        return fromDistrictId;
    }

    public void setFromDistrictId(Districts fromDistrictId) {
        this.fromDistrictId = fromDistrictId;
    }

    public Districts getToDistrictId() {
        return toDistrictId;
    }

    public void setToDistrictId(Districts toDistrictId) {
        this.toDistrictId = toDistrictId;
    }

    public Service getServiceId() {
        return serviceId;
    }

    public void setServiceId(Service serviceId) {
        this.serviceId = serviceId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hbt.QuanLyGiaoHangBackend.pojo.Product[ id=" + id + " ]";
    }

}
