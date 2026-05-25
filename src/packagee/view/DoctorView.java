/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.view;

import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import packagee.controller.AppointmentController;
import packagee.controller.DataController;
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

    private java.util.HashMap<String, Object> userData;
    private int x, y;
    private long doctorId;
    private boolean isAdmin;

    private DoctorController doctorController;
    private AppointmentController appointmentController;
    private HospitalizationController hospitalizationController;
    private DataController dataController;

    public DoctorView(HashMap<String, Object> data, long doctorId, DoctorController dc, AppointmentController ac,
            HospitalizationController hc, DataController datac, boolean isAdmin) {
        this.userData = data;
        this.doctorId = doctorId;
        this.doctorController = dc;
        this.appointmentController = ac;
        this.hospitalizationController = hc;
        this.dataController = datac;
        this.isAdmin = isAdmin;
        initComponents();
        btnBack.setVisible(isAdmin);
        this.setLocationRelativeTo(null);
        StorageHospital.getInstance().addObserver(this);
        txtFirstName.setText((String) userData.get("firstname"));
        txtLastName.setText((String) userData.get("lastname"));
        loadComboBoxes();
        loadAppointmentsTable(false);
    }

    private void loadUserData() {
        txtFirstName.setText((String) userData.get("firstname"));
        txtLastName.setText((String) userData.get("lastname"));
        txtUsername.setText((String) userData.get("username"));
        txtLicenceNumber.setText((String) userData.get("licenceNumber"));
        txtAssignedOffice.setText((String) userData.get("assignedOffice"));

        Object spec = userData.get("specialty");
        if (spec != null) {
            String specStr = spec.toString();
            for (int i = 0; i < cmbSpecialty.getItemCount(); i++) {
                if (cmbSpecialty.getItemAt(i).equals(specStr)) {
                    cmbSpecialty.setSelectedIndex(i);
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
        try {
            String doctorIdStr = String.valueOf(this.doctorId);
            cmbAcceptAppointment.removeAllItems();
            cmbAcceptAppointment.addItem("Select one");
            cmbRescheduleAppointment.removeAllItems();
            cmbRescheduleAppointment.addItem("Select one");
            cmbCompleteAppointment.removeAllItems();
            cmbCompleteAppointment.addItem("Select one");
            cmbPrescribeAppointment.removeAllItems();
            cmbPrescribeAppointment.addItem("Select one");
            cmbHospitalization.removeAllItems();
            cmbHospitalization.addItem("Select one");
            cmbSelectPatient.removeAllItems();
            cmbSelectPatient.addItem("Select one");
            cmbPatientId.removeAllItems();
            cmbPatientId.addItem("Select one");

            packagee.util.Response resp = appointmentController.getDoctorAppointments(doctorIdStr, false);
            if (resp != null && resp.isSuccess() && resp.getData() != null) {
                java.util.ArrayList<java.util.HashMap<String, Object>> apts
                        = (java.util.ArrayList<java.util.HashMap<String, Object>>) resp.getData();

                for (java.util.HashMap<String, Object> ap : apts) {
                    String apId = String.valueOf(ap.get("id"));
                    String rawStatus = String.valueOf(ap.get("status"));

                    String status = rawStatus != null ? rawStatus.trim().toUpperCase() : "";

                    if (status.equals("REQUESTED")) {
                        cmbAcceptAppointment.addItem(apId);
                        cmbRescheduleAppointment.addItem(apId);
                    } else if (status.equals("PENDING") || status.equals("ACCEPTED")) { // Por si acaso usas ACCEPTED
                        cmbCompleteAppointment.addItem(apId);
                        cmbRescheduleAppointment.addItem(apId);
                    } else if (status.equals("COMPLETED")) {
                        cmbPrescribeAppointment.addItem(apId);
                    } else {
                        System.out.println(" -> Cita Cancelada o con Estado Desconocido. No se muestra en combos.");
                    }
                }
            }

            packagee.util.Response hResp = hospitalizationController.getHospitalizations(doctorIdStr);
            if (hResp != null && hResp.isSuccess() && hResp.getData() != null) {
                java.util.ArrayList<java.util.HashMap<String, Object>> hosps
                        = (java.util.ArrayList<java.util.HashMap<String, Object>>) hResp.getData();
                for (java.util.HashMap<String, Object> h : hosps) {
                    cmbHospitalization.addItem(String.valueOf(h.get("id")));
                }
            }

            packagee.util.Response pResp = dataController.getAllPatients();
            if (pResp != null && pResp.isSuccess() && pResp.getData() != null) {
                java.util.ArrayList<java.util.HashMap<String, Object>> patientsList
                        = (java.util.ArrayList<java.util.HashMap<String, Object>>) pResp.getData();
                for (java.util.HashMap<String, Object> p : patientsList) {
                    String id = String.valueOf(p.get("id"));
                    String fName = String.valueOf(p.get("firstName"));
                    String lName = String.valueOf(p.get("lastName"));

                    cmbSelectPatient.addItem(id + " - " + fName + " " + lName);
                    cmbPatientId.addItem(id);
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR FATAL AL CARGAR COMBOBOXES:");
            e.printStackTrace();
        }
    }

    private void loadAppointmentsTable(boolean pendingOnly) {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblDoctorView.getModel();
        model.setRowCount(0);

        packagee.util.Response response = appointmentController.getDoctorAppointments(String.valueOf(this.doctorId), pendingOnly);

        if (response.isSuccess()) {
            java.util.ArrayList<java.util.HashMap<String, Object>> appointments
                    = (java.util.ArrayList<java.util.HashMap<String, Object>>) response.getData();

            for (java.util.HashMap<String, Object> appt : appointments) {
                model.addRow(new Object[]{
                    appt.get("id"),
                    appt.get("datetime"),
                    appt.get("patientId"), // Muestra el ID o el nombre serializado
                    appt.get("reason"),
                    appt.get("status")
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
        cmbSelectPatient = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        btnSearchPatient = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtLicenceNumber = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtAssignedOffice = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        cmbSpecialty = new javax.swing.JComboBox<>();
        btnSave = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmbAcceptAppointment = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        btnAcceptAppointment = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cmbRescheduleAppointment = new javax.swing.JComboBox<>();
        btnReschedule = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cmbCompleteAppointment = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        btnComplete = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        btnGenerate = new javax.swing.JButton();
        cmbHospitalization = new javax.swing.JComboBox<>();
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
        btnCancelHospitalization = new javax.swing.JButton();
        cmbPatientId = new javax.swing.JComboBox<>();
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
        btnAddMedication = new javax.swing.JButton();
        btnPrescribe = new javax.swing.JButton();
        cmbPrescribeAppointment = new javax.swing.JComboBox<>();

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
        cmbSelectPatient.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbSelectPatient.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));
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
        btnSearchPatient.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnSearchPatient.setText("Search");
        btnSearchPatient.addActionListener(evt -> btnSearchPatientActionPerformed(evt));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup().addGap(37, 37, 37).addComponent(jLabel38).addGap(18, 18, 18).addComponent(cmbSelectPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createSequentialGroup().addGap(63, 63, 63).addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(174, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(btnSearchPatient).addGap(601, 601, 601))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel38).addComponent(cmbSelectPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18).addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44).addComponent(btnSearchPatient).addContainerGap(67, Short.MAX_VALUE))
        );
        tabbedpaneDoctorView.addTab("History Appointments of a patient", jPanel5);

        // ── jPanel3: Modify info ──
        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel2.setText("Firstname");
        txtFirstName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel3.setText("Lastname");
        txtLastName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel5.setText("Specialty");
        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel7.setText("License Number");
        txtLicenceNumber.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel8.setText("Assigned office");
        txtUsername.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("User");
        txtAssignedOffice.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        txtPassword.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Password");
        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel11.setText("Password confirmation");
        jTextField10.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbSpecialty.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbSpecialty.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one", "GENERAL_MEDICINE", "CARDIOLOGY", "PEDIATRICS", "NEUROLOGY", "TRAUMATOLOGY_ORTHOPEDICS", "GYNECOLOGY_OBSTETRICS", "DERMATOLOGY", "PSYCHIATRY", "ONCOLOGY", "OPHTHALMOLOGY", "INTERNAL_MEDICINE"}));
        btnSave.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnSave.setText("Save");
        btnSave.addActionListener(evt -> btnSaveActionPerformed(evt));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup().addGap(211, 211, 211).addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel3).addGap(18, 18, 18).addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel5).addGap(18, 18, 18).addComponent(cmbSpecialty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createSequentialGroup().addGap(351, 351, 351).addComponent(jLabel7).addGap(18, 18, 18).addComponent(txtLicenceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel8).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(txtAssignedOffice, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createSequentialGroup().addGap(558, 558, 558).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE).addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGroup(jPanel3Layout.createSequentialGroup().addGap(521, 521, 521).addComponent(jLabel11))
                                        .addGroup(jPanel3Layout.createSequentialGroup().addGap(576, 576, 576).addComponent(btnSave))
                                        .addGroup(jPanel3Layout.createSequentialGroup().addGap(561, 561, 561).addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(369, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel3).addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel5).addComponent(cmbSpecialty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel7).addComponent(txtLicenceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(txtAssignedOffice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel8))
                                .addGap(30, 30, 30).addComponent(jLabel9).addGap(18, 18, 18).addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18).addComponent(jLabel10).addGap(27, 27, 27).addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18).addComponent(jLabel11).addGap(18, 18, 18).addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32).addComponent(btnSave).addContainerGap(161, Short.MAX_VALUE))
        );
        tabbedpaneDoctorView.addTab("Modify info", jPanel3);

        // ── jPanel1: Request/Appointments ──
        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel13.setText("Accept medical appointment");
        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Appointment ID");
        cmbAcceptAppointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbAcceptAppointment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        btnAcceptAppointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnAcceptAppointment.setText("Accept");
        btnAcceptAppointment.addActionListener(evt -> btnAcceptAppointmentActionPerformed(evt));
        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Reschedule medical appointment");
        jLabel16.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Appointment");
        cmbRescheduleAppointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbRescheduleAppointment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));
        btnReschedule.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnReschedule.setText("Accept");
        btnReschedule.addActionListener(evt -> btnRescheduleActionPerformed(evt));
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
        cmbCompleteAppointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbCompleteAppointment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));
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
        btnComplete.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnComplete.setText("Complete");
        btnComplete.addActionListener(evt -> btnCompleteActionPerformed(evt));
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
        btnGenerate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnGenerate.setText("Generate");
        btnGenerate.addActionListener(evt -> btnGenerateActionPerformed(evt));
        cmbHospitalization.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbHospitalization.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));
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
        btnCancelHospitalization.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnCancelHospitalization.setText("Cancel");
        btnCancelHospitalization.addActionListener(evt -> btnCancelHospitalizationActionPerformed(evt));
        cmbPatientId.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbPatientId.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));
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
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(btnAcceptAppointment).addGap(87, 87, 87))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(cmbAcceptAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(67, 67, 67))))
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
                                                                .addGroup(jPanel1Layout.createSequentialGroup().addGap(90, 90, 90).addComponent(cmbRescheduleAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jPanel1Layout.createSequentialGroup().addGap(99, 99, 99).addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jPanel1Layout.createSequentialGroup().addGap(98, 98, 98).addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jPanel1Layout.createSequentialGroup().addGap(112, 112, 112).addComponent(btnReschedule)))
                                                        .addGap(91, 91, 91))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGap(112, 112, 112).addComponent(btnComplete).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))).addGroup(jPanel1Layout.createSequentialGroup().addGap(99, 99, 99).addComponent(cmbCompleteAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(0, 66, Short.MAX_VALUE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(jPanel1Layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)).addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(42, 42, 42).addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createSequentialGroup().addGap(41, 41, 41).addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(42, 42, 42).addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createSequentialGroup().addGap(43, 43, 43).addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(0, 0, Short.MAX_VALUE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(7, 7, 7).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))).addGroup(jPanel1Layout.createSequentialGroup().addGap(121, 121, 121).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(0, 0, Short.MAX_VALUE))).addContainerGap())
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGap(45, 45, 45).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addGroup(jPanel1Layout.createSequentialGroup().addComponent(btnCancelHospitalization).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnGenerate)).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(87, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(18, 18, 18).addComponent(cmbHospitalization, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createSequentialGroup().addGap(37, 37, 37).addComponent(jRadioButton5))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(jRadioButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(19, 19, 19)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(cmbPatientId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(29, 29, 29))))
                                        .addGroup(jPanel1Layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(47, 47, 47))))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1)
                        .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator2)
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGap(20, 20, 20).addComponent(jLabel19).addGap(10, 10, 10).addComponent(jLabel20).addGap(18, 18, 18).addComponent(cmbCompleteAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel21).addGap(18, 18, 18).addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel22).addGap(18, 18, 18).addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel23).addGap(18, 18, 18).addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel24).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(btnComplete).addGap(12, 12, 12))
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(17, 17, 17).addComponent(jLabel13).addGap(18, 18, 18).addComponent(jLabel14).addGap(18, 18, 18).addComponent(cmbAcceptAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(31, 31, 31).addComponent(btnAcceptAppointment)).addGroup(jPanel1Layout.createSequentialGroup().addGap(19, 19, 19).addComponent(jLabel15).addGap(18, 18, 18).addComponent(jLabel16).addGap(18, 18, 18).addComponent(cmbRescheduleAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel17).addGap(18, 18, 18).addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel18).addGap(18, 18, 18).addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(30, 30, 30).addComponent(btnReschedule))).addGap(18, 18, Short.MAX_VALUE))))
                        .addGroup(jPanel1Layout.createSequentialGroup().addGap(26, 26, 26).addComponent(jLabel25).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jRadioButton5).addComponent(jRadioButton6)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(cmbHospitalization, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(cmbPatientId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18).addComponent(jLabel27).addGap(16, 16, 16).addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel28).addGap(18, 18, 18).addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel29).addGap(18, 18, 18).addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel30).addGap(18, 18, 18).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnGenerate).addComponent(btnCancelHospitalization)).addGap(0, 0, Short.MAX_VALUE))
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
        btnAddMedication.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnAddMedication.setText("Add");
        btnAddMedication.addActionListener(evt -> btnAddMedicationActionPerformed(evt));
        btnPrescribe.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnPrescribe.setText("Prescribe");
        btnPrescribe.addActionListener(evt -> btnPrescribeActionPerformed(evt));
        cmbPrescribeAppointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbPrescribeAppointment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup().addGap(62, 62, 62).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1125, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel31).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cmbPrescribeAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(9, 9, 9).addComponent(jLabel32)).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel36).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel37).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel35).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel33).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel34).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnAddMedication))))
                                        .addGroup(jPanel2Layout.createSequentialGroup().addGap(583, 583, 583).addComponent(btnPrescribe)))
                                .addContainerGap(183, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup().addGap(57, 57, 57)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel31).addComponent(jLabel32).addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel33).addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel34).addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(btnAddMedication).addComponent(cmbPrescribeAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel36).addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel37).addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel35).addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30).addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(47, 47, 47).addComponent(btnPrescribe).addContainerGap(64, Short.MAX_VALUE))
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
        packagee.model.storage.StorageHospital storage = packagee.model.storage.StorageHospital.getInstance();

        // ¡VITAL! Desuscribir la vista antes de cerrar sesión
        storage.removeObserver(this);

        this.dispose(); // Destruye la ventana y libera memoria

        new LoginView(
                new packagee.controller.LoginController(storage),
                new packagee.controller.PatientController(storage),
                new packagee.controller.DoctorController(storage),
                new packagee.controller.AppointmentController(storage),
                new packagee.controller.HospitalizationController(storage),
                new packagee.controller.DataController(storage)
        ).setVisible(true);
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        StorageHospital.getInstance().removeObserver(this);
        this.dispose();
    }

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        Response response = doctorController.updateDoctor(
                String.valueOf(doctorId), txtUsername.getText(), txtFirstName.getText(), txtLastName.getText(),
                txtPassword.getText(), jTextField10.getText(), (String) cmbSpecialty.getSelectedItem(),
                txtLicenceNumber.getText(), txtAssignedOffice.getText()
        );
        JOptionPane.showMessageDialog(this, response.getMessage(), response.isSuccess() ? "Éxito" : "Error", response.isSuccess() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    private void btnAcceptAppointmentActionPerformed(java.awt.event.ActionEvent evt) {
        // 1. Validar que realmente haya seleccionado un ID válido
        if (cmbAcceptAppointment.getSelectedIndex() <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Please select a valid appointment ID from the list.",
                    "Warning",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return; // Detenemos la ejecución aquí
        }

        // 2. Extraer el ID seleccionado
        String appointmentId = cmbAcceptAppointment.getSelectedItem().toString();

        // 3. Enviar al controlador (MVC puro)
        packagee.util.Response res = appointmentController.acceptAppointment(appointmentId, String.valueOf(doctorId));

        // 4. Mostrar el resultado al doctor
        if (res.isSuccess()) {
            javax.swing.JOptionPane.showMessageDialog(this, res.getMessage(), "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            // Reseteamos el combo a "Select one"
            cmbAcceptAppointment.setSelectedIndex(0);
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, res.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        };
    }

    private void btnRescheduleActionPerformed(java.awt.event.ActionEvent evt) {
        Response res = appointmentController.rescheduleAppointment((String) cmbRescheduleAppointment.getSelectedItem(), String.valueOf(doctorId), jTextField13.getText(), jTextField14.getText());
        JOptionPane.showMessageDialog(this, res.getMessage());
    }

    private void btnCompleteActionPerformed(java.awt.event.ActionEvent evt) {
        // 1. Validamos que no elija "Select one"
        if (cmbCompleteAppointment.getSelectedIndex() <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Please select a valid appointment ID.",
                    "Warning",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Ejecutamos la acción
        String appointmentId = (String) cmbCompleteAppointment.getSelectedItem();
        packagee.util.Response res = appointmentController.completeAppointment(appointmentId, String.valueOf(doctorId));

        // 3. Mostramos mensaje y limpiamos la interfaz
        if (res.isSuccess()) {
            javax.swing.JOptionPane.showMessageDialog(this, res.getMessage(), "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            cmbCompleteAppointment.setSelectedIndex(0); // Reiniciamos el combo a "Select one"
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, res.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {
        // 1. Convertimos el ID del doctor a String (ya no usamos userData)
        String currentDoctorId = String.valueOf(this.doctorId);

        if (jRadioButton6.isSelected()) {
            // --- OPCIÓN 1: Hospitalizar desde una Cita ---

            // Revisamos qué fila seleccionó el doctor en la tabla
            int selectedRow = tblDoctorView.getSelectedRow();

            // Si no seleccionó nada (selectedRow es -1), le avisamos y detenemos la ejecución
            if (selectedRow == -1) {
                javax.swing.JOptionPane.showMessageDialog(this, "Por favor, seleccione una cita de la tabla para proceder con la hospitalización.", "Error", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Extraemos el ID de la cita que está en la primera columna (índice 0)
            String appointmentId = tblDoctorView.getValueAt(selectedRow, 0).toString();

            // Obtenemos los textos de tus campos
            String date = jTextField21.getText();
            String reason = jTextArea9.getText();
            String roomType = "STANDARD"; // Nota: Si tienes un ComboBox para esto en DoctorView, cámbialo por tuCombo.getSelectedItem().toString()
            String observations = jTextArea1.getText();

            // Llamamos al controlador con datos 100% reales
            packagee.util.Response res = hospitalizationController.hospitalizeFromAppointment(
                    appointmentId,
                    currentDoctorId,
                    date,
                    reason,
                    roomType,
                    observations
            );

            javax.swing.JOptionPane.showMessageDialog(this, res.getMessage());

            // Si la hospitalización fue un éxito, recargamos la tabla para que la cita desaparezca o se actualice a COMPLETED
            if (res.isSuccess()) {
                loadAppointmentsTable(true); // O false, dependiendo de cómo quieras refrescar la vista
            }

        } else if (jRadioButton5.isSelected()) {
            // --- OPCIÓN 2: Aprobar una hospitalización solicitada (Desde el ComboBox) ---

            Object selectedItem = cmbHospitalization.getSelectedItem();

            // Validación por si el combo está vacío o en "Seleccionar"
            if (selectedItem == null || selectedItem.toString().equals("Select one") || selectedItem.toString().trim().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Por favor, seleccione una solicitud de hospitalización válida.", "Error", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            String hospitalizationId = selectedItem.toString();

            packagee.util.Response res = hospitalizationController.approveHospitalization(
                    hospitalizationId,
                    currentDoctorId
            );

            javax.swing.JOptionPane.showMessageDialog(this, res.getMessage());
        }
    }

    private void btnCancelHospitalizationActionPerformed(java.awt.event.ActionEvent evt) {
        Response res = hospitalizationController.denyHospitalization((String) cmbHospitalization.getSelectedItem(), String.valueOf(doctorId));
        JOptionPane.showMessageDialog(this, res.getMessage());
    }

    private void btnSearchPatientActionPerformed(java.awt.event.ActionEvent evt) {
        if (cmbSelectPatient.getSelectedIndex() <= 0) {
            return;
        }
        String patId = ((String) cmbSelectPatient.getSelectedItem()).split(" - ")[0];

        packagee.util.Response res = appointmentController.getPatientAppointments(patId);
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable3.getModel();
        model.setRowCount(0);

        if (res.isSuccess()) {
            java.util.ArrayList<java.util.HashMap<String, Object>> appointments
                    = (java.util.ArrayList<java.util.HashMap<String, Object>>) res.getData();

            for (java.util.HashMap<String, Object> appt : appointments) {
                model.addRow(new Object[]{
                    appt.get("id"),
                    appt.get("datetime"),
                    appt.get("doctorId"),
                    appt.get("specialty"),
                    appt.get("isRequestedByDoctor") != null && (boolean) appt.get("isRequestedByDoctor") ? "Doctor" : "Specialty",
                    appt.get("status")
                });
            }
        }
    }

    private void btnAddMedicationActionPerformed(java.awt.event.ActionEvent evt) {
        if (cmbPrescribeAppointment.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita válida.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addRow(new Object[]{
            cmbPrescribeAppointment.getSelectedItem().toString(), // CORREGIDO AQUÍ
            jTextField24.getText(),
            jTextField25.getText(),
            jTextField26.getText(),
            jTextField28.getText(),
            jTextField29.getText(),
            jTextField27.getText()
        });
    }

    private void btnPrescribeActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay medicamentos en la lista para prescribir.");
            return;
        }

        // Iteramos desde la última fila hacia la primera para poder eliminar filas sin errores de índice
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            try {
                // Extraer datos de la fila i
                String apId = String.valueOf(model.getValueAt(i, 0));
                String medName = String.valueOf(model.getValueAt(i, 1));

                // Conversiones seguras
                double dose = Double.parseDouble(String.valueOf(model.getValueAt(i, 2)));
                String route = String.valueOf(model.getValueAt(i, 3));
                int duration = Integer.parseInt(String.valueOf(model.getValueAt(i, 4)));
                String instructions = String.valueOf(model.getValueAt(i, 5));
                int frequency = Integer.parseInt(String.valueOf(model.getValueAt(i, 6)));

                // Enviar al controlador
                Response res = appointmentController.prescribeMedication(
                        apId, String.valueOf(doctorId), medName, dose, route, duration, instructions, frequency
                );

                if (res.isSuccess()) {
                    model.removeRow(i); // Eliminamos solo si tuvo éxito
                } else {
                    JOptionPane.showMessageDialog(this, "Error en fila " + (i + 1) + ": " + res.getMessage());
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error en los números de la fila " + (i + 1) + ". Revisa la dosis, duración o frecuencia.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error inesperado en fila " + (i + 1) + ": " + e.getMessage());
            }
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnX;
    private javax.swing.JButton btnPrescribe;
    private javax.swing.JButton btnCancelHospitalization;
    private javax.swing.JButton btnAcceptAppointment;
    private javax.swing.JButton btnReschedule;
    private javax.swing.JButton btnComplete;
    private javax.swing.JButton btnGenerate;
    private javax.swing.JButton btnAddMedication;
    private javax.swing.JButton btnSearchPatient;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cmbSpecialty;
    private javax.swing.JComboBox<String> cmbAcceptAppointment;
    private javax.swing.JComboBox<String> cmbRescheduleAppointment;
    private javax.swing.JComboBox<String> cmbCompleteAppointment;
    private javax.swing.JComboBox<String> cmbSelectPatient;
    private javax.swing.JComboBox<String> cmbHospitalization;
    private javax.swing.JComboBox<String> cmbPrescribeAppointment;
    private javax.swing.JComboBox<String> cmbPatientId;
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
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField txtLicenceNumber;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtAssignedOffice;
    private javax.swing.JTextField txtPassword;
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
