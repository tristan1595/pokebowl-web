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

import sopra.pokebowl.model.Salon;
import sopra.pokebowl.model.Views;
import sopra.pokebowl.repository.ISalonRepository;

@RestController
@RequestMapping("/salon")
@CrossOrigin("*")
public class SalonRestController {

	@Autowired
	private ISalonRepository salonRepo;
	
	@GetMapping("")
	@JsonView(Views.ViewSalon.class)
	public List<Salon> findAll() {
		return salonRepo.findAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.ViewSalon.class)
	public Salon find(@PathVariable Long id) {
		Optional<Salon> optSalon = salonRepo.findById(id);
		
		if(optSalon.isPresent()) {
			return optSalon.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}
	}
	
	@PostMapping("")
	@JsonView(Views.ViewSalon.class)
	public Salon create(@Valid @RequestBody Salon salon, BindingResult result) {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid");
		}
		
		salon = salonRepo.save(salon);
		
		return salon;
	}
	
	@PutMapping("/{id}")
	@JsonView(Views.ViewSalon.class)
	public Salon update(@RequestBody Salon salon, @PathVariable Long id) {
		if (!salonRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}
		
		salon = salonRepo.save(salon);
		
		return salon;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		salonRepo.deleteById(id);
	}
}
