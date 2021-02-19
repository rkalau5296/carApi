package pl.rafal.carapi.car.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.rafal.carapi.car.model.Film;
import pl.rafal.carapi.car.model.Vehicle;
import pl.rafal.carapi.car.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getFilms(){
        return filmService.getFilms();
    }

    @PostMapping
    public void addFilm(@RequestBody Film film){
         filmService.addNewFilm(film);
    }
}

