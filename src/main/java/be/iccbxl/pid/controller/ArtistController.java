package be.iccbxl.pid.controller;

import be.iccbxl.pid.model.Artist;
import be.iccbxl.pid.service.ArtistService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Controller
public class ArtistController {
    @Autowired
    ArtistService service;

    @GetMapping("/artists")
    public String index(Model model) {
        List<Artist> artists = service.getAllArtists();

        model.addAttribute("artists", artists);
        model.addAttribute("title", "Liste des artistes");

        return "artist/index";
    }
    @GetMapping("/artists/{id}")
    public String show(Model model, @PathVariable("id") long id) {

        Artist artist = service.getArtist(id);
        if(artist == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Artist not found");
        }

        model.addAttribute("artist", artist);
        model.addAttribute("title", "Fiche d'un artiste");

        return "artist/show";
    }
    @GetMapping("/artists/{id}/edit")
    public String edit(Model model, @PathVariable long id, HttpServletRequest request) {
        Artist artist = service.getArtist(id);

        model.addAttribute("artist", artist);


        //Générer le lien retour pour l'annulation
        String referrer = request.getHeader("Referer");

        if(referrer!=null && !referrer.equals("")) {
            model.addAttribute("back", referrer);
        } else {
            model.addAttribute("back", "/artists/"+artist.getId());
        }

        return "artist/edit";
    }
    @PutMapping("/artists/{id}/edit")
    public String update(@Valid @ModelAttribute Artist artist, BindingResult bindingResult, @PathVariable long id, Model model) {

        if (bindingResult.hasErrors()) {
            return "artist/edit";
        }

        Artist existing = service.getArtist(id);

        if(existing==null) {
            return "artist/index";
        }

        service.updateArtist(id, artist);

        return "redirect:/artists/"+artist.getId();
    }


}



