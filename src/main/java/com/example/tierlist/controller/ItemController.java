package com.example.tierlist.controller;

import com.example.tierlist.entities.Item;
import com.example.tierlist.entities.User;
import com.example.tierlist.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> addItem(@RequestBody Item params) throws Exception {
        try {
            Item item = new Item();

            if (params.getLabel().isEmpty())
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le label ne peut pas être vide");
            item.setLabel(params.getLabel());

            if (params.getImage().isEmpty())
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("L'image ne peut pas être vide");
            item.setImage(params.getImage());

            item.setTag(params.getTag());

            itemRepository.save(item);
            return ResponseEntity.ok("Item créé");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la création de l'item");
        }
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody ResponseEntity<String> deleteItem(@RequestBody Item item) throws Exception {
        try {
            itemRepository.delete(item);
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

    @GetMapping(path = "/myItems")
    public @ResponseBody Iterable<Item> getMyItems(@RequestParam User user) {
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
