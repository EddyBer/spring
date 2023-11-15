package com.example.tierlist.controller;

import com.example.tierlist.Annotations.ValidateToken;
import com.example.tierlist.entities.Item;
import com.example.tierlist.entities.User;
import com.example.tierlist.repository.ItemRepository;
import com.example.tierlist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @ValidateToken
    @CrossOrigin(origins = "http://localhost:5173/home")
    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<String> addItem(@RequestBody Item params) throws Exception {
        try {
            User user = userRepository.findById(params.getUser().getId()).get();

            Item item = new Item();

            if (params.getLabel().isEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le label ne peut pas être vide");
            item.setLabel(params.getLabel());

            if (params.getImage().isEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'image ne peut pas être vide");

            int index = params.getImage().indexOf(',');
            String base64Formatted = params.getImage().substring(index + 1);
            item.setImage(base64Formatted);

            item.setTag(params.getTag());
            item.setUser(user);
            itemRepository.save(item);
            return ResponseEntity.ok("Item créé");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la création de l'item");
        }
    }

    @ValidateToken
    @CrossOrigin(origins = "http://localhost:5173/home")
    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody ResponseEntity<String> deleteItem(@PathVariable String id) throws Exception {
        try {
            itemRepository.deleteById(Integer.parseInt(id));
            return ResponseEntity.ok("Item supprimé");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la suppression de l'item");
        }
    }

    @PutMapping(path = "/update")
    public @ResponseBody ResponseEntity<String> updateItem(@RequestBody Item item) throws Exception {
        try {
            Item myItem = itemRepository.findById(item.getId()).get();

            myItem.setTag(item.getTag());
            myItem.setImage(item.getImage());
            myItem.setLabel(item.getLabel());

            if (!item.getRanksList().isEmpty())
                myItem.setRanksList(item.getRanksList());

            itemRepository.save(myItem);
            return ResponseEntity.ok("Item modifié");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la modification de l'item");
        }
    }

    @ValidateToken
    @CrossOrigin(origins = "http://localhost:5173/home")
    @PostMapping(path = "/myItems")
    public @ResponseBody Iterable<Item> getMyItems(@RequestBody User user) {
        return itemRepository.findByUser(user);
    }

    @GetMapping(path = "/item")
    public @ResponseBody Item getItem(@RequestBody Item item) {
        try {
            return itemRepository.findById(item.getId()).get();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(path = "/allItems")
    public @ResponseBody Iterable<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
