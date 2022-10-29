import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class FileOperationGui extends JPanel implements ActionListener {
    JButton selectFolderButton;
    JButton saveFolderButton;
    JButton fileOrganizerButton;
    JFileChooser chooser;
    static File fromPath;
    static File toPath;

    public FileOperationGui() {
        selectFolderButton = new JButton("Select the Folder of files you want to move...");
        selectFolderButton.setBounds(20, 100, 95, 30);
        selectFolderButton.addActionListener(this);
        add(selectFolderButton);

        saveFolderButton = new JButton("Select Folder to Save");
        saveFolderButton.setBounds(20, 100, 95, 30);
        saveFolderButton.addActionListener(actionListener -> {
            chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            //chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("select folder to save");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.showSaveDialog(null);
            chooser.setAcceptAllFileFilterUsed(false);
            toPath = new File(chooser.getSelectedFile().getAbsolutePath());
        });
        add(saveFolderButton);

        fileOrganizerButton = new JButton("Start File Organizing...");
        fileOrganizerButton.setBounds(20, 200, 95, 30);
        fileOrganizerButton.addActionListener(actionListener -> {
            try {
                startFileProcessing();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        add(fileOrganizerButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chooser = new JFileChooser();
        chooser.showOpenDialog(this);
        //chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("select folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showSaveDialog(null);
        chooser.setAcceptAllFileFilterUsed(false);
        fromPath = new File(chooser.getSelectedFile().getAbsolutePath());
    }

//    public Dimension getPreferredSize(){
//        return new Dimension(200, 200);
//    }

    private static void startFileProcessing() throws IOException {
        int duplicate = 0;
        int total = 0;

        int[] ar = new int[2];
        String[] stringArr = {"*.jpg", "*png", "*.3gp", "*.jpeg", "*.mp4"};
        FileOperationGui obj = new FileOperationGui();
        if (fromPath == null) {
            return;
        }
        for (String s : stringArr) {
            ar = obj.FileOrganizer(fromPath, s);
            duplicate += ar[0];
            total += ar[1];
        }
        createLogs(total, duplicate);
    }

    int[] FileOrganizer(File path, String extension) throws IOException {
        int fileCount = 0;
        int isExist = 0;
        int[] arr = new int[2];

        File[] fileList = path.listFiles();
        for (File file : fileList) {
            if (file != null) {
                BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                LocalDateTime time = attr.creationTime()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                int year = time.getYear();

                switch (year) {
                    case 2021:
                        isExist = fileMover(file, isExist, fileCount, year);
                        fileCount++;
                        break;
                    case 2020:
                        isExist = fileMover(file, isExist, fileCount, year);
                        fileCount++;
                        break;
                    case 2019:
                        isExist = fileMover(file, isExist, fileCount, year);
                        fileCount++;
                        break;
                    case 2018:
                        isExist = fileMover(file, isExist, fileCount, year);
                        fileCount++;
                        break;
                    case 2017:
                        isExist = fileMover(file, isExist, fileCount, year);
                        fileCount++;
                        break;
                    case 2016:
                        isExist = fileMover(file, isExist, fileCount, year);
                        fileCount++;
                        break;
                    case 2015:
                        isExist = fileMover(file, isExist, fileCount, year);
                        fileCount++;
                        break;
                    case 2014:
                        isExist = fileMover(file, isExist, fileCount, year);
                        break;
                    case 2013:
                        isExist = fileMover(file, isExist, fileCount, year);
                        fileCount++;
                        break;
                    case 2012:
                        isExist = fileMover(file, isExist, fileCount, year);
                        fileCount++;
                        break;
                    default:
                        break;
                }
            }
            arr[0] = isExist;
            arr[1] = fileCount;
        }
        return arr;
    }

    private int fileMover(File file, int isExist, int fileCount, int year) throws IOException {
        String from = file.getName();
        //checks year folder is exist, if not create a new folder with year name and then move.
        String to = toPath.getPath() + "'\'"+year + "'\'" + file.getName();
        if (file.exists()) {
            file.delete();
            isExist++;
        }
        Files.move(file.toPath(), Path.of(to));
        return isExist;
    }
// will look into this
//    private boolean isFolderExists(File path){
//        //if (Files.exists(path)) {
//            if (Files.isDirectory(toPath.toPath())) {
//                System.out.println("It is a directory");
//            } else if (Files.isRegularFile(path)) {
//                System.out.println("File test.txt present");
//            }
//
//        else {
//            System.out.println("File not found ");
//        }
//    }
    private static void createLogs(int total, int duplicate) {
        try {
            OutputStream outputStream = new FileOutputStream("C:\\Users\\Upkar\\Documents\\output.txt", true);
            Writer outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write("Total files : " + total + " , Duplicate files : " + duplicate);

            outputStreamWriter.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void main(String... args) throws IOException {
        JFrame frame = new JFrame("File Organizer");
        //JPanel panel2 = new JPanel();
        FileOperationGui panel = new FileOperationGui();
        panel.setBounds(0, 0, 600, 600);
        panel.setBackground(Color.white);
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //System.out.println("calling gui"+path);


    }
}
