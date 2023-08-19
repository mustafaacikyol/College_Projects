import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.concurrent.*;

class Robot extends JFrame {

    int i, j;
    int squareCounter = 0;
    FileWriter file;
    int urlNumber;
    Boolean isFast = false;
    ArrayList<Integer> squareListI = new ArrayList<Integer>();
    ArrayList<Integer> squareListJ = new ArrayList<Integer>();

    Robot() {

    }

    Robot(int urlNumber) {
        try {
            this.urlNumber = urlNumber;
            if (urlNumber == 1) {
                file = new FileWriter("D:\\projects\\secondterm\\firstproject\\App\\result\\result1.txt");
                file.write("i  " + " j" + "\n");
            } else if (urlNumber == 2) {
                file = new FileWriter("D:\\projects\\secondterm\\firstproject\\App\\result\\result2.txt");
                file.write("i  " + " j" + "\n");
            } else if (urlNumber == 3) {
                file = new FileWriter("D:\\projects\\secondterm\\firstproject\\App\\result\\result3.txt");
                file.write("i  " + " j" + "\n");
            } else {
                file = new FileWriter("D:\\projects\\secondterm\\firstproject\\App\\result\\result4.txt");
                file.write("i  " + " j" + "\n");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public ImageIcon createRobotImage() {
        ImageIcon robotImage = new ImageIcon(getClass().getResource("robot.png"));
        return robotImage;
    }

    public void positionRobot(int i, int j) {
        try {
            this.i = i;
            this.j = j;
            file.write(String.valueOf(i) + " - " + String.valueOf(j) + "\n");
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public int[] getPosition() {
        int[] positionArr = new int[2];
        positionArr[0] = i;
        positionArr[1] = j;
        return positionArr;
    }

    public void setPosition(int i, int j, String status) {
        try {
            this.i = i;
            this.j = j;
            squareListI.add(i);
            squareListJ.add(j);
            file.write(String.valueOf(i) + " - " + String.valueOf(j) + "\n");
            Grid grid = new Grid();
            grid.removeCloud(i, j, status);
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void moveRobot(Implementation implement) {
        CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
            moveDown(implement);
        });

    }

    public void moveLeft(Implementation implement) {
        String status = "horizontal";
        if (urlNumber != 3 && urlNumber != 4) {
            if (Grid.checkSquare(i, j - 1)) {
                this.j -= 1;
                setPosition(i, j, status);
                squareCounter++;
                if (Grid.isTargetReached(i, j)) {
                    Grid grid = new Grid();
                    grid.findUnnecessarySquare(squareListI, squareListJ, this);
                    implement.finishGame(this);
                    try {
                        file.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    return;
                }
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveUp(implement);
                    });
                } else {
                    moveUp(implement);
                }
            }else {
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveRight(implement);
                    });
                } else {
                    moveRight(implement);
                }
            }
        } else {
            if (Grid.checkSquare(i, j - 1, 3)) {
                this.j -= 1;
                setPosition(i, j, status);
                squareCounter++;
                if (Grid.isTargetReached(i, j, 3)) {
                    Grid grid = new Grid();
                    grid.findUnnecessarySquare(squareListI, squareListJ, this);
                    implement.finishGame(this);
                    try {
                        file.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    return;
                }
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveUp(implement);
                    });
                } else {
                    moveUp(implement);
                }
            }else {
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveRight(implement);
                    });
                } else {
                    moveRight(implement);
                }
            }
        }

    }

    public void moveRight(Implementation implement) {
        String status = "horizontal";
        if (urlNumber != 3 && urlNumber != 4) {
            if (Grid.checkSquare(i, j + 1)) {
                this.j += 1;
                setPosition(i, j, status);
                squareCounter++;
                if (Grid.isTargetReached(i, j)) {
                    Grid grid = new Grid();
                    grid.findUnnecessarySquare(squareListI, squareListJ, this);
                    implement.finishGame(this);
                    try {
                        file.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    return;
                }
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveDown(implement);
                    });
                } else {
                    moveDown(implement);
                }

            } else {
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveUp(implement);
                    });
                } else {
                    moveUp(implement);
                }

            }
        } else {
            if (Grid.checkSquare(i, j + 1, 3)) {
                this.j += 1;
                setPosition(i, j, status);
                squareCounter++;
                if (Grid.isTargetReached(i, j, 3)) {
                    Grid grid = new Grid();
                    grid.findUnnecessarySquare(squareListI, squareListJ, this);
                    implement.finishGame(this);
                    try {
                        file.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    return;
                }
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveDown(implement);
                    });
                } else {
                    moveDown(implement);
                }
            } else {
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveUp(implement);
                    });
                } else {
                    moveUp(implement);
                }
            }
        }

    }

    public void moveUp(Implementation implement) {
        String status = "vertical";
        if (urlNumber != 3 && urlNumber != 4) {
            if (Grid.checkSquare(i - 1, j)) {
                this.i -= 1;
                setPosition(i, j, status);
                squareCounter++;
                if (Grid.isTargetReached(i, j)) {
                    Grid grid = new Grid();
                    grid.findUnnecessarySquare(squareListI, squareListJ, this);
                    implement.finishGame(this);
                    try {
                        file.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    return;
                }
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveRight(implement);
                    });
                } else {
                    moveRight(implement);
                }

            } else {
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveLeft(implement);
                    });
                } else {
                    moveLeft(implement);
                }

            }
        } else {
            if (Grid.checkSquare(i - 1, j, 3)) {
                this.i -= 1;
                setPosition(i, j, status);
                squareCounter++;
                if (Grid.isTargetReached(i, j, 3)) {
                    Grid grid = new Grid();
                    grid.findUnnecessarySquare(squareListI, squareListJ, this);
                    implement.finishGame(this);
                    try {
                        file.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    return;
                }
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveRight(implement);
                    });
                } else {
                    moveRight(implement);
                }
            } else {
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveLeft(implement);
                    });
                } else {
                    moveLeft(implement);
                }
            }
        }

    }

    public void moveDown(Implementation implement) {
        String status = "vertical";
        if (urlNumber != 3 && urlNumber != 4) {
            if (Grid.checkSquare(i + 1, j)) {
                this.i += 1;
                setPosition(i, j, status);
                squareCounter++;
                if (Grid.isTargetReached(i, j)) {
                    Grid grid = new Grid();
                    grid.findUnnecessarySquare(squareListI, squareListJ, this);
                    implement.finishGame(this);
                    try {
                        file.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    return;
                }
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveDown(implement);
                    });
                } else {
                    moveDown(implement);
                }

            } else {
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveRight(implement);
                    });
                } else {
                    moveRight(implement);
                }

            }
        } else {
            if (Grid.checkSquare(i + 1, j, 3)) {
                this.i += 1;
                setPosition(i, j, status);
                squareCounter++;
                if (Grid.isTargetReached(i, j, 3)) {
                    Grid grid = new Grid();
                    grid.findUnnecessarySquare(squareListI, squareListJ, this);
                    implement.finishGame(this);
                    try {
                        file.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    return;
                }
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveDown(implement);
                    });
                } else {
                    moveDown(implement);
                }
            } else {
                if (!isFast) {
                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        moveRight(implement);
                    });
                } else {
                    moveRight(implement);
                }
            }
        }

    }

    public void setSquareNumber(int number){
        squareCounter -= number;
    }

    public int getSquareNumber() {
        return squareCounter;
    }

    public void faster() {
        if (!isFast)
            isFast = true;
    }

}

