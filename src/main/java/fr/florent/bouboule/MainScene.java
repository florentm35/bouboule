package fr.florent.bouboule;

import fr.florent.bouboule.model.Boule;
import fr.florent.lwjgl.scene.Scene;
import fr.florent.lwjgl.shape.CircleShape;
import fr.florent.lwjgl.shape.ColorShape;
import fr.florent.lwjgl.utils.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainScene extends Scene {

    private static final int radius = 50;
    private static final int screenWidth = 1800;
    private static final int screenHeight = 1000;

    private Map<CircleShape, Boule> mapShapeBoule = new HashMap<>();

    private final Random random = new Random();

    @Override
    public void onInit() {


        for (int i = 0; i < 10; i++) {
            Boule boule = createBoule(random);
            CircleShape circleShape = createShapeFromBoule(boule);
            addShape(circleShape);



            mapShapeBoule.put(circleShape, boule);
        }
    }

    //xB = R * cos +xO
    //Yb = -R * sin + yO
    @Override
    public void update() {

        mapShapeBoule.entrySet().stream()
                .forEach(e -> {
                    Boule boule = e.getValue();

                    Point tmpPoint = getTmpPoint(boule);

                    int angle = boule.getDirection();

                    boolean left = tmpPoint.getX() - radius <= 0;
                    boolean rigth = tmpPoint.getX() + radius >= screenWidth;

                    boolean bottom = tmpPoint.getY() - radius <= 0;
                    boolean top = tmpPoint.getY() + radius >= screenHeight;






                });

    }

    private Point getTmpPoint(Boule boule) {
        double x = boule.getVitesse() * Math.cos(boule.getDirection() * Math.PI / 180.0) + boule.getPosition().getX();
        double y = boule.getVitesse() * Math.sin(boule.getDirection() * Math.PI / 180.0) + boule.getPosition().getY();

        return new Point((int)x, (int)y);
    }

    private Boule createBoule(Random random) {
        Boule boule = new Boule();
        boule.setVitesse(random.nextInt() % 15);
        boule.setPosition(new Point((random.nextInt() % (screenWidth-2*radius)) + radius,
                (random.nextInt() % (screenHeight-2*radius)) + radius));
        boule.setDirection(random.nextInt() % 360);
        return boule;
    }

    private CircleShape createShapeFromBoule(Boule boule) {
        return new CircleShape(new ColorShape(random.nextInt() % 255, random.nextInt() % 255, random.nextInt() % 255),
                boule.getPosition(),
                radius);
    }

}
