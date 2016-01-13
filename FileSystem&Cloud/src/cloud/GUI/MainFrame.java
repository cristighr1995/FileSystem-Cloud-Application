package cloud.GUI;

import cloud.CloudServiceSystem.CloudService;
import cloud.Commands.CommandRunnable;
import cloud.CommandsConfiguration.CommandConfiguration;
import cloud.Configurations.SystemConfiguration;
import cloud.Observator.Subject;
import cloud.ProgramFiles.TreeFileSystem;
import cloud.Users.UserManagement;
import cloud.Utils.AutoComplete;
import cloud.Utils.StackCommandsHistory;
import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.Manager;
import javazoom.jl.player.Player;
import org.jdesktop.swingx.JXTable;

public class MainFrame extends javax.swing.JFrame implements ActionListener, KeyListener {
    private final SystemConfiguration mSystemConfiguration;
    private final UserManagement mUserManagement;
    private final TreeFileSystem mTreeFile;
    private final CloudService mCloudService;
    private final CommandRunnable mCommandRunnable;
    private final Subject mSubject = CommandConfiguration.getSubject();
    private String username, password;
    private String commandStringReturn = "";
    private String commandText = "";
    private String wallpaperPath = "";
    private String terminalBackgroundPath = "";
    private Component mRigidArea;
    private int heightToFill;
    private Action setLastCommandUp, setLastCommandDown, setAutoComplete, setBackSpaceAC;
    private final StackCommandsHistory<String> mLastCommands;
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private static final String GET_LAST_COMMAND_UP = "get last command up";
    private static final String GET_LAST_COMMAND_DOWN = "get last command down";
    private static final String SET_AUTOCOMPLETE = "set autocomplete";
    private static final String SET_BACKSPACE = "set backspace";
    private final AutoComplete<String> mAutoComplete;
    private String commandTextAutoComplete = "";
    private int trigger = 0;
    private SimpleDateFormat mSimpleDateFormat;
    private Calendar mCalendar;
    private String musicPath = "";
    private Player mPlayer;
    private int isPlayingMusic = 0;
    private static final int SECONDS_IN_HOUR = 3600;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int PLAY = 0;
    private static final int PAUSE = 1;
    private static final int RESUME = 2;
    private int mPlayerStatus = PLAY;
    private final DefaultListModel mMusicPlayList = new DefaultListModel();
    
    class PersonalizedListCellRenderer extends JLabel implements ListCellRenderer {

        public PersonalizedListCellRenderer() {
            setOpaque(true);
        }

        public Component getListCellRendererComponent(JList paramlist, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setBackground(Color.red);
            setForeground(Color.white);
            return this;

        }
    }
    
    FileInputStream fis = null;
    File mFile;
    BufferedInputStream bis;
    private long pauseLocation;
    private long songTotalLength;
    
    private static final MainFrame instance = new MainFrame();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        outerPanel = new javax.swing.JPanel();
        panel_clock = new javax.swing.JPanel();
        lbl_clock_text = new javax.swing.JLabel();
        lbl_clock_icon = new javax.swing.JLabel();
        panel_dock = new javax.swing.JPanel();
        btn_terminal = new javax.swing.JButton();
        btn_calendar = new javax.swing.JButton();
        btn_music = new javax.swing.JButton();
        btn_settings = new javax.swing.JButton();
        panel_login = new javax.swing.JPanel();
        txt_user = new javax.swing.JTextField();
        txt_pass = new javax.swing.JPasswordField();
        btn_login = new javax.swing.JButton();
        frame_settings = new javax.swing.JInternalFrame();
        panel_settings_inside = new javax.swing.JPanel();
        lbl_changeTerminalBackground = new javax.swing.JLabel();
        lbl_changeWallpaper = new javax.swing.JLabel();
        btn_browseWallpaper = new javax.swing.JButton();
        btn_browseTerminalBackground = new javax.swing.JButton();
        btn_shutdown = new javax.swing.JButton();
        btn_apply = new javax.swing.JButton();
        panel_aboutThisComputer = new javax.swing.JPanel();
        lbl_aboutComputer = new javax.swing.JLabel();
        lbl_os = new javax.swing.JLabel();
        lbl_procesor = new javax.swing.JLabel();
        lbl_memory = new javax.swing.JLabel();
        lbl_sizehdd = new javax.swing.JLabel();
        lbl_background_settings = new javax.swing.JLabel();
        frame_terminal = new javax.swing.JInternalFrame();
        panel_terminal_inside = new javax.swing.JPanel();
        scroll = new javax.swing.JScrollPane();
        panel_scrollable_inside = new javax.swing.JPanel();
        txt_input_terminal = new javax.swing.JTextField();
        lbl_background_terminal = new javax.swing.JLabel();
        frame_calendar = new javax.swing.JInternalFrame();
        panel_calendar = new javax.swing.JPanel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        lbl_background_calendar = new javax.swing.JLabel();
        frame_music = new javax.swing.JInternalFrame();
        panel_music = new org.jdesktop.swingx.JXPanel();
        btn_music_browse = new org.jdesktop.swingx.JXButton();
        btn_music_play = new org.jdesktop.swingx.JXButton();
        btn_music_stop = new org.jdesktop.swingx.JXButton();
        btn_music_remove = new org.jdesktop.swingx.JXButton();
        scroll_music_list = new javax.swing.JScrollPane();
        lst_music_playlist = new org.jdesktop.swingx.JXList();
        lbl_background_music = new javax.swing.JLabel();
        lbl_backgroundImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        outerPanel.setPreferredSize(new java.awt.Dimension(1750, 950));
        outerPanel.setLayout(null);

        panel_clock.setBackground(new java.awt.Color(255, 255, 204));

        lbl_clock_text.setFont(new java.awt.Font("sansserif", 0, 30)); // NOI18N
        lbl_clock_text.setForeground(new java.awt.Color(255, 255, 255));
        lbl_clock_text.setText("21:50:30");

