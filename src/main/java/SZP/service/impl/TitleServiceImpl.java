package SZP.service.impl;

import SZP.model.TitleModel;
import SZP.repository.TitleRepository;
import SZP.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TitleServiceImpl implements TitleService {
    private final TitleRepository titleRepository;

    @Override
    public void save(TitleModel titleModel) {
        titleRepository.save(titleModel);
    }
}


