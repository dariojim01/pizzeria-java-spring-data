package com.pizza.pizzeria.web.controller;

import com.pizza.pizzeria.persistence.entity.PizzaEntity;
import com.pizza.pizzeria.service.PizzaService;
import com.pizza.pizzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {
    private final PizzaService pizzaService;
    @Autowired
    public PizzaController(PizzaService pizzaService){
        this.pizzaService= pizzaService;
    }
    @GetMapping
    public ResponseEntity<List<PizzaEntity>> getAll(){
        return ResponseEntity.ok(this.pizzaService.getAll());
    }
    @GetMapping("/pageable")
    public ResponseEntity<Page<PizzaEntity>> getAllPageSort(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int elements){

        return ResponseEntity.ok(this.pizzaService.getAllPagSort(page, elements));
    }
    @GetMapping("/sort")
    public ResponseEntity<Page<PizzaEntity>> getAllSort(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int elements,
                                                        @RequestParam(defaultValue = "price") String sortBy,
                                                        @RequestParam(defaultValue = "ASC") String sortDirection){

        return ResponseEntity.ok(this.pizzaService.getAvailableSort(page, elements, sortBy, sortDirection));
    }

    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> getOne(@PathVariable int idPizza){

        return ResponseEntity.ok(this.pizzaService.getOne(idPizza));
    }
    @GetMapping("/available")
    public ResponseEntity<List<PizzaEntity>> getAvailable(){

        return ResponseEntity.ok(this.pizzaService.getAvailable());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PizzaEntity> getByName(@PathVariable String name){

        return ResponseEntity.ok(this.pizzaService.getByName(name));
    }
    @GetMapping("/firstname/{name}")
    public ResponseEntity<PizzaEntity> getFirstByName(@PathVariable String name){

        return ResponseEntity.ok(this.pizzaService.getFirstByName(name));
    }
    @GetMapping("/with/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWith(@PathVariable String ingredient){

        return ResponseEntity.ok(this.pizzaService.getWith(ingredient));
    }
    @GetMapping("/without/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWithout(@PathVariable String ingredient){

        return ResponseEntity.ok(this.pizzaService.getWithout(ingredient));
    }
    @GetMapping("/cheapest/{price}")
    public ResponseEntity<List<PizzaEntity>> getGetCheapest(@PathVariable int price){

        return ResponseEntity.ok(this.pizzaService.getCheapest(price));
    }
    @PostMapping
    public ResponseEntity<PizzaEntity> add(@RequestBody PizzaEntity pizza){
        if(pizza.getIdPizza()==null || !this.pizzaService.exists(pizza.getIdPizza())){
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<PizzaEntity> update(@RequestBody PizzaEntity pizzaEntity){
        if(pizzaEntity.getIdPizza()!=null && this.pizzaService.exists(pizzaEntity.getIdPizza())){
            return ResponseEntity.ok(this.pizzaService.save(pizzaEntity));
        }
        return ResponseEntity.badRequest().build();
    }
    @PutMapping("/price")
    public ResponseEntity<Void> updatePrice(@RequestBody UpdatePizzaPriceDto dto){
        if(this.pizzaService.exists(dto.getPizzaId())){
            this.pizzaService.updatePrice(dto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{idPizza}")
    public ResponseEntity<Void> delete(@PathVariable int idPizza){
        if(this.pizzaService.exists(idPizza)){
            this.pizzaService.delete(idPizza);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
}
