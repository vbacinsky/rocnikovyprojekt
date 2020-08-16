package server;

public class Mission {
    private String from;
    private String to;
    private String points;

    public Mission(String from, String to, String points) {
        this.from = from;
        this.to = to;
        this.points = points;
    }

    public String getFrom() {
        return from;
    }

    public String getTo(){
        return to;
    }

    public String getPoints() {
        return this.points;
    }
}
