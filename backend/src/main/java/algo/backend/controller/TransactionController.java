package algo.backend.controller;

import algo.backend.config.CustomReturnables;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@RestController
@RequestMapping("/api/v1")
public class TransactionController {

  @GetMapping("/transactions")
  public Map<String, Object> shoes() {
    return CustomReturnables.getOkResponseMap("");
  }
}
