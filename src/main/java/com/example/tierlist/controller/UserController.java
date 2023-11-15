package com.example.tierlist.controller;

import com.example.tierlist.Annotations.ValidateToken;
import com.example.tierlist.auth.JwtUtil;
import com.example.tierlist.entities.User;
import com.example.tierlist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(path = "/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);

    @PostMapping(path = "/login")
    public @ResponseBody ResponseEntity<Map> login(@RequestBody User params)
    {
        Iterable<User> user = userRepository.findByLogin(params.getLogin());
        for (User u : user)
        {
            if (encoder.matches(params.getPassword(), u.getPassword()))
            {
                String token = jwtUtil.generateToken(u.getId().toString());
                HashMap<String, String> map = new HashMap<>();
                map.put("token", token);
                map.put("id", u.getId().toString());

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(map);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping(path = "/signing", consumes = MediaType.APPLICATION_JSON_VALUE )
    public @ResponseBody ResponseEntity<String> addNewUser (@RequestBody User params) throws Exception {
        try {
            User user = new User();
            if (params.getLogin().isEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le login ne peut pas être vide");

            if (userRepository.findByLogin(params.getLogin()).iterator().hasNext())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le login est déjà utilisé");
            user.setLogin(params.getLogin());

            if (params.getEmail().isEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'email ne peut pas être vide");

            if (userRepository.findByEmail(params.getEmail()).iterator().hasNext())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'email est déjà utilisé");
            user.setEmail(params.getEmail());

            if (params.getPassword().isEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le mot de passe ne peut pas être vide");

            if (params.getPassword().length() < 8)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le mot de passe doit contenir au moins 8 caractères");
            String encodedPassword = encoder.encode(params.getPassword());
            user.setPassword(encodedPassword);

            user.setIsAdmin(params.getIsAdmin());
            user.setName(params.getName());
            user.setFirstname(params.getFirstname());
            user.setBirthdate(params.getBirthdate());
            user.setUsername(params.getUsername());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Utilisateur créé");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la création de l'utilisateur");
        }
    }
    @ValidateToken
    @CrossOrigin(origins = "http://localhost:5173/profile")
    @PutMapping(path = "/user/updateMyProfile")
    public @ResponseBody ResponseEntity<String> updateUser (@RequestBody User user) throws Exception {
        try {
            User myUser = userRepository.findById(user.getId()).get();

            if (user.getLogin().isEmpty())
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le login ne peut pas être vide");

            if (userRepository.findByLogin(user.getLogin()).iterator().hasNext() && !myUser.getLogin().equals(user.getLogin()))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le login est déjà utilisé");
            else
                myUser.setLogin(user.getLogin());

            if (userRepository.findByEmail(user.getEmail()).iterator().hasNext() && !myUser.getEmail().equals(user.getEmail()))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("L'email est déjà utilisé");

            if (user.getEmail().isEmpty())
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("L'email ne peut pas être vide");
            else
                myUser.setEmail(user.getEmail());

            myUser.setName(user.getName());
            myUser.setFirstname(user.getFirstname());
            myUser.setBirthdate(user.getBirthdate());
            myUser.setUsername(user.getUsername());

            userRepository.save(myUser);
            return ResponseEntity.ok("Utilisateur modifié");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la modification de l'utilisateur");
        }
    }

    @ValidateToken
    @GetMapping(path = "/users")
    public @ResponseBody Iterable<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @ValidateToken
    @CrossOrigin(origins = "http://localhost:5173/profile")
    @GetMapping(path = "/user")
    public @ResponseBody User getUser(@RequestParam Integer id)
    {
        try {
            return userRepository.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }
}
