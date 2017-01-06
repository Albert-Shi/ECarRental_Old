import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLEncoder;

/**
 * Created by 史书恒 on 2016/11/8.
 */

public class RegisterCar extends JFrame {

    JFrame father;
    int style;
    public String account;

    JTextField number_editor, able_editor, style_editor, brand_editor, name_editor, people_editor, cargo_editor, price_editor;

    public RegisterCar(JFrame father, int style) {
        this.father = father;
        this.style = style;
        account = null;
        init();
    };

    void init() {   //初始化，创建窗口
        JButton positiveButton, negativeButton;
        Box baseBox, labelBox, editorBox, groupBox, buttonBox;
        number_editor = new JTextField();
        number_editor.setColumns(15);
        able_editor = new JTextField();
        able_editor.setColumns(15);
        style_editor = new JTextField();
        style_editor.setColumns(15);
        brand_editor = new JTextField();
        brand_editor.setColumns(15);
        name_editor = new JTextField();
        name_editor.setColumns(15);
        people_editor = new JTextField();
        people_editor.setColumns(15);
        cargo_editor = new JTextField();
        cargo_editor.setColumns(15);
        price_editor = new JTextField();
        price_editor.setColumns(15);
        positiveButton = new JButton("确定");
        negativeButton = new JButton("关闭");
        baseBox = Box.createVerticalBox();
        labelBox = Box.createVerticalBox();
        editorBox = Box.createVerticalBox();
        groupBox = Box.createHorizontalBox();
        buttonBox = Box.createHorizontalBox();
        labelBox.add(new JLabel("车牌:"));
        labelBox.add(Box.createVerticalStrut(14));
        labelBox.add(new JLabel("类型:"));
        labelBox.add(Box.createVerticalStrut(14));
        labelBox.add(new JLabel("品牌:"));
        labelBox.add(Box.createVerticalStrut(14));
        labelBox.add(new JLabel("车名:"));
        labelBox.add(Box.createVerticalStrut(14));
        labelBox.add(new JLabel("载人:"));
        labelBox.add(Box.createVerticalStrut(14));
        labelBox.add(new JLabel("载货:"));
        labelBox.add(Box.createVerticalStrut(14));
        labelBox.add(new JLabel("价格:"));
        editorBox.add(number_editor);
        editorBox.add(Box.createVerticalStrut(10));
        editorBox.add(style_editor);
        editorBox.add(Box.createVerticalStrut(10));
        editorBox.add(brand_editor);
        editorBox.add(Box.createVerticalStrut(10));
        editorBox.add(name_editor);
        editorBox.add(Box.createVerticalStrut(10));
        editorBox.add(people_editor);
        editorBox.add(Box.createVerticalStrut(10));
        editorBox.add(cargo_editor);
        editorBox.add(Box.createVerticalStrut(10));
        editorBox.add(price_editor);
        groupBox.add(labelBox);
        groupBox.add(Box.createHorizontalStrut(10));
        groupBox.add(editorBox);
        buttonBox.add(negativeButton);
        buttonBox.add(Box.createHorizontalStrut(100));
        buttonBox.add(positiveButton);
        baseBox.add(Box.createVerticalStrut(50));
        baseBox.add(groupBox);
        baseBox.add(Box.createVerticalStrut(20));
        baseBox.add(buttonBox);
//        baseBox.setSize(350, 300);
        add(baseBox);

//        positiveButton.addActionListener(new LogonListen(account_text, password_text));
//        negativeButton.addActionListener();

        //以下是根据传入的style来判断窗口的样式
        if (style == 0) {//注册新车辆模式
            setTitle("注册新车辆");
            number_editor.setEditable(true);
            able_editor.setEditable(true);
            style_editor.setEditable(true);
            brand_editor.setEditable(true);
            name_editor.setEditable(true);
            people_editor.setEditable(true);
            cargo_editor.setEditable(true);
            price_editor.setEditable(true);
            negativeButton.setVisible(true);
            positiveButton.setVisible(true);
        }
        else if (style == 1) {//更新车辆信息模式
            setTitle("更改车辆信息");
            number_editor.setEditable(false);
            able_editor.setEditable(true);
            style_editor.setEditable(true);
            brand_editor.setEditable(true);
            name_editor.setEditable(true);
            people_editor.setEditable(true);
            cargo_editor.setEditable(true);
            price_editor.setEditable(true);
            negativeButton.setVisible(true);
            positiveButton.setVisible(true);
        }else if (style == 2) {//显示车辆信息模式
            setTitle("详细信息");
            number_editor.setEditable(false);
            able_editor.setEditable(false);
            style_editor.setEditable(false);
            brand_editor.setEditable(false);
            name_editor.setEditable(false);
            people_editor.setEditable(false);
            cargo_editor.setEditable(false);
            price_editor.setEditable(false);
            negativeButton.setVisible(false);
            positiveButton.setVisible(false);
        }
        setSize(400, 400);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        negativeButton.addActionListener(new ActionListener() {//设置取消按钮监听器
            @Override
            public void actionPerformed(ActionEvent e) {
                if (father != null)
                    father.setVisible(true);//父窗口显示
                dispose();
            }
        });
        positiveButton.addActionListener(new ActionListener() {//设置确定按钮监听器
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog();
                dialog.setSize(160, 80);
                dialog.setTitle("提示");
                dialog.setLocationRelativeTo(null);
                if (number_editor.getText().equals("") || style_editor.getText().equals("") || name_editor.getText().equals("") || brand_editor.getText().equals("") || people_editor.getText().equals("") || cargo_editor.getText().equals("") || price_editor.getText().equals("")) {
                    System.out.println("请填入完整信息！");
                    dialog.add(new JLabel("请填入完整信息！"));
                    dialog.setVisible(true);
                } else {
                    try{
                        String info = "";
                        if (style == 0) {//创建车辆查询
                            String webpath = Server.HOST + "/php?admin=true&insertdata=3&n_number="+new String(URLEncoder.encode(number_editor.getText(),"utf-8").getBytes())+"&n_style="+new String(URLEncoder.encode(style_editor.getText(),"utf-8").getBytes())+"&brand="+new String(URLEncoder.encode(brand_editor.getText(),"utf-8").getBytes())+"&carname="+new String(URLEncoder.encode(name_editor.getText(),"utf-8").getBytes())+ "&n_people=" + people_editor.getText() + "&n_cargo=" + cargo_editor.getText() + "&n_price=" + price_editor.getText();
                            info = Server.getString(webpath);
                        } else if (style == 1) {//更新车辆信息查询
                            String webpath = Server.HOST + "/php?update=4&admin=true&number="+number_editor.getText()+"&able="+able_editor.getText()+"&cname="+new String(URLEncoder.encode(name_editor.getText(),"utf-8").getBytes())+"&people="+people_editor.getText()+"&cargo="+cargo_editor.getText()+"&price="+price_editor.getText()+"&brand="+new String(URLEncoder.encode(brand_editor.getText(),"utf-8").getBytes())+"&style="+new String(URLEncoder.encode(style_editor.getText(),"utf-8").getBytes());
                            info = Server.getString(webpath);
                        }
                        if (info.equals("successful")) {
                            System.out.println("注册成功!");
                            dialog.add(new JLabel("操作成功!"));
                        }else {
                            System.out.println("注册失败!");
                            dialog.add(new JLabel("操作失败!"));
                        }
                        dialog.setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        dialog.add(new JLabel("失败!"));
                        dialog.setVisible(true);
                    }
                    dialog.setAlwaysOnTop(true);
                }
            }
        });
        setLocationRelativeTo(null);
        setVisible(true);
    }
}