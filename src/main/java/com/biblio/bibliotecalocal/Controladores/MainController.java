package com.biblio.bibliotecalocal.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    
    @GetMapping("index")
    public String index(){
        return "index";
    }
    
    
    
}
