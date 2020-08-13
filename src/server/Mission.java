package server;

public class Mission {
    private String from;
    private String to;
    private String reward;
    private String points;

    public Mission(String from, String to, String reward, String points) {
        this.from = from;
        this.to = to;
        this.reward = reward;
        this.points = points;
    }

    public String getFrom() {
        return from;
    }

    public String getTo(){
        return to;
    }
}
