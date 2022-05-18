package AssetManagement.Asset.controller;

import AssetManagement.Asset.entity.College;
import AssetManagement.Asset.entity.Staff;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class FreshController {


    @GetMapping("/fresh")
    public String fresh( Model model){
        model.addAttribute("errorMessage" , null);
        model.addAttribute("empty" , new ArrayList() );
        return "fresh";
    }

    @PostMapping("/fresh")
    public String freshsubmit(@Valid College college, BindingResult binding, Model model){

        if(binding.hasErrors()){
            model.addAttribute("errorMessage" , binding);
        }

        return "fresh";
    }

}
