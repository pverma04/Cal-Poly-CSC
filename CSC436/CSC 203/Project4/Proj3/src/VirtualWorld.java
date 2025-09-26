import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Predicate;
import java.lang.Thread;

import processing.core.*;

public final class VirtualWorld extends PApplet
{
    public static final int TIMER_ACTION_PERIOD = 100;

    public static final int VIEW_WIDTH = 640;
    public static final int VIEW_HEIGHT = 480;
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    public static final int WORLD_WIDTH_SCALE = 2;
    public static final int WORLD_HEIGHT_SCALE = 2;

    public static int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    public static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
    private int VIEW_LEFT = 0;
    private int VIEW_RIGHT = VIEW_COLS;
    private int VIEW_TOP = 0;
    private int VIEW_BOTTOM = VIEW_ROWS;
    public static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
    public static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

    public static final String IMAGE_LIST_FILE_NAME = "imagelist";
    public static final String DEFAULT_IMAGE_NAME = "background_default";
    public static final int DEFAULT_IMAGE_COLOR = 0x33B8FF;

    public static String LOAD_FILE_NAME = "world.sav";

    public static final String FAST_FLAG = "-fast";
    public static final String FASTER_FLAG = "-faster";
    public static final String FASTEST_FLAG = "-fastest";
    public static final double FAST_SCALE = 0.5;
    public static final double FASTER_SCALE = 0.25;
    public static final double FASTEST_SCALE = 0.10;

    public static double timeScale = 1.0;


    public ImageStore imageStore;
    public WorldModel world;
    public WorldView view;
    public EventScheduler scheduler;
    private Predicate<Point> occupied;
    private Predicate<Point> withinBounds;

    public long nextTime;
    private int drawCount;
    private LinkedList<Point> resetPoints;
    private LinkedList<Background> resetBacks;
    private int score;
    private int launch;


    public void settings() {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    /*
       Processing entry point for "sketch" setup.
    */
    public void setup() {
        this.imageStore = new ImageStore(
                createImageColored(TILE_WIDTH, TILE_HEIGHT,
                                   DEFAULT_IMAGE_COLOR));
        this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
                                    createDefaultBackground(imageStore));
        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH,
                                  TILE_HEIGHT);
        this.scheduler = new EventScheduler(timeScale);
        this.occupied = p -> world.isOccupied(p);
        this.withinBounds = p -> world.withinBounds(p);
        this.drawCount = 0;
        this.resetPoints = new LinkedList<>();
        this.resetBacks = new LinkedList<>();
        this.score = 0;
        this.launch = 0;



        loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
        loadWorld(world, LOAD_FILE_NAME, imageStore);

        scheduleActions(world, scheduler, imageStore);

        nextTime = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
    }

    public void draw() {
        if(!world.findNearest(new Point(20, 15), new ArrayList<>(Arrays.asList(Tree.class))).isPresent()) {
            System.out.println("SCORE: " + score);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            exit();
        }
        long time = System.currentTimeMillis();

        if (time >= nextTime) {
//            Functions.updateOnTime(this.scheduler, time);
            this.scheduler.updateOnTime(time);
            nextTime = time + TIMER_ACTION_PERIOD;
        }
        if(drawCount%2 == 0) {
            if(!resetPoints.isEmpty()) {
                resetBacks.pop().setBackground(world, resetPoints.pop());
            }
//            Background bg = new Background("grass", imageStore.getImageList("grass"));
//            bg.setBackground(world, new Point(5, 3));
        }

        view.drawViewport();
        drawCount++;
    }

    // Just for debugging and for P5
    // Be sure to refactor this method as appropriate
