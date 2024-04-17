import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WebCrawlerGUI extends JFrame implements KeyListener {
    private Thread crawlerThread;
    private boolean paused = false;

    public WebCrawlerGUI() {
        // Set up the JFrame
        this.setTitle("Web Crawler with Pause/Resume");
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();

        // Set up the web crawler thread
        crawlerThread = new Thread(this::runCrawler);
        crawlerThread.start();
    }

    private void runCrawler() {
        while (true) {
            checkPaused();
            // Your web crawling logic here
            System.out.println("Crawling...");
            try {
                Thread.sleep(1000);  // Simulate the work of crawling
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private synchronized void checkPaused() {
        while (paused) {
            try {
                wait();  // Wait until notified to resume
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private synchronized void togglePause() {
        paused = !paused;
        if (!paused) {
            notifyAll();  // Notify to resume crawling
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'p') {  // Press 'p' to toggle pause/resume
            togglePause();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WebCrawlerGUI frame = new WebCrawlerGUI();
            frame.setVisible(true);
        });
    }
}
