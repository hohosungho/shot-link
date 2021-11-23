package com.ab180.shortlinkmanagement.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShortLink {

    @Column(name="url")
    private String url;

    @Id
    @Column(name="shortId")
    private String shortId;

    @Column(name="createdAt")
    private String createdAt;

    @Column(name="aliasName")
    private String aliasName;
}
