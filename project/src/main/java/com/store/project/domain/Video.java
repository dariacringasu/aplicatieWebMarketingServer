package com.store.project.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "videos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Video {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name="description")
    private String description;
    @Column(name = "approved")
    private Boolean approved;
    @Column(name = "dateOfPosting")
    private LocalDate dateOfPosting;
    @Column(name = "urlmoreinfo")
    private String urlmoreinfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    @JsonIgnore
    private User client;





    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Video(){

    }
}