class Grid extends JFrame {
    private static ArrayList<ArrayList<Integer>> gridList;
    private static ArrayList<ArrayList<JButton>> buttonList;
    public static JButton startBtn;
    public static JButton changeBtn;
    public static JButton showResultBtn;
    public static JButton gridBtn;
    static int fileNumber = 1;
    public static int displayNumber;
    ImageIcon redHorizontalImage = new ImageIcon(getClass().getResource("red1.png"));
    ImageIcon redVerticalImage = new ImageIcon(getClass().getResource("red2.png"));
    ImageIcon blueHorizontalImage = new ImageIcon(getClass().getResource("blue1.png"));
    ImageIcon blueVerticalImage = new ImageIcon(getClass().getResource("blue2.png"));

    public Grid() {
        super("Mobile Robot Problem - 1");
    }

    public Grid(int number) {
        super("Mobile Robot Problem - 2");
    }

    public JButton createButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(Color.DARK_GRAY);
        button.setBackground(Color.CYAN);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public void createList() {
        try {
            gridList = new ArrayList<ArrayList<Integer>>();
            URL url;
            if (fileNumber == 1) {
                url = new URL("http://bilgisayar.kocaeli.edu.tr/prolab2/url1.txt");
            } else {
                url = new URL("http://bilgisayar.kocaeli.edu.tr/prolab2/url2.txt");
            }
            URLConnection yc = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String str;
            int indexGridList = 0;
            while ((str = br.readLine()) != null) {
                gridList.add(new ArrayList<>());
                char[] charArr = str.toCharArray();
                for (int i = 0; i < charArr.length; i++) {
                    gridList.get(indexGridList).add(i, Character.getNumericValue(charArr[i]));
                }
                indexGridList++;
            }
            br.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void displayGUI(Robot robot) {
        buttonList = new ArrayList<ArrayList<JButton>>();
        int gridSize = gridList.size() + 2;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        startBtn = createButton("Start");
        changeBtn = createButton("Change");
        showResultBtn = createButton("Show Result");
        gridBtn = createButton("Grid");
        Implementation implement = new Implementation();
        leftPanel.add(implement.timeText);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 5)));
        leftPanel.add(implement.timeCounter);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        leftPanel.add(implement.squareText);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 5)));
        leftPanel.add(implement.squareCounter);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 50)));
        leftPanel.add(startBtn);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        leftPanel.add(changeBtn);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        leftPanel.add(showResultBtn);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        leftPanel.add(gridBtn);
        panel.add(leftPanel);

        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                implement.startGame(robot, implement);
            }
        });

        changeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fileNumber == 1)
                    fileNumber = 2;
                else
                    fileNumber = 1;
                implement.changeFile();
            }
        });

        showResultBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                robot.faster();
            }
        });

        gridBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < buttonList.size() - 2; i++) {
                    for (int j = 0; j < buttonList.size() - 2; j++) {
                        if (buttonList.get(i).get(j).getIcon() != null) {
                            if (!buttonList.get(i).get(j).getIcon().toString()
                                    .equals(robot.createRobotImage().toString())
                                    && !buttonList.get(i).get(j).getIcon().toString()
                                            .equals(redHorizontalImage.toString())
                                    && !buttonList.get(i).get(j).getIcon().toString()
                                            .equals(redVerticalImage.toString())) {
                                buttonList.get(i).get(j).setIcon(null);
                            }
                        }
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(gridSize, gridSize, 0, 0));
        Boolean isRobotLocated = false;
        Boolean isTargetLocated = false;
        ImageIcon cloudImage = new ImageIcon(getClass().getResource("cloud1.jpg"));
        for (int i = 0; i < gridSize; i++) {
            buttonList.add(new ArrayList<JButton>());
            for (int j = 0; j < gridSize; j++) {
                JButton button;
                if (i == 0 || j == 0 || j == gridList.size() + 1 || i == gridList.size() + 1) {
                    button = new JButton();
                    button.setBackground(Color.GRAY);
                } else {
                    if (gridList.get(i - 1).get(j - 1) == 0 && !isRobotLocated) {
                        button = new JButton();
                        isRobotLocated = true;
                        button.setIcon(robot.createRobotImage());
                        buttonList.get(i - 1).add(j - 1, button);
                        robot.positionRobot(i - 1, j - 1);
                    } else if (i == gridSize - 2 && j == gridSize - 2 && !isTargetLocated
                            && gridList.get(i - 1).get(j - 1) == 0) {
                        button = new JButton("T");
                        button.setBackground(Color.PINK);
                        button.setIcon(cloudImage);
                        buttonList.get(i - 1).add(j - 1, button);
                        buttonList.get(i - 1).get(j - 1).setIcon(cloudImage);
                        isTargetLocated = true;
                    } else if (gridList.get(i - 1).get(j - 1) != 0) {
                        button = new JButton(String.valueOf(gridList.get(i - 1).get(j - 1)));
                        if (gridList.get(i - 1).get(j - 1) == 1) {
                            ObstacleOne obstacleOne = new ObstacleOne(gridList);
                            obstacleOne.createObstacle(button);
                            buttonList.get(i - 1).add(j - 1, button);
                            buttonList.get(i - 1).get(j - 1).setIcon(cloudImage);
                        } else if (gridList.get(i - 1).get(j - 1) == 2) {
                            if ((i - 1) % 2 == 1 && ((i + j - 2) % 3 == 1 || (i + j - 2) % 3 == 2)) {
                                button.setBackground(Color.GREEN);
                                button.setIcon(cloudImage);
                                buttonList.get(i - 1).add(j - 1, button);
                                buttonList.get(i - 1).get(j - 1).setIcon(cloudImage);
                            } else {
                                ObstacleTwo obstacleTwo = new ObstacleTwo(gridList);
                                obstacleTwo.createObstacle(button);
                                buttonList.get(i - 1).add(j - 1, button);
                                buttonList.get(i - 1).get(j - 1).setIcon(cloudImage);
                            }
                        } else if (gridList.get(i - 1).get(j - 1) == 3) {
                            if ((i - 1) % 3 == 1
                                    && ((i + j - 2) % 4 == 1 || (i + j - 2) % 4 == 2 || (i + j - 2) % 4 == 3)) {
                                button.setBackground(Color.GREEN);
                                button.setIcon(cloudImage);
                                buttonList.get(i - 1).add(j - 1, button);
                                buttonList.get(i - 1).get(j - 1).setIcon(cloudImage);
                            } else {
                                ObstacleThree obstacleThree = new ObstacleThree(gridList);
                                obstacleThree.createObstacle(button);
                                buttonList.get(i - 1).add(j - 1, button);
                                buttonList.get(i - 1).get(j - 1).setIcon(cloudImage);
                            }
                        }

                    } else {
                        button = new JButton(String.valueOf(gridList.get(i - 1).get(j - 1)));
                        buttonList.get(i - 1).add(j - 1, button);
                        buttonList.get(i - 1).get(j - 1).setIcon(cloudImage);
                    }

                }
                button.setMargin(new Insets(1, 1, 1, 1));
                button.setPreferredSize(new Dimension(30, 30));
                buttonPanel.add(button);
            }
        }
        panel.add(buttonPanel);

        setContentPane(panel);
        pack();
        setVisible(true);
        setSize(1370, 740);

    }

    public void removeCloud(int i, int j, String status) {
        buttonList.get(i).get(j).setIcon(null);
        if (status.equals("horizontal"))
            buttonList.get(i).get(j).setIcon(redHorizontalImage);
        else
            buttonList.get(i).get(j).setIcon(redVerticalImage);
    }

    public void findUnnecessarySquare(ArrayList<Integer> squareListI, ArrayList<Integer> squareListJ, Robot robot){
        for (int i = 0; i < squareListI.size()-1; i++) {
            for (int j = i+1; j < squareListJ.size(); j++) {
                if(squareListI.get(i) == squareListI.get(j) && squareListJ.get(i) == squareListJ.get(j)){
                    for (int k = i+1; k < j; k++) {
                        buttonList.get(squareListI.get(k)).get(squareListJ.get(k)).setIcon(blueVerticalImage);
                    }
                    robot.setSquareNumber(j - i);
                }
            }
        }
    }

    public static Boolean checkSquare(int i, int j) {
        if (i > -1 && i < gridList.size() && j > -1 && j < gridList.size()) {
            if ((gridList.get(i).get(j) == 0 || buttonList.get(i).get(j).getBackground() == Color.GREEN)
                    && (buttonList.get(i).get(j).getBackground() != Color.GRAY))
                return true;
            else
                return false;
        } else
            return false;
    }

    public static Boolean checkSquare(int i, int j, int k) {
        if (buttonList.get(i).get(j).getBackground() != Color.BLACK) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean isTargetReached(int i, int j) {
        if (buttonList.get(i).get(j).getText().equals("T")) {
            return true;
        } else
            return false;
    }

    public static Boolean isTargetReached(int i, int j, int k) {
        if (i == buttonList.size() - 2 && j == buttonList.size() - 1) {
            return true;
        } else
            return false;
    }

    public void takeMatrixValues(Robot robot) {
        JFrame frame = new JFrame("Mobile Robot Problem - 2");
        JLabel rowText = new JLabel("ROW : ");
        rowText.setBounds(610, 100, 100, 50);
        rowText.setFont(new Font("Arial", Font.BOLD, 17));
        rowText.setForeground(Color.WHITE);
        JTextArea row = new JTextArea();
        row.setBounds(710, 115, 50, 20);
        row.setBackground(Color.PINK.darker());
        row.setForeground(Color.WHITE);
        row.setFont(new Font("Arial", Font.BOLD, 17));
        JLabel columnText = new JLabel("COLUMN : ");
        columnText.setBounds(610, 150, 100, 50);
        columnText.setFont(new Font("Arial", Font.BOLD, 17));
        columnText.setForeground(Color.WHITE);
        JTextArea column = new JTextArea();
        column.setBounds(710, 165, 50, 20);
        column.setBackground(Color.PINK.darker());
        column.setForeground(Color.WHITE);
        column.setFont(new Font("Arial", Font.BOLD, 17));
        JButton startBtn = createButton("Start");
        startBtn.setBounds(660, 220, 70, 30);

        ImageIcon background = new ImageIcon("D:\\projects\\secondterm\\firstproject\\App\\src\\background.jpg");
        frame.setContentPane(new JLabel(background));

        frame.add(rowText);
        frame.add(row);
        frame.add(columnText);
        frame.add(column);
        frame.add(startBtn);

        frame.setSize(1370, 740);
        frame.setLayout(null);
        frame.setVisible(true);

        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int matrixNumber = Integer.parseInt(row.getText());
                displayMatrix(robot, matrixNumber, 1);
            }
        });

    }

    public void displayMatrix(Robot robot, int matrixNumber, int number) {
        displayNumber = number;
        buttonList = new ArrayList<ArrayList<JButton>>();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        startBtn = createButton("Start");
        changeBtn = createButton("Change");
        showResultBtn = createButton("Show Result");
        gridBtn = createButton("Grid");
        Implementation implement = new Implementation();
        leftPanel.add(implement.timeText);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 5)));
        leftPanel.add(implement.timeCounter);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        leftPanel.add(implement.squareText);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 5)));
        leftPanel.add(implement.squareCounter);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 50)));
        leftPanel.add(startBtn);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        leftPanel.add(changeBtn);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        leftPanel.add(showResultBtn);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        leftPanel.add(gridBtn);
        panel.add(leftPanel);

        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                implement.startGame(robot, implement);
            }
        });

        showResultBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                robot.faster();
            }
        });

        gridBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < buttonList.size(); i++) {
                    for (int j = 0; j < buttonList.size(); j++) {
                        if (buttonList.get(i).get(j).getIcon() != null) {
                            if (!buttonList.get(i).get(j).getIcon().toString()
                                    .equals(redHorizontalImage.toString())
                                    && !buttonList.get(i).get(j).getIcon().toString()
                                            .equals(redVerticalImage.toString())) {
                                buttonList.get(i).get(j).setIcon(null);
                            }
                        }
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(matrixNumber, matrixNumber, 0, 0));
        ImageIcon cloudImage = new ImageIcon(getClass().getResource("cloud2.jpg"));
        for (int i = 0; i < matrixNumber; i++) {
            buttonList.add(new ArrayList<JButton>());
            for (int j = 0; j < matrixNumber; j++) {
                JButton button = new JButton();
                if ((i == 1 && j == 0)) {
                    buttonList.get(i).add(j, button);
                    robot.positionRobot(i, j);
                } else if ((i == matrixNumber - 2 && j == matrixNumber - 1)) {
                    buttonList.get(i).add(j, button);
                } else if (i == 0 || (j == 0 && i != 1) || i == matrixNumber - 1
                        || (j == matrixNumber - 1 && i != matrixNumber - 2)) {
                    button.setBackground(Color.BLACK);
                    buttonList.get(i).add(j, button);
                } else if ((i == 1 && j == 1) || (i == matrixNumber - 2 && j == matrixNumber - 2)) {
                    buttonList.get(i).add(j, button);
                    buttonList.get(i).get(j).setIcon(cloudImage);
                } else {
                    if (displayNumber == 1) {
                        if ((i % 3 == 2 && (j % 3 == 1 || j % 3 == 2)) || (i % 4 == 3 && (j % 4 == 2 || j % 4 == 1))
                                || (i % 5 == 4 && j % 5 == 3) || (i % 6 == 5 && j % 6 == 4)
                                || (i % 7 == 6 && j % 7 == 5)
                                || (i % 15 == 10 && j % 15 == 0)) {
                            ObstacleOne obstacleOne = new ObstacleOne();
                            obstacleOne.createObstacle(button, 2);
                            buttonList.get(i).add(j, button);
                            buttonList.get(i).get(j).setIcon(cloudImage);
                        }
                        
                    }else if(displayNumber == 2){
                        if ((i % 5 == 1 && j % 7 == 0) || (i % 5 == 2 && (j % 5 == 0 || j % 4 == 0))
                        || (i % 5 == 3 && (j % 4 == 2 || j % 6 == 5)) || (i % 5 == 4 && j % 4 == 3)
                        || (i % 5 == 0 && (j % 4 == 0 || j % 5 == 1))) {
                            ObstacleOne obstacleOne = new ObstacleOne();
                            obstacleOne.createObstacle(button, 2);
                            buttonList.get(i).add(j, button);
                            buttonList.get(i).get(j).setIcon(cloudImage);
                        }
                    }


                    buttonList.get(i).add(j, button);
                    buttonList.get(i).get(j).setIcon(cloudImage);
                }
                button.setMargin(new Insets(1, 1, 1, 1));
                button.setPreferredSize(new Dimension(20, 20));
                buttonPanel.add(button);
            }
            panel.add(buttonPanel);
        }

        if(displayNumber == 1)
            displayNumber = 2;
        else
            displayNumber = 1;
        changeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Robot robot = new Robot(displayNumber+2);
                displayMatrix(robot, matrixNumber, displayNumber);
            }
        });

        setContentPane(panel);
        pack();
        setVisible(true);
        setSize(1370, 740);
    }

}

