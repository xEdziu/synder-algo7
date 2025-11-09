package algo.backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
public class Transaction {
    @Id
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "orderId",
            referencedColumnName = "id"
    )
    private Orders order;

    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String paymentMethod;
    private String status;

    public Transaction(Orders order, BigDecimal amount, LocalDateTime transactionDate, String paymentMethod, String status) {
        this.order = order;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }
}
