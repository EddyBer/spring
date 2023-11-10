package com.example.tierlist.controller;

import com.example.tierlist.entities.List;
import com.example.tierlist.entities.Ranks;
import com.example.tierlist.repository.RanksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ranks")
public class RanksController {

    @Autowired
    private RanksRepository ranksRepository;
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

        @GetMapping(path = "/allRanksForAList")
        public @ResponseBody ResponseEntity<String> getAllRanksForAList(@RequestParam List list) {
            try {
                Iterable<Ranks> listOfRank = ranksRepository.findByList(list);

                if (list == null)
                    return ResponseEntity.badRequest().body("La liste ne peut pas être vide");

                return ResponseEntity.ok(listOfRank.toString());
            } catch (Exception e) {
                return  ResponseEntity.badRequest().body("Erruer lors de la récupération des rangs");
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
}