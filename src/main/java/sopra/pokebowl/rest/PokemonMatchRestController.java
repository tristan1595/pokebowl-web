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

import sopra.pokebowl.model.PokemonMatch;
import sopra.pokebowl.model.Views;
import sopra.pokebowl.repository.IPokemonMatchRepository;

@RestController
@RequestMapping("/pokemonMatch")
@CrossOrigin("*")
public class PokemonMatchRestController {
	
	@Autowired
	private IPokemonMatchRepository pokeMatchRepo;

	@GetMapping("")
	@JsonView(Views.ViewPokemonMatch.class)
	public List<PokemonMatch> findAll() {
		return pokeMatchRepo.findAll();
	}

	@GetMapping("/{id}")
	@JsonView(Views.ViewPokemonMatch.class)
	public PokemonMatch find(@PathVariable Integer id) {

		Optional<PokemonMatch> optPokeMatch = pokeMatchRepo.findById(id);

		if (optPokeMatch.isPresent()) {
			return optPokeMatch.get();
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
	@JsonView(Views.ViewPokemonMatch.class)
	public PokemonMatch create(@Valid @RequestBody PokemonMatch pokeMatch, BindingResult result) {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur validation");
		}

		pokeMatch = pokeMatchRepo.save(pokeMatch);

		return pokeMatch;
	}

	@PutMapping("/{id}")
	@JsonView(Views.ViewPokemonMatch.class)
	public PokemonMatch update(@RequestBody PokemonMatch pokeMatch, @PathVariable Integer id) {
		if (!pokeMatchRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}

		pokeMatch = pokeMatchRepo.save(pokeMatch);

		return pokeMatch;
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		pokeMatchRepo.deleteById(id);
	}

}
