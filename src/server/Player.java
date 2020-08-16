package server;

public class Player {
    private String nick;
    private boolean startedMission;
    private Mission currentMission;
    private int points = 0;
    private int act_position;
    private String color;

    //asi nieco ze kde sa nachadza jeho figurka





    public Player(String nick, boolean startedMission, Mission currentMission, int start_position, String color) {
        this.color = color;
        this.act_position = start_position;
        this.nick = nick;
        this.startedMission = startedMission;
        this.currentMission = currentMission;
    }


    public int getPoints() {
        return this.points;
    }

    public Mission getCurrentMission() {
        return this.currentMission;
    }

    public void setCurrentMission(Mission newMission) {
        this.currentMission = newMission;
    }

    public String getNick(){
        return this.nick;
    }

    public int getAct_position() {
        return this.act_position;
    }

    public void setAct_position(int newActPos) {
        this.act_position = newActPos;
    }

    public void setStartedMission(boolean bool) {
        this.startedMission = bool;
    }

    public boolean getStartedMission() {
        return this.startedMission;
    }

    public void setPoints(int plusPoints) {
        this.points += plusPoints;
    }

    public String getColor() {
        return this.color;
    }

}
