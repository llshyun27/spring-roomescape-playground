package roomescape.Controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import roomescape.Domain.Reservation;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }
    private List<Reservation>reservations = new ArrayList<>();
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>>getReservations(){
        return ResponseEntity.ok(reservations);
    }
    private AtomicLong index = new AtomicLong(1);
    @PostMapping("/reservations")
    public ResponseEntity<?> addReservation(@RequestBody Reservation reservation){
        if(reservation ==null || StringUtils.isEmpty(reservation.getName())||StringUtils.isEmpty(reservation.getDate())||StringUtils.isEmpty(reservation.getTime())){
            return ResponseEntity.badRequest().body("필수정보 누락");
        }
        Reservation newReservation = reservation.toEntity(reservation,index.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){
        try{
            Reservation reservation = reservations.stream()
                    .filter(it -> Objects.equals(it.getId(),id))
                    .findFirst().orElseThrow(() -> new NoSuchElementException("삭제할 유저가 없습니다."));

            reservations.remove(reservation);
            return ResponseEntity.noContent().build();
        } catch(NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
}
