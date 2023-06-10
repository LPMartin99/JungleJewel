public class Score{
    private String profileName;
    private int score;

    public Score(String name, int score){
        this.profileName = name;
        this.score = score;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}