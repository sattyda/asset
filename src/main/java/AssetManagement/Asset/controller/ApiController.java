package AssetManagement.Asset.controller;

import AssetManagement.Asset.entity.Assign;
import AssetManagement.Asset.services.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    WebService webService;


    @GetMapping("/api")
    public String index(Model model) {

        return " hi";
    }
}