abstract class Obstacle extends JFrame {

    ArrayList<ArrayList<Integer>> list = new ArrayList<>();

    public Obstacle() {

    }

    public Obstacle(ArrayList<ArrayList<Integer>> list) {
        this.list = list;
    }

    public void createObstacle(JButton button) {
        button.setBackground(Color.GRAY);
    }

    public void createObstacle(JButton button, int number) {
        button.setBackground(Color.BLACK);
    }

}

class ObstacleOne extends Obstacle {

    public ObstacleOne() {
        super();
    }

    public ObstacleOne(ArrayList<ArrayList<Integer>> list) {
        super();
    }

}

class ObstacleTwo extends Obstacle {

    public ObstacleTwo() {
        super();
    }

    public ObstacleTwo(ArrayList<ArrayList<Integer>> list) {
        super();
    }

}

class ObstacleThree extends Obstacle {

    public ObstacleThree() {
        super();
    }

    public ObstacleThree(ArrayList<ArrayList<Integer>> list) {
        super();
    }

}

class Implementation extends JFrame {

    public JLabel timeText = new JLabel("TIME");
    public JLabel timeCounter;
    public JLabel squareText = new JLabel("SQUARE");
    public JLabel squareCounter;
    static long startTime, endTime, duration;

    public Implementation() {
        timeCounter = new JLabel();
        timeCounter.setFont(new Font("Arial", Font.BOLD, 16));
        timeCounter.setForeground(Color.MAGENTA);
        squareCounter = new JLabel();
        squareCounter.setFont(new Font("Arial", Font.BOLD, 16));
        squareCounter.setForeground(Color.MAGENTA);
        timeText.setFont(new Font("Arial", Font.BOLD, 15));
        timeText.setForeground(Color.PINK.darker());
        squareText.setFont(new Font("Arial", Font.BOLD, 15));
        squareText.setForeground(Color.PINK.darker());
    }

