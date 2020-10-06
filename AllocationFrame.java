import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class AllocationFrame extends JFrame {

    private TreeSet<Partition> partitionTreeSet = new TreeSet<>();
    private List<PCB> reserveList = new ArrayList<>();
    private Queue<PCB> readyQueue = new PriorityQueue<>();

    //添加进程组件
    private JTextField[] addField = new JTextField[12];
    private List<JComboBox<String>> addComboBox = new ArrayList<>();
    private JButton[] addButton = new JButton[4];

    //后备队列
    private Vector<String> reserveNames = new Vector<>();
    private Vector<String> reserveTimes = new Vector<>();
    private Vector<String> reserveRanks = new Vector<>();
    private JList<String> reserveNameJList = new JList<>();
    private JList<String> reserveTimeJList = new JList<>();
    private JList<String> reserveRankJList = new JList<>();

    //就绪队列
    private Vector<String> readyNames = new Vector<>();
    private Vector<String> readyTimes = new Vector<>();
    private Vector<String> readyRanks = new Vector<>();
    private JList<String> readyNameJList = new JList<>();
    private JList<String> readyRankJList = new JList<>();
    private JList<String> readyTimeJList = new JList<>();

    //挂起队列
    private Vector<String> handUpNames = new Vector<>();
    private Vector<String> handUpTimes = new Vector<>();
    private Vector<String> handUpRanks = new Vector<>();
    private JList<String> handUpNameJList = new JList<>();
    private JList<String> handUpRankJList = new JList<>();
    private JList<String> handUpTimeJList = new JList<>();
    private Vector<PCB> handUpPCBS = new Vector<>();

    //运行进程
    private JComboBox<String> channelNum = new JComboBox<>();//管道数
    private JTextField runNameField = new JTextField();
    private JTextField runRankField = new JTextField();
    private JTextField runTimeField = new JTextField();

    private JList<String> endNameJList = new JList<>();//结束队列
    private JList<String> endSizeJList = new JList<>();
    private Vector<String> endNames = new Vector<>();
    private Vector<String> endSizes = new Vector<>();
    private JTable jTable = new JTable();//内存分区表

    public AllocationFrame() {
        initComponents();
        partitionTreeSet.add(new Partition(0, 26));
        this.setTitle("CPU Scheduling");
        this.setLocationRelativeTo(null);  //设置窗口居中显示
        addField[0].requestFocus();

        //设置默认先后优先级
        addComboBox.get(0).setSelectedIndex(4);
        addComboBox.get(1).setSelectedIndex(1);
        addComboBox.get(2).setSelectedIndex(2);
        addComboBox.get(3).setSelectedIndex(5);

        Timer timer = new Timer();
        //定时器执行任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, 1000);
    }

    private void initComponents() {

        for(int i=0; i<4; i++){
            addField[i*3] = new JTextField();
            addField[i*3+1] = new JTextField();
            addField[i*3+2] = new JTextField();
            addButton[i] = new JButton();
            addComboBox.add(new JComboBox<>());
        }
        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        JPanel jPanel32 = new JPanel();
        JLabel jLabel2 = new JLabel();

        JPanel jPanel18 = new JPanel();
        JLabel jLabel5 = new JLabel();

        JPanel jPanel23 = new JPanel();
        JLabel jLabel1 = new JLabel();
        JPanel jPanel21 = new JPanel();
        JLabel jLabel6 = new JLabel();

        JPanel jPanel30 = new JPanel();
        JButton jButton2 = new JButton();

        JPanel jPanel6 = new JPanel();
        JPanel jPanel12 = new JPanel();
        JLabel jLabel30 = new JLabel();
        JScrollPane jScrollPane17 = new JScrollPane();
        JPanel jPanel14 = new JPanel();
        JScrollPane jScrollPane18 = new JScrollPane();
        JLabel jLabel28 = new JLabel();
        JPanel jPanel13 = new JPanel();
        JLabel jLabel26 = new JLabel();
        JScrollPane jScrollPane20 = new JScrollPane();
        JPanel jPanel25 = new JPanel();
        JButton jButton9 = new JButton();
        JButton jButton10 = new JButton();
        JLabel jLabel3 = new JLabel();
        JPanel jPanel26 = new JPanel();
        JPanel jPanel9 = new JPanel();
        JLabel jLabel12 = new JLabel();
        JPanel jPanel22 = new JPanel();
        JLabel jLabel15 = new JLabel();
        JPanel jPanel35 = new JPanel();
        JLabel jLabel17 = new JLabel();
        JPanel jPanel8 = new JPanel();
        JLabel jLabel13 = new JLabel();
        JPanel jPanel5 = new JPanel();
        JPanel jPanel27 = new JPanel();
        JScrollPane jScrollPane1 = new JScrollPane();
        JLabel jLabel7 = new JLabel();
        JPanel jPanel31 = new JPanel();
        JLabel jLabel11 = new JLabel();
        JScrollPane jScrollPane5 = new JScrollPane();
        JPanel jPanel19 = new JPanel();
        JPanel jPanel33 = new JPanel();
        JButton jButton3 = new JButton();
        JPanel jPanel34 = new JPanel();
        JScrollPane jScrollPane8 = new JScrollPane();
        JLabel jLabel16 = new JLabel();
        JPanel jPanel17 = new JPanel();
        JLabel jLabel18 = new JLabel();
        JScrollPane jScrollPane7 = new JScrollPane();
        JPanel jPanel24 = new JPanel();
        JScrollPane jScrollPane10 = new JScrollPane();
        JLabel jLabel14 = new JLabel();

        JPanel jPanel7 = new JPanel();
        JPanel jPanel10 = new JPanel();
        JLabel jLabel20 = new JLabel();
        JScrollPane jScrollPane15 = new JScrollPane();
        JPanel jPanel11 = new JPanel();
        JScrollPane jScrollPane13 = new JScrollPane();
        JLabel jLabel22 = new JLabel();
        JPanel jPanel15 = new JPanel();
        JScrollPane jScrollPane12 = new JScrollPane();
        JLabel jLabel24 = new JLabel();
        JButton jButton5 = new JButton();
        JButton jButton4 = new JButton();
        JScrollPane jScrollPane2 = new JScrollPane();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Font font14 = new Font("宋体", Font.PLAIN, 14);
        Font font18 = new Font("宋体", Font.PLAIN, 18);

        jPanel2.setBorder(BorderFactory.createTitledBorder(null, "添加进程", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, font18));

        jLabel2.setFont(font14);
        jLabel2.setText("进程名");

        addField[6].setFont(font14);
        addField[6].setText("r");

        addField[3].setFont(font14);
        addField[3].setText("d");

        addField[9].setFont(font14);
        addField[9].setText("y");

        addField[0].setFont(font14);
        addField[0].setText("a");

        GroupLayout jPanel32Layout = new GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
                jPanel32Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel32Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel32Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(addField[3], GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                                        .addComponent(addField[6])
                                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addField[9])
                                        .addComponent(addField[0]))
                                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
                jPanel32Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel32Layout.createSequentialGroup()
                                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(addField[0], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addField[3], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addField[6], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addField[9], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jLabel5.setFont(font14);
        jLabel5.setText("运行时间");

        addField[7].setFont(font14);
        addField[7].setText("6");

        addField[4].setFont(font14);
        addField[4].setText("3");

        addField[1].setFont(font14);
        addField[1].setText("5");

        addField[10].setFont(font14);
        addField[10].setText("8");

        GroupLayout jPanel18Layout = new GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
                jPanel18Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel18Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(addField[4], GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addField[7], GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)
                                        .addComponent(addField[1], GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addField[10], GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
                jPanel18Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addField[1], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addField[4], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addField[7], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addField[10], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
        );

        addComboBox.get(1).setFont(font14);
        addComboBox.get(1).setModel(new DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7" }));

        addComboBox.get(0).setFont(font14);
        addComboBox.get(0).setModel(new DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7" }));

        addComboBox.get(2).setFont(font14);
        addComboBox.get(2).setModel(new DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7" }));

        addComboBox.get(3).setFont(font14);
        addComboBox.get(3).setModel(new DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7" }));

        jLabel1.setFont(font14);
        jLabel1.setText("优先权");

        GroupLayout jPanel23Layout = new GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
                jPanel23Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel23Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel23Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel23Layout.createSequentialGroup()
                                                .addGroup(jPanel23Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel23Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(addComboBox.get(2), 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(addComboBox.get(1), 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(addComboBox.get(3), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(addComboBox.get(0), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
                jPanel23Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel23Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addComboBox.get(0), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addComboBox.get(1), GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addComboBox.get(2), GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addComboBox.get(3), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        jLabel6.setFont(font14);
        jLabel6.setText("内存大小");

        addField[8].setFont(font14);
        addField[8].setText("9");

        addField[5].setFont(font14);
        addField[5].setText("4");

        addField[2].setFont(font14);
        addField[2].setText("12");

        addField[11].setFont(font14);
        addField[11].setText("3");

        GroupLayout jPanel21Layout = new GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
                jPanel21Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel21Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(addField[5], GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addField[8], GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6)
                                        .addComponent(addField[2], GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addField[11], GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
                jPanel21Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel21Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addField[2], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addField[5], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addField[8], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addField[11], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
        );

        jButton2.setFont(font14);
        jButton2.setText("clear");
        jButton2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                clear();
            }
        });

        for (int i = 0; i < 4; i++) {
            addButton[i].setFont(font14);
            addButton[i].setText("OK");
            addButton[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    add(evt);
                }
            });
        }

        GroupLayout jPanel30Layout = new GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
                jPanel30Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel30Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel30Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(addButton[0], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(addButton[3], GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(addButton[2], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(addButton[1], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel30Layout.setVerticalGroup(
                jPanel30Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel30Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addButton[0])
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addButton[1])
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addButton[2])
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addButton[3])
                                .addContainerGap())
        );

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel32, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel18, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel21, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel23, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel30, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel32, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel21, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jPanel18, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanel23, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jPanel30, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        jPanel6.setBorder(BorderFactory.createTitledBorder(null, "就绪队列", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, font18));

        jLabel30.setFont(font14);
        jLabel30.setText("进程名");

        jScrollPane17.setViewportView(readyNameJList);

        GroupLayout jPanel12Layout = new GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
                jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane17, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel30, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
                jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel30, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane17)
                                .addContainerGap())
        );

        jScrollPane18.setViewportView(readyRankJList);

        jLabel28.setFont(font14);
        jLabel28.setText("优先级");

        GroupLayout jPanel14Layout = new GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
                jPanel14Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel14Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel14Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel28, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane18, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
        );
        jPanel14Layout.setVerticalGroup(
                jPanel14Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane18, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        jLabel26.setFont(font14);
        jLabel26.setText("运行时间");

        jScrollPane20.setViewportView(readyTimeJList);

        GroupLayout jPanel13Layout = new GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
                jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel26)
                                        .addComponent(jScrollPane20, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
                jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane20, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton9.setFont(font14);
        jButton9.setText("Run");
        jButton9.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                step();
            }
        });

        jButton10.setFont(font14);
        jButton10.setText("挂起");
        jButton10.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                handUp();
            }
        });

        GroupLayout jPanel25Layout = new GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
                jPanel25Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel25Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel25Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel25Layout.createSequentialGroup()
                                                .addComponent(jButton10)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jButton9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
                jPanel25Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel25Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton10)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton9)
                                .addContainerGap())
        );

        GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jPanel12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel14, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel25, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(jPanel25, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jPanel14, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new Font("宋体", Font.BOLD, 18));
        jLabel3.setText("CPU Scheduling");

        jPanel26.setBorder(BorderFactory.createTitledBorder(null, "CPU", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, font18));

        runNameField.setEditable(false);
        runNameField.setFont(font18);

        jLabel12.setFont(font18);
        jLabel12.setText("CPU");

        jLabel15.setFont(font18);
        jLabel15.setText("时间");

        runTimeField.setEditable(false);
        runTimeField.setFont(font18);

        jLabel17.setFont(font18);
        jLabel17.setText("优先级");

        runRankField.setEditable(false);
        runRankField.setFont(font18);

        GroupLayout jPanel35Layout = new GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
                jPanel35Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel35Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel35Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel17)
                                        .addComponent(runRankField, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
                jPanel35Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel35Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(runRankField, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        GroupLayout jPanel22Layout = new GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
                jPanel22Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel22Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel22Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel22Layout.createSequentialGroup()
                                                .addComponent(jLabel15)
                                                .addGap(15, 15, 15))
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                                                .addComponent(runTimeField, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addComponent(jPanel35, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
                jPanel22Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel22Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel22Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel35, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel22Layout.createSequentialGroup()
                                                .addComponent(jLabel15)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(runTimeField, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())))
        );

        channelNum.setModel(new DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));

        jLabel13.setFont(font18);
        jLabel13.setText("道数");

        GroupLayout jPanel8Layout = new GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addGap(10, 10, 10))
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                                .addComponent(channelNum, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())))
        );
        jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(channelNum, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
        );

        GroupLayout jPanel9Layout = new GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
                jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel12, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                                        .addComponent(runNameField))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel22, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(27, 27, 27))
                                        .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addComponent(jPanel8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel9Layout.setVerticalGroup(
                jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addComponent(jLabel12, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(runNameField, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jPanel22, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        GroupLayout jPanel26Layout = new GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
                jPanel26Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel26Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
                jPanel26Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel26Layout.createSequentialGroup()
                                .addComponent(jPanel9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.setBorder(BorderFactory.createTitledBorder(null, "终止进程", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, font18));

        jScrollPane1.setViewportView(endNameJList);

        jLabel7.setFont(font14);
        jLabel7.setText("进程名");

        GroupLayout jPanel27Layout = new GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
                jPanel27Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel27Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel27Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
                jPanel27Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel27Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel11.setFont(font14);
        jLabel11.setText("内存大小");

        jScrollPane5.setViewportView(endSizeJList);

        GroupLayout jPanel31Layout = new GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
                jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel31Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11)
                                        .addComponent(jScrollPane5, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
                jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel31Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel27, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel31, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(1080, 1080, 1080))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel27, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel31, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel19.setBorder(BorderFactory.createTitledBorder(null, "后备队列", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, font18));

        jButton3.setFont(font14);
        jButton3.setText("运行");
        jButton3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                add2ReadyQueue();
            }
        });

        GroupLayout jPanel33Layout = new GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
                jPanel33Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3)
                                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
                jPanel33Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel33Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton3)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane8.setViewportView(reserveRankJList);

        jLabel16.setFont(font14);
        jLabel16.setText("优先级");

        GroupLayout jPanel34Layout = new GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
                jPanel34Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel34Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel34Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane8, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel16, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
                jPanel34Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel34Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel16)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane8, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jLabel18.setFont(font14);
        jLabel18.setText("进程名");

        jScrollPane7.setViewportView(reserveNameJList);

        GroupLayout jPanel17Layout = new GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
                jPanel17Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGroup(jPanel17Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel17Layout.createSequentialGroup()
                                                .addGap(13, 13, 13)
                                                .addComponent(jScrollPane7, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel17Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel18)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
                jPanel17Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel17Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel18)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jScrollPane10.setViewportView(reserveTimeJList);

        jLabel14.setFont(font14);
        jLabel14.setText("运行时间");

        GroupLayout jPanel24Layout = new GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
                jPanel24Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel24Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel24Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel14, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane10, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
                jPanel24Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel24Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel14)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane10, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        GroupLayout jPanel19Layout = new GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
                jPanel19Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jPanel17, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel24, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel34, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel33, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        jPanel19Layout.setVerticalGroup(
                jPanel19Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jPanel33, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel19Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel24, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jPanel34, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jPanel17, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel7.setBorder(BorderFactory.createTitledBorder(null, "挂起进程", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, font18));

        jLabel20.setFont(font14);
        jLabel20.setText("运行时间");

        jScrollPane15.setViewportView(handUpTimeJList);

        GroupLayout jPanel10Layout = new GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
                jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane15, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel20))
                                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
                jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel20)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane15, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0))
        );

        jScrollPane13.setViewportView(handUpRankJList);

        jLabel22.setFont(font14);
        jLabel22.setText("优先级");

        GroupLayout jPanel11Layout = new GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
                jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel22, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane13, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
                jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel22)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane13, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane12.setViewportView(handUpNameJList);

        jLabel24.setFont(font14);
        jLabel24.setText("进程名");

        GroupLayout jPanel15Layout = new GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
                jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel15Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel24, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane12, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
                jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel15Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel24)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane12, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
        );

        jButton5.setFont(font14);
        jButton5.setText("解挂");

        jButton5.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                handDown();
            }
        });

        GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(jPanel11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5)
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jButton5)
                                        .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jPanel15, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jPanel10, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(0, 21, Short.MAX_VALUE))
        );

        jButton4.setFont(font18);
        jButton4.setText("退出");
        jButton4.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                System.exit(WIDTH);
            }
        });

        jTable.setFont(font18);
        jTable.setModel(new DefaultTableModel(
                new Object [][] {
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null}
                },
                new String [] {
                        "进程名", "起址", "长度"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable.setRowHeight(23);
        jTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable);
        if (jTable.getColumnModel().getColumnCount() > 0) {
            jTable.getColumnModel().getColumn(0).setResizable(false);
            jTable.getColumnModel().getColumn(1).setResizable(false);
            jTable.getColumnModel().getColumn(2).setResizable(false);
        }

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
                                                .addGap(714, 714, 714)
                                                .addComponent(jButton4))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanel7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPanel5, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanel26, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanel19, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton4, GroupLayout.Alignment.TRAILING))
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(8, 8, 8)
                                                                .addComponent(jPanel19, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanel26, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPanel7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 629, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 687, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 38, Short.MAX_VALUE))
        );

        pack();
    }

    //添加进程到后备队列
    private void add(MouseEvent evt) {
        int index;
        for (index = 0; index < 4; index++) {
            if(addButton[index].equals(evt.getComponent())){
                break;
            }
        }
        String name = addField[index * 3].getText();
        String time = addField[index * 3 + 1].getText();
        String size = addField[index * 3 + 2].getText();
        if(!"".equals(name) && !"".equals(time) && !"".equals(size)){
            reserveNames.add(name);
            reserveTimes.add(time);
            reserveRanks.add(Objects.requireNonNull(addComboBox.get(index)
                    .getSelectedItem()).toString());
            reserveList.add(new PCB(name,Integer.parseInt(time),
                    Integer.parseInt(reserveRanks.lastElement()), Integer.parseInt(size)));
        }else{
            JOptionPane.showMessageDialog(null, "请完整输入进程所需信息！","提示",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        refreshReserveQueue();
        //清空
        addField[index * 3].setText("");
        addField[index * 3+1].setText("");
        addField[index * 3+2].setText("");
        addComboBox.get(index).setSelectedIndex(0);
    }

    //清空
    private void clear() {
        for (int i = 0; i < 4; i++) {
            addField[i*3].setText("");
            addField[i*3+1].setText("");
            addField[i*3+2].setText("");
            addComboBox.get(i).setSelectedIndex(0);
        }
    }

    //批量添加到就绪队列
    private void add2ReadyQueue(){
        int n = Integer.parseInt(Objects.requireNonNull(channelNum.getSelectedItem()).toString())-readyQueue.size();
        int j = 0;
        for(int i=0; i<n; i++){
            if(reserveList.isEmpty()) break;
            if(j < reserveList.size()){
                PCB pcb = reserveList.get(j);
                if(addOne2Ready(pcb)) {
                    reserveList.remove(j);
                    int index = reserveNames.indexOf(pcb.getName());
                    reserveNames.remove(index);
                    reserveTimes.remove(index);
                    reserveRanks.remove(index);
                }else{
                    j++;
                    i--;
                }
            }else{
                break;
            }
        }
        refreshReserveQueue();
        refreshReadyQueue();
    }

    //挂起
    private void handUp(){
        int selectedIndex = readyNameJList.getSelectedIndex();
        if(-1 == selectedIndex) return;
        handUpNames.add(readyNameJList.getModel().getElementAt(selectedIndex));
        handUpTimes.add(readyTimeJList.getModel().getElementAt(selectedIndex));
        handUpRanks.add(readyRankJList.getModel().getElementAt(selectedIndex));
        String name = readyNames.get(selectedIndex);
        Iterator<PCB> iterator = readyQueue.iterator();
        while (iterator.hasNext()) {
            PCB pcb = iterator.next();
            if(name.equals(pcb.getName())){
                handUpPCBS.add(pcb);
                iterator.remove();
                recycle(pcb);
                if(!reserveList.isEmpty()){
                    for (PCB temp : reserveList) {
                        if(addOne2Ready(temp)){
                            reserveList.remove(temp);
                            int index = reserveNames.indexOf(temp.getName());
                            reserveNames.remove(index);
                            reserveTimes.remove(index);
                            reserveRanks.remove(index);
                            break;
                        }
                    }
                }
                break;
            }
        }
        readyNames.remove(selectedIndex);
        readyTimes.remove(selectedIndex);
        readyRanks.remove(selectedIndex);
        refreshReadyQueue();
        refreshHandUpQueue();
    }

    //解挂
    private void handDown(){
        int selectedIndex = handUpNameJList.getSelectedIndex();
        if(-1 == selectedIndex) return;
        int n = Integer.parseInt(Objects.requireNonNull(channelNum.getSelectedItem()).toString())-readyQueue.size();
        if(n <= 0) return;
        PCB pcb = handUpPCBS.get(selectedIndex);
        if(addOne2Ready(pcb)){
            handUpNames.remove(selectedIndex);
            handUpTimes.remove(selectedIndex);
            handUpRanks.remove(selectedIndex);
            reSort();
            refreshReadyQueue();
            refreshHandUpQueue();
            handUpPCBS.remove(selectedIndex);
        }

    }

    //步进
    private void step(){
        if (readyQueue.isEmpty()) return;
        PCB pcb = readyQueue.remove();
        pcb.setRank(pcb.getRank()-1);
        pcb.setTime(pcb.getTime()-1);
        if(0 == pcb.getTime()){
            endNames.add(pcb.getName());
            endSizes.add(String.valueOf(pcb.getSize()));
            endNameJList.setListData(endNames);
            endSizeJList.setListData(endSizes);
            recycle(pcb);
            if(!reserveList.isEmpty()){
                for (PCB temp : reserveList) {
                    if (addOne2Ready(temp)) {
                        reserveList.remove(temp);
                        int index = reserveNames.indexOf(temp.getName());
                        reserveNames.remove(index);
                        reserveTimes.remove(index);
                        reserveRanks.remove(index);
                        refreshReserveQueue();
                        break;
                    }
                }
            }
        }else{
            readyQueue.add(pcb);
            if (pcb.equals(readyQueue.element())){
                readyTimes.set(0, String.valueOf(pcb.getTime()));
                readyRanks.set(0, String.valueOf(pcb.getRank()));
                refreshReadyQueue();
                return;
            }
        }
        reSort();
        refreshReadyQueue();
    }

    //添加进程到就绪队列
    private boolean addOne2Ready(PCB pcb){
        boolean isFull = true;
        for (Partition partition : partitionTreeSet) {
            if(partition.getSize() >= pcb.getSize()){
                isFull = false;
                //修改空闲分区表
                pcb.setStart(partition.getStart());
                partition.setStart(partition.getStart()+pcb.getSize());
                partition.setSize(partition.getSize()-pcb.getSize());
                if(0 == partition.getSize()){
                    partitionTreeSet.remove(partition);
                }
                //展示
                jTable.setValueAt(pcb.getName(), pcb.getStart(), 0);
                jTable.setValueAt(pcb.getStart(), pcb.getStart(), 1);
                int iter = pcb.getStart();
                for(int j = 1; j <= pcb.getSize(); j++){
                    jTable.setValueAt(j, iter++, 2);
                }
                //添加到就绪队列
                readyQueue.add(pcb);
                readyNames.add(pcb.getName());
                readyTimes.add(String.valueOf(pcb.getTime()));
                readyRanks.add(String.valueOf(pcb.getRank()));
                break;
            }
        }
        if(isFull) {
            JOptionPane.showMessageDialog(null, "找不到空间大于进程" + pcb.getName() + "的空闲分区",
                    "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        return !isFull;
    }

    //内存回收
    private void recycle(PCB pcb){
        //空闲分区表刷新展示
        int start = pcb.getStart();
        int size = pcb.getSize();
        jTable.setValueAt(null, start, 0);
        jTable.setValueAt(null, start, 1);
        for(int i = start; i < start + size; i++){
            jTable.setValueAt(null, i, 2);
        }

        //回收合并空闲分区
        Partition partition = new Partition(pcb.getStart(), pcb.getSize());
        partitionTreeSet.add(partition);
        Partition lower = partitionTreeSet.lower(partition);
        Partition higher = partitionTreeSet.higher(partition);

        if(null != lower && lower.getStart()+lower.getSize() == partition.getStart()){
            partitionTreeSet.remove(lower);
            partition.setStart(lower.getStart());
            partition.setSize(lower.getSize()+partition.getSize());
        }
        if(null != higher && partition.getStart()+partition.getSize() == higher.getStart()){
            partitionTreeSet.remove(higher);
            partition.setSize(higher.getSize()+partition.getSize());
        }
    }

    //更新后备队列
    private void refreshReserveQueue(){
        reserveNameJList.setListData(reserveNames);
        reserveTimeJList.setListData(reserveTimes);
        reserveRankJList.setListData(reserveRanks);
    }

    //更新就绪队列和CPU
    private void refreshReadyQueue(){
        readyNameJList.setListData(readyNames);
        readyTimeJList.setListData(readyTimes);
        readyRankJList.setListData(readyRanks);
        runNameField.setText(readyNames.isEmpty() ? "" : readyNames.get(0));
        runTimeField.setText(readyTimes.isEmpty() ? "" : readyTimes.get(0));
        runRankField.setText(readyRanks.isEmpty() ? "" : readyRanks.get(0));
    }

    //更新挂起队列
    private void refreshHandUpQueue(){
        handUpNameJList.setListData(handUpNames);
        handUpTimeJList.setListData(handUpTimes);
        handUpRankJList.setListData(handUpRanks);
    }

    //重新排列
    private void reSort(){
        readyNames.clear();
        readyTimes.clear();
        readyRanks.clear();
        int i = readyQueue.size();
        Queue<PCB> tempQueue = new PriorityQueue<>();
        while(0 != i--){
            PCB PCB = readyQueue.remove();
            tempQueue.add(PCB);
            readyNames.add(PCB.getName());
            readyTimes.add(String.valueOf(PCB.getTime()));
            readyRanks.add(String.valueOf(PCB.getRank()));
        }
        readyQueue = tempQueue;
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        EventQueue.invokeLater(() -> new AllocationFrame().setVisible(true));
    }
}