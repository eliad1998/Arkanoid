package game;
import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import animation.Animation;
import animation.PauseScreen;
import animation.CountdownAnimation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import levels.LevelInformation;
import listeners.Counter;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import geometry.Line;
import geometry.Rectangle;
import geometry.Point;
import java.awt.Color;
/**.
 * GameLevel
 * GameLevel class that will hold the sprites and the collidables, and will be in charge of the animation.
 */
public class GameLevel implements Animation {
    //Final variables of the screen size.
    static final  int GUIWIDTH = 800;
    static final int GUIHEIGHT = 600;
    //The margin of screen where the game will be. (The blocks of screen width).
    static final int SCREENMARGIN = 25;
    //Block sizes
    static final int BLOCKWIDTH = 40;
    static final int BLOCKHEIGHT = 20;
    //The maximum blocks on the top line
    static final int MAXLINEOFBLOCKS = 12;
    //Paddle sizes
    static final int PADDLEWIDTH = 60;
    static final int PADDLEHEIGHT = 20;
    //The sprite collection and game environment.
    private SpriteCollection sprites;
    private GameEnvironment environment;
    //Our keyboard sensor
    private KeyboardSensor keyboard;
    //The counter of remaining blocks.
    private Counter remainedBlocks;
    //The counter of remaining balls.
    private Counter remainedBalls;
    //The counter of scores.
    private Counter scores;
    //The counter of lives.
    private Counter remainedLives;
    private AnimationRunner runner;
    //Determines if stop or not the game.
    private boolean running;
    //Level information
    private LevelInformation levelInformation;
    /**.
     * Creates new instance of GameLevel.
     * The constructor of our class GameLevel.
     * Instalize the list of sprite objects and the game environment.
     *
     * @param levelInformation a levelInformation object that contains information about the current level.
     * @param keyboardSensor a keyboard sensor.
     * @param runner an animation runner.
     * @param remainedLives a refference to the remained lives.
     * @param scores a refference to the current scores.
     */
    public GameLevel(LevelInformation levelInformation, KeyboardSensor keyboardSensor, AnimationRunner runner
            , Counter remainedLives, Counter scores) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.keyboard = keyboardSensor;
        this.runner = runner;
        //Instalizing the count to be the number of the blocks.
        int count = 0;
        for (int i = MAXLINEOFBLOCKS; i >= 8; i--) {
            count += i;
        }
        //Instalizing the counters.
        this.remainedBlocks = new Counter(levelInformation.numberOfBlocksToRemove());
        //Setting the counters.
        this.remainedBalls = new Counter(0);
        this.scores = scores;
        this.remainedLives = remainedLives;
        this.running = true;

