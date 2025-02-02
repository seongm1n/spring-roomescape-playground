package roomescape.domain.entity;

public class Reservation {
    private Long id;
    private String name;
    private String data;
    private String time;

    public Reservation(Long id, String name, String data, String time) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.time = time;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getData() { return data; }
    public String getTime() { return time; }
}
