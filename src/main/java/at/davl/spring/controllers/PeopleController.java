package at.davl.spring.controllers;

import at.davl.spring.services.ItemService;
import at.davl.spring.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import at.davl.spring.dao.PersonDAO;
import at.davl.spring.models.Person;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {

    //private final PersonDAO personDAO;
    private final PeopleService peopleService;
    private final ItemService itemService;
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO, PeopleService peopleService, ItemService itemService, PersonDAO personDAO1) {
        this.peopleService = peopleService;
        //this.personDAO = personDAO;
        this.itemService = itemService;
        this.personDAO = personDAO1;
    }

    @GetMapping()
    public String index(Model model) {

        model.addAttribute("people", peopleService.findAll());

        itemService.findByItemName("Iphone");
        itemService.findByOwner(peopleService.findAll().get(0));

        peopleService.test();

        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        //model.addAttribute("person", personDAO.show(id));
        model.addAttribute("person", peopleService.findOne(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        //personDAO.save(person);
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        //model.addAttribute("person", personDAO.show(id));
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        //personDAO.update(id, person);
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        //personDAO.delete(id);
        peopleService.delete(id);
        return "redirect:/people";
    }
}