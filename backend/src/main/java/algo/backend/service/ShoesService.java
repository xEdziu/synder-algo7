package algo.backend.service;

import algo.backend.model.entity.Shoes;
import algo.backend.repository.ShoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoesService {
    private final ShoesRepository shoesRepository;

    public List<Shoes> getAllShoes() {
        return shoesRepository.findAll();
    }
}
