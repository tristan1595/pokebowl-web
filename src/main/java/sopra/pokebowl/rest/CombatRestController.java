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

import sopra.pokebowl.model.Combat;
import sopra.pokebowl.model.Views;
import sopra.pokebowl.repository.ICombatRepository;

@RestController
@RequestMapping("/combat")
@CrossOrigin("*")
public class CombatRestController {

	@Autowired
	private ICombatRepository combatRepo;
	
	@GetMapping("")
	@JsonView(Views.ViewCombat.class)
	public List<Combat> findAll() {
		return combatRepo.findAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.ViewCombat.class)
	public Combat find(@PathVariable Long id) {
		Optional<Combat> optCombat = combatRepo.findById(id);
		
		if(optCombat.isPresent()) {
			return optCombat.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}
	}
	
	@PostMapping("")
	@JsonView(Views.ViewCombat.class)
	public Combat create(@Valid @RequestBody Combat combat, BindingResult result) {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid");
		}
		
		combat = combatRepo.save(combat);
		
		return combat;
	}
	
	@PutMapping("/{id}")
	@JsonView(Views.ViewCombat.class)
	public Combat update(@RequestBody Combat combat, @PathVariable Long id) {
		if (!combatRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}
		
		combat = combatRepo.save(combat);
		
		return combat;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		combatRepo.deleteById(id);
	}
}
