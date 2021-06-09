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

import sopra.pokebowl.model.MonPokemon;
import sopra.pokebowl.model.Views;
import sopra.pokebowl.repository.IMonPokemonRepository;

@RestController
@RequestMapping("/monPokemon")
@CrossOrigin("*")
public class MonPokemonRestController {
	
	@Autowired
	private IMonPokemonRepository monPokeRepo;

	@GetMapping("")
	@JsonView(Views.ViewMonPokemon.class)
	public List<MonPokemon> findAll() {
		return monPokeRepo.findAll();
	}

	@GetMapping("/{id}")
	@JsonView(Views.ViewMonPokemon.class)
	public MonPokemon find(@PathVariable Long id) {

		Optional<MonPokemon> optMonPoke = monPokeRepo.findById(id);

		if (optMonPoke.isPresent()) {
			return optMonPoke.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}
	}
	
//	@GetMapping("/{id}/detail")
//	@JsonView(Views.ViewFiliereWithReferent.class)
//	public Filiere detail(@PathVariable Long id) {
//
//		Optional<Filiere> optFiliere = filiereRepo.findByIdWithReferent(id);
//
//		if (optFiliere.isPresent()) {
//			return optFiliere.get();
//		} else {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
//		}
//	}

	@PostMapping("")
	@JsonView(Views.ViewMonPokemon.class)
	public MonPokemon create(@Valid @RequestBody MonPokemon monPokemon, BindingResult result) {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur validation");
		}

		monPokemon = monPokeRepo.save(monPokemon);

		return monPokemon;
	}

	@PutMapping("/{id}")
	@JsonView(Views.ViewMonPokemon.class)
	public MonPokemon update(@RequestBody MonPokemon monPokemon, @PathVariable Long id) {
		if (!monPokeRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}

		monPokemon = monPokeRepo.save(monPokemon);

		return monPokemon;
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		monPokeRepo.deleteById(id);
	}

}