        javax.swing.GroupLayout panel_clockLayout = new javax.swing.GroupLayout(panel_clock);
        panel_clock.setLayout(panel_clockLayout);
        panel_clockLayout.setHorizontalGroup(
            panel_clockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_clockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_clock_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_clock_text, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_clockLayout.setVerticalGroup(
            panel_clockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_clockLayout.createSequentialGroup()
                .addGroup(panel_clockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_clockLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_clock_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_clockLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(lbl_clock_text)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        outerPanel.add(panel_clock);
        panel_clock.setBounds(1550, 10, 200, 70);

        javax.swing.GroupLayout panel_dockLayout = new javax.swing.GroupLayout(panel_dock);
        panel_dock.setLayout(panel_dockLayout);
        panel_dockLayout.setHorizontalGroup(
            panel_dockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_terminal, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_settings, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_calendar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_music, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_dockLayout.setVerticalGroup(
            panel_dockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_dockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_terminal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_calendar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_music, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_settings, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        outerPanel.add(panel_dock);
        panel_dock.setBounds(690, 870, 290, 70);

        panel_login.setBackground(new java.awt.Color(247, 180, 132));
        panel_login.setOpaque(false);
        panel_login.setPreferredSize(new java.awt.Dimension(300, 250));

        txt_user.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        txt_user.setText("Enter username...");
        txt_user.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 10, 2, 2));

        txt_pass.setFont(new java.awt.Font("sansserif", 0, 16)); // NOI18N
        txt_pass.setText("jPasswordField1");
        txt_pass.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 10, 2, 2));
        txt_pass.setEchoChar('\u25cf');
        txt_pass.setPreferredSize(new java.awt.Dimension(110, 28));

        btn_login.setText("Login");

        javax.swing.GroupLayout panel_loginLayout = new javax.swing.GroupLayout(panel_login);
        panel_login.setLayout(panel_loginLayout);
        panel_loginLayout.setHorizontalGroup(
            panel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_loginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_pass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_user))
                .addContainerGap())
            .addGroup(panel_loginLayout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(147, Short.MAX_VALUE))
        );
        panel_loginLayout.setVerticalGroup(
            panel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_loginLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(txt_user, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(txt_pass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_login, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap())
        );

        outerPanel.add(panel_login);
        panel_login.setBounds(70, 400, 378, 214);

        frame_settings.setClosable(true);
        frame_settings.setVisible(true);

        panel_settings_inside.setLayout(null);

        lbl_changeTerminalBackground.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lbl_changeTerminalBackground.setText("Schimba fundal terminal : ");
        panel_settings_inside.add(lbl_changeTerminalBackground);
        lbl_changeTerminalBackground.setBounds(10, 50, 163, 30);

        lbl_changeWallpaper.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lbl_changeWallpaper.setText("Schimba fundal :");
        panel_settings_inside.add(lbl_changeWallpaper);
        lbl_changeWallpaper.setBounds(10, 10, 105, 30);

        btn_browseWallpaper.setText("Browse");
        panel_settings_inside.add(btn_browseWallpaper);
        btn_browseWallpaper.setBounds(120, 10, 70, 28);

        btn_browseTerminalBackground.setText("Browse");
        panel_settings_inside.add(btn_browseTerminalBackground);
        btn_browseTerminalBackground.setBounds(170, 50, 70, 28);

        btn_shutdown.setText("Shut down");
        panel_settings_inside.add(btn_shutdown);
        btn_shutdown.setBounds(10, 240, 90, 28);

        btn_apply.setText("Apply");
        panel_settings_inside.add(btn_apply);
        btn_apply.setBounds(460, 240, 57, 28);

        lbl_aboutComputer.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lbl_aboutComputer.setText("About this computer");

        lbl_os.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lbl_os.setText("Sistem operare:");

        lbl_procesor.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lbl_procesor.setText("Procesor: ");

        lbl_memory.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lbl_memory.setText("Memorie: ");

        lbl_sizehdd.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lbl_sizehdd.setText("Capacitate HDD:");

        javax.swing.GroupLayout panel_aboutThisComputerLayout = new javax.swing.GroupLayout(panel_aboutThisComputer);
        panel_aboutThisComputer.setLayout(panel_aboutThisComputerLayout);
        panel_aboutThisComputerLayout.setHorizontalGroup(
            panel_aboutThisComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_aboutThisComputerLayout.createSequentialGroup()
                .addContainerGap(182, Short.MAX_VALUE)
                .addComponent(lbl_aboutComputer)
                .addGap(180, 180, 180))
            .addGroup(panel_aboutThisComputerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_aboutThisComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_aboutThisComputerLayout.createSequentialGroup()
                        .addComponent(lbl_memory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap(450, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_aboutThisComputerLayout.createSequentialGroup()
                        .addComponent(lbl_procesor)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_aboutThisComputerLayout.createSequentialGroup()
                        .addGroup(panel_aboutThisComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_sizehdd)
                            .addComponent(lbl_os))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panel_aboutThisComputerLayout.setVerticalGroup(
            panel_aboutThisComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_aboutThisComputerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_aboutComputer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_os)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_procesor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_memory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_sizehdd, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        panel_settings_inside.add(panel_aboutThisComputer);
        panel_aboutThisComputer.setBounds(10, 80, 520, 150);
        panel_settings_inside.add(lbl_background_settings);
        lbl_background_settings.setBounds(1, -4, 530, 290);

        javax.swing.GroupLayout frame_settingsLayout = new javax.swing.GroupLayout(frame_settings.getContentPane());
        frame_settings.getContentPane().setLayout(frame_settingsLayout);
        frame_settingsLayout.setHorizontalGroup(
            frame_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_settings_inside, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        frame_settingsLayout.setVerticalGroup(
            frame_settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_settings_inside, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        outerPanel.add(frame_settings);
        frame_settings.setBounds(60, 70, 540, 310);

        frame_terminal.setClosable(true);
        frame_terminal.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        frame_terminal.setVisible(true);

        panel_terminal_inside.setBackground(new java.awt.Color(51, 255, 153));
        panel_terminal_inside.setLayout(null);

        scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        panel_scrollable_inside.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        panel_scrollable_inside.setVerifyInputWhenFocusTarget(false);
        panel_scrollable_inside.setLayout(new java.awt.GridBagLayout());
        scroll.setViewportView(panel_scrollable_inside);

        panel_terminal_inside.add(scroll);
        scroll.setBounds(-2, -2, 740, 386);

        txt_input_terminal.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        txt_input_terminal.setForeground(new java.awt.Color(18, 30, 49));
        txt_input_terminal.setText("Scrieti o comanda...");
        txt_input_terminal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        panel_terminal_inside.add(txt_input_terminal);
        txt_input_terminal.setBounds(-1, 382, 742, 38);
        panel_terminal_inside.add(lbl_background_terminal);
        lbl_background_terminal.setBounds(0, 0, 740, 419);

        javax.swing.GroupLayout frame_terminalLayout = new javax.swing.GroupLayout(frame_terminal.getContentPane());
        frame_terminal.getContentPane().setLayout(frame_terminalLayout);
        frame_terminalLayout.setHorizontalGroup(
            frame_terminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_terminal_inside, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
        );
        frame_terminalLayout.setVerticalGroup(
            frame_terminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_terminal_inside, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        outerPanel.add(frame_terminal);
        frame_terminal.setBounds(490, 170, 750, 450);

        frame_calendar.setClosable(true);
        frame_calendar.setPreferredSize(new java.awt.Dimension(290, 260));
        frame_calendar.setVisible(true);

        panel_calendar.setPreferredSize(new java.awt.Dimension(278, 248));
        panel_calendar.setLayout(null);
        panel_calendar.add(jXDatePicker1);
        jXDatePicker1.setBounds(6, 6, 266, 28);
        panel_calendar.add(lbl_background_calendar);
        lbl_background_calendar.setBounds(-3, 1, 280, 230);

        javax.swing.GroupLayout frame_calendarLayout = new javax.swing.GroupLayout(frame_calendar.getContentPane());
        frame_calendar.getContentPane().setLayout(frame_calendarLayout);
        frame_calendarLayout.setHorizontalGroup(
            frame_calendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_calendar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        frame_calendarLayout.setVerticalGroup(
            frame_calendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_calendar, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
        );

        outerPanel.add(frame_calendar);
        frame_calendar.setBounds(1320, 160, 290, 260);

        frame_music.setClosable(true);
        frame_music.setVisible(true);

        panel_music.setLayout(null);
        panel_music.add(btn_music_browse);
        btn_music_browse.setBounds(10, 10, 30, 30);
        panel_music.add(btn_music_play);
        btn_music_play.setBounds(120, 10, 40, 40);
        panel_music.add(btn_music_stop);
        btn_music_stop.setBounds(160, 10, 40, 40);
        panel_music.add(btn_music_remove);
        btn_music_remove.setBounds(40, 10, 30, 30);

        lst_music_playlist.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        scroll_music_list.setViewportView(lst_music_playlist);

        panel_music.add(scroll_music_list);
        scroll_music_list.setBounds(5, 53, 310, 190);
        panel_music.add(lbl_background_music);
        lbl_background_music.setBounds(0, 0, 320, 250);

        javax.swing.GroupLayout frame_musicLayout = new javax.swing.GroupLayout(frame_music.getContentPane());
        frame_music.getContentPane().setLayout(frame_musicLayout);
        frame_musicLayout.setHorizontalGroup(
            frame_musicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_music, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
        );
        frame_musicLayout.setVerticalGroup(
            frame_musicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_music, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        outerPanel.add(frame_music);
        frame_music.setBounds(1320, 460, 330, 280);

        lbl_backgroundImage.setBackground(new java.awt.Color(251, 242, 213));
        lbl_backgroundImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outerPanel.add(lbl_backgroundImage);
        lbl_backgroundImage.setBounds(0, -10, 1800, 1020);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(outerPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(outerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_apply;
    private javax.swing.JButton btn_browseTerminalBackground;
    private javax.swing.JButton btn_browseWallpaper;
    private javax.swing.JButton btn_calendar;
    private javax.swing.JButton btn_login;
    private javax.swing.JButton btn_music;
    private org.jdesktop.swingx.JXButton btn_music_browse;
    private org.jdesktop.swingx.JXButton btn_music_play;
    private org.jdesktop.swingx.JXButton btn_music_remove;
    private org.jdesktop.swingx.JXButton btn_music_stop;
    private javax.swing.JButton btn_settings;
    private javax.swing.JButton btn_shutdown;
    private javax.swing.JButton btn_terminal;
    private javax.swing.JInternalFrame frame_calendar;
    private javax.swing.JInternalFrame frame_music;
    private javax.swing.JInternalFrame frame_settings;
    private javax.swing.JInternalFrame frame_terminal;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private javax.swing.JLabel lbl_aboutComputer;
    private javax.swing.JLabel lbl_backgroundImage;
    private javax.swing.JLabel lbl_background_calendar;
    private javax.swing.JLabel lbl_background_music;
    private javax.swing.JLabel lbl_background_settings;
    private javax.swing.JLabel lbl_background_terminal;
    private javax.swing.JLabel lbl_changeTerminalBackground;
    private javax.swing.JLabel lbl_changeWallpaper;
    private javax.swing.JLabel lbl_clock_icon;
    private javax.swing.JLabel lbl_clock_text;
    private javax.swing.JLabel lbl_memory;
    private javax.swing.JLabel lbl_os;
    private javax.swing.JLabel lbl_procesor;
    private javax.swing.JLabel lbl_sizehdd;
    private org.jdesktop.swingx.JXList lst_music_playlist;
    private javax.swing.JPanel outerPanel;
    private javax.swing.JPanel panel_aboutThisComputer;
    private javax.swing.JPanel panel_calendar;
    private javax.swing.JPanel panel_clock;
    private javax.swing.JPanel panel_dock;
    private javax.swing.JPanel panel_login;
    private org.jdesktop.swingx.JXPanel panel_music;
    private javax.swing.JPanel panel_scrollable_inside;
    private javax.swing.JPanel panel_settings_inside;
    private javax.swing.JPanel panel_terminal_inside;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JScrollPane scroll_music_list;
    private javax.swing.JTextField txt_input_terminal;
    private javax.swing.JPasswordField txt_pass;
    private javax.swing.JTextField txt_user;
    // End of variables declaration//GEN-END:variables

    private MainFrame() {
        initComponents();
        initGUI();
        runInBackgroundClock();

        mSystemConfiguration = new SystemConfiguration();
        mUserManagement = mSystemConfiguration.getExistingUsers();
        mTreeFile = mSystemConfiguration.getSystemConfiguration();
        mCloudService = new CloudService(mTreeFile);
        mCommandRunnable = new CommandRunnable(mTreeFile, mUserManagement, "", mSystemConfiguration, mCloudService);
        mLastCommands = new StackCommandsHistory<>();
        mAutoComplete = new AutoComplete<>(mTreeFile);
    }
    
    private void initGUI() {
        mSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        //Start din mijlocul ecranului 
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
        
        /*
         * Labels Area
        */
        lbl_backgroundImage.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\background_main_frame.png")).getImage()).getScaledInstance(lbl_backgroundImage.getWidth(), lbl_backgroundImage.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        lbl_backgroundImage.setHorizontalTextPosition(JButton.CENTER);
        lbl_backgroundImage.setVerticalTextPosition(JButton.CENTER);
        lbl_backgroundImage.setBorder(BorderFactory.createEmptyBorder());
        
        lbl_background_terminal.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\background_terminal_frame.png")).getImage()).getScaledInstance(lbl_background_terminal.getWidth(), lbl_background_terminal.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        lbl_background_terminal.setHorizontalTextPosition(JButton.CENTER);
        lbl_background_terminal.setVerticalTextPosition(JButton.CENTER);
        lbl_background_terminal.setBorder(BorderFactory.createEmptyBorder()); 
        
        lbl_background_settings.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\background_settings_frame.png")).getImage()).getScaledInstance(lbl_background_settings.getWidth(), lbl_background_settings.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        lbl_background_settings.setHorizontalTextPosition(JButton.CENTER);
        lbl_background_settings.setVerticalTextPosition(JButton.CENTER);
        lbl_background_settings.setBorder(BorderFactory.createEmptyBorder());  
        
        lbl_background_calendar.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\background_calendar_frame.png")).getImage()).getScaledInstance(lbl_background_calendar.getWidth(), lbl_background_calendar.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        lbl_background_calendar.setHorizontalTextPosition(JButton.CENTER);
        lbl_background_calendar.setVerticalTextPosition(JButton.CENTER);
        lbl_background_calendar.setBorder(BorderFactory.createEmptyBorder());
        
        lbl_background_music.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\background_music_frame.png")).getImage()).getScaledInstance(lbl_background_music.getWidth(), lbl_background_music.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        lbl_background_music.setHorizontalTextPosition(JButton.CENTER);
        lbl_background_music.setVerticalTextPosition(JButton.CENTER);
        lbl_background_music.setBorder(BorderFactory.createEmptyBorder()); 
        
        lbl_clock_icon.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\icon_clock.png")).getImage()).getScaledInstance(lbl_clock_icon.getWidth(), lbl_clock_icon.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        lbl_clock_icon.setHorizontalTextPosition(JButton.CENTER);
        lbl_clock_icon.setVerticalTextPosition(JButton.CENTER);
        lbl_clock_icon.setBorder(BorderFactory.createEmptyBorder());
        
        String os = ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getName();
        lbl_os.setText("Sistem operare: " + os);
        String procId = System.getenv("PROCESSOR_IDENTIFIER");
        String procArh = System.getenv("PROCESSOR_ARCHITECTURE");
        String procNr = System.getenv("NUMBER_OF_PROCESSORS");
        lbl_procesor.setText("Procesor: " + procId + ", " + procArh + ", " + procNr + " cores");
        long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
        memorySize /= 1024 * 1024 * 1024;
        lbl_memory.setText("RAM: " + memorySize + " GB");
        long diskSize = new java.io.File("/").getTotalSpace();
        diskSize /= 1024 * 1024 * 1024;
        lbl_sizehdd.setText("Capacitate HDD: " + diskSize + " GB");
        
        lbl_os.setForeground(new Color(252, 142, 91));
        lbl_procesor.setForeground(new Color(212, 36, 38));
        lbl_memory.setForeground(new Color(60, 141, 13));
        lbl_sizehdd.setForeground(new Color(47, 67, 177));
        
        /*
         * Text Area
        */     
        txt_user.setBackground(new Color(18, 30, 49));
        txt_user.setForeground(new Color(255, 255, 255));
        
        txt_pass.setBackground(new Color(18, 30, 49));
        txt_pass.setForeground(new Color(255, 255, 255));
        
        // Urmatoarele 2 linii fac JTextField transparent
        txt_input_terminal.setOpaque(false);
        txt_input_terminal.setBackground(new Color(0, 0, 0, 0));
        Border rounded = new LineBorder(new Color(4, 38, 76), 1, true);
        Border empty = new EmptyBorder(0, 5, 0, 0);
        Border border = new CompoundBorder(rounded, empty);
        txt_input_terminal.setBorder(border);
        txt_input_terminal.setForeground(new Color(18, 30, 49));
        
        JTextPane firstTextPane = new JTextPane();
        firstTextPane.setBorder(new EmptyBorder(5, 5, 0, 0));
        firstTextPane.setText("Output-ul comenzilor...Sarbatori fericite!");
        firstTextPane.setOpaque(false);
        firstTextPane.setBackground(new Color(0, 0, 0, 0));
        firstTextPane.setEditable(false);
        firstTextPane.setForeground(Color.yellow);
        firstTextPane.setFont(new Font("sansserif", Font.PLAIN, 18));
        
        // Inaltimea de care am nevoie pentru a putea completa JPanel, deoarece BoxLayout intinde elementele pe toata inaltimea panel-ului
        heightToFill = firstTextPane.getPreferredSize().height;
        heightToFill = 380 - heightToFill;
        
        /*
         * Buttons Area
        */
        btn_login.setBackground(new Color(18, 30, 49));
        btn_login.setForeground(new Color(255, 255, 255));
        btn_login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn_terminal.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\icon_terminal.png")).getImage()).getScaledInstance(btn_terminal.getWidth(), btn_terminal.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        btn_terminal.setHorizontalTextPosition(JButton.CENTER);
        btn_terminal.setVerticalTextPosition(JButton.CENTER);
        btn_terminal.setContentAreaFilled(false);
        btn_terminal.setFocusPainted(false);
        btn_terminal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_terminal.setBorder(BorderFactory.createEmptyBorder());
        
        btn_settings.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\icon_settings.png")).getImage()).getScaledInstance(btn_settings.getWidth(), btn_settings.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        btn_settings.setHorizontalTextPosition(JButton.CENTER);
        btn_settings.setVerticalTextPosition(JButton.CENTER);
        btn_settings.setContentAreaFilled(false);
        btn_settings.setFocusPainted(false);
        btn_settings.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_settings.setBorder(BorderFactory.createEmptyBorder());
        
        btn_calendar.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\icon_calendar.png")).getImage()).getScaledInstance(btn_calendar.getWidth(), btn_calendar.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        btn_calendar.setHorizontalTextPosition(JButton.CENTER);
        btn_calendar.setVerticalTextPosition(JButton.CENTER);
        btn_calendar.setContentAreaFilled(false);
        btn_calendar.setFocusPainted(false);
        btn_calendar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_calendar.setBorder(BorderFactory.createEmptyBorder());
        
        btn_music.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\icon_music.png")).getImage()).getScaledInstance(btn_music.getWidth(), btn_music.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        btn_music.setHorizontalTextPosition(JButton.CENTER);
        btn_music.setVerticalTextPosition(JButton.CENTER);
        btn_music.setContentAreaFilled(false);
        btn_music.setFocusPainted(false);
        btn_music.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_music.setBorder(BorderFactory.createEmptyBorder());
        
        btn_music_browse.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\icon_add.png")).getImage()).getScaledInstance(btn_music_browse.getWidth(), btn_music_browse.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        btn_music_browse.setHorizontalTextPosition(JButton.CENTER);
        btn_music_browse.setVerticalTextPosition(JButton.CENTER);
        btn_music_browse.setContentAreaFilled(false);
        btn_music_browse.setFocusPainted(false);
        btn_music_browse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_music_browse.setBorder(BorderFactory.createEmptyBorder());
        
        btn_music_play.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\icon_play.png")).getImage()).getScaledInstance(btn_music_play.getWidth(), btn_music_play.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        btn_music_play.setHorizontalTextPosition(JButton.CENTER);
        btn_music_play.setVerticalTextPosition(JButton.CENTER);
        btn_music_play.setContentAreaFilled(false);
        btn_music_play.setFocusPainted(false);
        btn_music_play.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_music_play.setBorder(BorderFactory.createEmptyBorder());
        
        btn_music_stop.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\icon_stop.png")).getImage()).getScaledInstance(btn_music_stop.getWidth(), btn_music_stop.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        btn_music_stop.setHorizontalTextPosition(JButton.CENTER);
        btn_music_stop.setVerticalTextPosition(JButton.CENTER);
        btn_music_stop.setContentAreaFilled(false);
        btn_music_stop.setFocusPainted(false);
        btn_music_stop.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_music_stop.setBorder(BorderFactory.createEmptyBorder());
        
        btn_music_remove.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\icon_subtract.png")).getImage()).getScaledInstance(btn_music_remove.getWidth(), btn_music_remove.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        btn_music_remove.setHorizontalTextPosition(JButton.CENTER);
        btn_music_remove.setVerticalTextPosition(JButton.CENTER);
        btn_music_remove.setContentAreaFilled(false);
        btn_music_remove.setFocusPainted(false);
        btn_music_remove.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_music_remove.setBorder(BorderFactory.createEmptyBorder());
        
        /*
         * Lists Area
        */
        lst_music_playlist.removeAll();
        //lst_music_playlist.setCellRenderer(new PersonalizedListCellRenderer());
        lst_music_playlist.setModel(mMusicPlayList);
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {

                    runInBackgroundPlayMusic();
                }
            }
        };
        lst_music_playlist.addMouseListener(mouseListener);
        
        /*
         * Panels Area
        */
        panel_dock.setBackground(new Color(18, 30, 49));
        panel_dock.setVisible(false);
        panel_settings_inside.setOpaque(false);
        panel_aboutThisComputer.setOpaque(false);
        panel_scrollable_inside.setOpaque(false);
        panel_clock.setOpaque(false);
        panel_scrollable_inside.setLayout(new BoxLayout(panel_scrollable_inside, BoxLayout.Y_AXIS));
        
        panel_scrollable_inside.add(firstTextPane);
        mRigidArea = Box.createRigidArea(new Dimension(0, heightToFill));
        panel_scrollable_inside.add(mRigidArea);
        
        /*
         * ScrollPanes Area
        */
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBackground(new Color(0, 0, 0, 0));
        scroll.setBorder(new EmptyBorder(0, 0, 0, 0));
        
        scroll_music_list.setOpaque(false);
        scroll_music_list.getViewport().setOpaque(false);
        scroll_music_list.setBackground(new Color(0, 0, 0, 0));
        scroll_music_list.setBorder(new EmptyBorder(0, 0, 0, 0));
        lst_music_playlist.setOpaque(false);
        lst_music_playlist.setBackground(new Color(0, 0, 0, 0));
        lst_music_playlist.setForeground(Color.white);
        lst_music_playlist.setSelectionBackground(new Color(46, 174, 156));
        lst_music_playlist.setSelectionForeground(new Color(255, 255, 255));
        
        /*
         * Frames Area
        */
        frame_terminal.setVisible(false);
        frame_settings.setVisible(false);
        frame_calendar.setVisible(false);
        frame_music.setVisible(false);
        
        /*
         * Listeners Area
        */
        btn_login.addActionListener(this);
        btn_terminal.addActionListener(this);
        btn_settings.addActionListener(this);
        btn_browseWallpaper.addActionListener(this);
        btn_browseTerminalBackground.addActionListener(this);
        btn_shutdown.addActionListener(this);
        btn_apply.addActionListener(this);
        btn_calendar.addActionListener(this);
        txt_input_terminal.addActionListener(this);
        btn_music.addActionListener(this);
        btn_music_browse.addActionListener(this);
        btn_music_play.addActionListener(this);
        btn_music_stop.addActionListener(this);
        btn_music_remove.addActionListener(this);
        
        txt_user.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                txt_user.setText("");
            }
        });
        txt_pass.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txt_pass.setText("");
            }

            @Override
            public void focusLost(FocusEvent fe) {

            }
        });
        txt_input_terminal.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                txt_input_terminal.setText("");
            }
        });
        
        setLastCommandUp = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String lastCommandEntered = mLastCommands.next();
                int index = mLastCommands.getIndex();
                txt_input_terminal.setText(lastCommandEntered);
            }
        };
        
        setLastCommandDown = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String lastCommandEntered = mLastCommands.back();
                int index = mLastCommands.getIndex();
                txt_input_terminal.setText(lastCommandEntered);
            }
        };
        
        setAutoComplete = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                
                ActionListener menuListener = new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        System.out.println("Popup menu item ["
                                + event.getActionCommand() + "] was pressed.");
                    }
                };
                
                if(trigger == 0) {
                    commandTextAutoComplete = txt_input_terminal.getText();
                    trigger = 1;
                    mAutoComplete.updatePosibilities(commandTextAutoComplete);
                }
                String txtInput = txt_input_terminal.getText();
                StringTokenizer st = new StringTokenizer(txtInput);
                ArrayList<String> params = new ArrayList<>();
                while(st.hasMoreTokens()) {
                    params.add(st.nextToken());
                }
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < params.size() - 1; i++)
                    sb.append(params.get(i)).append(" ");
                
                if(sb.toString().isEmpty())
                    txt_input_terminal.setText(mAutoComplete.next());
                else
                    txt_input_terminal.setText(sb.toString() + mAutoComplete.next());
            }
        };
        
        txt_input_terminal.getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), GET_LAST_COMMAND_UP);
        txt_input_terminal.getActionMap().put(GET_LAST_COMMAND_UP, setLastCommandUp);
        
        txt_input_terminal.getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), GET_LAST_COMMAND_DOWN);
        txt_input_terminal.getActionMap().put(GET_LAST_COMMAND_DOWN, setLastCommandDown);
        
        txt_input_terminal.setFocusTraversalKeysEnabled(false);
        txt_input_terminal.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), SET_AUTOCOMPLETE);
        txt_input_terminal.getActionMap().put(SET_AUTOCOMPLETE, setAutoComplete);
        
        txt_input_terminal.addKeyListener(this);
    }
    
    public static MainFrame getInstance() {
        return instance;
    }
    
    public static int getContentHeight(String content) {
        JEditorPane dummyEditorPane=new JEditorPane();
        dummyEditorPane.setSize(100, Short.MAX_VALUE);
        dummyEditorPane.setText(content);
        
        return dummyEditorPane.getPreferredSize().height;
    }
    
    public CommandRunnable getCommandRunnable() {
        return this.mCommandRunnable;
    }
    
    public JTextField getInputTextField() {
        return txt_input_terminal;
    }
    
    public void setCommandStringReturn(String str) {
        commandStringReturn = str;
    }
    
    private void scrollToBottom(JScrollPane scrollPane) {
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }
    
    public void runWelcome() {
        if(mUserManagement.findUser(username) == null || mUserManagement.findUser(username).getPassword().compareTo(password) != 0) {
            username = txt_user.getText();
            password = txt_pass.getText();
            JOptionPane.showMessageDialog(null, "Username / Parola gresit(a)!");
            return;
        }
        
        JOptionPane.showMessageDialog(null, "Bine ati venit, " + mUserManagement.findUser(username).getFirstName() + " " + mUserManagement.findUser(username).getLastName());
        panel_login.setVisible(false);
        panel_dock.setVisible(true);
        
        //System.out.println("Bine ati venit, " + mUserManagement.findUser(username).getFirstName() + " " + mUserManagement.findUser(username).getLastName());
        mUserManagement.login(mUserManagement.findUser(username));
        mSystemConfiguration.updateUsersInFile(mUserManagement);
        
        mCalendar = Calendar.getInstance();
        mSubject.setStateUser("S-a logat user-ul: " + username + " la data: " + mSimpleDateFormat.format(mCalendar.getTime()));
        
        //System.out.println("Puteti sa va faceti de cap prin sistem! :) ");
        //System.out.println("Introduceti orice comanda...");
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof JButton) {
            if (((JButton) ae.getSource()).equals(btn_login)) {
                username = txt_user.getText();
                password = txt_pass.getText();
                runWelcome();
            }

            if (((JButton) ae.getSource()).equals(btn_terminal)) {
                frame_terminal.setVisible(true);
                frame_terminal.setTitle(mUserManagement.id().getUsername() + "PC");
            }
            
            if (((JButton) ae.getSource()).equals(btn_settings)) {
                frame_settings.setVisible(true);
            }
            
            if (((JButton) ae.getSource()).equals(btn_calendar)) {
                frame_calendar.setVisible(true);
            }
            
            if (((JButton) ae.getSource()).equals(btn_music)) {
                frame_music.setVisible(true);
            }
            
            if (((JButton) ae.getSource()).equals(btn_browseWallpaper)) {
                JFileChooser mChooser = new JFileChooser();
                mChooser.showOpenDialog(null);
                java.io.File fileChosen = mChooser.getSelectedFile();
                wallpaperPath = fileChosen.getAbsolutePath();
            }
            if (((JButton) ae.getSource()).equals(btn_browseTerminalBackground)) {
                JFileChooser mChooser = new JFileChooser();
                mChooser.showOpenDialog(null);
                java.io.File fileChosen = mChooser.getSelectedFile();
                terminalBackgroundPath = fileChosen.getAbsolutePath();
            }
            
            if (((JButton) ae.getSource()).equals(btn_shutdown)) {
                System.exit(0);
            }
            
            if (((JButton) ae.getSource()).equals(btn_apply)) {
                if (!wallpaperPath.isEmpty() && wallpaperPath != null) {
                    lbl_backgroundImage.setIcon(new ImageIcon(((new ImageIcon(wallpaperPath)).getImage()).getScaledInstance(lbl_backgroundImage.getWidth(), lbl_backgroundImage.getHeight(), java.awt.Image.SCALE_SMOOTH)));
                    lbl_backgroundImage.setHorizontalTextPosition(JButton.CENTER);
                    lbl_backgroundImage.setVerticalTextPosition(JButton.CENTER);
                    lbl_backgroundImage.setBorder(BorderFactory.createEmptyBorder());
                }
                
                if(!terminalBackgroundPath.isEmpty() && terminalBackgroundPath != null) {
                    lbl_background_terminal.setIcon(new ImageIcon(((new ImageIcon(terminalBackgroundPath)).getImage()).getScaledInstance(lbl_background_terminal.getWidth(), lbl_background_terminal.getHeight(), java.awt.Image.SCALE_SMOOTH)));
                    lbl_background_terminal.setHorizontalTextPosition(JButton.CENTER);
                    lbl_background_terminal.setVerticalTextPosition(JButton.CENTER);
                    lbl_background_terminal.setBorder(BorderFactory.createEmptyBorder());
                }
            }
            
            if (((JButton) ae.getSource()).equals(btn_music_browse)) {
                JFileChooser mChooser = new JFileChooser();
                mChooser.showOpenDialog(null);
                java.io.File fileChosen = mChooser.getSelectedFile();
                musicPath = fileChosen.getAbsolutePath();
                mPlayerStatus = PLAY;
                mMusicPlayList.addElement(musicPath);
                lst_music_playlist.setModel(mMusicPlayList);
                lst_music_playlist.setSelectedIndex(mMusicPlayList.size() - 1);
            }
            
            if (((JButton) ae.getSource()).equals(btn_music_play)) {
                String text = ((JButton) ae.getSource()).getText();
                
                if(mPlayerStatus == PLAY)
                    runInBackgroundPlayMusic();
                if(mPlayerStatus == PAUSE)
                    runInBackgroundPauseMusic();
                if(mPlayerStatus == RESUME)
                    runInBackgroundResumeMusic();
            }
            
            if (((JButton) ae.getSource()).equals(btn_music_stop)) {
                runInBackgroundStopMusic();
            }
            
            if (((JButton) ae.getSource()).equals(btn_music_remove)) {
                mMusicPlayList.remove(lst_music_playlist.getSelectedIndex());
                lst_music_playlist.setModel(mMusicPlayList);
            }
        }
        
        if (ae.getSource() instanceof JTextField) {
            if (((JTextField) ae.getSource()).equals(txt_input_terminal)) {
                mLastCommands.resetIndex();
                
                commandText = txt_input_terminal.getText();
                mCommandRunnable.run(commandText);
                commandStringReturn = CommandConfiguration.getReturnString();

                mLastCommands.add(commandText);
                
                trigger = 0;
                
                try {
                    JTextPane mTextPane = new JTextPane();
                    mTextPane.setBorder(new EmptyBorder(5, 5, 0, 0));
                    mTextPane.setOpaque(false);
                    mTextPane.setBackground(new Color(0, 0, 0, 0));
                    mTextPane.setEditable(false);
                    mTextPane.setForeground(Color.white);
                    mTextPane.setFont(new Font("sansserif", Font.PLAIN, 18));

                    String prompt = mUserManagement.id().getUsername() + "@" + mTreeFile.getCurrentDirector().getPath() + ": ";

                    StyleContext scCommandText = StyleContext.getDefaultStyleContext();
                    AttributeSet attrsCommandText = scCommandText.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.cyan);
                    mTextPane.getStyledDocument().insertString(mTextPane.getText().length(), prompt + commandText, attrsCommandText);

                    if (commandStringReturn.startsWith("Invalid")) {
                        StyleContext scCommandStringReturn = StyleContext.getDefaultStyleContext();
                        AttributeSet attrsCommandStringReturn = scCommandStringReturn.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(230, 1, 20));
                        mTextPane.getStyledDocument().insertString(mTextPane.getText().length(), "\n" + commandStringReturn, attrsCommandStringReturn);
                    } else {
                        if (commandStringReturn.startsWith("Success")) {
                            StyleContext scCommandStringReturn = StyleContext.getDefaultStyleContext();
                            AttributeSet attrsCommandStringReturn = scCommandStringReturn.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(7, 159, 65));
                            mTextPane.getStyledDocument().insertString(mTextPane.getText().length(), "\n" + commandStringReturn, attrsCommandStringReturn);
                        } else {
                            if (commandText.contains("-POO")) {
                                if (commandText.startsWith("echo")) {
                                    JOptionPane.showMessageDialog(null, commandStringReturn);
                                }

                                if (commandText.startsWith("ls") && (commandText.contains("-a") || commandText.contains("-ar"))) {
                                    // Continutul trebuie pus in JTable
                                    final String[] tableHeader = {"Tip", "Nume", "Stapan", "Creat la data",
                                                            "Dimensiune", "Permisiuni"};
                                    
                                    int lines = 0;
                                    for(int i = 0; i < commandStringReturn.length(); i++)
                                        if(commandStringReturn.charAt(i) == '\n')
                                            lines++;
                                    lines ++;
                                    
                                    Object[][] data = new Object[lines][tableHeader.length];
                                    
                                    StringTokenizer stLine = new StringTokenizer(commandStringReturn, "\n");
                                    for(int i = 0; i < lines; i++) {
                                        String line = stLine.nextToken();
                                        StringTokenizer stColumn = new StringTokenizer(line);
                                        int col = 0;
                                        String type = stColumn.nextToken();
                                        if(type.compareTo("[D]") == 0)
                                            type = "Director";
                                        else
                                            type = "Fisier";
                                        
                                        String fdName = stColumn.nextToken();
                                        String ownerName = stColumn.nextToken();
                                        String timeCreated = stColumn.nextToken();
                                        timeCreated = timeCreated.substring(1);
                                        timeCreated = timeCreated + " " + stColumn.nextToken();
                                        timeCreated = timeCreated.substring(0, timeCreated.length() - 1);
                                        String dimension = stColumn.nextToken();
                                        String permNr = stColumn.nextToken();
                                        
                                        data[i][col++] = type;
                                        data[i][col++] = fdName;
                                        data[i][col++] = ownerName;
                                        data[i][col++] = timeCreated;
                                        data[i][col++] = dimension;
                                        data[i][col++] = permNr;
                                    }
                                    
                                    JXTable tblX_ls = new JXTable(data, tableHeader);
                                    tblX_ls.packAll();
                                    tblX_ls.getColumn(0).setPreferredWidth(40);
                                    tblX_ls.getColumn(tableHeader.length - 1).setPreferredWidth(40);
                                    tblX_ls.setFont(new Font("sansserif", Font.PLAIN, 17));
                                    tblX_ls.setOpaque(false);
                                    tblX_ls.setBackground(new Color(0, 0, 0, 0));
                                    tblX_ls.setForeground(Color.white);
                                    tblX_ls.setEditable(false);
                                    
                                    JScrollPane scroll_ls = new JScrollPane(tblX_ls);
                                    int h = tblX_ls.getPreferredSize().height;
                                    scroll_ls.setPreferredSize(new Dimension(0, h + 30));
                                    scroll_ls.setOpaque(false);
                                    scroll_ls.getViewport().setOpaque(false);
                                    scroll_ls.setBackground(new Color(0, 0, 0, 0));
                                    scroll_ls.setBorder(new EmptyBorder(0, 0, 0, 0));
                                    
                                    heightToFill -= mTextPane.getPreferredSize().height;
                                    heightToFill -= scroll_ls.getPreferredSize().height;
                                    
                                    if (heightToFill > 0) {
                                        panel_scrollable_inside.remove(mRigidArea);
                                        mRigidArea = Box.createRigidArea(new Dimension(0, heightToFill));
                                        panel_scrollable_inside.add(mTextPane);
                                        panel_scrollable_inside.add(scroll_ls);
                                        panel_scrollable_inside.add(mRigidArea);
                                    } else {
                                        panel_scrollable_inside.remove(mRigidArea);
                                        mRigidArea = Box.createRigidArea(new Dimension(0, 15));
                                        panel_scrollable_inside.add(mTextPane);
                                        panel_scrollable_inside.add(scroll_ls);
                                        panel_scrollable_inside.add(mRigidArea);
                                    }
                                }

                                if (commandText.startsWith("userinfo")) {
                                    // Continutul trebuie pus in JList
                                    DefaultListModel dlm = new DefaultListModel();
                                    StringTokenizer st = new StringTokenizer(commandStringReturn, "--");
                                    String userName = "Username: " + st.nextToken();
                                    st.nextToken();
                                    String lastName = "Nume: " + st.nextToken();
                                    String firstName = "Prenume: " + st.nextToken();
                                    String timeCreated = "Creat: " + st.nextToken();
                                    String timeLastLogin = "Ultima logare: " + st.nextToken();
                                    dlm.addElement(userName);
                                    dlm.addElement(lastName);
                                    dlm.addElement(firstName);
                                    dlm.addElement(timeCreated);
                                    dlm.addElement(timeLastLogin);
                                    
                                    JList lst_userinfo = new JList(dlm);
                                    lst_userinfo.setFont(new Font("sansserif", Font.PLAIN, 18));
                                    JScrollPane scroll_userinfo = new JScrollPane(lst_userinfo);
                                    scroll_userinfo.setPreferredSize(new Dimension(0, 142));

                                    scroll_userinfo.setOpaque(false);
                                    scroll_userinfo.getViewport().setOpaque(false);
                                    scroll_userinfo.setBackground(new Color(0, 0, 0, 0));
                                    scroll_userinfo.setBorder(new EmptyBorder(0, 0, 0, 0));
                                    
                                    lst_userinfo.setOpaque(false);
                                    lst_userinfo.setBackground(new Color(0, 0, 0, 0));
                                    lst_userinfo.setForeground(Color.white);
                                    
                                    heightToFill -= mTextPane.getPreferredSize().height;
                                    heightToFill -= scroll_userinfo.getPreferredSize().height;
                                    
                                    if (heightToFill > 0) {
                                        panel_scrollable_inside.remove(mRigidArea);
                                        mRigidArea = Box.createRigidArea(new Dimension(0, heightToFill));
                                        panel_scrollable_inside.add(mTextPane);
                                        panel_scrollable_inside.add(scroll_userinfo);
                                        panel_scrollable_inside.add(mRigidArea);
                                    } else {
                                        panel_scrollable_inside.remove(mRigidArea);
                                        mRigidArea = Box.createRigidArea(new Dimension(0, 15));
                                        panel_scrollable_inside.add(mTextPane);
                                        panel_scrollable_inside.add(scroll_userinfo);
                                        panel_scrollable_inside.add(mRigidArea);
                                    }
                                }
                            }
                            else {
                                mTextPane.getStyledDocument().insertString(mTextPane.getText().length(), "\n" + commandStringReturn, null);
                                if (txt_input_terminal.getText().startsWith("login")) {
                                    frame_terminal.setTitle(mUserManagement.id().getUsername() + "PC");
                                }
                            }
                        }
                    }
                    
                    if (!commandText.contains("-POO") || (commandText.contains("-POO") && commandStringReturn.startsWith("Invalid"))) {
                        heightToFill -= mTextPane.getPreferredSize().height;
                        if (heightToFill > 0) {
                            panel_scrollable_inside.remove(mRigidArea);
                            mRigidArea = Box.createRigidArea(new Dimension(0, heightToFill));
                            panel_scrollable_inside.add(mTextPane);
                            panel_scrollable_inside.add(mRigidArea);
                        } else {
                            panel_scrollable_inside.remove(mRigidArea);
                            mRigidArea = Box.createRigidArea(new Dimension(0, 15));
                            panel_scrollable_inside.add(mTextPane);
                            panel_scrollable_inside.add(mRigidArea);
                        }
                    }

                    panel_scrollable_inside.revalidate();
                    panel_scrollable_inside.repaint();

                    scrollToBottom(scroll);

                } catch (BadLocationException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                txt_input_terminal.setText("");
            }
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            //java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
        
        MainFrame mainFrame = MainFrame.getInstance();
    }
    
    private void updateTime() {
        while (true) {
            mCalendar = Calendar.getInstance();
            lbl_clock_text.setText(mSimpleDateFormat.format(mCalendar.getTime()));
        }
    }
    
    private void runInBackgroundClock() {
        Runnable r = new Runnable() {
            public void run() {
                updateTime();
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(r);
    }
    
    private void playMusic() {
        try {
            musicPath = (String) lst_music_playlist.getSelectedValue();
            fis = new FileInputStream(musicPath);
            songTotalLength = fis.available();
            bis = new BufferedInputStream(fis);
            
            if(isPlayingMusic == 1)
                mPlayer.close();
            
            isPlayingMusic = 1;
            mPlayerStatus = PAUSE;
            btn_music_play.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\icon_pause.png")).getImage()).getScaledInstance(btn_music_play.getWidth(), btn_music_play.getHeight(), java.awt.Image.SCALE_SMOOTH)));
            
            mPlayer = new Player(bis);
            mPlayer.play();
            
        } catch (FileNotFoundException | JavaLayerException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void runInBackgroundPlayMusic() {
        Runnable r = new Runnable() {
            public void run() {
                playMusic();
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(r);
    }
    
    private void stopMusic() {
        if (mPlayer != null) {
            isPlayingMusic = 0;
            mPlayer.close();
            mPlayerStatus = PLAY;
            btn_music_play.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\icon_play.png")).getImage()).getScaledInstance(btn_music_play.getWidth(), btn_music_play.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        }
    }
    
    private void runInBackgroundStopMusic() {
        Runnable r = new Runnable() {
            public void run() {
                stopMusic();
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(r);
    }
    
    private void pauseMusic() {
        if(mPlayer != null) {
            try {
                pauseLocation = fis.available();
                mPlayer.close();
                mPlayerStatus = RESUME;
                btn_music_play.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\icon_play.png")).getImage()).getScaledInstance(btn_music_play.getWidth(), btn_music_play.getHeight(), java.awt.Image.SCALE_SMOOTH)));
                
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void runInBackgroundPauseMusic() {
        Runnable r = new Runnable() {
            public void run() {
                pauseMusic();
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(r);
    }
    
    private void resumeMusic() {
        try {
            mPlayerStatus = PAUSE;
            btn_music_play.setIcon(new ImageIcon(((new ImageIcon("src\\Images\\icon_pause.png")).getImage()).getScaledInstance(btn_music_play.getWidth(), btn_music_play.getHeight(), java.awt.Image.SCALE_SMOOTH)));
            fis = new FileInputStream(musicPath);
            fis.skip(songTotalLength - pauseLocation - 60);
            bis = new BufferedInputStream(fis);
            
            mPlayer = new Player(bis);
            mPlayer.play();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavaLayerException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void runInBackgroundResumeMusic() {
        Runnable r = new Runnable() {
            public void run() {
                resumeMusic();
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(r);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        char key = ke.getKeyChar();
        int k = key;

        if (k == KeyEvent.VK_BACK_SPACE || k == KeyEvent.VK_SPACE || k == KeyEvent.VK_SLASH) {
            trigger = 0;
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
}