        this.levelInformation = levelInformation;
    }
    /**.
     * addCollidable.
     * Add the given collidable to the game by adding it into the game environment.
     * @param  c the collideable we want to add.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**.
     * removeCollidable.
     * Remove the given collidable from the game by removing it into the game environment.
     * @param  c the collideable we want to add.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }
    /**.
     * addSprite.
     * Add the given Sprite to the game by adding it into a sprite collection.
     * @param  s the Sprite we want to remove.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**.
     * removeSprite.
     * Remove the given Sprite to the game by removing it from the sprite collection.
     * @param  s the Sprite we want to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**.
     * initialize.
     * Initialize a new game: create the Blocks and Ball (and Paddle) and add them to the game.
     */
    public void initialize() {
        levelInformation.getBackground().addToGame(this);
        //Create the screen blocks.
        createScreen();
        initializeBlocks();
        initializeIndicators();
    }
    /**.
     * initializeIndicators.
     * Initialize the game indicators.
     */
    private void initializeIndicators() {
        //Adding the score indicator to the game
        ScoreIndicator si = new ScoreIndicator(this.scores);
        si.addToGame(this);
        //Adding the lives indicator to the game.
        LivesIndicator li = new LivesIndicator(this.remainedLives);
        li.addToGame(this);
        //Adding the level name to the screen.
        LevelIndicator levelIndicator = new LevelIndicator(this.levelInformation.levelName());
        levelIndicator.addToGame(this);
    }
    /**.
     * initializeBlocks
     * Initialize the game blocks.
     */
    private void initializeBlocks() {
        for (Block block: this.levelInformation.blocks()) {
            //Adding copy block because we dont won't to harm after losing because block is refference.
            Block copy = block.copy();
            copy.addToGame(this);
            copy.addHitListener(new BlockRemover(this, this.remainedBlocks));
            copy.addHitListener(new ScoreTrackingListener(this.scores));

        }
    }
    /**.
     * createBalls.
     * Creating several balls.
     *
     * @param  n the number of the balls we want.
     */
    private void createBalls(int n) {
        //Increase from 0 to n.
        this.remainedBalls.increase(n);
        Rectangle screen = gameRectangle();
        //The middle of the bottom screen.
        Point paddlePosition = new Line(screen.getLowerLeft(), screen.getLowerRight()).middle();
        //The angle the speed will be
        double angle = 180;
        for (int i = 0; i < n; i++) {
            //Creating the ball
            Sprite ball = new Ball((int) paddlePosition.getX()
                    , (int) paddlePosition.getY() - 5, 5, Color.white);
            Ball b = (Ball) ball;
            b.setGameEnvironment(this.environment);
            //Instalize the ball velocity.
            b.setVelocity(levelInformation.initialBallVelocities().get(i));
            //Add the ball to the game.
            b.addToGame(this);
        }

    }
    /**.
     * createScreen.
     * We notify the screen margins as 4 blocks.
     * In that function we will create each block and add it into the game.
     */
    private void createScreen() {
        //Creating the screen margins.
        Collidable topScreen = new Block(new Rectangle(new Point(0, 0)
                , GUIWIDTH, SCREENMARGIN), Color.gray);
        Collidable leftScreen = new Block(new Rectangle(new Point(0, 0)
                , SCREENMARGIN, GUIHEIGHT), Color.gray);
        Collidable rightScreen = new Block(new Rectangle(new Point(GUIWIDTH - SCREENMARGIN, 0)
                , SCREENMARGIN, GUIHEIGHT), Color.gray);
        //When the ball hits there you are die.
        Collidable bottomScreen = new Block(new Rectangle(new Point(SCREENMARGIN
                , GUIHEIGHT), GUIWIDTH, 0), new Color(255, 255, 255));
        //The screen margins array.
        Collidable[] screen = {topScreen, leftScreen, rightScreen, bottomScreen};
        //Adding each screen margins to the block.
        for (int i = 0; i < screen.length; i++) {
            Block screenPart = (Block) screen[i];
            screenPart.addToGame(this);
            //Adding hit listener to the screen.
            if (i != screen.length - 1) {
                screenPart.addHitListener(new BlockRemover(this, this.remainedBlocks));
            } else { //Adding the listener to the "death region" the bottom screen.
                screenPart.addHitListener(new BallRemover(this, this.remainedBalls));
            }
            //Setting to 0 in order to not removing the blocks of screen.
            //We remove blocks with 1 count hit.
            screenPart.setCountHits(0);
        }
    }
    /**.
     * gameRectangle.
     * We notify the screen margins as 4 blocks.
     * @return the rectangle of the inner screen where the game is.
     */
    private Rectangle gameRectangle() {
        return new Rectangle(new Point(SCREENMARGIN, SCREENMARGIN), GUIWIDTH - 2 * SCREENMARGIN
                , GUIHEIGHT - 2 * SCREENMARGIN);
    }
    /**
     * doOneFrame.
     * In charge of the logic.
     * In this case the logic is the game itself.
     *
     * @param d a drawsurfce.
     * @param dt  It specifies the amount of seconds passed since the last call.
     * As we will be dealing with speeds that show many frames per second.
     * Each invocation will result in a small value for dt.
     * For example, in case we set 60 frames per second the dt value will be 1/60
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // the logic from the previous playOneTurn method goes here.
        // the `return` or `break` statements should be replaced with
        // this.running = false;
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }
        //No more blocks at the screen
        if (this.remainedBlocks.getValue() == 0) {
            //Destroying all blocks is worth another 100 points.
            this.scores.increase(100);
            //Clear the GUI resources and close the window.
            this.running = false;
        }
        //No more balls at the screen
        if (this.remainedBalls.getValue() == 0) {
            //No more balls so we lose live.
            this.remainedLives.decrease(1);
            //Remove the paddle
            this.running = false;
        }
        //Drawing the sprites objects.
        this.sprites.drawAllOn(d);
        //Moving the sprites we can move.
        this.sprites.notifyAllTimePassed(dt);
    }
    /**
     * shouldStop.
     * The stopping conditions are for example no more balls or blocks.
     *
     * @return true if the animation should stop, false otherwise.
     */
    public boolean shouldStop() {
        return !this.running;
    }
    /**.
     * playOneTurn.
     * Run the game -- start the animation loop.
     * We will use the object Sleeper and draw all the sprites.
     */
    public void playOneTurn() {
        //We create rectangle in order to identify where the ball, paddle and other blocks will be.
        Rectangle screen = gameRectangle();
        //Create the paddle
        Point paddlePosition = new Line(screen.getLowerLeft(), screen.getLowerRight()).middle();
        paddlePosition.setX(paddlePosition.getX() - this.levelInformation.paddleWidth() / 2);
        Paddle paddle = new Paddle(new Rectangle(paddlePosition
                , this.levelInformation.paddleWidth(), PADDLEHEIGHT), this.keyboard, Color.orange);
        //Setting the speed of the paddle.
        paddle.setSpeed(this.levelInformation.paddleSpeed());
        paddle.addToGame(this);
        //Creating back the balls.
        createBalls(levelInformation.numberOfBalls());
       // this.remainedBalls.increase(levelInformation.numberOfBalls());
        //Running the countdown
        this.runner.run(new CountdownAnimation(2, 3, this.sprites)); // countdown before turn starts.
        this.running = true;
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(this);
        //Removing the paddle because we want to restart his position.
        paddle.removeFromGame(this);
    }
    /**.
     * noMoreBlocks.
     * @return true if there are more blocks, false otherwise.
     */
    public boolean noMoreBlocks() {
        return this.remainedBlocks.getValue() == 0;
    }
}
