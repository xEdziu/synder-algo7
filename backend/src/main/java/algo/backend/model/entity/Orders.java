package algo.backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
public class Orders {
    @Id
    @SequenceGenerator(
            name = "orders_sequence",
            sequenceName = "orders_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orders_sequence"
    )
    private Long id;
    @ManyToOne
    @JoinColumn(
            name="shoesId",
            referencedColumnName = "id"
    )
    private Shoes shoes;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    private String description="";
    private LocalDate data;
    private Integer quantity;


    public Orders(Shoes shoes, OrderType orderType, String description, LocalDate data, Integer quantity) {
        this.shoes = shoes;
        this.orderType = orderType;
        this.description = description;
        this.data = data;
        this.quantity = quantity;
    }
}
