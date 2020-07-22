package magerramov.Controller;

import magerramov.models.User;
import magerramov.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping("/addUser")
    public String addUserGetMethod(){
        return "AddUserForm";
    }
    @PostMapping("/addUser")
    public String addUserPostMethod(@RequestParam(value = "login", required=false, defaultValue="Hello") String login,
                                      @RequestParam(value = "pass", required=false, defaultValue="World") String pass, Model model) {
        boolean bool = userService.saveUser(new User(login, pass));
        if (bool){
            model.addAttribute("Err","Пользователь '"+login+"' успешно добавлен");
            model.addAttribute("Color","Green");
        }
        else {
            model.addAttribute("Err","Пользователь с логином '"+login+"' уже существует");
            model.addAttribute("Color","Red");
        }

        return "AddUserForm";
    }

    @GetMapping("/reUser")
    public String updUserGetMethod(){
        return "UpdUserForm";
    }

    @GetMapping("/delUser")
    public String delUserGetMethod(){
        return "DelUserForm";
    }

    @PostMapping("/delUser")
    public String delUserPostMethod(@RequestParam(value = "login", required=false, defaultValue="Hello") String login,
                                    Model model){
        Boolean bool = userService.UserFromLogin(login);
        if(bool){
            userService.deleteUser(login);
            model.addAttribute("Color","Green");
            model.addAttribute("Err","Пользователь с логином '"+login+"' успешно удалён");
        }
        else {
            model.addAttribute("Color","Red");
            model.addAttribute("Err","Пользователь с логином '"+login+"' не существует");
        }
        return "DelUserForm";
    }

    @PostMapping("/reUser")
    public String updUserPostMethod(@RequestParam(value = "login", required=false, defaultValue="Hello") String login,
                                    @RequestParam(value = "value", required=false, defaultValue="Hello") String value,
                                    @RequestParam(value = "typeUpd") String type,
                                    Model model) {

        Boolean bool1 = userService.UserFromLogin(login);
        if (bool1){
            switch (type){
                case "updPass":
                    String oldPass = userService.getPassUser(login);
                    userService.updateUserPass(login,value);
                    model.addAttribute("Color","Green");
                    model.addAttribute("Err","Пароль пользователя '"+login+"' изменён с '"+oldPass+"' на '"+value+"'");
                    break;
                case "updLog":
                    Boolean bull2 = userService.UserFromLogin(value);
                    if(bull2){
                        model.addAttribute("Color","Red");
                        model.addAttribute("Err","Нельзя заменить логин на уже существующий");
                    }
                    else{
                        userService.updateUserLogin(login,value);
                        model.addAttribute("Color","Green");
                        model.addAttribute("Err","Логин пользователя '"+login+"'  изменён на '"+value+"'");
                    }
            }
        }
        else {
            model.addAttribute("Color","Red");
            model.addAttribute("Err","Пользователь с логином '"+login+"' не существует");
        }
        return "UpdUserForm";


    }

    @GetMapping("/Home")
    public String seyHelloWorld(){

        return "HomePage";
    }

}
