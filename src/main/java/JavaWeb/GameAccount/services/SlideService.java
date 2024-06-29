package JavaWeb.GameAccount.services;


import JavaWeb.GameAccount.model.Slide;
import JavaWeb.GameAccount.repositories.SlideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class SlideService {
    @Autowired
    private final SlideRepository slideRepository;

    public List<Slide> findAllSlide() {return slideRepository.findAll();

    }
}
