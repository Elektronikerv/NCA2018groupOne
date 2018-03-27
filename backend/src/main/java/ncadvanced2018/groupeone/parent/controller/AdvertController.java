package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.dto.Feedback;
import ncadvanced2018.groupeone.parent.model.entity.Advert;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealAdvert;
import ncadvanced2018.groupeone.parent.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advert")
public class AdvertController {

    private AdvertService advertService;

    @Autowired
    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }


    @GetMapping
    public ResponseEntity<List<Advert>> findAllAdverts() {
        List<Advert> all = advertService.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
    @GetMapping("/feedback")
    public ResponseEntity<List<Feedback>> findAllFeedback() {
        List<Feedback> all = advertService.findAllFeedback();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Advert>> findAllAdvertsSortedBy(@RequestParam String sortedField,
                                                               @RequestParam boolean asc) {
        List<Advert> allSorted = advertService.findAllSortedBy(sortedField, asc);
        return new ResponseEntity<>(allSorted, HttpStatus.OK);
    }

    @GetMapping("type/{id}")
    public ResponseEntity<List<Advert>> findAllAdvertsWithType(@PathVariable Long id) {
        List<Advert> all = advertService.findAdvertsWithType(id);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Advert> getAdvertById(@PathVariable Long id) {
        Advert byId = advertService.findById(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Advert> create(@RequestBody RealAdvert advert) {
        Advert createdAdvert = advertService.create(advert);
        return new ResponseEntity<>(createdAdvert, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAdvert(@PathVariable Long id) {
        advertService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Advert> updateAdvert(@RequestBody Advert advert, @PathVariable Long id) {
        Advert updatedAdvert = advertService.update(advert);
        return new ResponseEntity<>(updatedAdvert, HttpStatus.CREATED);

    }

}
