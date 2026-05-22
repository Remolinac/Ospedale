/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.view;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import packagee.controller.AppointmentController;
import packagee.controller.DoctorController;
import packagee.controller.HospitalizationController;
import packagee.model.storage.StorageHospital;
import packagee.util.Observer;
import packagee.util.Response;

/**
 * @author jjlora
 * @author edangulo
 */
public class DoctorView extends javax.swing.JFrame implements Observer {

    private int x, y;
    private HashMap<String, Object> userData;
    private boolean isAdmin;

    public DoctorView(HashMap<String, Object> userData) {
        initComponents();
        this.userData = userData;
        this.isAdmin = false;
        loadUserData();
        loadComboBoxes();
        loadAppointmentsTable(false);
        btnBack.setVisible(false);
        this.setLocationRelativeTo(null);
        StorageHospital.getInstance().addObserver(this);
    }

    public DoctorView(HashMap<String, Object> userData, boolean isAdmin) {
        initComponents();
        this.userData = userData;
        this.isAdmin = isAdmin;
        loadUserData();
        loadComboBoxes();
        loadAppointmentsTable(false);
        btnBack.setVisible(isAdmin);
        this.setLocationRelativeTo(null);
    }


    private void loadUserData() {
        jTextField1.setText((String) userData.get("firstname"));
        jTextField2.setText((String) userData.get("lastname"));
        jTextField7.setText((String) userData.get("username"));
        jTextField6.setText((String) userData.get("licenceNumber"));
        jTextField8.setText((String) userData.get("assignedOffice"));

        Object spec = userData.get("specialty");
        if (spec != null) {
            String specStr = spec.toString();
            for (int i = 0; i < jComboBox1.getItemCount(); i++) {
                if (jComboBox1.getItemAt(i).equals(specStr)) {
                    jComboBox1.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    @Override
    public void update(String event) {
        switch (event) {
            case "APPOINTMENT_ADDED":
            case "APPOINTMENT_UPDATED":
                loadAppointmentsTable(false);
                loadComboBoxes();
                break;
            case "HOSPITALIZATION_ADDED":
                loadComboBoxes();
                break;
            case "PATIENT_ADDED":
                loadComboBoxes();
                break;
        }
    }

    private void loadComboBoxes() {
        String doctorId = String.valueOf(userData.get("id"));
        AppointmentController ac = new AppointmentController();

        // Citas del doctor para aceptar (REQUESTED), reagendar y completar
        Response resp = ac.getDoctorAppointments(doctorId, false);
        jComboBox2.removeAllItems();
        jComboBox2.addItem("Select one");
        jComboBox3.removeAllItems();
        jComboBox3.addItem("Select one");
        jComboBox4.removeAllItems();
        jComboBox4.addItem("Select one");
        jComboBox7.removeAllItems();
        jComboBox7.addItem("Select one");

        if (resp.isSuccess()) {
            ArrayList<HashMap<String, Object>> apts
                    = (ArrayList<HashMap<String, Object>>) resp.getData();
            for (HashMap<String, Object> ap : apts) {
                String apId = (String) ap.get("id");
                jComboBox2.addItem(apId);
                jComboBox3.addItem(apId);
                jComboBox4.addItem(apId);
                jComboBox7.addItem(apId);
            }
        }

        // Hospitalizaciones para cancelar/aprobar
        HospitalizationController hc = new HospitalizationController();
        Response hResp = hc.getHospitalizations(doctorId);
        jComboBox6.removeAllItems();
        jComboBox6.addItem("Select one");
        if (hResp.isSuccess()) {
            ArrayList<HashMap<String, Object>> hosps
                    = (ArrayList<HashMap<String, Object>>) hResp.getData();
            for (HashMap<String, Object> h : hosps) {
                jComboBox6.addItem((String) h.get("id"));
            }
        }

  
        jComboBox5.removeAllItems();
        jComboBox5.addItem("Select one");
        for (packagee.model.Patient p : StorageHospital.getInstance().getAllPatients().values()) {
            jComboBox5.addItem(p.getId() + " - " + p.getFirstname() + " " + p.getLastname());
        }

       
        jComboBox8.removeAllItems();
        jComboBox8.addItem("Select one");
        for (packagee.model.Patient p : StorageHospital.getInstance().getAllPatients().values()) {
            jComboBox8.addItem(String.valueOf(p.getId()));
        }
    }

    private void loadAppointmentsTable(boolean pendingOnly) {
        AppointmentController ac = new AppointmentController();
        String doctorId = String.valueOf(userData.get("id"));
        Response resp = ac.getDoctorAppointments(doctorId, pendingOnly);
        DefaultTableModel model = (DefaultTableModel) tblDoctorView.getModel();
        model.setRowCount(0);
        if (resp.isSuccess()) {
            StorageHospital storage = StorageHospital.getInstance();
            ArrayList<HashMap<String, Object>> apts
                    = (ArrayList<HashMap<String, Object>>) resp.getData();
            for (HashMap<String, Object> ap : apts) {
                Object patIdObj = ap.get("patientId");
                String patientName = "";
                if (patIdObj != null) {
                    packagee.model.Patient pat = storage.getPatient(((Number) patIdObj).longValue());
                    if (pat != null) {
                        patientName = pat.getFirstname() + " " + pat.getLastname();
                    }
                }
                model.addRow(new Object[]{
                    ap.get("id"),
                    ap.get("datetime"),
                    patientName,
                    ap.get("specialty"),
                    Boolean.TRUE.equals(ap.get("type")) ? "In-person" : "Remote",
                    ap.get("status")
                });
            }
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        panelRound1 = new packagee.view.PanelRound();
        panelRound2 = new packagee.view.PanelRound();
        btnX = new javax.swing.JButton();
        lblDoctorView = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        tabbedpaneDoctorView = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        radiobtnTotalAppointments = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDoctorView = new javax.swing.JTable();
        radiobutonPendingAppointments = new javax.swing.JRadioButton();
        tblLogout = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton9 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jButton3 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton6 = new javax.swing.JButton();
        jComboBox6 = new javax.swing.JComboBox<>();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea7 = new javax.swing.JTextArea();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea8 = new javax.swing.JTextArea();
        jSeparator4 = new javax.swing.JSeparator();
        jButton13 = new javax.swing.JButton();
        jComboBox8 = new javax.swing.JComboBox<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextArea9 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jTextField24 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jTextField27 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jTextField28 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jTextField29 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jComboBox7 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelRound1.setRadius(50);
        panelRound2.setRadius(50);
        panelRound2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelRound2MouseDragged(evt);
            }
        });
        panelRound2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelRound2MousePressed(evt);
            }
        });

