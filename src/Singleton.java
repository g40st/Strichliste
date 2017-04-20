import java.io.*;

public class Singleton {
    private static Singleton singleton;
    private static final User[] users = new User[16];
    private static final Price price = new Price(); 
    
    private Singleton() {
        users[0] = new User("Michi");
        users[1] = new User("Barth");
        users[2] = new User("Timo");
		users[3] = new User("Schwanz");
        users[4] = new User("Jenny");
        users[5] = new User("Denise");
        users[6] = new User("Steck");
        users[7] = new User("ghost");
        users[8] = new User("Jan");
		users[9] = new User("Glaser");
		users[10] = new User("Karl-Josef");
		users[11] = new User("Wohnhaas");
        users[12] = new User("Jannic");
        users[13] = new User("Pitsch");
        users[14] = new User("Achim");
        users[15] = new User("Ralle");
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
    // Linux (user home)
    private final static String userDir = System.getProperty("user.home");
    private final static String fileName = new String(userDir + "/StrichlisteDB.txt");

    // Unter Windows /apache-tomcat/bin
    //private final static String fileName = new String("StrichlisteDB.txt");
    
    // Backup auf USB-Stick gemounted unter F: (fuer Windows)
    private final static String stickFilePath = "F:\\" +fileName; 

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
    public static void readFromFile(User[] users) {
        Singleton singleton = Singleton.getInstance();
        try {        
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            for (int i = 0; i < users.length ; i++) {
                User tmp = (User) in.readObject();
                singleton.setDataFromFile(tmp, i);
            }
            in.close();
        } catch(Exception ex) {
            System.out.println("Exception " + ex);
        }
    }
}