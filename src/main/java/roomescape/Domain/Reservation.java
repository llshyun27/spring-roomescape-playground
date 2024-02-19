package roomescape.Domain;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;
    public Reservation(long id, String name, String date, String time){
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
    public Reservation toEntity(Reservation reservation,Long id){
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
    public long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getDate(){
        return date;
    }
    public String getTime(){
        return time;
    }
}
