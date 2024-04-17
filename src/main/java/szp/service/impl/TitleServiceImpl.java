package szp.service.impl;

import szp.model.TitleModel;
import szp.repository.TitleRepository;
import szp.service.TitleService;
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