    public void startGame(Robot robot, Implementation implement) {
        startTime = System.nanoTime();
        robot.moveRobot(implement);
    }

    public void changeFile() {
        Robot robot = new Robot(2);
        Grid grid = new Grid();
        grid.createList();
        grid.displayGUI(robot);
    }

    public void finishGame(Robot robot) {
        endTime = System.nanoTime();
        calculateTime();
        squareCounter.setText(String.valueOf(robot.squareCounter));
    }

    public void calculateTime() {
        duration = (endTime - startTime) / 1000000000;
        timeCounter.setText(duration + " s");
    }

}

public class App extends JFrame {
    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("Mobile Robot");
        JLabel choiceText = new JLabel("MAKE YOUR CHOICE");
        choiceText.setFont(new Font("Arial", Font.BOLD, 25));
        choiceText.setForeground(Color.CYAN);
        JButton problem1Btn = new JButton("Problem - 1");
        problem1Btn.setFont(new Font("Arial", Font.BOLD, 16));
        problem1Btn.setForeground(Color.CYAN);
        problem1Btn.setBackground(Color.PINK.darker());
        problem1Btn.setBorder(null);
        problem1Btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton problem2Btn = new JButton("Problem - 2");
        problem2Btn.setFont(new Font("Arial", Font.BOLD, 16));
        problem2Btn.setForeground(Color.CYAN);
        problem2Btn.setBackground(Color.PINK.darker());
        problem2Btn.setBorder(null);
        problem2Btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        choiceText.setBounds(600, 100, 400, 50);
        problem1Btn.setBounds(100, 100, 200, 50);
        problem2Btn.setBounds(1070, 100, 200, 50);

        ImageIcon background = new ImageIcon("D:\\projects\\secondterm\\firstproject\\App\\src\\background.jpg");
        frame.setContentPane(new JLabel(background));
        frame.add(choiceText);
        frame.add(problem1Btn);
        frame.add(problem2Btn);

        frame.setSize(1370, 740);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        problem1Btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Robot robot = new Robot(1);

                Grid grid = new Grid();
                grid.createList();
                grid.displayGUI(robot);
            }
        });

        problem2Btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Robot robot = new Robot(3);

                Grid grid = new Grid(2);
                grid.takeMatrixValues(robot);

            }
        });

    }
}
