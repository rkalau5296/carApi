package pl.rafal.carapi.car.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.rafal.carapi.car.mail.SimpleEmailService;
import pl.rafal.carapi.car.model.Film;
import pl.rafal.carapi.car.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@AllArgsConstructor
public class FilmService {

    private final List<Film> films;

    @Autowired
    private SimpleEmailService emailService;

    public FilmService() {
        this.films = new ArrayList<>();
        films.add(new Film(1L, "M jak milosc", "1990", "TVP"));
        films.add(new Film(2L, "Brzydula", "2010", "TVN"));
        films.add(new Film(3L, "Przyjaciolki", "2012", "Polsat"));
        films.add(new Film(4L, "Lekarze", "2010", "TVN"));
        films.add(new Film(5L, "Komisarz Alex", "2000", "TVP"));

    }

    @EventListener(ApplicationReadyEvent.class)
    public List<Film> getFilms(){
        System.out.println(films);
        return films;
    }


    public void addNewFilm(Film film) {
        films.add(film);
        emailService.send();
    }

}
