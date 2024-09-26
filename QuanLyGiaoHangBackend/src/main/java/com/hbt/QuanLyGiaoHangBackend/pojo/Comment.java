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
@Table(name = "comment")
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c"),
    @NamedQuery(name = "Comment.findById", query = "SELECT c FROM Comment c WHERE c.id = :id"),
    @NamedQuery(name = "Comment.findByCommentOfUser", query = "SELECT c FROM Comment c WHERE c.commentOfUser = :commentOfUser")})
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "comment_of_user")
    private String commentOfUser;
    @JoinColumn(name = "shipper_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Shipper shipperId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public Comment() {
    }

    public Comment(Long id) {
        this.id = id;
    }

    public Comment(Long id, String commentOfUser) {
        this.id = id;
        this.commentOfUser = commentOfUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentOfUser() {
        return commentOfUser;
    }

    public void setCommentOfUser(String commentOfUser) {
        this.commentOfUser = commentOfUser;
    }

    public Shipper getShipperId() {
        return shipperId;
    }

    public void setShipperId(Shipper shipperId) {
        this.shipperId = shipperId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hbt.QuanLyGiaoHangBackend.pojo.Comment[ id=" + id + " ]";
    }
    
}
