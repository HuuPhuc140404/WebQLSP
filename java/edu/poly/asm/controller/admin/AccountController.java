package edu.poly.asm.controller.admin;

import edu.poly.asm.domain.Account;
import edu.poly.asm.model.AccountDto;
import edu.poly.asm.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("account", new AccountDto());
        return "admin/accounts/addOrEdit";
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("account") AccountDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/accounts/addOrEdit");
        }
        Account entity = new Account();
        BeanUtils.copyProperties(dto, entity);
        accountService.save(entity);
        model.addAttribute("message", "Account saved successfully");
        return new ModelAndView("redirect:/admin/accounts", model);
    }

    @RequestMapping("")
    public String list(ModelMap model) {
        List<Account> list = accountService.findAll();
        model.addAttribute("accounts", list);
        return "admin/accounts/list";
    }

    @GetMapping("edit/{username}")
    public ModelAndView edit(ModelMap model, @PathVariable("username") String username) {
        Optional<Account> opt = accountService.findById(username);
        AccountDto dto = new AccountDto();
        if (opt.isPresent()) {
            Account entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);
            model.addAttribute("account", dto);
            return new ModelAndView("admin/accounts/addOrEdit", model);
        }
        model.addAttribute("message", "Account does not exist");
        return new ModelAndView("forward:/admin/accounts", model);
    }

    @GetMapping("delete/{username}")
    public ModelAndView delete(ModelMap model, @PathVariable("username") String username) {
        accountService.deleteById(username);
        model.addAttribute("message", "Account deleted successfully");
        return new ModelAndView("forward:/admin/accounts", model);
    }
}
