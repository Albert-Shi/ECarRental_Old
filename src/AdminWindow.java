import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.plaf.IconUIResource;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by 史书恒 on 2016/11/1.
 */
public class AdminWindow extends JFrame {
    JFrame father;

    UsersInfo[] usersInfos, hasRentInfos;
    CarsInfo[] carsInfos;
    DoubleClickList uclick, cclick, hclick;

    AdminWindow(JFrame father) {
        this.father = father;
        father.setVisible(false);
        init();
    }
    void init() {   //初始化，创建窗口以及相关组件
        Box baseBox, usersBox, carsBox, rentBox, groupBox, buttonBox;
        JButton deleteButton, addButton, exitButton, refreshButton;
        JList<UsersInfo> usinfo, hasRentUser;
        JList<CarsInfo> carinfo;
        JScrollPane userscroller, carscroller, rentscroller;
        DefaultListModel usersListModel, carsListMode, hasRentListMode;

        baseBox = Box.createVerticalBox();
        usersBox = Box.createVerticalBox();
        carsBox = Box.createVerticalBox();
        rentBox = Box.createVerticalBox();
        groupBox = Box.createHorizontalBox();
        buttonBox = Box.createHorizontalBox();
        refreshButton = new JButton("刷新");
        deleteButton = new JButton("删除");
        addButton = new JButton("添加");
        exitButton = new JButton("退出");
        usersListModel = new DefaultListModel();
        carsListMode = new DefaultListModel();
        hasRentListMode = new DefaultListModel();
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                usersListModel.removeAllElements();
                carsListMode.removeAllElements();
                dealJSON(usersListModel, carsListMode, hasRentListMode);
                uclick = new DoubleClickList(usersInfos);
                cclick = new DoubleClickList(carsInfos);
                */
                new AdminWindow(father);
                dispose();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });

        dealJSON(usersListModel, carsListMode, hasRentListMode);

        usinfo = new JList<>(usersListModel);
        usinfo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carinfo = new JList<>(carsListMode);
        carinfo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        hasRentUser = new JList<>(hasRentListMode);
        hasRentUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userscroller = new JScrollPane(usinfo);
        carscroller = new JScrollPane(carinfo);
        rentscroller = new JScrollPane(hasRentUser);
        uclick = new DoubleClickList(usersInfos);
        usinfo.addMouseListener(uclick);
        cclick = new DoubleClickList(carsInfos);
        carinfo.addMouseListener(cclick);
        hclick = new DoubleClickList(hasRentInfos);
        hasRentUser.addMouseListener(hclick);

        usersBox.add(new JLabel("用户信息"));
        usersBox.add(Box.createVerticalStrut(5));
        usersBox.add(userscroller);
        carsBox.add(new JLabel("车辆信息"));
        carsBox.add(Box.createVerticalStrut(5));
        carsBox.add(carscroller);
        rentBox.add(new JLabel("租车信息"));
        rentBox.add(Box.createVerticalStrut(5));
        rentBox.add(rentscroller);
        groupBox.add(Box.createHorizontalStrut(10));
        groupBox.add(usersBox);
        groupBox.add(Box.createHorizontalStrut(5));
        groupBox.add(carsBox);
        groupBox.add(Box.createHorizontalStrut(5));
        groupBox.add(rentBox);
        groupBox.add(Box.createHorizontalStrut(10));
        groupBox.setPreferredSize(new Dimension(700, 500));
