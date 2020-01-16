package com.devphilip.tacos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.devphilip.tacos.Ingredient;
import com.devphilip.tacos.Order;
import com.devphilip.tacos.Taco;
import com.devphilip.tacos.data.IngredientRepository;
import com.devphilip.tacos.data.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.devphilip.tacos.Ingredient.Type;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private  TacoRepository tacoDesignRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoDesignRepository = tacoRepository;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        /*int[] count = { 0 };
        ingredientRepository.findAll().forEach(ingredient -> {
            log.info("Ingredient at position {}: {}", count[0]++, ingredient);
            ingredients.add(ingredient);
        });*/
        ingredientRepository.findAll().forEach(ingredient -> ingredients.add(ingredient));
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());
        return "design";
    }

    /*@PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()){
            log.info("Shey Error dey here again????????????????");
            return "design";
        }
        // Save the taco design...
        Taco savedTaco = tacoDesignRepository.save(design);
        order.addDesign(savedTaco);

//        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }*/

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {

        if (errors.hasErrors()) {
            return "design";
        }

        Taco saved = tacoDesignRepository.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}
