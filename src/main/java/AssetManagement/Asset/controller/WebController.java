package AssetManagement.Asset.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.View;

@Controller
public class WebController {

    @GetMapping("/")
    public String index(Model model) {

        return "index";
    }
}