//    public void mousePressed() {
//        Point pressed = mouseToPoint(mouseX, mouseY);
//        System.out.println("CLICK! " + pressed.getX() + ", " + pressed.getY());
//
////        Optional<Entity> entityOptional = world.getOccupant(pressed);
////        if (entityOptional.isPresent() )
////        {
////            Entity entity = entityOptional.get();
////            HasHealth healthEntity = (HasHealth)entity;
////            System.out.println(entity.getId() + ": " + healthEntity.getClass() + " : " + healthEntity.getHealth());
////        }
//
//    }

    public void mousePressed(){
        Point clicked = mouseToPoint(mouseX, mouseY);
        Optional<Entity> shooter = world.findNearest(clicked, new ArrayList<>(Arrays.asList(Tree.class)));
        if(shooter.isPresent()){
            Point shooterPos = shooter.get().getPosition();
            if(!shooterPos.equals(clicked)) {
                Point left = new Point(shooterPos.getX() - 1, shooterPos.getY());
                Point right = new Point(shooterPos.getX() + 1, shooterPos.getY());
                Point up = new Point(shooterPos.getX(), shooterPos.getY() - 1);
                Point down = new Point(shooterPos.getX(), shooterPos.getY() + 1);
                String direction = "none";
//            Point shootFrom = shooterPos;
//

//                if (clicked.getY() < shooterPos.getY() + 3 && clicked.getY() > shooterPos.getY() - 3) {
//                    if (clicked.getX() > shooterPos.getX()) {
//                        direction = "right";
//                        shooterPos = right;
//                    } else if (clicked.getX() < shooterPos.getX()) {
//                        direction = "left";
//                        shooterPos = left;
//                    }
//                }
//                if (clicked.getX() < shooterPos.getX() + 3 && clicked.getX() > shooterPos.getX() - 3) {
//                    if (clicked.getY() > shooterPos.getY()) {
//                        direction = "down";
//                        shooterPos = down;
//                    } else if (clicked.getY() < shooterPos.getY()) {
//                        direction = "up";
//                        shooterPos = up;
//                    }
//                }

                double xVal = clicked.getX()-shooterPos.getX();
                double yVal = clicked.getY()-shooterPos.getY();
                if (xVal != 0 &&
                        yVal/xVal >= -1 &&
                        yVal/xVal <= 1) {
                    if (clicked.getX() > shooterPos.getX()) {
                        direction = "right";
                        shooterPos = right;
                    } else if (clicked.getX() < shooterPos.getX()) {
                        direction = "left";
                        shooterPos = left;
                    }
                }
                if (yVal != 0 &&
                        xVal/yVal >= -1 &&
                        xVal/yVal <= 1) {
                    if (clicked.getY() > shooterPos.getY()) {
                        direction = "down";
                        shooterPos = down;
                    } else if (clicked.getY() < shooterPos.getY()) {
                        direction = "up";
                        shooterPos = up;
                    }
                }

                if(!direction.equals("none")) {
                    Background power = new Background("fairy", imageStore.getImageList("fairy"));

//                    LinkedList<Point> resetPoints = new LinkedList<>();
//                    LinkedList<Background> resetCells = new LinkedList<>();
//                    Background bg;
                    int i = 0;
                    while (i < 6) {
//                        if(world.isOccupied(shooterPos)){
//                            Entity occupant = world.getOccupant(shooterPos).get();
//                            world.removeEntity(occupant);
//                        }
                        if(world.isOccupied(shooterPos)){
                            Optional<Entity> optEnemy = world.getOccupant(shooterPos);
                            if(optEnemy.isPresent()){
                                Entity enemy = optEnemy.get();
                                if(enemy instanceof DudeNotFull){
                                    ((DudeNotFull)enemy).transform(world, scheduler, imageStore);
                                    score += 10;
                                }
                                else if(enemy instanceof Fairy){
                                    ((Fairy)enemy).transform(world, scheduler, imageStore);
                                    score += 10;
                                }

                                Random random = new Random();
                                int rand = random.nextInt(4);
                                Point newPos;
                                Point oppPos;
                                if(rand == 0) {
                                    newPos = new Point(0, 0);
                                    oppPos = new Point(39, 29);
                                }

                                else if(rand == 1) {
                                    newPos = new Point(0, 26);
                                    oppPos = new Point(39, 0);
                                }
                                else if(rand == 2) {
                                    newPos = new Point(39, 0);
                                    oppPos = new Point(0, 26);
                                }
                                else {
                                    newPos = new Point(39, 29);
                                    oppPos = new Point(0, 0);
                                }
                                Entity newEnemy = new DudeNotFull("dude_",
                                        newPos,
                                        imageStore.getImageList("dude"),
                                        100,
                                        800,
                                        Resourceful.DUDE_LIMIT,
                                        0
                                        );
                                if(launch % 4 ==0) {
                                    Entity newEnemy2 = new Fairy("fairy_",
                                            oppPos,
                                            imageStore.getImageList("zuko"),
                                            100,
                                            800
                                    );
                                    world.addEntity(newEnemy2);
                                }
                                world.addEntity(newEnemy);
//                                scheduleActions(world, scheduler, imageStore);
                                scheduleActions(world, scheduler, imageStore);
                                launch += 1;
                            }
                        }
                        if(world.withinBounds(shooterPos)) {
                            resetPoints.add(shooterPos);
                            resetBacks.add(world.getBackgroundCell(shooterPos));
                        }
                        power.setBackground(world, shooterPos);
//                        resetPoints.add(shooterPos);
//                        resetCells.add(bg);
                        if (direction.equals("left"))
                            shooterPos = new Point(shooterPos.getX() - 1, shooterPos.getY());
                        else if (direction.equals("right"))
                            shooterPos = new Point(shooterPos.getX() + 1, shooterPos.getY());
                        else if (direction.equals("down"))
                            shooterPos = new Point(shooterPos.getX(), shooterPos.getY() + 1);
                        else shooterPos = new Point(shooterPos.getX(), shooterPos.getY() - 1);
                        i++;
                    }
//                    long time = System.currentTimeMillis();
//                    System.out.println("START" + System.currentTimeMillis());
//                    long newTime = time;
//                    int k = 1;
//                    while(newTime - time < 10000){
//                        if((newTime-time)/100.0 > k){
//                            if(!resetCells.isEmpty()) {
//                                resetCells.pop().setBackground(world, resetPoints.pop());
//                                k++;
//                                System.out.println("RESET");
//                            }
//                        }
//                        newTime = System.currentTimeMillis();
//                        System.out.println("NEWTIME: " + newTime);
//                    }
                }
//                Fairy entity = new Fairy("fairy_", shooterPos, imageStore.getImageList("fairy"),
//                        Fairy.FAIRY_ANIMATION_PERIOD,
//                        Fairy.FAIRY_ACTION_PERIOD);
//                world.addEntity(entity);
//                entity.execute(world, imageStore, scheduler);
//                entity.scheduleActions(scheduler, world, imageStore);
            }

        }
//        double direction = (double)mouseX/mouseY;
//        entity.execute(world, imageStore, scheduler);
//        Background power = new Background("fairy", imageStore.getImageList("fairy"));
//        Background bg = new Background("grass", imageStore.getImageList("grass"));

//        power.setBackground(world, clicked);
//        for(int i = 0; i < 10; i++){
//            Point travel = new Point(clicked.getX()+1, clicked.getY());
//            power.setBackground(world, travel);
//            clicked = travel;
//        }
    }

    private Point mouseToPoint(int x, int y)
    {
        return view.getViewport().viewportToWorld(mouseX/TILE_WIDTH, mouseY/TILE_HEIGHT);
    }
    public void keyPressed() {
        Optional<Entity> optTree = world.findNearest(new Point(10, 10),
                new ArrayList<>(Arrays.asList(Tree.class)));
        if(optTree.isPresent()) {


            Entity tree = optTree.get();

            Point up = new Point(tree.getPosition().getX(), tree.getPosition().getY() - 1);
            Point left = new Point(tree.getPosition().getX() - 1, tree.getPosition().getY());
            Point down = new Point(tree.getPosition().getX(), tree.getPosition().getY() + 1);
            Point right = new Point(tree.getPosition().getX() + 1, tree.getPosition().getY());

            if (key == 'w' && withinBounds.test(up) && (!occupied.test(up) || up.equals(new Point(3, 3)))) {
//                tree.setPosition(up);
                world.moveEntity(tree, up);
                if (up.getY() == VIEW_TOP && world.withinBounds(new Point(up.getX(), up.getY() - 1))) {
                    view.shiftView(0, -1);
                    VIEW_TOP -= 1;
                    VIEW_BOTTOM -= 1;
                }
            } else if (key == 'a' && withinBounds.test(left) && (!occupied.test(left) ||
                    left.equals(new Point(3, 3)))) {
//                house.setPosition(left);
                world.moveEntity(tree, left);
                if (left.getX() == VIEW_LEFT && world.withinBounds(new Point(left.getX() - 1, left.getY()))) {
                    view.shiftView(-1, 0);
                    VIEW_LEFT -= 1;
                    VIEW_RIGHT -= 1;
                }
            } else if (key == 's' && withinBounds.test(down) && (!occupied.test(down) ||
                    down.equals(new Point(3, 3)))) {
//                house.setPosition(down);
                world.moveEntity(tree, down);
                if (down.getY() == VIEW_BOTTOM - 1 && world.withinBounds(new Point(down.getX(), down.getY() + 1))) {
                    view.shiftView(0, 1);
                    VIEW_BOTTOM += 1;
                    VIEW_TOP += 1;
                }
            } else if (key == 'd' && withinBounds.test(right) && (!occupied.test(right) ||
                    right.equals(new Point(3, 3)))) {
//                house.setPosition(right);
                world.moveEntity(tree, right);
                if (right.getX() == VIEW_RIGHT - 1 && world.withinBounds(new Point(right.getX() + 1, right.getY()))) {
                    view.shiftView(1, 0);
                    VIEW_RIGHT += 1;
                    VIEW_LEFT += 1;
                }
            }
        }

//        if(house.getPosition().getY() == 0)
//            view.shiftView(-1, 0);
//        if(house.getPosition().getX() == 0)
//            view.shiftView(0, -1);
//        if(house.getPosition().getY() == VIEW_ROWS)
//            view.shiftView(1, 0);







        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP:
                    dy = -1;
                    break;
                case DOWN:
                    dy = 1;
                    break;
                case LEFT:
                    dx = -1;
                    break;
                case RIGHT:
                    dx = 1;
                    break;
            }
            view.shiftView(dx, dy);
        }

    }

    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME,
                              imageStore.getImageList(
                                                     DEFAULT_IMAGE_NAME));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            img.pixels[i] = color;
        }
        img.updatePixels();
        return img;
    }

    static void loadImages(
            String filename, ImageStore imageStore, PApplet screen)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            imageStore.loadImages(in, screen);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void loadWorld(
            WorldModel world, String filename, ImageStore imageStore)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            world.load(in, imageStore);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void scheduleActions(
            WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        for (Entity entity : world.getEntities()) {
            if(entity instanceof Animated)
                ((Animated)entity).scheduleActions(scheduler, world, imageStore);
        }
    }

    public static void parseCommandLine(String[] args) {
        if (args.length > 1)
        {
            if (args[0].equals("file"))
            {

            }
        }
        for (String arg : args) {
            switch (arg) {
                case FAST_FLAG:
                    timeScale = Math.min(FAST_SCALE, timeScale);
                    break;
                case FASTER_FLAG:
                    timeScale = Math.min(FASTER_SCALE, timeScale);
                    break;
                case FASTEST_FLAG:
                    timeScale = Math.min(FASTEST_SCALE, timeScale);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class);

    }
}
