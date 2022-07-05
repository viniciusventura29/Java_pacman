import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Model extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallfont = new Font("Arial",Font.BOLD,14);
    private boolean inGame = false;
    private boolean diyng = false;

    private final int blockSize = 24;
    private final int nBlocks = 15;
    private final int screenSize = nBlocks * blockSize;
    private final int maxGhost = 12;
    private final int pacmanSpeed = 6;

    private int nGhost = 6;
    private int lives, score;
    private int [] dx, dy;
    private int[] ghostX, ghostY,ghostDx,ghostDy,ghostSpeed;

    private Image heart, ghost;
    private Image up,down,left,right;

    private int pacmanX,pacmanY,pacamandX,pacmandY;
    private int reqDx,reqDy;

    private final int validSpeed[]= {1, 2, 3, 4, 6, 8};
    private final int maxSpeed=6;
    private int currentSpeed=3;
    private short [] screenData;
    private Timer timer;


    //right boder = 4,  blue = 0,  top border = 2,  white dots = 16,  bottom border = 8,  left boder = 1
    private final short leveData[]={
            19,18,18,18,18,18,18,18,18,18,18,18,18,18,22,
            17,16,16,16,16,24,16,16,16,16,16,16,16,16,20,
            25,24,24,24,28,0,17,16,16,16,16,16,16,16,20,
            0, 0, 0, 0, 0, 0,17,16,16,16,16,16,16,16,20,
            19,18,18,18,18,18,16,16,16,16,24,24,24,24,20,
            17,16,16,16,16,16,16,16,16,20, 0, 0, 0, 0,21,
            17,16,16,16,16,16,16,16,16,20, 0, 0, 0, 0,21,
            17,16,16,16,24,16,16,16,16,20, 0, 0, 0, 0,21,
            17,16,16,20, 0,17,16,16,16,16,18,18,18,18,20,
            17,24,24,28, 0,25,24,24,16,16,16,16,16,16,20,
            21, 0, 0, 0, 0, 0, 0, 0,17,16,16,16,16,16,20,
            17,18,18,22, 0,19,18,18,16,16,16,16,16,16,20,
            17,16,16,20, 0,17,16,16,16,16,16,16,16,16,20,
            17,16,16,20, 0,17,16,16,16,16,16,16,16,16,20,
            25,24,24,24,26,24,24,24,24,24,24,24,24,24,28
    };

    private void playGame(Graphics2D g2d){

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,d.width,d.height);

        drawMaze(g2d);
        drawScore(g2d);

        if (inGame){
            playGame(g2d);
        }else {
            showIntroScreen(g2d);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    public Model(){
        loadImages();
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        initGame();
    }

    public void initGame(){
        lives = 3;
        score = 0;
        initLevel();
        nGhost = 6;
        currentSpeed = 3;
    }

    private void initLevel(){
        for (int i = 0; i <nBlocks*nBlocks ; i++) {
            screenData[i] = leveData[i];
        }
    }

    private void continueLevel(){
        int dx = 1;
        int random;

        for (int i = 0; i < nGhost; i++) {
            ghostY[i] = 4*blockSize;
            ghostX[i] = 4*blockSize;
            ghostDy[i] = 0;
            ghostDx[i] = dx;

            dx = -dx;

            random = (int) (Math.random() * (currentSpeed+1));

            if (random>currentSpeed){
                random=currentSpeed;
            }

            ghostSpeed[i]=validSpeed[random];
        }
        pacamandX = 7*blockSize;
        pacmandY = 11*blockSize;
        pacamandX=0;
        pacmandY=0;
        reqDx=0;
        reqDy=0;
        diyng=false;
    }

    private void loadImages(){
        down = new ImageIcon("lugar da imagem").getImage();
        up = new ImageIcon("lugar da imagem").getImage();
        left = new ImageIcon("lugar da imagem").getImage();
        right = new ImageIcon("lugar da imagem").getImage();
        ghost = new ImageIcon("lugar da imagem").getImage();
        heart = new ImageIcon("lugar da imagem").getImage();
    }

    private void initVariables(){
        screenData = new short[nBlocks * nBlocks];
        d = new Dimension(400,400);
        ghostX = new int[maxGhost];
        ghostDx = new int[maxGhost];
        ghostY = new int[maxGhost];
        ghostDy = new int[maxGhost];
        ghostSpeed = new int[maxGhost];

        dx = new int[4];
        dy = new int[4];

        timer = new Timer(40,this);
        timer.restart();
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }

    class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (inGame){
                if (key == KeyEvent.VK_LEFT){
                    reqDx = -1;
                    reqDy = 0;
                }
                else if (key == KeyEvent.VK_RIGHT){
                    reqDx = 1;
                    reqDy = 0;
                }
                else if (key == KeyEvent.VK_UP){
                    reqDx = 0;
                    reqDy = -1;
                }
                else if (key == KeyEvent.VK_DOWN){
                    reqDx = 0;
                    reqDy = 1;
                }
                else if(key == KeyEvent.VK_ESCAPE && timer.isRunning()){
                    inGame=false;
                }
                else {
                    if (key == KeyEvent.VK_ESCAPE){
                        inGame = true;
                        initGame();
                    }
                }

            }
        }
    }
}
