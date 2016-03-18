import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;


public class ConnectionManager {
	
	// Tmp Sessionlist fuer nicht angemeldete user
	private static List<Session> sessionList = Collections.synchronizedList(new ArrayList<Session>());

    // Ausgabe aller aktiven Sessions
    public static synchronized void printall() {
    	System.out.println("Anzahl temp Sessions: " + sessionList.size());
    	for (Session tempS : sessionList) {
    		System.out.println("tmpSessions: " + tempS);
    	}
    }
    
    public static synchronized int sessionCount() {
        return sessionList.size();
    }
    
    // TmpSession handling
    public static synchronized void addTmpSession(Session session) {
    	sessionList.add(session);
    }
    
    public static synchronized boolean removeTmpSession(Session session) {
    	if(sessionList.remove(session)) {
    		return true;
    	} 
    	return false;
    }
    
    public static synchronized List<Session> getTmpSessions() {
    	return sessionList;
    }
}
