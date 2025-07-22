package org.example.minorproject1.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(unique = true, nullable = false)
    private String rollNumber;
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Department department;
    private String address;

    @UpdateTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

//    @JoinColumn
//    @OneToMany(mappedBy = "student")     // This is adding a back reference
//    private List<Book> bookList;
    // books assigned to a student:

    // Get the student details
    // select * from student where id = ?
    // select * from book where studentId = ?


}
