package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Class responsible for setting up the GUI
 */
public class AppWindow {

    private JFrame frame;
    private JButton sourceButton;
    private JButton destinationButton;
    private JButton startButton;
    private JTextField fileMaskField;
    private File sourceDirectory;
    private File destinationDirectory;
    private JLabel statusLabel;

    public AppWindow() {
        frame = new JFrame();
        frame.setTitle("Multithreaded Copy App");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());

        JPanel northButtonPanel = new JPanel();
        northButtonPanel.setLayout(new BoxLayout(northButtonPanel, BoxLayout.LINE_AXIS));

        sourceButton = new JButton("Select Source");
        destinationButton = new JButton("Select Destination");
        startButton = new JButton("Start");
        fileMaskField = new JTextField("*.*");

        northButtonPanel.add(sourceButton);
        northButtonPanel.add(destinationButton);
        northButtonPanel.add(Box.createHorizontalGlue());
        northButtonPanel.add(startButton);

        frame.add(northButtonPanel, BorderLayout.NORTH);

        JPanel separatorPanel = new JPanel(new BorderLayout());
        separatorPanel.add(new JSeparator(), BorderLayout.NORTH);
        frame.add(separatorPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        bottomPanel.add(new JSeparator(), BorderLayout.NORTH);

        statusLabel = new JLabel("Waiting for source input...");
        statusLabel.setHorizontalAlignment(JLabel.RIGHT);
        bottomPanel.add(statusLabel, BorderLayout.EAST);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        sourceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectSourceDirectory();
            }
        });

        destinationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectDestinationDirectory();
            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startCopying();
            }
        });
    }

    private void selectSourceDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            sourceDirectory = fileChooser.getSelectedFile();
            statusLabel.setText("Source Selected: " + sourceDirectory.getAbsolutePath());
        }
    }

    private void selectDestinationDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            destinationDirectory = fileChooser.getSelectedFile();
            statusLabel.setText("Destination Selected: " + destinationDirectory.getAbsolutePath());
        }
    }

    private void startCopying() {
        String fileMask = fileMaskField.getText();

        if (sourceDirectory == null || destinationDirectory == null) {
            JOptionPane.showMessageDialog(frame, "Please select source and destination directories", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!sourceDirectory.exists() || !sourceDirectory.isDirectory()) {
            JOptionPane.showMessageDialog(frame, "Invalid source directory", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!destinationDirectory.exists() || !destinationDirectory.isDirectory()) {
            JOptionPane.showMessageDialog(frame, "Invalid destination directory", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Perform file copying logic here
            File[] filesToCopy = sourceDirectory.listFiles();

            // Create a thread pool with the specified number of threads
            int numThreads = 4;
            ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

            for (File file : filesToCopy) {
                if (file.isFile() && file.getName().matches(fileMask)) {
                    executorService.execute(new FileCopyTask(file, destinationDirectory));
                }
            }

            executorService.shutdown();

            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int copiedFilesCount = FileCopyTask.getCopiedFilesCount();
            statusLabel.setText("Copied files count: " + copiedFilesCount);
        }
    }


    public void show() {
        frame.setVisible(true);
    }
}
