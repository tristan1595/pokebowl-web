package sopra.pokebowl.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

import sopra.pokebowl.model.Tour;
import sopra.pokebowl.model.Views;
import sopra.pokebowl.repository.ITourRepository;

@RestController
@RequestMapping("/tour")
@CrossOrigin("*")
public class TourRestController {

	@Autowired
	private ITourRepository tourRepo;
	
	@GetMapping("")
	@JsonView(Views.ViewTour.class)
	public List<Tour> findAll() {
		return tourRepo.findAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.ViewTour.class)
	public Tour find(@PathVariable Long id) {
		Optional<Tour> opttour = tourRepo.findById(id);
		
		if(opttour.isPresent()) {
			return opttour.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}
	}
	
	@PostMapping("")
	@JsonView(Views.ViewTour.class)
	public Tour create(@Valid @RequestBody Tour tour, BindingResult result) {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid");
		}
		
		tour = tourRepo.save(tour);
		
		return tour;
	}
	
	@PutMapping("/{id}")
	@JsonView(Views.ViewTour.class)
	public Tour update(@RequestBody Tour tour, @PathVariable Long id) {
		if (!tourRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}
		
		tour = tourRepo.save(tour);
		
		return tour;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		tourRepo.deleteById(id);
	}
}
