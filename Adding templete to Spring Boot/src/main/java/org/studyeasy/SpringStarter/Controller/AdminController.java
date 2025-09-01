package org.studyeasy.SpringStarter.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.studyeasy.SpringStarter.models.Account;

public class AdminController {

    @GetMapping("/admin")
    public String admin(Model model){
        return "admin_views/admin";
    }
    
}
