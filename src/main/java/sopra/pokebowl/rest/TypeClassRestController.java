package sopra.pokebowl.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

import sopra.pokebowl.model.TypeClass;
import sopra.pokebowl.model.Views;
import sopra.pokebowl.repository.ITypeClassRepository;

@RestController
@RequestMapping("/typeClass")
@CrossOrigin("*")
public class TypeClassRestController {

	@Autowired
	private ITypeClassRepository typeClassRepo;
	
	@GetMapping("")
	@JsonView(Views.ViewTypeClass.class)
	public List<TypeClass> findAll() {
		return typeClassRepo.findAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.ViewTypeClass.class)
	public TypeClass find(@PathVariable Long id) {
		Optional<TypeClass> opttypeClass = typeClassRepo.findById(id);
		
		if(opttypeClass.isPresent()) {
			return opttypeClass.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}
	}
}
