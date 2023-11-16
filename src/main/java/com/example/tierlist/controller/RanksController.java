package com.example.tierlist.controller;

import com.example.tierlist.Annotations.ValidateToken;
import com.example.tierlist.entities.Item;
import com.example.tierlist.entities.Ranks;
import com.example.tierlist.repository.RanksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/ranks")
public class RanksController {

    @Autowired
    private RanksRepository ranksRepository;
    @ValidateToken
    @CrossOrigin(origins = "http://localhost:5173/home")
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE )
    public @ResponseBody ResponseEntity<String> addRanks (@RequestBody Ranks params) throws Exception {
        try {
            Ranks rank = new Ranks();

            if (params.getLabel().isEmpty())
                return ResponseEntity.badRequest().body("Le label ne peut pas être vide");

            rank.setLabel(params.getLabel());

            if (params.getRankIndex() == null)
                return ResponseEntity.badRequest().body("L'index ne peut pas être vide");

            rank.setRankIndex(params.getRankIndex());

            if (params.getList() == null)
                return ResponseEntity.badRequest().body("La liste ne peut pas être vide");

            rank.setList(params.getList());

            rank.setListOfItem(params.getListOfItem());

            ranksRepository.save(rank);
            return ResponseEntity.ok("Rang créé");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création du rang");
        }
    }
        @ValidateToken
        @CrossOrigin(origins = "http://localhost:5173/home")
        @GetMapping(path = "/allRanksForAList/{id}")
        public @ResponseBody ResponseEntity<Iterable<Ranks>> getAllRanksForAList(@PathVariable String id) {
            try {
                Iterable<Ranks> listOfRank = ranksRepository.findByListId(Integer.parseInt(id));

                if (id == null)
                    return ResponseEntity.badRequest().body(null);

                return ResponseEntity.ok(listOfRank);
            } catch (Exception e) {
                return  ResponseEntity.badRequest().body(null);
            }
        }

        @ValidateToken
        @CrossOrigin(origins = "http://localhost:5173/home")
        @DeleteMapping(path = "/delete/{id}")
        public @ResponseBody ResponseEntity<String> deleteRanks (@PathVariable String id) throws Exception {
            try {
                ranksRepository.deleteById(Integer.parseInt(id));
                return ResponseEntity.ok("Rang supprimé");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Erreur lors de la suppression du rang");
            }
        }

        @DeleteMapping(path = "/delete")
        public @ResponseBody ResponseEntity<String> deleteRanks (@RequestBody Ranks rank) throws Exception {
            try {
                ranksRepository.delete(rank);
                return ResponseEntity.ok("Rang supprimé");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Erreur lors de la suppression du rang");
            }
        }
        @ValidateToken
        @CrossOrigin(origins = "http://localhost:5173/home")
        @PutMapping(path = "/addItems")
        public @ResponseBody ResponseEntity<String> addItem (@RequestBody Ranks params) throws Exception {
            try {
                Ranks myRank = ranksRepository.findById(params.getId()).get();

                if (params.getListOfItem() == null)
                    return ResponseEntity.badRequest().body("La liste ne peut pas être vide");

                List<Item> currentItems = myRank.getListOfItem();
                System.out.println(params.getListOfItem());
                currentItems.addAll(params.getListOfItem());

                myRank.setListOfItem(currentItems);

                ranksRepository.save(myRank);
                return ResponseEntity.ok("Item ajouté");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Erreur lors de l'ajout de l'item");
            }
        }

    @ValidateToken
    @CrossOrigin(origins = "http://localhost:5173/home")
    @PutMapping(path = "/deleteItem/{id}")
    public @ResponseBody ResponseEntity<String> deleteItem (@PathVariable String id,@RequestBody Item params) throws Exception {
        try {
            Ranks myRank = ranksRepository.findById(Integer.parseInt(id)).get();

            if (params.getId() == null)
                return ResponseEntity.badRequest().body("Il faut obligatoirement un item");

            myRank.getListOfItem().remove(params);

            ranksRepository.save(myRank);
            return ResponseEntity.ok("Item ajouté");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'ajout de l'item");
        }
    }

        @PutMapping(path = "/update")
        public @ResponseBody ResponseEntity<String> updateRanks (@RequestBody Ranks params) throws Exception {
            try {
                Ranks myRank = ranksRepository.findById(params.getId()).get();

                if (params.getLabel().isEmpty())
                    return ResponseEntity.badRequest().body("Le label ne peut pas être vide");

                myRank.setLabel(params.getLabel());

                if (params.getRankIndex() == null)
                    return ResponseEntity.badRequest().body("L'index ne peut pas être vide");

                myRank.setRankIndex(params.getRankIndex());

                if (params.getList() == null)
                    return ResponseEntity.badRequest().body("La liste ne peut pas être vide");

                myRank.setList(params.getList());

                myRank.setListOfItem(params.getListOfItem());

                ranksRepository.save(myRank);
                return ResponseEntity.ok("Rang mis à jour");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Erreur lors de la création du rang");
            }
        }
}
