package algo.backend.controller;

import algo.backend.config.CustomReturnables;
import java.util.Map;

import algo.backend.service.TransactionsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RequiredArgsConstructor
@Setter
@RestController
@RequestMapping("/api/v1")
public class TransactionsController {
    private final TransactionsService transactionsService;

    @GetMapping("/transactions")
  public Map<String, Object> shoes() {
    return CustomReturnables.getOkResponseMap("");
  }

  @GetMapping("/transactions/all")
    public Map<String,Object> getAllTransactions(){
      return transactionsService.getAllTransactions();
  }


}
