package fr.florent.bouboule;

import fr.florent.lwjgl.LwjglManager;
import fr.florent.lwjgl.keyboard.EnumKey;
import fr.florent.lwjgl.keyboard.EnumKeyAction;
import fr.florent.lwjgl.window.Window;
import fr.florent.lwjgl.window.WindowPropertyBuilder;

public class App {

    public static void main(String[] args) {

        LwjglManager.init();

        Window window = new Window(WindowPropertyBuilder.builder()
                .width(1800)
                .height(1000)
                .title("Test")
                .build());

        window.addKeyboardEvent((w, k, a) -> {
                    w.closeWindow();
                },
                EnumKey.ESCAPE,
                EnumKeyAction.KEYUP);


        LwjglManager.launch(window, new MainScene());

    }

}
