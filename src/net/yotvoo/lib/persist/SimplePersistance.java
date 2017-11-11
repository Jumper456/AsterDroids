package net.yotvoo.lib.persist;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Simple persistance library.
 * Allows to persis for instance game settings/ configuration, high scores, etc
 */
public class SimplePersistance {
    private String scoresFileName;
    private String playerNick;


    public SimplePersistance(String scoresFileName) {
        this.scoresFileName = scoresFileName;
    }

    public Long loadSimpleHighScore() {
        try {
            Long score = new DataInputStream(new FileInputStream(scoresFileName)).readLong();
            System.out.println("Loaded hiScore: " + score);
            return score;
        } catch (Exception e) {
            System.out.println("Exception during loading HiScore: " + e.toString());
            return 0L;
        }
    }

    public void saveSimpleHighScore(Long score) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(scoresFileName));
            out.writeLong(score);
            out.flush();
            System.out.println("Saved HiScore: " + score);
        } catch (Exception e) {
            System.out.println("Exception during saving HiScore: " + e.toString());
        }
    }

    public String getPlayerNick() {
        return playerNick;
    }

    public void setPlayerNick(String playerNick) {
        this.playerNick = playerNick;
    }

}
