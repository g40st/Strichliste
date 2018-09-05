import java.io.*;
import java.util.*;

public class Singleton {
    private static Singleton singleton;
    private static User[] users;
    private static final Price price = new Price();

    private Singleton() {
      // read the users from the text file
      try {
        Scanner sc = new Scanner(new File("/home/user/data/users.txt"));
        int count_users = 0;
        while(sc.hasNextLine()) {
            sc.nextLine();
            count_users++;
        }
        users = new User[count_users];
        sc.close();

        Scanner sc2 = new Scanner(new File("/home/user/data/users.txt"));
        int i = 0;
        while(sc2.hasNextLine()) {
            users[i] = new User(sc2.nextLine());
            i++;
        }
        sc2.close();
      } catch(Exception e) {
        e.printStackTrace();
      }
    }

    public static synchronized Singleton getInstance() {
        if (singleton == null){
            singleton = new Singleton();
            Serial serial = new Serial();
            serial.readFromFile(users);
        }
        return singleton;
    }

    public synchronized User[] getUsers() {
        return users;
    }

    public synchronized Price getPrice () {
        return price;
    }

    public synchronized void incAntiAlkSingleton(User user) {
        user.incAntiAlk();
        Serial serial = new Serial();
        serial.writeToFile(users);
    }
    public synchronized void incSchnapsSingleton(User user) {
        user.incSchnaps();
        Serial serial = new Serial();
        serial.writeToFile(users);
    }
    public synchronized void incBeerSingleton(User user) {
        user.incBeer();
        Serial serial = new Serial();
        serial.writeToFile(users);
    }
    public synchronized void incShotSingleton(User user) {
        user.incShot();
        Serial serial = new Serial();
        serial.writeToFile(users);
    }

    public synchronized void setDataFromFile(User user, int i) {
        users[i].setAntiAlk(user.getAntiAlk());
        users[i].setBeer(user.getBeer());
        users[i].setSchnaps(user.getSchnaps());
        users[i].setShot(user.getShot());
    }

    public synchronized void setData(User user, int i) {
        user.setAntiAlk(i);
        user.setBeer(i);
        user.setSchnaps(i);
        user.setShot(i);
        Serial serial = new Serial();
        serial.writeToFile(users);
    }

    public synchronized void setDataMinusTen(User user, int i) {
        user.setMinusTen(10);
        Serial serial = new Serial();
        serial.writeToFile(users);
    }

    public synchronized void writeToStick() {
        Serial serial = new Serial();
        serial.writeToStick(users);
    }
}
class Serial {
    // Linux (docker container)
    private final static String fileName = new String("/home/user/data/StrichlisteDB.txt");
    // Backup
    private final static String stickFilePath = new String("/home/user/backup/BackupStrichlisteDB.txt");

    public static void writeToFile(User[] users) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            for (int i = 0; i < users.length ; i++) {
                out.writeObject(users[i]);
            }
            out.close();
        } catch(Exception ex) {
            System.out.println("Exception " + ex);
        }
    }
    public static void writeToStick(User[] users) {
        if(stickFilePath != null) {
            try {
                ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(stickFilePath));
                for (int i = 0; i < users.length ; i++) {
                    out2.writeObject(users[i]);
                }
                out2.close();
            } catch(Exception ex) {
                System.out.println("Exception " + ex);
            }
        }
    }
    public static void readFromFile(User[] users) {
        Singleton singleton = Singleton.getInstance();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            for (int i = 0; i < users.length; i++) {
                User tmp = (User) in.readObject();
                singleton.setDataFromFile(tmp, i);
            }
            in.close();
        } catch(Exception ex) {
            System.out.println("Exception " + ex);
        }
    }
}
