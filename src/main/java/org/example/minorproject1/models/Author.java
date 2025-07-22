package org.example.minorproject1.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(unique = true, nullable = false) edgvhj
    private String email;
    private String country;

    @UpdateTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}
