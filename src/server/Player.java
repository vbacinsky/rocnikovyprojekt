package server;

public class Player {
    private String nick;
    private boolean startedMission;
    private Mission currentMission;
    private int points = 0;

    //asi nieco ze kde sa nachadza jeho figurka





    public Player(String nick, boolean startedMission, Mission currentMission) {
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

    public String getNick(){
        return this.nick;
    }

}
