package br.com.sqs.bridge.entity;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class TableAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ativo", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean ativo;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Boolean getAtivo() {
	return ativo;
    }

    public void setAtivo(Boolean ativo) {
	this.ativo = ativo;
    }

    public Instant getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
	this.createdDate = createdDate;
    }

    public String getCreatedBy() {
	return createdBy;
    }

    public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
	return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
	this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
	return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
	this.lastModifiedBy = lastModifiedBy;
    }

}
