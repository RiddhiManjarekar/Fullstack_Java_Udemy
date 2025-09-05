package org.studyeasy.SpringStarter.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class AdminController {

    @GetMapping("/admin")
    public String admin(Model model){
        return "admin_views/admin";
    }
    
}