        btnX.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnX.setText("X");
        btnX.setBorderPainted(false);
        btnX.setContentAreaFilled(false);
        btnX.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnX.setFocusable(false);
        btnX.setRequestFocusEnabled(false);
        btnX.addActionListener(evt -> btnXActionPerformed(evt));

        lblDoctorView.setFont(new java.awt.Font("Yu Gothic UI", 0, 14));
        lblDoctorView.setText("DOCTOR VIEW");

        btnBack.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnBack.setText("Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
                panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                                .addContainerGap().addComponent(lblDoctorView).addGap(32, 32, 32)
                                .addComponent(btnBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnX).addGap(10, 10, 10))
        );
        panelRound2Layout.setVerticalGroup(
                panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblDoctorView, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnBack))
        );

        // ── jPanel4: Appointments visualization ──
        radiobtnTotalAppointments.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        radiobtnTotalAppointments.setText("Total appointments");
        radiobtnTotalAppointments.addActionListener(evt -> radiobtnTotalAppointmentsActionPerformed(evt));

        tblDoctorView.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{{null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}},
                new String[]{"ID", "Date", "Patient", "Specialty", "Type", "Status"}
        ));
        jScrollPane3.setViewportView(tblDoctorView);

        radiobutonPendingAppointments.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        radiobutonPendingAppointments.setText("Pending appointments");
        radiobutonPendingAppointments.addActionListener(evt -> radiobutonPendingAppointmentsActionPerformed(evt));

        tblLogout.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        tblLogout.setText("Logout");
        tblLogout.addActionListener(evt -> tblLogoutActionPerformed(evt));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(tblLogout)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel4Layout.createSequentialGroup().addGap(16, 16, 16).addComponent(radiobtnTotalAppointments).addGap(18, 18, 18).addComponent(radiobutonPendingAppointments))
                                                .addGroup(jPanel4Layout.createSequentialGroup().addGap(108, 108, 108).addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1035, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(227, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(radiobtnTotalAppointments).addComponent(radiobutonPendingAppointments))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(tblLogout).addGap(23, 23, 23))
        );
        tabbedpaneDoctorView.addTab("Appointments visualization", jPanel4);

        // ── jPanel5: History appointments of a patient ──
        jComboBox5.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));
        jLabel38.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel38.setText("Patient");
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{{null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}},
                new String[]{"ID", "Date", "Doctor", "Specialty", "Type", "Status"}
        ) {
            boolean[] canEdit = {false, false, false, false, false, false};

            public boolean isCellEditable(int r, int c) {
                return canEdit[c];
            }
        });
        jScrollPane4.setViewportView(jTable3);
        jButton8.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jButton8.setText("Search");
        jButton8.addActionListener(evt -> jButton8ActionPerformed(evt));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup().addGap(37, 37, 37).addComponent(jLabel38).addGap(18, 18, 18).addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createSequentialGroup().addGap(63, 63, 63).addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(174, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(jButton8).addGap(601, 601, 601))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel38).addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18).addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44).addComponent(jButton8).addContainerGap(67, Short.MAX_VALUE))
        );
        tabbedpaneDoctorView.addTab("History Appointments of a patient", jPanel5);

        // ── jPanel3: Modify info ──
        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel2.setText("Firstname");
        jTextField1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel3.setText("Lastname");
        jTextField2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel5.setText("Specialty");
        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel7.setText("License Number");
        jTextField6.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel8.setText("Assigned office");
        jTextField7.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("User");
        jTextField8.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jTextField9.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Password");
        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel11.setText("Password confirmation");
        jTextField10.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jComboBox1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one", "GENERAL_MEDICINE", "CARDIOLOGY", "PEDIATRICS", "NEUROLOGY", "TRAUMATOLOGY_ORTHOPEDICS", "GYNECOLOGY_OBSTETRICS", "DERMATOLOGY", "PSYCHIATRY", "ONCOLOGY", "OPHTHALMOLOGY", "INTERNAL_MEDICINE"}));
        jButton9.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jButton9.setText("Save");
        jButton9.addActionListener(evt -> jButton9ActionPerformed(evt));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup().addGap(211, 211, 211).addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel3).addGap(18, 18, 18).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel5).addGap(18, 18, 18).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createSequentialGroup().addGap(351, 351, 351).addComponent(jLabel7).addGap(18, 18, 18).addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel8).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createSequentialGroup().addGap(558, 558, 558).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE).addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGroup(jPanel3Layout.createSequentialGroup().addGap(521, 521, 521).addComponent(jLabel11))
                                        .addGroup(jPanel3Layout.createSequentialGroup().addGap(576, 576, 576).addComponent(jButton9))
                                        .addGroup(jPanel3Layout.createSequentialGroup().addGap(561, 561, 561).addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(369, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel3).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel5).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel7).addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel8))
                                .addGap(30, 30, 30).addComponent(jLabel9).addGap(18, 18, 18).addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18).addComponent(jLabel10).addGap(27, 27, 27).addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18).addComponent(jLabel11).addGap(18, 18, 18).addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32).addComponent(jButton9).addContainerGap(161, Short.MAX_VALUE))
        );
        tabbedpaneDoctorView.addTab("Modify info", jPanel3);

        // ── jPanel1: Request/Appointments ──
        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel13.setText("Accept medical appointment");
        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Appointment ID");
        jComboBox2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jButton3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jButton3.setText("Accept");
        jButton3.addActionListener(evt -> jButton3ActionPerformed(evt));
        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Reschedule medical appointment");
        jLabel16.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Appointment");
        jComboBox3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));
        jButton4.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jButton4.setText("Accept");
        jButton4.addActionListener(evt -> jButton4ActionPerformed(evt));
        jLabel17.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("New time appointment");
        jTextField13.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel18.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Reason for appointment");
        jTextField14.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jLabel19.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Complete medical appointment");
        jLabel20.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Appointment");
        jComboBox4.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));
        jLabel21.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Diagnosis");
        jLabel22.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Observations");
        jLabel23.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Recommended treatment");
        jLabel24.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Follow-up indication");
        jButton5.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jButton5.setText("Complete");
        jButton5.addActionListener(evt -> jButton5ActionPerformed(evt));
        jLabel25.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Hospitalization");
        jLabel27.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Reason for hospitalization");
        jLabel28.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Date of entry");
        jTextField21.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel29.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Estimated duration");
        jTextField22.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel30.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Observations");
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);
        jButton6.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jButton6.setText("Generate");
        jButton6.addActionListener(evt -> jButton6ActionPerformed(evt));
        jComboBox6.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));
        jRadioButton5.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jRadioButton5.setText("Requests");
        jRadioButton6.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jRadioButton6.setText("Patient ID");
        jTextArea5.setColumns(20);
        jTextArea5.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jTextArea5.setRows(5);
        jScrollPane6.setViewportView(jTextArea5);
        jTextArea6.setColumns(20);
        jTextArea6.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jTextArea6.setRows(5);
        jScrollPane7.setViewportView(jTextArea6);
        jTextArea7.setColumns(20);
        jTextArea7.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jTextArea7.setRows(5);
        jScrollPane8.setViewportView(jTextArea7);
        jTextArea8.setColumns(20);
        jTextArea8.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jTextArea8.setRows(5);
        jScrollPane9.setViewportView(jTextArea8);
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jButton13.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jButton13.setText("Cancel");
        jButton13.addActionListener(evt -> jButton13ActionPerformed(evt));
        jComboBox8.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));
        jTextArea9.setColumns(20);
        jTextArea9.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jTextArea9.setRows(5);
        jScrollPane10.setViewportView(jTextArea9);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGap(26, 26, 26)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(jButton3).addGap(87, 87, 87))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(67, 67, 67))))
                                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(1, 1, 1))
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGap(26, 26, 26).addComponent(jLabel13).addGap(22, 22, 22)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(jPanel1Layout.createSequentialGroup().addGap(90, 90, 90).addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jPanel1Layout.createSequentialGroup().addGap(99, 99, 99).addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jPanel1Layout.createSequentialGroup().addGap(98, 98, 98).addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jPanel1Layout.createSequentialGroup().addGap(112, 112, 112).addComponent(jButton4)))
                                                        .addGap(91, 91, 91))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGap(112, 112, 112).addComponent(jButton5).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))).addGroup(jPanel1Layout.createSequentialGroup().addGap(99, 99, 99).addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(0, 66, Short.MAX_VALUE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(jPanel1Layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)).addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(42, 42, 42).addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createSequentialGroup().addGap(41, 41, 41).addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(42, 42, 42).addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createSequentialGroup().addGap(43, 43, 43).addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(0, 0, Short.MAX_VALUE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(7, 7, 7).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))).addGroup(jPanel1Layout.createSequentialGroup().addGap(121, 121, 121).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(0, 0, Short.MAX_VALUE))).addContainerGap())
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGap(45, 45, 45).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jButton13).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jButton6)).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(87, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(18, 18, 18).addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createSequentialGroup().addGap(37, 37, 37).addComponent(jRadioButton5))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(jRadioButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(19, 19, 19)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(29, 29, 29))))
                                        .addGroup(jPanel1Layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(47, 47, 47))))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1)
                        .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator2)
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGap(20, 20, 20).addComponent(jLabel19).addGap(10, 10, 10).addComponent(jLabel20).addGap(18, 18, 18).addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel21).addGap(18, 18, 18).addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel22).addGap(18, 18, 18).addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel23).addGap(18, 18, 18).addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel24).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton5).addGap(12, 12, 12))
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(17, 17, 17).addComponent(jLabel13).addGap(18, 18, 18).addComponent(jLabel14).addGap(18, 18, 18).addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(31, 31, 31).addComponent(jButton3)).addGroup(jPanel1Layout.createSequentialGroup().addGap(19, 19, 19).addComponent(jLabel15).addGap(18, 18, 18).addComponent(jLabel16).addGap(18, 18, 18).addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel17).addGap(18, 18, 18).addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel18).addGap(18, 18, 18).addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(30, 30, 30).addComponent(jButton4))).addGap(18, 18, Short.MAX_VALUE))))
                        .addGroup(jPanel1Layout.createSequentialGroup().addGap(26, 26, 26).addComponent(jLabel25).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jRadioButton5).addComponent(jRadioButton6)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18).addComponent(jLabel27).addGap(16, 16, 16).addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel28).addGap(18, 18, 18).addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel29).addGap(18, 18, 18).addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel30).addGap(18, 18, 18).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton6).addComponent(jButton13)).addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        tabbedpaneDoctorView.addTab("Request/Appointments", jPanel1);

        // ── jPanel2: Prescribe medications ──
        jLabel31.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel31.setText("Appointment ID");
        jLabel32.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel32.setText("Medication name");
        jTextField24.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel33.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel33.setText("Dose");
        jTextField25.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel34.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel34.setText("Administration route");
        jTextField26.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel35.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel35.setText("Frecuency");
        jTextField27.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel36.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel36.setText("Treatment duration");
        jTextField28.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel37.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel37.setText("Additional instructions");
        jTextField29.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{{null, null, null, null, null, null, null}, {null, null, null, null, null, null, null}, {null, null, null, null, null, null, null}, {null, null, null, null, null, null, null}}, new String[]{"Appointment ID", "Medication name", "Dose", "Administration route", "Treatment duration", "Additional instructions", "Frecuency"}) {
            Class[] types = {java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class};
            boolean[] canEdit = {false, false, false, false, false, false, false};

            public Class getColumnClass(int c) {
                return types[c];
            }

            public boolean isCellEditable(int r, int c) {
                return canEdit[c];
            }
        });
        jScrollPane2.setViewportView(jTable1);
        jButton7.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jButton7.setText("Add");
        jButton7.addActionListener(evt -> jButton7ActionPerformed(evt));
        jButton10.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jButton10.setText("Prescribe");
        jButton10.addActionListener(evt -> jButton10ActionPerformed(evt));
        jComboBox7.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup().addGap(62, 62, 62).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1125, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel31).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(9, 9, 9).addComponent(jLabel32)).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel36).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel37).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel35).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel33).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel34).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jButton7))))
                                        .addGroup(jPanel2Layout.createSequentialGroup().addGap(583, 583, 583).addComponent(jButton10)))
                                .addContainerGap(183, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup().addGap(57, 57, 57)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel31).addComponent(jLabel32).addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel33).addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel34).addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButton7).addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel36).addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel37).addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel35).addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30).addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(47, 47, 47).addComponent(jButton10).addContainerGap(64, Short.MAX_VALUE))
        );
        tabbedpaneDoctorView.addTab("Prescribe medications", jPanel2);

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
                panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelRound1Layout.createSequentialGroup().addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(panelRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(tabbedpaneDoctorView)).addGap(0, 0, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
                panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelRound1Layout.createSequentialGroup().addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(tabbedpaneDoctorView))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }// </editor-fold>

    // ── Handlers de eventos ────────────────────────────────────────────────
    private void panelRound2MousePressed(java.awt.event.MouseEvent evt) {
        x = evt.getX();
        y = evt.getY();
    }

    private void panelRound2MouseDragged(java.awt.event.MouseEvent evt) {
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }

    private void btnXActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void radiobtnTotalAppointmentsActionPerformed(java.awt.event.ActionEvent evt) {
        radiobutonPendingAppointments.setSelected(false);
        loadAppointmentsTable(false);
    }

    private void radiobutonPendingAppointmentsActionPerformed(java.awt.event.ActionEvent evt) {
        radiobtnTotalAppointments.setSelected(false);
        loadAppointmentsTable(true);
    }

    private void tblLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        new LoginView().setVisible(true);
        this.dispose();
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        new AdminView(userData).setVisible(true);
        StorageHospital.getInstance().removeObserver(this);
        this.dispose();
    }

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {
        // Actualizar doctor
        DoctorController controller = new DoctorController();
        String selectedSpec = (String) jComboBox1.getSelectedItem();
        if (selectedSpec == null || selectedSpec.equals("Select one")) {
            JOptionPane.showMessageDialog(this, "Selecciona una especialidad", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Response response = controller.updateDoctor(
                String.valueOf(userData.get("id")),
                jTextField7.getText(), // username
                jTextField1.getText(), // firstname
                jTextField2.getText(), // lastname
                jTextField9.getText(), // password
                jTextField10.getText(), // confirm
                selectedSpec,
                jTextField6.getText(), // licenseNumber
                jTextField8.getText() // assignedOffice
        );
        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            userData = (HashMap<String, Object>) response.getData();
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        AppointmentController controller = new AppointmentController();
        String appointmentId = (String) jComboBox2.getSelectedItem();
        if (appointmentId == null || appointmentId.equals("Select one")) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Response response = controller.acceptAppointment(appointmentId, String.valueOf(userData.get("id")));
        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            loadAppointmentsTable(false);
            loadComboBoxes();
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        // Reagendar cita
        AppointmentController controller = new AppointmentController();
        String appointmentId = (String) jComboBox3.getSelectedItem();
        if (appointmentId == null || appointmentId.equals("Select one")) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Response response = controller.rescheduleAppointment(
                appointmentId, String.valueOf(userData.get("id")),
                jTextField13.getText(), jTextField14.getText()
        );
        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            loadAppointmentsTable(false);
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        // Completar cita
        AppointmentController controller = new AppointmentController();
        String appointmentId = (String) jComboBox4.getSelectedItem();
        if (appointmentId == null || appointmentId.equals("Select one")) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Response response = controller.completeAppointment(appointmentId, String.valueOf(userData.get("id")));
        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            loadAppointmentsTable(false);
            loadComboBoxes();
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
        // Hospitalizar desde cita (jRadioButton6) o aprobar hospitalización (jRadioButton5)
        HospitalizationController controller = new HospitalizationController();
        String doctorId = String.valueOf(userData.get("id"));
        if (jRadioButton6.isSelected()) {
            String selectedPatient = (String) jComboBox8.getSelectedItem();
            if (selectedPatient == null || selectedPatient.equals("Select one")) {
                JOptionPane.showMessageDialog(this, "Selecciona un paciente", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Buscar una cita PENDING del paciente para hospitalizar desde ella
            AppointmentController ac = new AppointmentController();
            Response aptsResp = ac.getPatientAppointments(selectedPatient);
            String appointmentId = null;
            if (aptsResp.isSuccess()) {
                ArrayList<HashMap<String, Object>> apts = (ArrayList<HashMap<String, Object>>) aptsResp.getData();
                for (HashMap<String, Object> ap : apts) {
                    if ("PENDING".equals(String.valueOf(ap.get("status")))) {
                        appointmentId = (String) ap.get("id");
                        break;
                    }
                }
            }
            if (appointmentId == null) {
                JOptionPane.showMessageDialog(this, "El paciente no tiene citas PENDING", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Response response = controller.hospitalizeFromAppointment(
                    appointmentId, doctorId, jTextField21.getText(),
                    jTextArea9.getText(), "STANDARD", jTextArea1.getText()
            );
            if (response.isSuccess()) {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                loadComboBoxes();
            } else {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (jRadioButton5.isSelected()) {
            String hospId = (String) jComboBox6.getSelectedItem();
            if (hospId == null || hospId.equals("Select one")) {
                JOptionPane.showMessageDialog(this, "Selecciona una hospitalización", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Response response = controller.approveHospitalization(hospId, doctorId);
            if (response.isSuccess()) {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                loadComboBoxes();
            } else {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona Requests o Patient ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {
        // Denegar hospitalización
        HospitalizationController controller = new HospitalizationController();
        String hospId = (String) jComboBox6.getSelectedItem();
        if (hospId == null || hospId.equals("Select one")) {
            JOptionPane.showMessageDialog(this, "Selecciona una hospitalización", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Response response = controller.denyHospitalization(hospId, String.valueOf(userData.get("id")));
        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            loadComboBoxes();
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedPatient = (String) jComboBox5.getSelectedItem();
        if (selectedPatient == null || selectedPatient.equals("Select one")) {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String patientId = selectedPatient.split(" - ")[0];
        AppointmentController controller = new AppointmentController();
        Response response = controller.getPatientAppointments(patientId);
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0);
        if (response.isSuccess()) {
            StorageHospital storage = StorageHospital.getInstance();
            ArrayList<HashMap<String, Object>> apts
                    = (ArrayList<HashMap<String, Object>>) response.getData();
            for (HashMap<String, Object> ap : apts) {
                Object docIdObj = ap.get("doctorId");
                String doctorName = "";
                if (docIdObj != null) {
                    packagee.model.Doctor doc = storage.getDoctor(((Number) docIdObj).longValue());
                    if (doc != null) {
                        doctorName = doc.getFirstname() + " " + doc.getLastname();
                    }
                }
                model.addRow(new Object[]{
                    ap.get("id"), ap.get("datetime"), doctorName,
                    ap.get("specialty"),
                    Boolean.TRUE.equals(ap.get("type")) ? "In-person" : "Remote",
                    ap.get("status")
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        // Agregar medicamento a la tabla temporal
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        String appointmentId = (String) jComboBox7.getSelectedItem();
        String medicationName = jTextField24.getText();
        String dose = jTextField25.getText();
        String adminRoute = jTextField26.getText();
        String duration = jTextField28.getText();
        String instructions = jTextField29.getText();
        String frequency = jTextField27.getText();
        if (appointmentId == null || appointmentId.equals("Select one") || medicationName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa los campos requeridos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        model.addRow(new Object[]{appointmentId, medicationName, dose, adminRoute, duration, instructions, frequency});
    }

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {
        // Prescribir el medicamento seleccionado en la tabla
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un medicamento de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String appointmentId = (String) model.getValueAt(selectedRow, 0);
        String medicationName = (String) model.getValueAt(selectedRow, 1);
        double dose = Double.parseDouble((String) model.getValueAt(selectedRow, 2));
        String adminRoute = (String) model.getValueAt(selectedRow, 3);
        int duration = Integer.parseInt((String) model.getValueAt(selectedRow, 4));
        String instructions = (String) model.getValueAt(selectedRow, 5);
        int frequency = Integer.parseInt((String) model.getValueAt(selectedRow, 6));

        AppointmentController controller = new AppointmentController();
        Response response = controller.prescribeMedication(
                appointmentId, String.valueOf(userData.get("id")),
                medicationName, dose, adminRoute, duration, instructions, frequency
        );
        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            model.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnX;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextArea jTextArea7;
    private javax.swing.JTextArea jTextArea8;
    private javax.swing.JTextArea jTextArea9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JLabel lblDoctorView;
    private packagee.view.PanelRound panelRound1;
    private packagee.view.PanelRound panelRound2;
    private javax.swing.JRadioButton radiobtnTotalAppointments;
    private javax.swing.JRadioButton radiobutonPendingAppointments;
    private javax.swing.JTabbedPane tabbedpaneDoctorView;
    private javax.swing.JTable tblDoctorView;
    private javax.swing.JButton tblLogout;
    // End of variables declaration
}
