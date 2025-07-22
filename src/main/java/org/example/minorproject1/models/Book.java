package org.example.minorproject1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    private Long issueCount;

    private Boolean isAvailable;

    @UpdateTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    @JoinColumn
    @ManyToOne // Many Books can be allotted to one Student
    @JsonIgnore
    private Student student;

    @JoinColumn
    @ManyToOne
    private Author author;

}


