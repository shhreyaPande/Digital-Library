package org.example.minorproject1.models;

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
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String externalTxnId;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus status;

    @Enumerated(value = EnumType.STRING)
    private TransactionType type;

    @UpdateTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    @JoinColumn
    @ManyToOne   // One student can do multiple transaction
    private Student student;

    @JoinColumn
    @ManyToOne   // One book can be transacted multiple time (can have multiple transactions)
    private Book book;

    // amount to be paid when the book is returned after the due date
    private Long fineAmount;
}
