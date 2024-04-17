package SZP.service.impl;

import SZP.model.TitleModel;
import SZP.model.WorkstationModel;
import SZP.repository.TitleRepository;
import SZP.repository.WorkstationRepository;
import SZP.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TitleServiceImpl implements TitleService {
    @Autowired
    private final TitleRepository titleRepository;

    public TitleServiceImpl(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }


    @Override
    public void save(TitleModel titleModel) {
        titleRepository.save(titleModel);
    }
}


