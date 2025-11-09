package algo.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrdersDTO {
    Long id;
    ShoesDTO shoes;
    String status;
    String description;
    String date;
    Integer quantity;
}
