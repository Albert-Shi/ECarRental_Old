import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.Box;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 史书恒 on 2016/10/31.
 */
public class Login extends JFrame{

    JTextField account_text;
    JPasswordField password_text;
    JLabel account_label, password_label;
    JButton positiveButton, registerButton;
    Box baseBox, labelBox, editorBox, groupBox, warnningBox, buttonBox;

    public Login() {
        init();
    }

    void init() {       //创建登录窗口
        account_text = new JTextField();
        account_text.setColumns(15);
        password_text = new JPasswordField();
        password_text.setColumns(15);
        account_label = new JLabel("账户:");
        password_label = new JLabel("密码:");
        positiveButton = new JButton("登录");
        registerButton = new JButton("注册");
        baseBox = Box.createVerticalBox();
        labelBox = Box.createVerticalBox();
        editorBox = Box.createVerticalBox();
        groupBox = Box.createHorizontalBox();
        warnningBox = Box.createHorizontalBox();
        buttonBox = Box.createHorizontalBox();

        labelBox.add(account_label);
        labelBox.add(Box.createVerticalStrut(10));
        labelBox.add(password_label);
        labelBox.add(Box.createVerticalStrut(10));
        editorBox.add(account_text);
        editorBox.add(Box.createVerticalStrut(10));
        editorBox.add(password_text);
        editorBox.add(Box.createVerticalStrut(10));
        groupBox.add(labelBox);
        groupBox.add(Box.createHorizontalStrut(10));
        groupBox.add(editorBox);
        warnningBox.add(new JLabel(" "));
        buttonBox.add(registerButton);
        buttonBox.add(Box.createHorizontalStrut(100));
        buttonBox.add(positiveButton);
        baseBox.add(Box.createVerticalStrut(50));
        baseBox.add(groupBox);
        baseBox.add(Box.createHorizontalStrut(10));
        baseBox.add(warnningBox, Box.LEFT_ALIGNMENT);
        baseBox.add(Box.createVerticalStrut(20));
        baseBox.add(buttonBox);
        baseBox.setSize(350, 200);
        add(baseBox);

        password_text.addActionListener(new LogonListen(this, account_text, password_text));
        positiveButton.addActionListener(new LogonListen(this, account_text, password_text));
        registerButton.addActionListener(new RegisterButton(this));

        setTitle("登录");
        setSize(400, 250);
        setLocationRelativeTo(null);    //窗口居中
        setVisible(true);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class LogonListen implements ActionListener {       //登录按钮监听器
    JTextField account;
    JTextField password;
    JFrame father;

    public LogonListen(JFrame father, JTextField account, JTextField password) {
        this.father = father;
        this.account = account;
        this.password = password;
    }

    @Override
    public void actionPerformed(ActionEvent e) {    //通过查询输入的账户，查询服务器数据库中相关账户信息进行比对，判断是否可以登录
        if (account.getText().equals("") || password.getText().equals("")) {
            System.out.println("请输入用户名或密码!");
        } else {
            String json = Server.getString(Server.HOST + "/php/?getinfomode=3&account=" + account.getText()); //查询服务器
            try {   //处理JSON数据
                JSONObject all = new JSONObject(json);
                JSONObject data = all.getJSONObject("data");
                JSONArray users = data.getJSONArray("userinfo");
                for (int i = 0; i < users.length(); i++) {
                    JSONObject user = users.getJSONObject(i);
                    if (account.getText().equals(user.getString("account"))) {
                        String pw = password.getText();
                        if (pw.equals(user.getString("password"))) {
                            System.out.println("登录成功！");
                            String admin = user.getString("admin");
                            if (admin.equals("1")) {
                                System.out.println("管理员账户登录");
                                AdminWindow adminWindow = new AdminWindow(father);
//                                adminWindow.setLocationRelativeTo(null);
                            }
                            else {
                                UserWindow userWindow = new UserWindow(father, user);
                                System.out.println("普通用户登录");
                            }
                        }
                        else {
                            ((Login)father).warnningBox.removeAll();
                            ((Login)father).warnningBox.add(new JLabel("密码输入错误!"), Box.LEFT_ALIGNMENT);
                        }
                    }
                    else {
                        ((Login)father).warnningBox.removeAll();
                        ((Login)father).warnningBox.add(new JLabel("账户输入错误!"), Box.LEFT_ALIGNMENT);
                    }
                    ((Login)father).validate();
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }
}

class RegisterButton implements ActionListener {    //注册按钮监听器
    JFrame father;
    public RegisterButton(JFrame father){
        this.father = father;
    };
    @Override
    public void actionPerformed(ActionEvent e) {
        father.setVisible(false);
        RegisterUser register = new RegisterUser(father, 0);
        register.setLocationRelativeTo(null);       //窗口居中
    }
}