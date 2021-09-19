package fr.florent.bouboule;

import fr.florent.bouboule.model.Boule;
import fr.florent.lwjgl.scene.Scene;
import fr.florent.lwjgl.shape.CircleShape;
import fr.florent.lwjgl.shape.ColorShape;
import fr.florent.lwjgl.shape.PointShape;
import fr.florent.lwjgl.utils.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainScene extends Scene {

    private static final int radius = 50;
    private static final int screenWidth = 1000;
    private static final int screenHeight = 800;

    private Map<CircleShape, Boule> mapShapeBoule = new HashMap<>();

    private final Random random = new Random();

    @Override
    public void onInit() {

        addShape(new PointShape(ColorShape.BLACK,
                new Point(0, 0),
                10));

        addShape(new PointShape(ColorShape.RED,
                new Point(screenWidth, screenHeight),
                10));

        for (int i = 0; i < 12; i++) {
            Boule boule = createBoule();
            CircleShape circleShape = createShapeFromBoule(boule);
            addShape(circleShape);
            mapShapeBoule.put(circleShape, boule);
        }

    }


    @Override
    public void update() {

        mapShapeBoule.forEach((key, boule) -> {

            Point tmpPoint = getTmpPoint(boule.getVitesse(), boule.getDirection(), boule.getPosition());

            int direction = boule.getDirection();

            int radius = key.getRadius();
            Point centre = key.getCenter();

            boolean left = tmpPoint.getX() - radius <= 0;
            boolean rigth = tmpPoint.getX() + radius >= screenWidth;

            boolean bottom = tmpPoint.getY() - radius <= 0;
            boolean top = tmpPoint.getY() + radius >= screenHeight;

            if (left) {
                direction = (direction > 180 ? 360 - direction - 180 : 180 - direction);
            } else if (rigth) {
                direction = (direction > 45 ? 360 - direction + 180 : 180 + direction);
            } else if (bottom) {
                direction = (direction > 270 ? 90 - direction - 270 : 270 - direction + 90);
            } else if (top) {
                direction = (direction > 90 ? 270 - direction - 90 : 270 - direction + 90);
            }

            key.setCenter(tmpPoint);
            boule.setPosition(tmpPoint);
            boule.setDirection(direction);
        });

    }

    private Point getTmpPoint(int vitesse, int angle, Point position) {
        double x = vitesse * Math.cos(angle * Math.PI / 180.0) + position.getX();
        double y = vitesse * Math.sin(angle * Math.PI / 180.0) + position.getY();

        return new Point((int) Math.round(x), (int) Math.round(y));
    }

    private Boule createBoule() {
        Boule boule = new Boule();
        boule.setVitesse(random.nextInt(15) + 1);

        boule.setPosition(new Point(random.nextInt(screenWidth - 2 * radius) + radius,
               random.nextInt(screenHeight - 2 * radius) + radius));


        // 0 = droite
        // 90 = haut
        // 180 = gauche
        // 270 = bas
        boule.setDirection(random.nextInt(360));

        return boule;
    }

    private CircleShape createShapeFromBoule(Boule boule) {
        return new CircleShape(new ColorShape(random.nextInt(255), random.nextInt(255), random.nextInt(255)),
                boule.getPosition(),
                radius);
    }
}