//        buttonBox.add(Box.createVerticalStrut(5));
//        buttonBox.add(Box.createHorizontalStrut(10));
//        buttonBox.add(deleteButton);
//        buttonBox.add(Box.createHorizontalStrut(10));
//        buttonBox.add(addButton);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(refreshButton);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(exitButton);
        buttonBox.add(Box.createHorizontalStrut(20));
        baseBox.add(Box.createVerticalStrut(20));
        baseBox.add(groupBox);
        baseBox.add(Box.createVerticalStrut(10));
        baseBox.add(buttonBox);
        baseBox.add(Box.createVerticalStrut(10));
        baseBox.setPreferredSize(new Dimension(700, 500));
        add(baseBox);

        setSize(800, 450);
        setTitle("管理员窗口");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void dealJSON(DefaultListModel usersListModel, DefaultListModel carsListMode, DefaultListModel hasRentListMode) {   //处理从服务器接受的JSON数据
        String jsonuser = Server.getString(Server.HOST + "/php?admin=true&getinfomode=5");
        String jsoncar = Server.getString(Server.HOST + "/php?admin=true&getinfomode=6");
        try {
            JSONObject root = new JSONObject(jsonuser);
            JSONObject data = root.getJSONObject("data");
            JSONArray users = data.getJSONArray("userinfo");
            usersInfos = null;
            usersInfos = new UsersInfo[users.length()];
            hasRentInfos = null;
            hasRentInfos = new UsersInfo[users.length()];
            hasRentListMode.removeAllElements();
            int hi = 0;
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                usersInfos[i] = new UsersInfo();
                usersInfos[i].account = user.getString("account");
                usersInfos[i].admin = user.getString("admin");
                usersInfos[i].day = user.getString("day");
                usersInfos[i].name = user.getString("name");
                usersInfos[i].number = user.getString("number");
                usersInfos[i].password = user.getString("password");
                usersInfos[i].startdate = user.getString("startdate");
                usersInfos[i].total = user.getString("total");
                usersInfos[i].cash = user.getString("cash");
                usersListModel.addElement(usersInfos[i].account);
                if (!usersInfos[i].number.equals("0") && !usersInfos[i].total.equals("0")) {
                    hasRentInfos[hi] = usersInfos[i];
                    hasRentListMode.addElement(usersInfos[i].account + "    租用了    " + usersInfos[i].number);
                    hi++;
                }
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        try {
            JSONObject root = new JSONObject(jsoncar);
            JSONObject data = root.getJSONObject("data");
            JSONArray cars = data.getJSONArray("carinfo");
            carsInfos = null;
            carsInfos = new CarsInfo[cars.length()];
            for (int i = 0; i < cars.length(); i++) {
                JSONObject car = cars.getJSONObject(i);
                carsInfos[i] = new CarsInfo();
                carsInfos[i].able = car.getString("able");
                carsInfos[i].brand = car.getString("brand");
                carsInfos[i].cargo = car.getString("cargo");
                carsInfos[i].people = car.getString("people");
                carsInfos[i].name = car.getString("name");
                if (car.getString("number").equals("0"))
                    carsInfos[i].number = "此项为系统预留项 请勿更改";
                else
                    carsInfos[i].number = car.getString("number");
                carsInfos[i].price = car.getString("price");
                carsInfos[i].style = car.getString("style");
                carsListMode.addElement(carsInfos[i].number);
            }
        }catch (Exception eee) {
            eee.printStackTrace();
        }
    }
}

class DoubleClickList extends MouseAdapter {        //鼠标时间监听器
    UsersInfo[] usersInfos;                         //从服务器已获取的用户信息
    CarsInfo[] carsInfos;                           //从服务器已获取的车辆信息
    int select;                                     //根据select进行判断进入对应的动作

    DoubleClickList(UsersInfo[] usersInfos) {       //当传入UserInfo[]时select=1
        this.usersInfos = usersInfos;
        this.select = 1;
    }

