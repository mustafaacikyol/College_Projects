import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.concurrent.*;
import java.time.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import org.jdatepicker.impl.*;
import java.util.Properties;

class Entrance extends JFrame {
    public Entrance() {

    }

    public void createInterface() {
        JFrame frame = new JFrame("Meta Land Game");
        JPanel entrancePanel = new JPanel();
        JButton adminBtn = new JButton("Admin");
        adminBtn.setBounds(500, 300, 150, 50);
        adminBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton playerBtn = new JButton("Player");
        playerBtn.setBounds(750, 300, 150, 50);
        playerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        entrancePanel.add(adminBtn);
        entrancePanel.add(playerBtn);

        frame.setContentPane(entrancePanel);
        frame.setSize(1370, 740);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        adminBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AdminPanel aPanel = new AdminPanel();
                aPanel.logIn();
            }
        });

        playerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlayerPanel pPanel = new PlayerPanel();
                pPanel.logIn();
            }
        });
    }
}

class AdminPanel extends JFrame {
    JFrame frame;
    JPanel panel = new JPanel();
    JPanel gameInfoLeftPanel = new JPanel();
    JPanel gameInfoRightPanel = new JPanel();
    JPanel right2Panel = new JPanel();
    int rowValue = 1;
    int columnValue = 1;

    public void logIn() {
        frame = new JFrame("Admin Panel Log In");
        JLabel adminUserNameText = new JLabel("Admin Username : ");
        adminUserNameText.setBounds(500, 200, 150, 30);
        JTextField adminUserName = new JTextField();
        adminUserName.setBounds(650, 200, 200, 30);
        JLabel adminPasswordText = new JLabel("Admin Password : ");
        adminPasswordText.setBounds(500, 300, 150, 30);
        JPasswordField adminPassword = new JPasswordField();
        adminPassword.setBounds(650, 300, 200, 30);
        JButton adminSubmitBtn = new JButton("Submit");
        adminSubmitBtn.setBounds(650, 400, 100, 30);
        frame.add(adminUserNameText);
        frame.add(adminPasswordText);
        frame.add(adminUserName);
        frame.add(adminPassword);
        frame.add(adminSubmitBtn);

        adminSubmitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = adminUserName.getText();
                String password = new String(adminPassword.getPassword());
                try {
                    PreparedStatement st = (PreparedStatement) App.connect
                            .prepareStatement(
                                    "Select admin_username, admin_password from admin where admin_username=? and admin_password=?");

                    st.setString(1, userName);
                    st.setString(2, password);
                    ResultSet rs = st.executeQuery();
                    if (rs.next()) {
                        dispose();

                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        dashboard();
                    } else {
                        JOptionPane.showMessageDialog(adminSubmitBtn, "Wrong Username & Password");
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });

        frame.setSize(1370, 740);
        frame.setLayout(null);
        frame.setVisible(true);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void dashboard() {

        frame = new JFrame("Admin Panel Dashboard");
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JButton startBtn = new JButton("Start New Game");
        startBtn.setMargin(new Insets(10, 10, 10, 10));
        startBtn.setPreferredSize(new Dimension(130, 50));
        JButton newPlayerBtn = new JButton("Make New Player");
        newPlayerBtn.setMargin(new Insets(10, 10, 10, 10));
        newPlayerBtn.setPreferredSize(new Dimension(130, 50));
        leftPanel.add(startBtn);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        leftPanel.add(newPlayerBtn);
        panel.add(leftPanel);
        retrieveGridArea();
        retrieveGameInfo();
        retrievePlayerInfo();

        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frameForm = new JFrame("Game Grid");
                JLabel rowText = new JLabel("Row : ");
                rowText.setBounds(50, 50, 100, 30);
                JTextField row = new JTextField();
                row.setBounds(200, 50, 50, 30);
                JLabel columnText = new JLabel("Column : ");
                columnText.setBounds(50, 150, 100, 30);
                JTextField column = new JTextField();
                column.setBounds(200, 150, 50, 30);
                JButton submitBtn = new JButton("Submit");
                submitBtn.setBounds(100, 250, 100, 30);
                frameForm.add(rowText);
                frameForm.add(row);
                frameForm.add(columnText);
                frameForm.add(column);
                frameForm.add(submitBtn);

                submitBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rowValue = Integer.parseInt(row.getText());
                        columnValue = Integer.parseInt(column.getText());
                        frameForm.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        // frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        startGame();
                        makeGridArea();
                        retrieveGridArea();
                        retrieveGameInfo();
                        retrievePlayerInfo();
                    }
                });

                frameForm.setSize(300, 400);
                frameForm.setLayout(null);
                frameForm.setVisible(true);
            }
        });

        newPlayerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frameForm = new JFrame("New Player");
                JLabel playerNameText = new JLabel("Player Name : ");
                playerNameText.setBounds(50, 50, 150, 30);
                JTextField playerName = new JTextField();
                playerName.setBounds(200, 50, 150, 30);
                JLabel playerSurnameText = new JLabel("Player Surname : ");
                playerSurnameText.setBounds(50, 150, 150, 30);
                JTextField playerSurname = new JTextField();
                playerSurname.setBounds(200, 150, 150, 30);
                JLabel playerPasswordText = new JLabel("Player Password : ");
                playerPasswordText.setBounds(50, 250, 150, 30);
                JPasswordField playerPassword = new JPasswordField();
                playerPassword.setBounds(200, 250, 150, 30);
                JButton submitBtn = new JButton("Add New Player");
                submitBtn.setBounds(100, 350, 150, 30);
                JLabel confirmText = new JLabel("NEW PLAYER ADDED");
                confirmText.setBounds(100, 400, 150, 30);
                confirmText.setVisible(false);
                JLabel warningText = new JLabel("error unable to add new player");
                warningText.setBounds(100, 400, 150, 30);
                warningText.setVisible(false);
                frameForm.add(playerNameText);
                frameForm.add(playerName);
                frameForm.add(playerSurnameText);
                frameForm.add(playerSurname);
                frameForm.add(playerPasswordText);
                frameForm.add(playerPassword);
                frameForm.add(submitBtn);
                frameForm.add(confirmText);
                frameForm.add(warningText);

                submitBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = playerName.getText();
                        String surname = playerSurname.getText();
                        String password = new String(playerPassword.getPassword());
                        try {
                            PreparedStatement st = (PreparedStatement) App.connect
                                    .prepareStatement(
                                            "insert into player (player_name, player_surname, player_password, initial_meal_amount, initial_property_amount, initial_money_amount) values (?,?,?,?,?,?)");

                            st.setString(1, name);
                            st.setString(2, surname);
                            st.setString(3, password);
                            st.setInt(4, 300);
                            st.setInt(5, 500);
                            st.setInt(6, 5000);
                            if (st.executeUpdate() == 1) {
                                confirmText.setVisible(true);
                                CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                                    frameForm.dispatchEvent(new WindowEvent(frameForm, WindowEvent.WINDOW_CLOSING));
                                });
                                retrieveGameInfo();
                                retrievePlayerInfo();

                            } else {
                                warningText.setVisible(true);
                                CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS).execute(() -> {
                                    warningText.setVisible(false);
                                });
                            }

                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                    }
                });

                frameForm.setSize(400, 500);
                frameForm.setLayout(null);
                frameForm.setVisible(true);
            }
        });

        frame.setContentPane(panel);
        frame.pack();
        frame.setSize(1370, 740);
        // frame.setLayout(null);
        frame.setVisible(true);

    }

    public void startGame() {
        try {
            PreparedStatement st = (PreparedStatement) App.connect
                    .prepareStatement(
                            "insert into game (game_start_date, playground_size) values (?,?)");

            st.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
            st.setString(2, rowValue + "x" + columnValue);
            st.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
    }

    public void makeGridArea() {
        int areaCount = rowValue * columnValue;
        try {
            Statement st1 = App.connect.createStatement();
            String sql = ("select game_id from game");
            ResultSet rs = st1.executeQuery(sql);
            int gameId = 0;
            if (rs.next()) {
                gameId = rs.getInt("game_id");
            }

            for (int i = 1; i <= areaCount; i++) {
                PreparedStatement st = (PreparedStatement) App.connect
                        .prepareStatement(
                                "insert into area (area_no, area_type, area_owner, area_game) values (?,?,?,?)");

                PreparedStatement st2 = (PreparedStatement) App.connect
                        .prepareStatement(
                                "insert into land (land_no, land_price) values (?,?)");

                PreparedStatement st3 = (PreparedStatement) App.connect
                        .prepareStatement(
                                "insert into market (market_id, market_meal_fee, market_fixed_income, market_level, market_capacity, admin_operating_fee, player_operating_fee, market_price, market_rental_price, market_level_start_date) values (?,?,?,?,?,?,?,?,?,?)");

                PreparedStatement st4 = (PreparedStatement) App.connect
                        .prepareStatement(
                                "insert into store (store_id, store_property_fee, store_fixed_income, store_level, store_capacity, admin_operating_fee, player_operating_fee, store_price, store_rental_price, store_level_start_date) values (?,?,?,?,?,?,?,?,?,?)");

                PreparedStatement st5 = (PreparedStatement) App.connect
                        .prepareStatement(
                                "insert into realestate (realestate_id, realestate_commission, realestate_fixed_income, realestate_level, realestate_capacity, admin_operating_fee, player_operating_fee, realestate_price, realestate_rental_price, realestate_level_start_date) values (?,?,?,?,?,?,?,?,?,?)");

                st.setInt(1, i);
                if (i < 4) {
                    if (i == 1) {
                        st.setString(2, "market");
                        st3.setInt(1, i);
                        st3.setInt(2, 50);
                        st3.setInt(3, 300);
                        st3.setInt(4, 1000);
                        st3.setInt(5, 100000);
                        st3.setInt(6, 200);
                        st3.setInt(7, 100);
                        st3.setInt(8, 1000);
                        st3.setInt(9, 400);
                        st3.setDate(10, java.sql.Date.valueOf(java.time.LocalDate.now()));
                        st3.executeUpdate();
                    } else if (i == 2) {
                        st.setString(2, "store");
                        st4.setInt(1, i);
                        st4.setInt(2, 100);
                        st4.setInt(3, 400);
                        st4.setInt(4, 1000);
                        st4.setInt(5, 100000);
                        st4.setInt(6, 300);
                        st4.setInt(7, 200);
                        st4.setInt(8, 1500);
                        st4.setInt(9, 500);
                        st4.setDate(10, java.sql.Date.valueOf(java.time.LocalDate.now()));
                        st4.executeUpdate();
                    } else if (i == 3) {
                        st.setString(2, "realestate");
                        st5.setInt(1, i);
                        st5.setInt(2, 5);
                        st5.setInt(3, 350);
                        st5.setInt(4, 1000);
                        st5.setInt(5, 100000);
                        st5.setInt(6, 250);
                        st5.setInt(7, 150);
                        st5.setInt(8, 1200);
                        st5.setInt(9, 450);
                        st5.setDate(10, java.sql.Date.valueOf(java.time.LocalDate.now()));
                        st5.executeUpdate();
                    }
                } else {
                    st.setString(2, "land");
                    st2.setInt(1, i);
                    st2.setInt(2, 500);
                    st2.executeUpdate();
                }
                st.setInt(3, 0);
                st.setInt(4, gameId);
                st.executeUpdate();

            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void reducePlayerElement() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                LocalTime myObj = LocalTime.now();
                if (myObj.getHour() == 00 && myObj.getMinute() == 00) {
                    try {
                        PreparedStatement st = (PreparedStatement) App.connect
                                .prepareStatement(
                                        "update player set initial_meal_amount=initial_meal_amount-daily_meal_expense , initial_property_amount=initial_property_amount-daily_property_expense, initial_money_amount=initial_money_amount-daily_money_expense");

                        st.executeUpdate();

                        PreparedStatement st2 = (PreparedStatement) App.connect
                                .prepareStatement(
                                        "delete from player where initial_meal_amount<=0 or initial_property_amount<=0");

                        st2.executeUpdate();

                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }

                }
            }
        }, 0, 60000);
    }

    public void retrieveGridArea() {
        try {
            ArrayList<Integer> areaNoList = new ArrayList<>();
            ArrayList<String> areaTypeList = new ArrayList<>();
            ArrayList<Integer> areaOwnerList = new ArrayList<>();
            ArrayList<JButton> buttonList = new ArrayList<>();
            ArrayList<Integer> priceList = new ArrayList<>();
            ArrayList<Integer> rentalPriceList = new ArrayList<>();
            ArrayList<Integer> feeList = new ArrayList<>();
            ArrayList<Integer> fixedIncomeList = new ArrayList<>();
            ArrayList<Integer> levelList = new ArrayList<>();
            ArrayList<Integer> capacityList = new ArrayList<>();
            ArrayList<Integer> adminOperatingFeeList = new ArrayList<>();
            ArrayList<Integer> playerOperatingFeeList = new ArrayList<>();
            Statement st = App.connect.createStatement();
            String sql = ("select playground_size from game");
            ResultSet rs = st.executeQuery(sql);
            String size = "";
            if (rs.next()) {
                size = rs.getString("playground_size");
                int row = Character.getNumericValue(size.toCharArray()[0]);
                int column = Character.getNumericValue(size.toCharArray()[2]);

                Statement st1 = App.connect.createStatement();
                String sql1 = ("select area.area_no, area.area_type, area.area_owner, land.land_no, land.land_price, market.market_id, market.market_price, market.market_rental_price, market.market_meal_fee, market.market_fixed_income, market_level, market.market_capacity, market.admin_operating_fee, market.player_operating_fee, store.store_id, store.store_price, store.store_rental_price, store.store_property_fee, store.store_fixed_income, store.store_level, store.store_capacity, store.admin_operating_fee, store.player_operating_fee, realestate.realestate_id, realestate.realestate_price, realestate.realestate_rental_price, realestate.realestate_commission, realestate.realestate_fixed_income, realestate.realestate_level, realestate.realestate_capacity, realestate.admin_operating_fee, realestate.player_operating_fee from area left join land on area.area_no=land.land_no left join market on area.area_no=market.market_id left join store on area.area_no=store.store_id left join realestate on area.area_no=realestate.realestate_id");
                ResultSet rs1 = st1.executeQuery(sql1);

                JPanel rightPanel = new JPanel();
                rightPanel.setLayout(new GridLayout(row, column, 20, 20));
                // frame = new JFrame("Admin Panel Game");

                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        if (rs1.next()) {
                            JButton button = new JButton(rs1.getString("area_no"));
                            button.setMargin(new Insets(1, 1, 1, 1));
                            button.setPreferredSize(new Dimension(50, 50));
                            if (rs1.getString("area_type").equals("land")) {
                                button.setBackground(Color.ORANGE);
                                priceList.add(rs1.getInt("land_price"));
                            } else if (rs1.getString("area_type").equals("market")) {
                                button.setBackground(Color.RED);
                                priceList.add(rs1.getInt("market_price"));
                                rentalPriceList.add(rs1.getInt("market_rental_price"));
                                feeList.add(rs1.getInt("market_meal_fee"));
                                fixedIncomeList.add(rs1.getInt("market_fixed_income"));
                                levelList.add(rs1.getInt("market_level"));
                                capacityList.add(rs1.getInt("market_capacity"));
                                adminOperatingFeeList.add(rs1.getInt("market.admin_operating_fee"));
                                playerOperatingFeeList.add(rs1.getInt("market.player_operating_fee"));
                            } else if (rs1.getString("area_type").equals("store")) {
                                button.setBackground(Color.BLUE);
                                priceList.add(rs1.getInt("store_price"));
                                rentalPriceList.add(rs1.getInt("store_rental_price"));
                                feeList.add(rs1.getInt("store_property_fee"));
                                fixedIncomeList.add(rs1.getInt("store_fixed_income"));
                                levelList.add(rs1.getInt("store_level"));
                                capacityList.add(rs1.getInt("store_capacity"));
                                adminOperatingFeeList.add(rs1.getInt("store.admin_operating_fee"));
                                playerOperatingFeeList.add(rs1.getInt("store.player_operating_fee"));
                            } else if (rs1.getString("area_type").equals("realestate")) {
                                button.setBackground(Color.MAGENTA);
                                priceList.add(rs1.getInt("realestate_price"));
                                rentalPriceList.add(rs1.getInt("realestate_rental_price"));
                                feeList.add(rs1.getInt("realestate_commission"));
                                fixedIncomeList.add(rs1.getInt("realestate_fixed_income"));
                                levelList.add(rs1.getInt("realestate_level"));
                                capacityList.add(rs1.getInt("realestate_capacity"));
                                adminOperatingFeeList.add(rs1.getInt("realestate.admin_operating_fee"));
                                playerOperatingFeeList.add(rs1.getInt("realestate.player_operating_fee"));
                            }
                            areaNoList.add(rs1.getInt("area_no"));
                            areaTypeList.add(rs1.getString("area_type"));
                            areaOwnerList.add(rs1.getInt("area_owner"));
                            buttonList.add(button);

                            rightPanel.add(button);
                        }
                    }
                }

                for (int i = 0; i < buttonList.size(); i++) {
                    int inneri = i;
                    buttonList.get(i).addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                            JFrame frameArea = new JFrame();
                            JPanel panelArea = new JPanel();
                            JPanel infoLeftPanel = new JPanel();
                            infoLeftPanel.setLayout(new BoxLayout(infoLeftPanel, BoxLayout.Y_AXIS));
                            JLabel areaNo = new JLabel("Area No : ");
                            JLabel areaType = new JLabel("Area Type : ");
                            JLabel areaOwner = new JLabel("Area Owner : ");
                            JLabel price = new JLabel("Price : ");
                            JLabel rentalPrice = new JLabel("Rental Price : ");
                            JLabel fee = new JLabel("Fee : ");
                            JLabel commission = new JLabel("Commission : ");
                            JLabel fixedIncome = new JLabel("Fixed Income : ");
                            JLabel level = new JLabel("Level : ");
                            JLabel capacity = new JLabel("Capacity : ");
                            JLabel adminOperatingFee = new JLabel("Admin Operating Fee : ");
                            JLabel playerOperatingFee = new JLabel("Player Operating Fee : ");
                            infoLeftPanel.add(areaNo);
                            infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            infoLeftPanel.add(areaType);
                            infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            infoLeftPanel.add(areaOwner);
                            infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            infoLeftPanel.add(price);
                            infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            if (areaTypeList.get(inneri).equals("market") || areaTypeList.get(inneri).equals("store")) {
                                infoLeftPanel.add(rentalPrice);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(fee);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(fixedIncome);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(level);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(capacity);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(adminOperatingFee);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(playerOperatingFee);
                            } else if (areaTypeList.get(inneri).equals("realestate")) {
                                infoLeftPanel.add(rentalPrice);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(commission);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(fixedIncome);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(level);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(capacity);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(adminOperatingFee);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(playerOperatingFee);
                            }
                            panelArea.add(infoLeftPanel);

                            JPanel infoRightPanel = new JPanel();
                            infoRightPanel.setLayout(new BoxLayout(infoRightPanel, BoxLayout.Y_AXIS));
                            JLabel areaNoValue = new JLabel(Integer.toString(areaNoList.get(inneri)));
                            JLabel areaTypeValue = new JLabel(areaTypeList.get(inneri));
                            JLabel areaOwnerValue = new JLabel();
                            JLabel priceValue = new JLabel(Integer.toString(priceList.get(inneri)));
                            JLabel rentalPriceValue = new JLabel();
                            JLabel feeValue = new JLabel();
                            JLabel fixedIncomeValue = new JLabel();
                            JLabel levelValue = new JLabel();
                            JLabel capacityValue = new JLabel();
                            JLabel adminOperatingFeeValue = new JLabel();
                            JLabel playerOperatingFeeValue = new JLabel();
                            if (!areaTypeList.get(inneri).equals("land")) {
                                rentalPriceValue.setText(Integer.toString(rentalPriceList.get(inneri)));
                                feeValue.setText(Integer.toString(feeList.get(inneri)));
                                fixedIncomeValue.setText(Integer.toString(fixedIncomeList.get(inneri)));
                                levelValue.setText(Integer.toString(levelList.get(inneri)));
                                capacityValue.setText(Integer.toString(capacityList.get(inneri)));
                                adminOperatingFeeValue.setText(Integer.toString(adminOperatingFeeList.get(inneri)));
                                playerOperatingFeeValue.setText(Integer.toString(playerOperatingFeeList.get(inneri)));
                            }

                            if (areaOwnerList.get(inneri) == 0) {
                                areaOwnerValue.setText("admin");
                            } else {
                                try {
                                    Statement st = App.connect.createStatement();
                                    String sql = ("select area.area_owner, player.player_no, player.player_name, player.player_surname from area inner join player on area.area_owner=player.player_no");
                                    ResultSet rs = st.executeQuery(sql);
                                    rs.next();
                                    areaOwnerValue.setText(
                                            rs.getString("player.player_name" + " " + "player.player_surname"));
                                } catch (SQLException sqlException) {
                                    sqlException.printStackTrace();
                                }

                            }

                            infoRightPanel.add(areaNoValue);
                            infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            infoRightPanel.add(areaTypeValue);
                            infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            infoRightPanel.add(areaOwnerValue);
                            infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            infoRightPanel.add(priceValue);
                            infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            if (!areaTypeList.get(inneri).equals("land")) {
                                infoRightPanel.add(rentalPriceValue);
                                infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoRightPanel.add(feeValue);
                                infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoRightPanel.add(fixedIncomeValue);
                                infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoRightPanel.add(levelValue);
                                infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoRightPanel.add(capacityValue);
                                infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoRightPanel.add(adminOperatingFeeValue);
                                infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoRightPanel.add(playerOperatingFeeValue);
                            }
                            panelArea.add(infoRightPanel);

                            frameArea.setContentPane(panelArea);
                            frameArea.pack();
                            frameArea.setSize(400, 500);
                            // frame.setLayout(null);
                            frameArea.setVisible(true);

                        }
                    });
                }

                panel.add(rightPanel);

                frame.setContentPane(panel);
                frame.pack();
                frame.setSize(1370, 740);
                // frame.setLayout(null);
                frame.setVisible(true);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void retrieveGameInfo() {
        try {
            Statement st = App.connect.createStatement();
            String sql = ("select game_start_date, playground_size from game");
            ResultSet rs = st.executeQuery(sql);

            Statement st1 = App.connect.createStatement();
            String sql1 = ("select count(*) from player");
            ResultSet rs1 = st1.executeQuery(sql1);

            if (rs.next() && rs1.next()) {
                gameInfoLeftPanel.removeAll();
                gameInfoRightPanel.removeAll();
                // add your elements

                gameInfoLeftPanel.setLayout(new BoxLayout(gameInfoLeftPanel, BoxLayout.Y_AXIS));
                JLabel gameStartDate = new JLabel("Game Start Date : ");
                JLabel playgroundSize = new JLabel("Playground Size : ");
                JLabel numberOfPlayer = new JLabel("Number of Player : ");
                gameInfoLeftPanel.add(gameStartDate);
                gameInfoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                gameInfoLeftPanel.add(playgroundSize);
                gameInfoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                gameInfoLeftPanel.add(numberOfPlayer);
                panel.add(gameInfoLeftPanel);

                gameInfoRightPanel.setLayout(new BoxLayout(gameInfoRightPanel, BoxLayout.Y_AXIS));
                JLabel gameStartDateValue = new JLabel(rs.getString("game_start_date"));
                JLabel playgroundSizeValue = new JLabel(rs.getString("playground_size"));
                JLabel numberOfPlayerValue = new JLabel(Integer.toString(rs1.getInt(1)));
                gameInfoRightPanel.add(gameStartDateValue);
                gameInfoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                gameInfoRightPanel.add(playgroundSizeValue);
                gameInfoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                gameInfoRightPanel.add(numberOfPlayerValue);
                panel.add(gameInfoRightPanel);

                frame.setContentPane(panel);
                frame.pack();
                frame.setSize(1370, 740);
                // frame.setLayout(null);
                frame.setVisible(true);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void retrievePlayerInfo() {

        try {
            ArrayList<JButton> buttonList = new ArrayList<>();
            ArrayList<String> nameList = new ArrayList<>();
            ArrayList<String> surnameList = new ArrayList<>();
            ArrayList<Integer> mealList = new ArrayList<>();
            ArrayList<Integer> propertyList = new ArrayList<>();
            ArrayList<Integer> moneyList = new ArrayList<>();
            Statement st = App.connect.createStatement();
            String sql = ("select player_no, player_name, player_surname, initial_meal_amount, initial_property_amount, initial_money_amount from player");
            ResultSet rs = st.executeQuery(sql);

            right2Panel.removeAll();
            right2Panel.setLayout(new BoxLayout(right2Panel, BoxLayout.Y_AXIS));
            while (rs.next()) {
                JButton button = new JButton(rs.getString("player_name") + " " + rs.getString("player_surname"));
                button.setMargin(new Insets(5, 10, 5, 10));
                button.setPreferredSize(new Dimension(150, 50));
                nameList.add(rs.getString("player_name"));
                surnameList.add(rs.getString("player_surname"));
                mealList.add(rs.getInt("initial_meal_amount"));
                propertyList.add(rs.getInt("initial_property_amount"));
                moneyList.add(rs.getInt("initial_money_amount"));
                buttonList.add(button);
                right2Panel.add(Box.createRigidArea(new Dimension(10, 20)));
                right2Panel.add(button);
            }

            for (int i = 0; i < buttonList.size(); i++) {
                int inneri = i;
                buttonList.get(i).addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        JFrame frameArea = new JFrame();
                        JPanel panelArea = new JPanel();
                        JPanel infoLeftPanel = new JPanel();
                        infoLeftPanel.setLayout(new BoxLayout(infoLeftPanel, BoxLayout.Y_AXIS));
                        JLabel playerName = new JLabel("Player Name : ");
                        JLabel playerSurname = new JLabel("Player Surname : ");
                        JLabel meal = new JLabel("Meal Amount : ");
                        JLabel property = new JLabel("Property Amount : ");
                        JLabel money = new JLabel("Money Amount : ");
                        infoLeftPanel.add(playerName);
                        infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                        infoLeftPanel.add(playerSurname);
                        infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                        infoLeftPanel.add(meal);
                        infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                        infoLeftPanel.add(property);
                        infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                        infoLeftPanel.add(money);

                        panelArea.add(infoLeftPanel);

                        JPanel infoRightPanel = new JPanel();
                        infoRightPanel.setLayout(new BoxLayout(infoRightPanel, BoxLayout.Y_AXIS));
                        JLabel playerNameValue = new JLabel(nameList.get(inneri));
                        JLabel playerSurnameValue = new JLabel(surnameList.get(inneri));
                        JLabel mealValue = new JLabel(Integer.toString(mealList.get(inneri)));
                        JLabel propertyValue = new JLabel(Integer.toString(propertyList.get(inneri)));
                        JLabel moneyValue = new JLabel(Integer.toString(moneyList.get(inneri)));

                        infoRightPanel.add(playerNameValue);
                        infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                        infoRightPanel.add(playerSurnameValue);
                        infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                        infoRightPanel.add(mealValue);
                        infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                        infoRightPanel.add(propertyValue);
                        infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                        infoRightPanel.add(moneyValue);
                        panelArea.add(infoRightPanel);

                        frameArea.setContentPane(panelArea);
                        frameArea.pack();
                        frameArea.setSize(400, 500);
                        // frame.setLayout(null);
                        frameArea.setVisible(true);

                    }
                });
            }

            panel.add(right2Panel);

            frame.setContentPane(panel);
            frame.pack();
            frame.setSize(1370, 740);
            // frame.setLayout(null);
            frame.setVisible(true);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}

class PlayerPanel extends JFrame {
    JFrame frame;
    JPanel panel = new JPanel();
    JPanel gridPanel = new JPanel();
    JPanel playerInfoPanel = new JPanel();
    JPanel workerInfoPanel = new JPanel();
    public int playerId;

    public void logIn() {
        frame = new JFrame("Player Panel Log In");
        JLabel playerNameText = new JLabel("Player Name : ");
        playerNameText.setBounds(500, 200, 150, 30);
        JTextField playerName = new JTextField();
        playerName.setBounds(650, 200, 200, 30);
        JLabel playerSurnameText = new JLabel("Player Surname : ");
        playerSurnameText.setBounds(500, 300, 150, 30);
        JTextField playerSurname = new JTextField();
        playerSurname.setBounds(650, 300, 200, 30);
        JLabel playerPasswordText = new JLabel("Player Password : ");
        playerPasswordText.setBounds(500, 400, 150, 30);
        JPasswordField playerPassword = new JPasswordField();
        playerPassword.setBounds(650, 400, 200, 30);
        JButton playerSubmitBtn = new JButton("Submit");
        playerSubmitBtn.setBounds(650, 500, 100, 30);
        frame.add(playerNameText);
        frame.add(playerSurnameText);
        frame.add(playerPasswordText);
        frame.add(playerName);
        frame.add(playerSurname);
        frame.add(playerPassword);
        frame.add(playerSubmitBtn);

        playerSubmitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = playerName.getText();
                String userSurname = playerSurname.getText();
                String password = new String(playerPassword.getPassword());
                try {
                    PreparedStatement st = (PreparedStatement) App.connect
                            .prepareStatement(
                                    "Select player_no, player_name, player_surname, player_password from player where player_name=? and player_surname=? and player_password=?");

                    st.setString(1, userName);
                    st.setString(2, userSurname);
                    st.setString(3, password);
                    ResultSet rs = st.executeQuery();
                    if (rs.next()) {
                        dispose();

                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        playerId = rs.getInt("player_no");
                        dashboard();
                    } else {
                        JOptionPane.showMessageDialog(playerSubmitBtn, "Wrong Username & Password");
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });

        frame.setSize(1370, 740);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void dashboard() {
        frame = new JFrame("Player Panel Dashboard");
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JButton moveForwardBtn = new JButton("Move Forward");
        moveForwardBtn.setMargin(new Insets(10, 10, 10, 10));
        moveForwardBtn.setPreferredSize(new Dimension(130, 50));
        leftPanel.add(moveForwardBtn);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        panel.add(leftPanel);
        retrieveGridArea();
        retrievePlayerInfo();
        retrieveWorkInfo();

        moveForwardBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame moveForwardFrame = new JFrame("Move Forward");
                JPanel moveForwardPanel = new JPanel();
                JPanel buttonPanel = new JPanel();

                buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
                JButton submitBtn = new JButton("Submit");
                JLabel confirmText = new JLabel("CONFIRM");
                confirmText.setVisible(false);
                buttonPanel.add(confirmText);
                buttonPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 80));
                buttonPanel.add(submitBtn);
                moveForwardPanel.add(buttonPanel);

                JPanel moveForwardLeftPanel = new JPanel();
                moveForwardLeftPanel.setLayout(new BoxLayout(moveForwardLeftPanel, BoxLayout.Y_AXIS));
                JLabel date = new JLabel("Date : ");
                moveForwardLeftPanel.add(date);
                moveForwardPanel.add(moveForwardLeftPanel);

                JPanel moveForwardRightPanel = new JPanel();
                moveForwardRightPanel.setLayout(new BoxLayout(moveForwardRightPanel, BoxLayout.Y_AXIS));

                UtilDateModel model = new UtilDateModel();
                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");
                JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

                moveForwardRightPanel.add(datePanel);
                moveForwardPanel.add(moveForwardRightPanel);

                submitBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        moveForwardFrame.dispatchEvent(new WindowEvent(
                                                    moveForwardFrame, WindowEvent.WINDOW_CLOSING));
                    }
                });

                moveForwardFrame.setContentPane(moveForwardPanel);
                moveForwardFrame.pack();
                moveForwardFrame.setSize(600, 300);
                // frame.setLayout(null);
                moveForwardFrame.setVisible(true);
            }
        });

    }

    public void retrieveGridArea() {

        try {
            ArrayList<Integer> areaNoList = new ArrayList<>();
            ArrayList<String> areaTypeList = new ArrayList<>();
            ArrayList<Integer> areaOwnerList = new ArrayList<>();
            ArrayList<JButton> buttonList = new ArrayList<>();
            ArrayList<Integer> priceList = new ArrayList<>();
            ArrayList<Integer> rentalPriceList = new ArrayList<>();
            ArrayList<Integer> feeList = new ArrayList<>();
            ArrayList<Integer> fixedIncomeList = new ArrayList<>();
            ArrayList<Integer> levelList = new ArrayList<>();
            ArrayList<Integer> capacityList = new ArrayList<>();
            ArrayList<Integer> adminOperatingFeeList = new ArrayList<>();
            ArrayList<Integer> playerOperatingFeeList = new ArrayList<>();
            Statement st = App.connect.createStatement();
            String sql = ("select playground_size from game");
            ResultSet rs = st.executeQuery(sql);
            String size = "";
            if (rs.next()) {
                size = rs.getString("playground_size");
                int row = Character.getNumericValue(size.toCharArray()[0]);
                int column = Character.getNumericValue(size.toCharArray()[2]);

                Statement st1 = App.connect.createStatement();
                String sql1 = ("select area.area_no, area.area_type, area.area_owner, land.land_no, land.land_price, market.market_id, market.market_price, market.market_rental_price, market.market_meal_fee, market.market_fixed_income, market_level, market.market_capacity, market.admin_operating_fee, market.player_operating_fee, store.store_id, store.store_price, store.store_rental_price, store.store_property_fee, store.store_fixed_income, store.store_level, store.store_capacity, store.admin_operating_fee, store.player_operating_fee, realestate.realestate_id, realestate.realestate_price, realestate.realestate_rental_price, realestate.realestate_commission, realestate.realestate_fixed_income, realestate.realestate_level, realestate.realestate_capacity, realestate.admin_operating_fee, realestate.player_operating_fee from area left join land on area.area_no=land.land_no left join market on area.area_no=market.market_id left join store on area.area_no=store.store_id left join realestate on area.area_no=realestate.realestate_id");
                ResultSet rs1 = st1.executeQuery(sql1);

                gridPanel.removeAll();
                gridPanel.setLayout(new GridLayout(row, column, 20, 20));
                // frame = new JFrame("Admin Panel Game");
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        if (rs1.next()) {
                            JButton button = new JButton(rs1.getString("area_no"));
                            button.setMargin(new Insets(1, 1, 1, 1));
                            button.setPreferredSize(new Dimension(50, 50));
                            if (rs1.getString("area_type").equals("land")) {
                                button.setBackground(Color.ORANGE);
                                priceList.add(rs1.getInt("land_price"));
                                rentalPriceList.add(0);
                                feeList.add(0);
                                fixedIncomeList.add(0);
                                levelList.add(0);
                                capacityList.add(0);
                                adminOperatingFeeList.add(0);
                                playerOperatingFeeList.add(0);
                            } else if (rs1.getString("area_type").equals("market")) {
                                button.setBackground(Color.RED);
                                priceList.add(rs1.getInt("market_price"));
                                rentalPriceList.add(rs1.getInt("market_rental_price"));
                                feeList.add(rs1.getInt("market_meal_fee"));
                                fixedIncomeList.add(rs1.getInt("market_fixed_income"));
                                levelList.add(rs1.getInt("market_level"));
                                capacityList.add(rs1.getInt("market_capacity"));
                                adminOperatingFeeList.add(rs1.getInt("market.admin_operating_fee"));
                                playerOperatingFeeList.add(rs1.getInt("market.player_operating_fee"));
                            } else if (rs1.getString("area_type").equals("store")) {
                                button.setBackground(Color.BLUE);
                                priceList.add(rs1.getInt("store_price"));
                                rentalPriceList.add(rs1.getInt("store_rental_price"));
                                feeList.add(rs1.getInt("store_property_fee"));
                                fixedIncomeList.add(rs1.getInt("store_fixed_income"));
                                levelList.add(rs1.getInt("store_level"));
                                capacityList.add(rs1.getInt("store_capacity"));
                                adminOperatingFeeList.add(rs1.getInt("store.admin_operating_fee"));
                                playerOperatingFeeList.add(rs1.getInt("store.player_operating_fee"));
                            } else if (rs1.getString("area_type").equals("realestate")) {
                                button.setBackground(Color.MAGENTA);
                                priceList.add(rs1.getInt("realestate_price"));
                                rentalPriceList.add(rs1.getInt("realestate_rental_price"));
                                feeList.add(rs1.getInt("realestate_commission"));
                                fixedIncomeList.add(rs1.getInt("realestate_fixed_income"));
                                levelList.add(rs1.getInt("realestate_level"));
                                capacityList.add(rs1.getInt("realestate_capacity"));
                                adminOperatingFeeList.add(rs1.getInt("realestate.admin_operating_fee"));
                                playerOperatingFeeList.add(rs1.getInt("realestate.player_operating_fee"));
                            }
                            areaNoList.add(rs1.getInt("area_no"));
                            areaTypeList.add(rs1.getString("area_type"));
                            areaOwnerList.add(rs1.getInt("area_owner"));
                            buttonList.add(button);

                            gridPanel.add(button);

                        }
                    }
                }

                for (int i = 0; i < buttonList.size(); i++) {
                    int inneri = i;
                    buttonList.get(i).addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JFrame frameArea = new JFrame("Area Information");
                            JPanel panelArea = new JPanel();
                            JPanel buttonPanel = new JPanel();
                            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
                            buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 80));
                            JButton applyJobButton = new JButton("Apply Job");
                            JButton doShoppingButton = new JButton("Do Shopping");
                            JButton buyLandButton = new JButton("Buy Land");
                            JButton buildMarketButton = new JButton("Build Market");
                            JButton buildStoreButton = new JButton("Build Store");
                            JButton buildRealestateButton = new JButton("Build Realestate");
                            if (!areaTypeList.get(inneri).equals("land") && areaOwnerList.get(inneri) != playerId) {
                                buttonPanel.add(applyJobButton);
                                buttonPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                buttonPanel.add(doShoppingButton);
                            } else if (areaOwnerList.get(inneri) == playerId && areaTypeList.get(inneri).equals("land")) {
                                buttonPanel.add(buildMarketButton);
                                buttonPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                buttonPanel.add(buildStoreButton);
                                buttonPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                buttonPanel.add(buildRealestateButton);
                            } else if(areaTypeList.get(inneri).equals("land")) {
                                buttonPanel.add(buyLandButton);
                            }
                            panelArea.add(buttonPanel);
                            JPanel infoLeftPanel = new JPanel();
                            infoLeftPanel.setLayout(new BoxLayout(infoLeftPanel, BoxLayout.Y_AXIS));
                            JLabel areaNo = new JLabel("Area No : ");
                            JLabel areaType = new JLabel("Area Type : ");
                            JLabel areaOwner = new JLabel("Area Owner : ");
                            JLabel price = new JLabel("Price : ");
                            JLabel rentalPrice = new JLabel("Rental Price : ");
                            JLabel fee = new JLabel("Fee : ");
                            JLabel commission = new JLabel("Commission : ");
                            JLabel fixedIncome = new JLabel("Fixed Income : ");
                            JLabel level = new JLabel("Level : ");
                            JLabel capacity = new JLabel("Capacity : ");
                            JLabel adminOperatingFee = new JLabel("Admin Operating Fee : ");
                            JLabel playerOperatingFee = new JLabel("Player Operating Fee : ");
                            infoLeftPanel.add(areaNo);
                            infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            infoLeftPanel.add(areaType);
                            infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            infoLeftPanel.add(areaOwner);
                            infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            infoLeftPanel.add(price);
                            infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));

                            if (areaTypeList.get(inneri).equals("market") || areaTypeList.get(inneri).equals("store")) {
                                infoLeftPanel.add(rentalPrice);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(fee);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(fixedIncome);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(level);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(capacity);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(adminOperatingFee);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(playerOperatingFee);
                            } else if (areaTypeList.get(inneri).equals("realestate")) {
                                infoLeftPanel.add(rentalPrice);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(commission);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(fixedIncome);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(level);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(capacity);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(adminOperatingFee);
                                infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoLeftPanel.add(playerOperatingFee);
                            }
                            panelArea.add(infoLeftPanel);

                            JPanel infoRightPanel = new JPanel();
                            infoRightPanel.setLayout(new BoxLayout(infoRightPanel, BoxLayout.Y_AXIS));
                            JLabel areaNoValue = new JLabel(Integer.toString(areaNoList.get(inneri)));
                            JLabel areaTypeValue = new JLabel(areaTypeList.get(inneri));
                            JLabel areaOwnerValue = new JLabel();
                            JLabel priceValue = new JLabel(Integer.toString(priceList.get(inneri)));
                            JLabel rentalPriceValue = new JLabel();
                            JLabel feeValue = new JLabel();
                            JLabel fixedIncomeValue = new JLabel();
                            JLabel levelValue = new JLabel();
                            JLabel capacityValue = new JLabel();
                            JLabel adminOperatingFeeValue = new JLabel();
                            JLabel playerOperatingFeeValue = new JLabel();
                            if (!areaTypeList.get(inneri).equals("land")) {
                                rentalPriceValue.setText(Integer.toString(rentalPriceList.get(inneri)));
                                feeValue.setText(Integer.toString(feeList.get(inneri)));
                                fixedIncomeValue.setText(Integer.toString(fixedIncomeList.get(inneri)));
                                levelValue.setText(Integer.toString(levelList.get(inneri)));
                                capacityValue.setText(Integer.toString(capacityList.get(inneri)));
                                adminOperatingFeeValue.setText(Integer.toString(adminOperatingFeeList.get(inneri)));
                                playerOperatingFeeValue.setText(Integer.toString(playerOperatingFeeList.get(inneri)));

                            }
                            if (areaOwnerList.get(inneri) == 0) {
                                areaOwnerValue.setText("admin");
                            } else {
                                try {
                                    Statement st = App.connect.createStatement();
                                    String sql = ("select area.area_owner, area.area_no, player.player_no, player.player_name, player.player_surname from area inner join player on area.area_owner=player.player_no where area.area_no='"
                                            + areaNoList.get(inneri) + "'");
                                    ResultSet rs = st.executeQuery(sql);
                                    rs.next();
                                    areaOwnerValue.setText(rs.getString("player.player_name") + " "
                                            + rs.getString("player.player_surname"));
                                } catch (SQLException sqlException) {
                                    sqlException.printStackTrace();
                                }

                            }

                            infoRightPanel.add(areaNoValue);
                            infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            infoRightPanel.add(areaTypeValue);
                            infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            infoRightPanel.add(areaOwnerValue);
                            infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            infoRightPanel.add(priceValue);
                            infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                            if (!areaTypeList.get(inneri).equals("land")) {
                                infoRightPanel.add(rentalPriceValue);
                                infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoRightPanel.add(feeValue);
                                infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoRightPanel.add(fixedIncomeValue);
                                infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoRightPanel.add(levelValue);
                                infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoRightPanel.add(capacityValue);
                                infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoRightPanel.add(adminOperatingFeeValue);
                                infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                infoRightPanel.add(playerOperatingFeeValue);
                            }
                            panelArea.add(infoRightPanel);

                            frameArea.setContentPane(panelArea);
                            frameArea.pack();
                            frameArea.setSize(500, 500);
                            // frame.setLayout(null);
                            frameArea.setVisible(true);

                            applyJobButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    JFrame applyJobFrame = new JFrame("Apply Job Information");
                                    JPanel applyJobPanel = new JPanel();
                                    JPanel buttonPanel = new JPanel();

                                    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
                                    JButton submitJobBtn = new JButton("Submit Job");
                                    JLabel confirmText = new JLabel("CONFIRM");
                                    confirmText.setVisible(false);
                                    buttonPanel.add(confirmText);
                                    buttonPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                    buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 80));
                                    buttonPanel.add(submitJobBtn);
                                    applyJobPanel.add(buttonPanel);

                                    JPanel applyJobLeftPanel = new JPanel();
                                    applyJobLeftPanel.setLayout(new BoxLayout(applyJobLeftPanel, BoxLayout.Y_AXIS));
                                    JLabel dailySalary = new JLabel("Daily Salary : ");
                                    JLabel workingHours = new JLabel("Working Hours : ");
                                    applyJobLeftPanel.add(dailySalary);
                                    applyJobLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                    applyJobLeftPanel.add(workingHours);
                                    applyJobPanel.add(applyJobLeftPanel);

                                    JPanel applyJobRightPanel = new JPanel();
                                    applyJobRightPanel.setLayout(new BoxLayout(applyJobRightPanel, BoxLayout.Y_AXIS));
                                    JLabel dailySalaryValue = new JLabel();
                                    if (areaTypeList.get(inneri).equals("market")) {
                                        dailySalaryValue = new JLabel("100");
                                    } else if (areaTypeList.get(inneri).equals("store")) {
                                        dailySalaryValue = new JLabel("200");
                                    } else if (areaTypeList.get(inneri).equals("realestate")) {
                                        dailySalaryValue = new JLabel("300");
                                    }

                                    JLabel workingHoursValue = new JLabel("9:00 - 18:00");
                                    applyJobRightPanel.add(dailySalaryValue);
                                    applyJobRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                    applyJobRightPanel.add(workingHoursValue);
                                    applyJobPanel.add(applyJobRightPanel);

                                    submitJobBtn.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            try {
                                                PreparedStatement st = (PreparedStatement) App.connect
                                                        .prepareStatement(
                                                                "insert into employee (employee_id, employee_start_date, employee_working_hours, business_no) values (?,?,?,?)");

                                                st.setInt(1, playerId);
                                                st.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
                                                st.setString(3, "9:00 - 18:00");
                                                st.setInt(4, inneri + 1);
                                                if (st.executeUpdate() == 1) {
                                                    confirmText.setVisible(true);
                                                    retrieveWorkInfo();
                                                    CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS)
                                                            .execute(() -> {
                                                                applyJobFrame.dispatchEvent(new WindowEvent(
                                                                        applyJobFrame, WindowEvent.WINDOW_CLOSING));
                                                            });

                                                }

                                            } catch (SQLException sqlException) {
                                                sqlException.printStackTrace();
                                            }
                                        }
                                    });
                                    applyJobFrame.setContentPane(applyJobPanel);
                                    applyJobFrame.pack();
                                    applyJobFrame.setSize(400, 150);
                                    // frame.setLayout(null);
                                    applyJobFrame.setVisible(true);
                                }
                            });

                            doShoppingButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    JFrame doShoppingFrame = new JFrame("Do Shopping");
                                    JPanel doShoppingPanel = new JPanel();
                                    JPanel buttonPanel = new JPanel();

                                    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
                                    JButton completeShoppingBtn = new JButton("Complete Shopping");
                                    JLabel confirmText = new JLabel("CONFIRM");
                                    confirmText.setVisible(false);
                                    buttonPanel.add(confirmText);
                                    buttonPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                    buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 80));
                                    buttonPanel.add(completeShoppingBtn);
                                    doShoppingPanel.add(buttonPanel);

                                    JPanel doShoppingLeftPanel = new JPanel();
                                    doShoppingLeftPanel.setLayout(new BoxLayout(doShoppingLeftPanel, BoxLayout.Y_AXIS));
                                    JLabel add = new JLabel();
                                    JLabel price = new JLabel();
                                    if (areaTypeList.get(inneri).equals("market")) {
                                        add = new JLabel("Meal : ");
                                        price = new JLabel("Price : ");
                                    } else if (areaTypeList.get(inneri).equals("store")) {
                                        add = new JLabel("Property : ");
                                        price = new JLabel("Price : ");
                                    } else if (areaTypeList.get(inneri).equals("realestate")) {
                                        price = new JLabel("Commission : ");
                                    }
                                    doShoppingLeftPanel.add(add);
                                    doShoppingLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                    doShoppingLeftPanel.add(price);
                                    doShoppingPanel.add(doShoppingLeftPanel);

                                    JPanel doShoppingRightPanel = new JPanel();
                                    doShoppingRightPanel
                                            .setLayout(new BoxLayout(doShoppingRightPanel, BoxLayout.Y_AXIS));
                                    JLabel addValue = new JLabel();
                                    JLabel priceValue = new JLabel();
                                    if (areaTypeList.get(inneri).equals("market")) {
                                        addValue = new JLabel("100");
                                        priceValue = new JLabel("50");
                                    } else if (areaTypeList.get(inneri).equals("store")) {
                                        addValue = new JLabel("200");
                                        priceValue = new JLabel("100");
                                    } else if (areaTypeList.get(inneri).equals("realestate")) {
                                        priceValue = new JLabel("5");
                                    }
                                    doShoppingRightPanel.add(addValue);
                                    doShoppingRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
                                    doShoppingRightPanel.add(priceValue);
                                    doShoppingPanel.add(doShoppingRightPanel);

                                    completeShoppingBtn.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            try {
                                                PreparedStatement st3 = (PreparedStatement) App.connect
                                                        .prepareStatement(
                                                                "select initial_money_amount from player where player_no='"
                                                                        + playerId + "'");

                                                ResultSet rs3 = st3.executeQuery();
                                                rs3.next();

                                                if (areaTypeList.get(inneri).equals("market")) {
                                                    if (rs3.getInt("initial_money_amount") >= 50) {
                                                        PreparedStatement st = (PreparedStatement) App.connect
                                                                .prepareStatement(
                                                                        "update player set initial_meal_amount=initial_meal_amount+100, initial_money_amount=initial_money_amount-50 where player_no='"
                                                                                + playerId + "'");

                                                        if (st.executeUpdate() == 1) {
                                                            confirmText.setVisible(true);
                                                            CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS)
                                                                    .execute(() -> {
                                                                        doShoppingFrame.dispatchEvent(
                                                                                new WindowEvent(doShoppingFrame,
                                                                                        WindowEvent.WINDOW_CLOSING));
                                                                    });
                                                            retrievePlayerInfo();
                                                            retrieveWorkInfo();
                                                        }
                                                    } else {
                                                        JFrame warningFrame = new JFrame("Warning");
                                                        JPanel warningPanel = new JPanel();
                                                        warningPanel
                                                                .setLayout(new FlowLayout(FlowLayout.CENTER, 50, 40));
                                                        JLabel warningText = new JLabel("You don't have enough money!");
                                                        warningPanel.add(warningText);

                                                        warningFrame.setContentPane(warningPanel);
                                                        warningFrame.pack();
                                                        warningFrame.setSize(300, 150);
                                                        // frame.setLayout(null);
                                                        warningFrame.setVisible(true);
                                                    }
                                                } else if (areaTypeList.get(inneri).equals("store")) {
                                                    if (rs3.getInt("initial_money_amount") >= 100) {
                                                        PreparedStatement st = (PreparedStatement) App.connect
                                                                .prepareStatement(
                                                                        "update player set initial_property_amount=initial_property_amount+200, initial_money_amount=initial_money_amount-100 where player_no='"
                                                                                + playerId + "'");

                                                        if (st.executeUpdate() == 1) {
                                                            confirmText.setVisible(true);
                                                            CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS)
                                                                    .execute(() -> {
                                                                        doShoppingFrame.dispatchEvent(
                                                                                new WindowEvent(doShoppingFrame,
                                                                                        WindowEvent.WINDOW_CLOSING));
                                                                    });
                                                            retrievePlayerInfo();
                                                            retrieveWorkInfo();
                                                        }
                                                    } else {
                                                        JFrame warningFrame = new JFrame("Warning");
                                                        JPanel warningPanel = new JPanel();
                                                        warningPanel
                                                                .setLayout(new FlowLayout(FlowLayout.CENTER, 50, 40));
                                                        JLabel warningText = new JLabel("You don't have enough money!");
                                                        warningPanel.add(warningText);

                                                        warningFrame.setContentPane(warningPanel);
                                                        warningFrame.pack();
                                                        warningFrame.setSize(300, 150);
                                                        // frame.setLayout(null);
                                                        warningFrame.setVisible(true);
                                                    }
                                                }

                                            } catch (SQLException sqlException) {
                                                sqlException.printStackTrace();
                                            }
                                        }
                                    });
                                    doShoppingFrame.setContentPane(doShoppingPanel);
                                    doShoppingFrame.pack();
                                    doShoppingFrame.setSize(400, 150);
                                    // frame.setLayout(null);
                                    doShoppingFrame.setVisible(true);
                                }
                            });

                            buyLandButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        PreparedStatement st3 = (PreparedStatement) App.connect
                                                .prepareStatement(
                                                        "select initial_money_amount from player where player_no='"
                                                                + playerId + "'");

                                        ResultSet rs3 = st3.executeQuery();
                                        rs3.next();
                                        if (rs3.getInt("initial_money_amount") >= 500) {
                                            PreparedStatement st = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "update player set initial_money_amount=initial_money_amount-'"
                                                                    + priceList.get(inneri) + "' where player_no='"
                                                                    + playerId + "'");

                                            st.executeUpdate();
                                            PreparedStatement st2 = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "update area set area_owner='" + playerId
                                                                    + "' where area_no='" + areaNoList.get(inneri)
                                                                    + "'");

                                            st2.executeUpdate();
                                            PreparedStatement st4 = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "insert into sell (sell_price, sell_date, by_realestate_operation, area_no) values (?,?,?,?)");

                                            st4.setInt(1, priceList.get(inneri));
                                            st4.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
                                            st4.setInt(3, 3);
                                            st4.setInt(4, areaNoList.get(inneri));
                                            st4.executeUpdate();
                                            CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS).execute(() -> {
                                                frameArea.dispatchEvent(
                                                        new WindowEvent(frameArea, WindowEvent.WINDOW_CLOSING));
                                            });
                                            retrieveGridArea();
                                            retrievePlayerInfo();
                                            retrieveWorkInfo();
                                        } else {
                                            JFrame warningFrame = new JFrame("Warning");
                                            JPanel warningPanel = new JPanel();
                                            warningPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 40));
                                            JLabel warningText = new JLabel("You don't have enough money!");
                                            warningPanel.add(warningText);

                                            warningFrame.setContentPane(warningPanel);
                                            warningFrame.pack();
                                            warningFrame.setSize(300, 150);
                                            // frame.setLayout(null);
                                            warningFrame.setVisible(true);
                                        }

                                    } catch (SQLException sqlException) {
                                        sqlException.printStackTrace();
                                    }
                                }
                            });

                            buildMarketButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        int marketPrice = 500;
                                        PreparedStatement st5 = (PreparedStatement) App.connect
                                                .prepareStatement(
                                                        "select initial_money_amount from player where player_no='"
                                                                + playerId + "'");

                                        ResultSet rs5 = st5.executeQuery();
                                        rs5.next();
                                        if (rs5.getInt("initial_money_amount") >= 500) {
                                            PreparedStatement st = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "update player set initial_money_amount=initial_money_amount-'"
                                                                    + marketPrice + "' where player_no='" + playerId
                                                                    + "'");

                                            st.executeUpdate();
                                            PreparedStatement st2 = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "update area set area_type='market' where area_no='"
                                                                    + areaNoList.get(inneri) + "'");

                                            st2.executeUpdate();

                                            PreparedStatement st3 = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "insert into market (market_id, market_meal_fee, market_fixed_income, market_level, market_capacity, admin_operating_fee, player_operating_fee, market_price, market_rental_price, market_level_start_date) values (?,?,?,?,?,?,?,?,?,?)");

                                            st3.setInt(1, areaNoList.get(inneri));
                                            st3.setInt(2, 50);
                                            st3.setInt(3, 300);
                                            st3.setInt(4, 1);
                                            st3.setInt(5, 3);
                                            st3.setInt(6, 200);
                                            st3.setInt(7, 100);
                                            st3.setInt(8, 1000);
                                            st3.setInt(9, 400);
                                            st3.setDate(10, java.sql.Date.valueOf(java.time.LocalDate.now()));
                                            st3.executeUpdate();

                                            PreparedStatement st4 = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "delete from land where land_no='" + areaNoList.get(inneri)
                                                                    + "'");

                                            st4.executeUpdate();

                                            buttonList.get(inneri).setBackground(Color.RED);
                                            CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS).execute(() -> {
                                                frameArea.dispatchEvent(
                                                        new WindowEvent(frameArea, WindowEvent.WINDOW_CLOSING));
                                            });
                                            retrieveGridArea();
                                            retrievePlayerInfo();
                                            retrieveWorkInfo();
                                        } else {
                                            JFrame warningFrame = new JFrame("Warning");
                                            JPanel warningPanel = new JPanel();
                                            warningPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 40));
                                            JLabel warningText = new JLabel("You don't have enough money!");
                                            warningPanel.add(warningText);

                                            warningFrame.setContentPane(warningPanel);
                                            warningFrame.pack();
                                            warningFrame.setSize(300, 150);
                                            // frame.setLayout(null);
                                            warningFrame.setVisible(true);
                                        }

                                    } catch (SQLException sqlException) {
                                        sqlException.printStackTrace();
                                    }
                                }
                            });

                            buildStoreButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        int storePrice = 1000;
                                        PreparedStatement st5 = (PreparedStatement) App.connect
                                                .prepareStatement(
                                                        "select initial_money_amount from player where player_no='"
                                                                + playerId + "'");

                                        ResultSet rs5 = st5.executeQuery();
                                        rs5.next();
                                        if (rs5.getInt("initial_money_amount") >= 500) {
                                            PreparedStatement st = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "update player set initial_money_amount=initial_money_amount-'"
                                                                    + storePrice + "' where player_no='" + playerId
                                                                    + "'");

                                            st.executeUpdate();
                                            PreparedStatement st2 = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "update area set area_type='store' where area_no='"
                                                                    + areaNoList.get(inneri) + "'");

                                            st2.executeUpdate();

                                            PreparedStatement st3 = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "insert into store (store_id, store_property_fee, store_fixed_income, store_level, store_capacity, admin_operating_fee, player_operating_fee, store_price, store_rental_price, store_level_start_date) values (?,?,?,?,?,?,?,?,?,?)");

                                            st3.setInt(1, areaNoList.get(inneri));
                                            st3.setInt(2, 100);
                                            st3.setInt(3, 400);
                                            st3.setInt(4, 1);
                                            st3.setInt(5, 3);
                                            st3.setInt(6, 300);
                                            st3.setInt(7, 200);
                                            st3.setInt(8, 1500);
                                            st3.setInt(9, 500);
                                            st3.setDate(10, java.sql.Date.valueOf(java.time.LocalDate.now()));
                                            st3.executeUpdate();

                                            PreparedStatement st4 = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "delete from land where land_no='" + areaNoList.get(inneri)
                                                                    + "'");

                                            st4.executeUpdate();

                                            buttonList.get(inneri).setBackground(Color.RED);
                                            CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS).execute(() -> {
                                                frameArea.dispatchEvent(
                                                        new WindowEvent(frameArea, WindowEvent.WINDOW_CLOSING));
                                            });
                                            retrieveGridArea();
                                            retrievePlayerInfo();
                                            retrieveWorkInfo();
                                        } else {
                                            JFrame warningFrame = new JFrame("Warning");
                                            JPanel warningPanel = new JPanel();
                                            warningPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 40));
                                            JLabel warningText = new JLabel("You don't have enough money!");
                                            warningPanel.add(warningText);

                                            warningFrame.setContentPane(warningPanel);
                                            warningFrame.pack();
                                            warningFrame.setSize(300, 150);
                                            // frame.setLayout(null);
                                            warningFrame.setVisible(true);
                                        }

                                    } catch (SQLException sqlException) {
                                        sqlException.printStackTrace();
                                    }
                                }
                            });

                            buildRealestateButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        int realestatePrice = 700;
                                        PreparedStatement st5 = (PreparedStatement) App.connect
                                                .prepareStatement(
                                                        "select initial_money_amount from player where player_no='"
                                                                + playerId + "'");

                                        ResultSet rs5 = st5.executeQuery();
                                        rs5.next();
                                        if (rs5.getInt("initial_money_amount") >= realestatePrice) {
                                            PreparedStatement st = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "update player set initial_money_amount=initial_money_amount-'"
                                                                    + realestatePrice + "' where player_no='" + playerId
                                                                    + "'");

                                            st.executeUpdate();
                                            PreparedStatement st2 = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "update area set area_type='realestate' where area_no='"
                                                                    + areaNoList.get(inneri) + "'");

                                            st2.executeUpdate();

                                            PreparedStatement st3 = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "insert into realestate (realestate_id, realestate_commission, realestate_fixed_income, realestate_level, realestate_capacity, admin_operating_fee, player_operating_fee, realestate_price, realestate_rental_price, realestate_level_start_date) values (?,?,?,?,?,?,?,?,?,?)");

                                            st3.setInt(1, areaNoList.get(inneri));
                                            st3.setInt(2, 5);
                                            st3.setInt(3, 350);
                                            st3.setInt(4, 1);
                                            st3.setInt(5, 3);
                                            st3.setInt(6, 250);
                                            st3.setInt(7, 150);
                                            st3.setInt(8, 1200);
                                            st3.setInt(9, 450);
                                            st3.setDate(10, java.sql.Date.valueOf(java.time.LocalDate.now()));
                                            st3.executeUpdate();

                                            PreparedStatement st4 = (PreparedStatement) App.connect
                                                    .prepareStatement(
                                                            "delete from land where land_no='" + areaNoList.get(inneri)
                                                                    + "'");

                                            st4.executeUpdate();

                                            buttonList.get(inneri).setBackground(Color.RED);
                                            CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS).execute(() -> {
                                                frameArea.dispatchEvent(
                                                        new WindowEvent(frameArea, WindowEvent.WINDOW_CLOSING));
                                            });
                                            retrieveGridArea();
                                            retrievePlayerInfo();
                                            retrieveWorkInfo();
                                        } else {
                                            JFrame warningFrame = new JFrame("Warning");
                                            JPanel warningPanel = new JPanel();
                                            warningPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 40));
                                            JLabel warningText = new JLabel("You don't have enough money!");
                                            warningPanel.add(warningText);

                                            warningFrame.setContentPane(warningPanel);
                                            warningFrame.pack();
                                            warningFrame.setSize(300, 150);
                                            // frame.setLayout(null);
                                            warningFrame.setVisible(true);
                                        }

                                    } catch (SQLException sqlException) {
                                        sqlException.printStackTrace();
                                    }
                                }
                            });

                        }
                    });
                }

                panel.add(gridPanel);

                frame.setContentPane(panel);
                frame.pack();
                frame.setSize(1370, 740);
                // frame.setLayout(null);
                frame.setVisible(true);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void retrievePlayerInfo() {
        try {
            Statement st = App.connect.createStatement();
            String sql = ("select player_name, player_surname, initial_meal_amount, initial_property_amount, initial_money_amount from player where player_no='"
                    + playerId + "'");
            ResultSet rs = st.executeQuery(sql);
            rs.next();

            playerInfoPanel.removeAll();
            JPanel infoLeftPanel = new JPanel();
            infoLeftPanel.setLayout(new BoxLayout(infoLeftPanel, BoxLayout.Y_AXIS));
            JLabel name = new JLabel("Name : ");
            JLabel surname = new JLabel("Surname : ");
            JLabel meal = new JLabel("Meal : ");
            JLabel property = new JLabel("Property : ");
            JLabel money = new JLabel("Money : ");
            infoLeftPanel.add(name);
            infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
            infoLeftPanel.add(surname);
            infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
            infoLeftPanel.add(meal);
            infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
            infoLeftPanel.add(property);
            infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
            infoLeftPanel.add(money);
            playerInfoPanel.add(infoLeftPanel);

            JPanel infoRightPanel = new JPanel();
            infoRightPanel.setLayout(new BoxLayout(infoRightPanel, BoxLayout.Y_AXIS));
            JLabel nameValue = new JLabel(rs.getString("player_name"));
            JLabel surnameValue = new JLabel(rs.getString("player_surname"));
            JLabel mealValue = new JLabel(rs.getString("initial_meal_amount"));
            JLabel propertyValue = new JLabel(rs.getString("initial_property_amount"));
            JLabel moneyValue = new JLabel(rs.getString("initial_money_amount"));
            infoRightPanel.add(nameValue);
            infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
            infoRightPanel.add(surnameValue);
            infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
            infoRightPanel.add(mealValue);
            infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
            infoRightPanel.add(propertyValue);
            infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
            infoRightPanel.add(moneyValue);
            playerInfoPanel.add(infoRightPanel);
            panel.add(playerInfoPanel);

            frame.setContentPane(panel);
            frame.pack();
            frame.setSize(1370, 740);
            // frame.setLayout(null);
            frame.setVisible(true);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void retrieveWorkInfo() {
        try {
            Statement st = App.connect.createStatement();
            String sql = ("select employee_id, employee_start_date, employee_working_hours, business_no from employee where employee_id='"
                    + playerId + "'");
            ResultSet rs = st.executeQuery(sql);
            rs.next();

            workerInfoPanel.removeAll();
            JPanel infoLeftPanel = new JPanel();
            infoLeftPanel.setLayout(new BoxLayout(infoLeftPanel, BoxLayout.Y_AXIS));
            JLabel employeeBusinessNo = new JLabel("Business No : ");
            JLabel employeeWorkingHours = new JLabel("Employee Working Hours : ");
            JLabel employeeStartDate = new JLabel("Employee Start Date : ");
            infoLeftPanel.add(employeeBusinessNo);
            infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
            infoLeftPanel.add(employeeWorkingHours);
            infoLeftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
            infoLeftPanel.add(employeeStartDate);
            workerInfoPanel.add(infoLeftPanel);

            JPanel infoRightPanel = new JPanel();
            infoRightPanel.setLayout(new BoxLayout(infoRightPanel, BoxLayout.Y_AXIS));
            JLabel employeeBusinessNoValue = new JLabel(rs.getString("business_no"));
            JLabel employeeWorkingHoursValue = new JLabel(rs.getString("employee_working_hours"));
            JLabel employeeStartDateValue = new JLabel(rs.getString("employee_start_date"));
            infoRightPanel.add(employeeBusinessNoValue);
            infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
            infoRightPanel.add(employeeWorkingHoursValue);
            infoRightPanel.add(Box.createRigidArea(new Dimension(10, 20)));
            infoRightPanel.add(employeeStartDateValue);
            workerInfoPanel.add(infoRightPanel);
            panel.add(workerInfoPanel);

            frame.setContentPane(panel);
            frame.pack();
            frame.setSize(1370, 740);
            // frame.setLayout(null);
            frame.setVisible(true);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}

public class App extends JFrame {
    public static Connection connect;

    public static void main(String[] args) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/metalanddb?characterEncoding=latin1&useConfigs=maxPerformance", "root",
                    "12345");
            // connect.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        AdminPanel.reducePlayerElement();

        Entrance entrance = new Entrance();
        entrance.createInterface();
    }

}
