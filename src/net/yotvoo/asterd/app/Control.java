package net.yotvoo.asterd.app;


import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.BitSet;

/**
 * Control handles keys pressing etc
 */
class Control {
    private final BitSet keyboardBitSet = new BitSet();
    private final GameView gameView;


    @SuppressWarnings("Convert2Lambda")
    Control(GameView gameView) {
        this.gameView = gameView;

     /*
      "Key Pressed" handler for all input events: register pressed key in the bitset
     */
        @SuppressWarnings("Convert2Lambda") EventHandler<KeyEvent> keyPressedEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                // register key down
                keyboardBitSet.set(event.getCode().ordinal(), true);
                updateKeyboardStatus();
            }
        };
        gameView.getScene().addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);

     /*
      "Key Released" handler for all input events: unregister released key in the bitset
     */
        EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                // register key up
                keyboardBitSet.set(event.getCode().ordinal(), false);
                updateKeyboardStatus();
            }
        };
        gameView.getScene().addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);

        updateKeyboardStatus();        // init label text
    }

    boolean checkIfKeyPressed(KeyCode keyCode){
        return keyboardBitSet.get(keyCode.ordinal());
    }



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
