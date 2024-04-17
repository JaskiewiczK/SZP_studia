package SZP.controller;

import SZP.model.TitleModel;
import SZP.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/title")
@RequiredArgsConstructor
public class TitleController {
    private final TitleService tileService;

    @PutMapping("/create")
    public void createTile() {
        TitleModel tm = new TitleModel();
        tm.setName("a");
        tileService.save(tm);
    }
}
