package com.example.tierlist.controller;

import com.example.tierlist.Annotations.ValidateToken;
import com.example.tierlist.entities.List;
import com.example.tierlist.entities.Ranks;
import com.example.tierlist.entities.User;
import com.example.tierlist.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@Controller
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/list")
public class ListController {

    @Autowired
    private ListRepository listRepository;

    @ValidateToken
    @CrossOrigin(origins = "http://localhost:5173/home")
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE )
    public @ResponseBody ResponseEntity<String> addList (@RequestBody List params) throws Exception {
        try {
            List list = new List();

            if (params.getLabel().isEmpty())
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le label ne peut pas être vide");
            list.setLabel(params.getLabel());
            list.setStatut(params.getStatut());
            list.setCreationDate(params.getCreationDate());
            list.setTag(params.getTag());
            if (params.getUser() == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("L'utilisateur ne peut pas être vide");

            list.setUser(params.getUser());

            listRepository.save(list);
            return ResponseEntity.ok("List créé");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la création de la liste");
        }
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody ResponseEntity<String> deleteList (@RequestBody List list) throws Exception {
        try {

            listRepository.delete(list);
            return ResponseEntity.ok("List supprimé");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la suppression de la liste");
        }
    }

    @PutMapping(path = "/update")
    public @ResponseBody ResponseEntity<String> updateList (@RequestBody List list) throws Exception {
        try {

            List myList = listRepository.findById(list.getId()).get();

            if (list.getLabel().isEmpty())
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le label ne peut pas être vide");
            myList.setLabel(list.getLabel());
            myList.setStatut(list.getStatut());
            myList.setCreationDate(list.getCreationDate());

            listRepository.save(list);
            return ResponseEntity.ok("List modifié");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la modification de la liste");
        }
    }
    @ValidateToken
    @CrossOrigin(origins = "http://localhost:5173/home")
    @PostMapping(path = "/myLists")
    public @ResponseBody Iterable<List> getMyLists(@RequestBody User user) {
        return listRepository.findByUser(user);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<List> getLists() {
        return listRepository.findAll();
    }

    @GetMapping(path = "/list")
    public @ResponseBody List getList(@RequestParam Integer id) {
        return listRepository.findById(id).get();
    }


    @PutMapping(path = "/updateRanks")
    public @ResponseBody ResponseEntity<String> updateRanks (@RequestParam List list,@RequestBody java.util.List<Ranks> listOfRanks) throws Exception {
        try {

            List myList = listRepository.findById(list.getId()).get();

            myList.setListOfRanks(listOfRanks);

            listRepository.save(myList);
            return ResponseEntity.ok("Rangs modifiés");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la modification des rangs");
        }
    }
}
