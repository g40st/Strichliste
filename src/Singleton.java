import java.io.*;

public class Singleton {
    private static Singleton singleton;
    private static final User[] users = new User[14];
    private static final Price price = new Price(); 
    
    private Singleton() {
        users[0] = new User("dummy");
        users[1] = new User("Barth");
        users[2] = new User("Timo");
		users[3] = new User("Schwanz");
        users[4] = new User("Jenny");
        users[5] = new User("Denise");
        users[6] = new User("Jonas");
        users[7] = new User("ghost");
        users[8] = new User("Jan");
		users[9] = new User("Glaser");
		users[10] = new User("Karl-Josef");
		users[11] = new User("Wohnhaas");
        users[12] = new User("Jannic");
        users[13] = new User("Michi");
    }

    public static synchronized Singleton getInstance(){
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
}
class Serial {
    private final static String fileName = new String("strichliste.txt");
    private final static String userDir = System.getProperty("user.home");
    private final static String separator = File.separator;
    private final static String filePath = userDir+separator+fileName;
    // auf Windows anpassen
    private final static String path = "/home/ghost/Downloads/"; 
    public static void writeToFile(User[] users) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(path + fileName));
            
            File file = new File(path + "backup.txt");
            file.createNewFile();
            FileWriter writer = new FileWriter(file); 
            writer.write("Name | AntiAlk  Bier  Schnaps  Shots \n");

            for (int i = 0; i < users.length ; i++) {
                out.writeObject(users[i]);
                out2.writeObject(users[i]);
                writer.write(users[i].getName() + " | " + users[i].getAntiAlk() + "  " + users[i].getBeer() + "  " + users[i].getSchnaps() + "  " + users[i].getShot() + "\n");
                writer.flush(); 
            }
            out.close();
            out2.close();
            writer.close();

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
            System.out.println("Exception beim Read " + ex);
        }
    }
}