    DoubleClickList(CarsInfo[] carsInfos) {         //当传入CarInfo[]时select=2
        this.carsInfos = carsInfos;
        this.select = 2;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JList list = (JList)e.getSource();
        JPopupMenu popupMenu = new JPopupMenu();
        if (select == 1) {          //添加用户
            popupMenu.removeAll();  //右击菜单选项清空
            if (e.getClickCount() == 1 && list.getSelectedIndex() >= 0) {   //单击用户列表项目时创建右击菜单（创建不显示）
                JMenuItem addAdmin = new JMenuItem("添加新的管理员账户");
                JMenuItem addUser = new JMenuItem("添加新的普通账户");
                JMenuItem delete = new JMenuItem("删除当前选定账户");
                AddItemListener addAdminLisetener = new AddItemListener(1, list);
                addAdmin.addActionListener(addAdminLisetener);
                AddItemListener addUserListener = new AddItemListener(2, list);
                addUser.addActionListener(addUserListener);
                DeleteItemListener deleteItemListener = new DeleteItemListener(select, usersInfos[list.getSelectedIndex()].account, list, usersInfos, carsInfos);
                delete.addActionListener(deleteItemListener);
                popupMenu.add(addUser);
                popupMenu.add(addAdmin);
                popupMenu.add(delete);
            }
            if (e.getClickCount() == 2) {           //双击用户列表项目时，创建详细信息窗口
                int index = list.getSelectedIndex();
                String admin = "";
                String number = "";
                String total = "";
                String start = "";
                String day = "";
                String buttonText = "";
                if (usersInfos[index].admin.equals("1"))
                    admin = "管理员用户";
                else
                    admin = "非管理员用户";
                if (usersInfos[index].total.equals("0")) {
                    number = "无";
                    total = "无";
                    start = "无";
                    day = "无";
                    buttonText = "租车";
                }
                else {
                    number = usersInfos[index].number;
                    total = usersInfos[index].total;
                    start = usersInfos[index].startdate;
                    day = usersInfos[index].day;
                    buttonText = "还车";
                }
                JDialog dialog = new JDialog();
                dialog.setTitle("详细: " + list.getSelectedValue());
                dialog.setSize(300, 180);
                JLabel acc = new JLabel("账户：" + usersInfos[index].account);
                JLabel name = new JLabel("姓名：" + usersInfos[index].name);
                JLabel ad = new JLabel("账户类型：" + admin);
                JLabel no = new JLabel("已租车辆：" + number);
                JLabel startdate = new JLabel("租车日期：" + start);
                JLabel sumday = new JLabel("租车总时长/天：" + total);
                JLabel d = new JLabel("已租车时间/天：" + day);
                JButton positive = new JButton(buttonText);
                Box layout = Box.createVerticalBox();
                layout.add(acc);
                layout.add(name);
                layout.add(ad);
                layout.add(no);
                layout.add(startdate);
                layout.add(sumday);
                layout.add(d);
//                layout.add(positive, Box.CENTER_ALIGNMENT);
                dialog.add(layout);
                dialog.setLocationRelativeTo(null);
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
            }
        } else if (select == 2) {   //选择车辆列表
            popupMenu.removeAll();
            if (e.getClickCount() == 1 && list.getSelectedIndex() >= 0) {   //单击车辆列表项目时创建右击菜单，不显示
                JMenuItem addCarIten = new JMenuItem("添加新的车辆");
                JMenuItem delete = new JMenuItem("删除当前选定车辆");
                addCarIten.addActionListener(new AddItemListener(3, list));
                delete.addActionListener(new DeleteItemListener(select, carsInfos[list.getSelectedIndex()].number, list, usersInfos, carsInfos));
                popupMenu.add(addCarIten);
                popupMenu.add(delete);
            }
            if (e.getClickCount() == 2 && list.getSelectedIndex() >= 0) {   //双击车辆列表项目时显示车辆信息更新窗口
                int index = list.getSelectedIndex();
                RegisterCar showCar = new RegisterCar(null, 1);//传入参数1即选择更新模式
                showCar.number_editor.setText(carsInfos[index].number);
                showCar.style_editor.setText(carsInfos[index].style);
                showCar.able_editor.setText(carsInfos[index].able);
                showCar.brand_editor.setText(carsInfos[index].brand);
                showCar.name_editor.setText(carsInfos[index].name);
                showCar.cargo_editor.setText(carsInfos[index].cargo);
                showCar.people_editor.setText(carsInfos[index].people);
                showCar.price_editor.setText(carsInfos[index].price);
//                showCar.setAlwaysOnTop(true);
            }
        }
        if (e.getButton() == 3) {//判断是否是右击，显示右击菜单
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}

class DeleteItemListener implements ActionListener {        //右击菜单中添加选项（创建用户、管理员、车辆信息）的监听器
    String account;                                         //要删除的用户账户或车辆车牌
    JList list;                                             //传入点击的列表
    int select;                                             //根据传入的select进行判断进入对应的动作
    UsersInfo[] usersInfos;
    CarsInfo[] carsInfos;

    DeleteItemListener(int userOrCars, String str, JList list, UsersInfo[] usersInfos, CarsInfo[] carsInfos){
        this.account = str;
        this.list = list;
        this.select = userOrCars;
        this.usersInfos = usersInfos;
        this.carsInfos = carsInfos;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String result = "";
        if (select == 1) {              //删除用户
            Server.getString(Server.HOST + "/php/?deal=2&account=" + account + "&number=" + usersInfos[select+1].number);
            result = Server.getString(Server.HOST + "/php/?admin=true&deletedata=1&n_account=" + account);
        } else if (select == 2) {       //删除车辆
            result = Server.getString(Server.HOST + "/php/?admin=true&deletedata=2&n_number=" + account);
        }
        if (result.equals("success")) {
            ((DefaultListModel)list.getModel()).removeElementAt(list.getSelectedIndex());
            System.out.println("删除信息成功!");
        }
        else
            System.out.println("删除信息失败!");
    }
}

class AddItemListener implements ActionListener {   //右击菜单中添加选项（创建用户、管理员、车辆信息）的触发器
    int select;                                     //根据传入的select进行判断进入对应的动作
    JList list;                                     //传入点击的列表
    AddItemListener (int select, JList list) {
        this.select = select;
        this.list = list;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (select == 1) {
            RegisterUser registerAdmin = new RegisterUser(null, 1);//创建管理员账户
            registerAdmin.setLocationRelativeTo(null);
//            registerAdmin.setAlwaysOnTop(true);
        }
        if (select == 2) {
            RegisterUser register = new RegisterUser(null, 0);//创建普通用户
            register.setLocationRelativeTo(null);
//            register.setAlwaysOnTop(true);
        }
        if (select == 3) {
            RegisterCar registerCar = new RegisterCar(null, 0);//添加车辆对话框
            registerCar.setLocationRelativeTo(null);
//            registerCar.setAlwaysOnTop(true);
        }
    }
}