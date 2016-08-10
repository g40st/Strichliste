import java.io.*;

public class User implements Serializable {
    String name = "";
    int antiAlk = 0;
    int beer = 0;
    int schnaps = 0;
    int shot = 0;

    public User (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAntiAlk() {
        return antiAlk;
    }

    public int getBeer() {
        return beer;
    }

    public int getSchnaps() {
        return schnaps;
    }

    public int getShot() {
        return shot;
    }

    public void incAntiAlk() {
        antiAlk++;
    }
   
    public void incBeer() {
        beer++;
    }

    public void incSchnaps() {
        schnaps++;
    }

    public void incShot() {
        shot++;
    }

    public void setAntiAlk(int count) {
        this.antiAlk = count;
    }

    public void setBeer(int count) {
        this.beer = count;
    }

    public void setSchnaps(int count) {
        this.schnaps = count;
    }

    public void setShot(int count) {
        this.shot = count;
    }

    public void setMinusTen(int count) {
        double tmp = 1;
        if((antiAlk *1 + beer * 1.5 + schnaps * 2 + shot * 1) > count) { 
            while(tmp <= count){
                while(antiAlk > 0) {
                    if(tmp <= count) {
                        antiAlk--;
                        tmp++;
                    } else {
                        break;
                    }
                }
                
                while(schnaps > 0) {
                    if(tmp <= count) {
                        schnaps--;
                        tmp += 2.00;
                    } else {
                        break;
                    }
                }
                while(shot > 0) {
                    if(tmp <= count) {
                        shot--;
                        tmp++;
                    } else {
                        break;
                    }
                }

                while(beer > 0) {                   
                    if(tmp <= count) {
                        beer--;
                        tmp += 1.5;
                    } else {
                        break;
                    }
                }
            }
            if(tmp == 11.5) {
                shot++;   
            } 
        }
    }
}