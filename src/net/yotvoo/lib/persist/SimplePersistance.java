package net.yotvoo.lib.persist;

import net.yotvoo.lib.network.ConnectionSettings;

import java.io.*;

/**
 * Simple persistance library.
 * Allows to persis for instance game settings/ configuration, high scores, etc
 */
public class SimplePersistance {

    private SimplePersistance() {
    }

    public static Long loadSimpleHighScore(String scoresFileName) {
        try {
            Long score = new DataInputStream(new FileInputStream(scoresFileName)).readLong();
            System.out.println("Loaded hiScore: " + score);
            return score;
        } catch (Exception e) {
            System.out.println("Exception during loading HiScore: " + e.toString());
            return 0L;
        }
    }

    public static void saveSimpleHighScore(String scoresFileName, Long score) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(scoresFileName));
            out.writeLong(score);
            out.flush();
            out.close();
            System.out.println("Saved HiScore: " + score);
        } catch (Exception e) {
            System.out.println("Exception during saving HiScore: " + e.toString());
        }
    }

    public static ConnectionSettings loadConnectionSettingsSingle(String connectionFileName) {
        try {
            ConnectionSettings connectionSettings = (ConnectionSettings) new ObjectInputStream(new FileInputStream(connectionFileName)).readObject();
            System.out.println("Loaded Connection Settings: " + connectionSettings);
            return connectionSettings;
        } catch (Exception e) {
            System.out.println("Exception during loading Connection Settings: " + e.toString());
            return null;
        }
    }

    public static void saveConnectionSettingsSingle(String connectionFileName, ConnectionSettings connectionSettings) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(connectionFileName));
            out.writeObject(connectionSettings);
            out.flush();
            out.close();
            System.out.println("Saved Connection Settings: " + connectionSettings.toString());
        } catch (Exception e) {
            System.out.println("Exception during saving Connection Settings " + e.toString());
        }
    }


/*
    public String getPlayerNick() {
        return playerNick;
    }

    public void setPlayerNick(String playerNick) {
        this.playerNick = playerNick;
    }
*/

}
