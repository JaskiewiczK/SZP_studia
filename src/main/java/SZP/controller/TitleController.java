package SZP.controller;

import SZP.model.TitleModel;
import SZP.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/title")
public class TitleController {
    @Autowired
    private final TitleService tileService;

    public TitleController(TitleService tileService) {
        this.tileService = tileService;
    }

    @PutMapping("/create")
    public void createTile() {
        TitleModel tm = new TitleModel();
        tm.setName("a");
        tileService.save(tm);
    }
}
