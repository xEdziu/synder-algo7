package algo.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private Long shoeID;
    private String status;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String notes;
}
