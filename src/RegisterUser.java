import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLEncoder;

/**
 * Created by 史书恒 on 2016/11/1.
 */
public class RegisterUser extends JFrame {

    JFrame father;
    int select;
    JTextField account_editor, password_editor, name_editor;
    public String account;
    public RegisterUser(JFrame father, int select) {
        this.father = father;
        this.select = select;
        account = null;
        init();
    };

    void init() {//初始化，创建窗口
        JButton positiveButton, negativeButton;
        Box baseBox, labelBox, editorBox, groupBox, buttonBox;

        account_editor = new JTextField();
        account_editor.setColumns(15);
        password_editor = new JTextField();
        password_editor.setColumns(15);
        name_editor = new JTextField();
        name_editor.setColumns(15);
        positiveButton = new JButton("确定");
        negativeButton = new JButton("关闭");
        baseBox = Box.createVerticalBox();
        labelBox = Box.createVerticalBox();
        editorBox = Box.createVerticalBox();
        groupBox = Box.createHorizontalBox();
        buttonBox = Box.createHorizontalBox();
        labelBox.add(new JLabel("账户:"));
        labelBox.add(Box.createVerticalStrut(10));
        labelBox.add(new JLabel("密码:"));
        labelBox.add(Box.createVerticalStrut(10));
        labelBox.add(new JLabel("姓名:"));
        editorBox.add(account_editor);
        editorBox.add(Box.createVerticalStrut(10));
        editorBox.add(password_editor);
        editorBox.add(Box.createVerticalStrut(10));
        editorBox.add(name_editor);
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
        baseBox.setSize(350, 200);
        add(baseBox);

//        positiveButton.addActionListener(new LogonListen(account_text, password_text));
//        negativeButton.addActionListener();

        if (select == 0) {
            account_editor.setEditable(true);
            setTitle("注册普通用户");
        }
        else if (select == 1) {
            account_editor.setEditable(true);
            setTitle("注册管理员用户");
        }
        else if (select == 2) {
            account_editor.setEditable(false);
            setTitle("更新用户信息");
        }
        setSize(400, 250);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        negativeButton.addActionListener(new ActionListener() {//取消按钮监听器
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                if (father != null)
                    father.setVisible(true);
            }
        });
        positiveButton.addActionListener(new ActionListener() {//确定按钮监听器
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog();
                dialog.setTitle("提示");
                dialog.setSize(200, 100);
                dialog.setLocationRelativeTo(null);
                if (account_editor.getText().equals("") || password_editor.getText().equals("") || name_editor.getText().equals("")) {
                    System.out.println("请填入完整信息！");
                    dialog.add(new JLabel("请填入完整信息！"));
                    dialog.setVisible(true);
                } else {
                    try{
                        String info = "";
//                        int r = 0;
                        if (select == 0) {//创建普通用户查询
                            info = Server.getString(Server.HOST + "/php?insertdata=2&n_account="+account_editor.getText()+"&n_password="+password_editor.getText()+"&n_username="+new String(URLEncoder.encode(name_editor.getText(),"utf-8").getBytes()));
                        } else if (select == 1) {//创建管理员查询
                            info = Server.getString(Server.HOST + "/php?insertdata=1&admin=true&n_account="+account_editor.getText()+"&n_password="+password_editor.getText()+"&n_username="+new String(URLEncoder.encode(name_editor.getText(),"utf-8").getBytes()));
                        } else if (select == 2) {//更新用户信息查询
                            info = Server.getString(Server.HOST + "/php?admin=true&update=5&account=" + account_editor.getText() +"&field=Password&newdata=" + password_editor.getText());
                            if (info.equals("successful")) {
                                System.out.println("密码更改成功");
                            }
                            info = Server.getString(Server.HOST + "/php?admin=true&update=5&account=" + account_editor.getText() +"&field=Name&newdata=" + new String(URLEncoder.encode(name_editor.getText(),"utf-8").getBytes()));
                            if (info.equals("successful")) {
                                System.out.println("用户名更改成功");
                            }
                        }
                        if (info.equals("successful")) {
                            System.out.println("注册成功!");
                            account = account_editor.getText();
                            dialog.add(new JLabel("操作成功!"));
                        }else {
                            System.out.println("注册失败!");
                            dialog.add(new JLabel("操作失败!"));
                        }
                        dialog.setVisible(true);
                        dialog.setAlwaysOnTop(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        if (father != null)
                            father.setVisible(true);
                        dispose();
                    }
                }
            }
        });
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
