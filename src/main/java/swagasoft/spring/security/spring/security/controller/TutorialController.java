package swagasoft.spring.security.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swagasoft.spring.security.spring.security.model.Tutorial;
import swagasoft.spring.security.spring.security.repository.TutorialRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
//@RequestMapping(value = "/api")
public class TutorialController {

    @Autowired
     TutorialRepository tutorialRepository;

    @GetMapping(value = "/tutorials")
    public ResponseEntity<List<Tutorial>>  getAllTutorials(@RequestParam(required = false) String title){
        try {
            List <Tutorial> tutorials= new ArrayList<Tutorial>();
            if (title == null)
                tutorialRepository.findAll().forEach(tutorials::add);
            else
                tutorialRepository.findTitleContainingString(title).forEach(tutorials::add);

                if (tutorials.isEmpty()){
                    return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return  new ResponseEntity< >(tutorials, HttpStatus.OK);


        }catch (Exception e){
            return new  ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping(value = "/tutorial/{id}")
    public ResponseEntity <Tutorial> getTutorialById(@PathVariable("id") long id){
        Optional<Tutorial>  tutorialData = tutorialRepository.findById(id);
        if (tutorialData.isPresent()){
            return  new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = "/tutorials")
    public  ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial){
        try {

            Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(),false ));
            return  new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        }catch (Exception e){
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial){
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()){
            Tutorial _tutorial = tutorialData.get();
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            return  new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(value = "/tutorials/{id}")
    public ResponseEntity<Tutorial> deleteTutorial(@PathVariable("id") long id){
        try {
            tutorialRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.valueOf("action was successful"));

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping(value = "/tutorials/published")
    public ResponseEntity<Tutorial> findByPublished(){

        try {

        List<Tutorial> tutorials =  tutorialRepository.findByPublished(true);
        if (tutorials.isEmpty()){
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return  new ResponseEntity<>(HttpStatus.valueOf("something went wrong"));

    }


    @GetMapping(value = "/hello")
    public String helloRoute(){
        return " hello from server";
    }
}
