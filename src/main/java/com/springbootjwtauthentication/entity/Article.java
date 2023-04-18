package com.springbootjwtauthentication.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
@Entity
@Table(name = "article")
@Data
//@AllArgsConstructor
//@NoArgsConstructor
@EqualsAndHashCode
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;
    @NotBlank
    private String department;
    @Column(name="user_Id")
    private Long userId;
    @NotBlank
    private String author;
    @Column(name="create_time")
    private Date createTime;

    public Article(long id, String title, String department, String author, Date createTime, long userId) {
        this.id = id;
        this.title = title;
        this.department = department;
        this.author = author;
        this.createTime = createTime;
        this.userId = userId;
    }

    public Article(){

    }

}
