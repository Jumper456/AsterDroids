package net.yotvoo.asterd.app;


import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.BitSet;

/**
 * Controll handles keys pressing etc
 */
public class Controll {
    private BitSet keyboardBitSet = new BitSet();
    private GameView gameView;


    public Controll(GameView gameView) {
        this.gameView = gameView;

        gameView.getScene().addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        gameView.getScene().addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);

        updateKeyboardStatus();        // init label text
    }

    public boolean checkIfKeyPressed(KeyCode keyCode){
        return keyboardBitSet.get(keyCode.ordinal());
    }


    /**
     * "Key Pressed" handler for all input events: register pressed key in the bitset
     */
    private EventHandler<KeyEvent> keyPressedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

            // register key down
            keyboardBitSet.set(event.getCode().ordinal(), true);
            updateKeyboardStatus();
        }
    };

    /**
     * "Key Released" handler for all input events: unregister released key in the bitset
     */
    private EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

            // register key up
            keyboardBitSet.set(event.getCode().ordinal(), false);
            updateKeyboardStatus();
        }
    };

    /**
     * Detect all keys and show them in the label
     */
    private void updateKeyboardStatus() {

        StringBuilder sb = new StringBuilder();
        sb.append("Current key combination: ");

        int count = 0;
        for( KeyCode keyCode: KeyCode.values()) {

            if( keyboardBitSet.get(keyCode.ordinal())) {

                if( count > 0) {
                    sb.append(" ");
                }

                sb.append(keyCode.toString());

                count++;
            }

        }

        gameView.updatePressedKeysLabel(sb.toString());
    }
}
