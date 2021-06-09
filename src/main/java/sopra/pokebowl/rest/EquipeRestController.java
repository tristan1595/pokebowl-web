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

import sopra.pokebowl.model.Equipe;
import sopra.pokebowl.model.Views;
import sopra.pokebowl.repository.IEquipeRepository;

@RestController
@RequestMapping("/equipe")
@CrossOrigin("*")
public class EquipeRestController {
	
	@Autowired
	private IEquipeRepository equipeRepo;

	@GetMapping("")
	@JsonView(Views.ViewEquipe.class)
	public List<Equipe> findAll() {
		return equipeRepo.findAll();
	}

	@GetMapping("/{id}")
	@JsonView(Views.ViewEquipe.class)
	public Equipe find(@PathVariable Long id) {

		Optional<Equipe> optEquipe = equipeRepo.findById(id);

		if (optEquipe.isPresent()) {
			return optEquipe.get();
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
	@JsonView(Views.ViewEquipe.class)
	public Equipe create(@Valid @RequestBody Equipe equipe, BindingResult result) {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur validation");
		}

		equipe = equipeRepo.save(equipe);

		return equipe;
	}

	@PutMapping("/{id}")
	@JsonView(Views.ViewEquipe.class)
	public Equipe update(@RequestBody Equipe equipe, @PathVariable Long id) {
		if (!equipeRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}

		equipe = equipeRepo.save(equipe);

		return equipe;
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		equipeRepo.deleteById(id);
	}

}
