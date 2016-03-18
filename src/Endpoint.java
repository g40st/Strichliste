import java.io.IOException;
import java.nio.ByteBuffer;

import java.util.List;

import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnClose;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.EncodeException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@ServerEndpoint("/bude")
public class Endpoint {
    Singleton singleton = Singleton.getInstance();

    Thread broadcast = new BroadcastThread();

    @OnOpen
    public void opened(Session session) throws IOException {
        ConnectionManager.addTmpSession(session);
        if(!broadcast.isAlive()) {
            broadcast.start();
        }
    }

    @SuppressWarnings("unchecked")
    @OnMessage
    public void receiveMessage(Session session, String message, boolean last) throws ParseException, IOException, EncodeException {
        JSONObject jsonMessage = (JSONObject)new JSONParser().parse(message);
        int msgType = Integer.parseInt((String) jsonMessage.get("Type"));
        System.out.println("Nachricht von Client: " + jsonMessage);
        if(msgType == 1) {
            User usertmp[] = singleton.getUsers();
            JSONObject userList = new JSONObject();
            userList.put("Type", "2");
            userList.put("Length", usertmp.length);
            JSONArray users = new JSONArray();
        
            for (int i = 0; i < usertmp.length; i++) {
                JSONObject tmp = new JSONObject();
                tmp.put("Username", usertmp[i].getName());
                tmp.put("AntiAlk", usertmp[i].getAntiAlk());
                tmp.put("Beer", usertmp[i].getBeer());
                tmp.put("Schnaps", usertmp[i].getSchnaps());
                tmp.put("Shot", usertmp[i].getShot());
                users.add(tmp);   
                userList.put("Users", users);
            }
            try {
                session.getBasicRemote().sendText(userList.toJSONString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if(msgType == 3) {
            User usertmp[] = singleton.getUsers();
            for (int i = 0; i < usertmp.length; i++) {
                String msgUsername = (String) jsonMessage.get("Username").toString();
                if(msgUsername.equals(usertmp[i].getName())) {
                    String msgContent = (String) jsonMessage.get("Content").toString();
                    switch(msgContent) {
                        case "AntiAlk":
                            singleton.incAntiAlkSingleton(usertmp[i]);
                        break;
                        case "Beer":
                            singleton.incBeerSingleton(usertmp[i]);
                        break;
                        case "Schnaps":
                            singleton.incSchnapsSingleton(usertmp[i]);
                        break;
                        case "Shot":
                            singleton.incShotSingleton(usertmp[i]);
                        break;
                    }
                    JSONObject userList2 = new JSONObject();
                    userList2.put("Type", "3");
                    userList2.put("Length", "1");
                    JSONArray user = new JSONArray();
                    JSONObject tmp = new JSONObject();
                    tmp.put("Username", usertmp[i].getName());
                    tmp.put("AntiAlk", usertmp[i].getAntiAlk());
                    tmp.put("Beer", usertmp[i].getBeer());
                    tmp.put("Schnaps", usertmp[i].getSchnaps());
                    tmp.put("Shot", usertmp[i].getShot());
                    user.add(tmp);   
                    userList2.put("Users", user);
          
                    try {
                        session.getBasicRemote().sendText(userList2.toJSONString());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        } else if(msgType == 8) { 
            String msgPassword = (String) jsonMessage.get("Password").toString();
            boolean pwOK = false;
            if(msgPassword.equals("admin")) {
                pwOK = true;
                
            }

            JSONObject userPassword = new JSONObject();
            userPassword.put("Type", "9");
            userPassword.put("Length", "1");
            userPassword.put("Login", pwOK);
            try {
                session.getBasicRemote().sendText(userPassword.toJSONString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if(msgType == 10) {
            User usertmp[] = singleton.getUsers();
            for (int i = 0; i < usertmp.length; i++) {
                String msgUsername = (String) jsonMessage.get("Username").toString();
                if(msgUsername.equals(usertmp[i].getName())) {
                    singleton.setData(usertmp[i], 0);
                }
            }
        } 
    }

    @OnClose
    public void closed(Session session) {
        ConnectionManager.removeTmpSession(session);
    }

    public static synchronized void sendJSON(JSONObject tmp) {
        List<Session> Sessions = ConnectionManager.getTmpSessions();
        if(Sessions.size() > 0) {
            for (Session tempS : Sessions) {
                try {
                    tempS.getBasicRemote().sendText(tmp.toJSONString());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}

class BroadcastThread extends Thread {
    Singleton singleton = Singleton.getInstance();

    BroadcastThread() {}
    
    @SuppressWarnings("unchecked")
    public void run() {
    /*  System.out.println("--------------------------------------------------------------");
        System.out.println("TrainBroadcastThread:");
        ConnectionManager.printall();
        System.out.println("---------------------------------------------------------------");
    */
        // Userarray beziehen        
        User usertmp[] = singleton.getUsers();
        Price userPrice = singleton.getPrice();

        JSONObject userList = new JSONObject();
        userList.put("Type", "1");
        userList.put("Length", usertmp.length);
        userList.put("PriceAntiAlk", userPrice.getPrAntiAlk());
        userList.put("PriceBeer", userPrice.getPrBeer());
        userList.put("PriceSchnaps", userPrice.getPrSchnaps());
        userList.put("PriceShot", userPrice.getPrShots());
        userList.put("Length", usertmp.length);
        JSONArray users = new JSONArray();
        
        for (int i = 0; i < usertmp.length; i++) {
            JSONObject tmp = new JSONObject();
            tmp.put("Username", usertmp[i].getName());
            tmp.put("AntiAlk", usertmp[i].getAntiAlk());
            tmp.put("Beer", usertmp[i].getBeer());
            tmp.put("Schnaps", usertmp[i].getSchnaps());
            tmp.put("Shot", usertmp[i].getShot());
            users.add(tmp);   
            userList.put("Users", users);
        }
        Endpoint.sendJSON(userList);
    }   
}