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
        double tmp = 0;
        Price price = new Price();
        // > 10 €
        if((antiAlk * price.getPrAntiAlk() + beer * price.getPrBeer() + schnaps * price.getPrSchnaps() + shot * price.getPrShots()) >= count) { 
            System.out.println("tmp: " + tmp);
            while(tmp < count){
                if(antiAlk != 0) {
                    antiAlk--;
                    tmp = tmp + price.getPrAntiAlk(); 
                } else if(schnaps != 0) {
                    schnaps--;
                    tmp = tmp + price.getPrSchnaps();
                } else if(shot != 0) {
                    shot--;
                    tmp = tmp + price.getPrShots();
                } else {
                    beer--;
                    tmp = tmp + price.getPrBeer();
                }
            }
            if(tmp == 10.5) {
                shot++;
            } else if(tmp == 11) {
                antiAlk++;
            } else if(tmp == 11.5) {
                antiAlk++;
                shot++;
            }
        }
    }
}