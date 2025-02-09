package hr.unizg.fer.backend.controller.primary;

import hr.unizg.fer.backend.model.primary.GrupaZaDigitalizaciju;
import hr.unizg.fer.backend.model.primary.StatusDigitalizacije;
import hr.unizg.fer.backend.service.primary.GrupaZaDigitalizacijuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/grupaZaDigitalizaciju")
public class GrupaZaDigitalizacijuController {
    private final GrupaZaDigitalizacijuService grupaZaDigitalizacijuService;

    @Autowired
    public GrupaZaDigitalizacijuController(GrupaZaDigitalizacijuService grupaZaDigitalizacijuService) {
        this.grupaZaDigitalizacijuService = grupaZaDigitalizacijuService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<GrupaZaDigitalizaciju>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(grupaZaDigitalizacijuService.getAll());
    }

    @GetMapping("/statusCounts/{statusDigitalizacije}")
    public Long getStatusCounts(@PathVariable StatusDigitalizacije statusDigitalizacije) {
        return grupaZaDigitalizacijuService.getFilmCountByStatus(statusDigitalizacije);
    }

    @GetMapping("/groupsOut/{idKorisnika}")
    public Long getGroupsTakenOutStatistics(@PathVariable Long idKorisnika) {
        return grupaZaDigitalizacijuService.countGroupsTakenOutByUser(idKorisnika);
    }

    @GetMapping("/groupsReturned/{idKorisnika}")
    public Long getGroupsReturnedStatistics(@PathVariable Long idKorisnika) {
        return grupaZaDigitalizacijuService.countGroupsReturnedByUser(idKorisnika);
    }

    //provjerit ko može slat na digitalizaciju pa ako treba radit provjeru
    // u tijelu dobijam naslove filmova koje zelim dodati u grupu za digitalizaciju
    @PostMapping(path = "/add")
    public ResponseEntity<GrupaZaDigitalizaciju> createGroup(@RequestBody GrupaZaDigitalizaciju
                                                                         grupaZaDigitalizaciju) {
        try{
            GrupaZaDigitalizaciju updatedGrupaZaDigitalizaciju = grupaZaDigitalizacijuService.createGroup(grupaZaDigitalizaciju);
            return ResponseEntity.status(HttpStatus.OK).body(updatedGrupaZaDigitalizaciju);
        } catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(path = "getFilmsInGroup/{idGrupe}")
    public ResponseEntity<List<String>> getFilmsInGroup(@PathVariable Long idGrupe) {
        try{
            List<String> filmsReturned = grupaZaDigitalizacijuService.getFilmsInGroup(idGrupe);
            return ResponseEntity.status(HttpStatus.OK).body(filmsReturned);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
