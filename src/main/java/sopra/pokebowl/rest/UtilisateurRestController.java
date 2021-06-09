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

import sopra.pokebowl.model.Utilisateur;
import sopra.pokebowl.model.Views;
import sopra.pokebowl.repository.IUtilisateurRepository;

@RestController
@RequestMapping("/utilisateur")
@CrossOrigin("*")
public class UtilisateurRestController {

	@Autowired
	private IUtilisateurRepository utilisateurRepo;
	
	@GetMapping("")
	@JsonView(Views.ViewUtilisateur.class)
	public List<Utilisateur> findAll() {
		return utilisateurRepo.findAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.ViewUtilisateur.class)
	public Utilisateur find(@PathVariable Long id) {
		Optional<Utilisateur> optUtilisateur = utilisateurRepo.findById(id);
		
		if(optUtilisateur.isPresent()) {
			return optUtilisateur.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}
	}
	
	@PostMapping("")
	@JsonView(Views.ViewUtilisateur.class)
	public Utilisateur create(@Valid @RequestBody Utilisateur utilisateur, BindingResult result) {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid");
		}
		
		utilisateur = utilisateurRepo.save(utilisateur);
		
		return utilisateur;
	}
	
	@PutMapping("/{id}")
	@JsonView(Views.ViewUtilisateur.class)
	public Utilisateur update(@RequestBody Utilisateur utilisateur, @PathVariable Long id) {
		if (!utilisateurRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}
		
		utilisateur = utilisateurRepo.save(utilisateur);
		
		return utilisateur;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		utilisateurRepo.deleteById(id);
	}
}
