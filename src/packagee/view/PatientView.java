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
import packagee.controller.HospitalizationController;
import packagee.controller.PatientController;
import packagee.model.Doctor;
import packagee.model.RoomType;
import packagee.model.Specialty;
import packagee.model.storage.StorageHospital;
import packagee.util.Response;

/**
 * @author jjlora
 * @author edangulo
 */
public class PatientView extends javax.swing.JFrame {

    private int x, y;
    private HashMap<String, Object> userData;
    private boolean isAdmin;

    public PatientView(HashMap<String, Object> userData) {
        initComponents();
        this.userData = userData;
        this.isAdmin = false;
        loadUserData();
        loadComboBoxes();
        loadAppointmentsTable();
        configurarBackButton();
        this.setLocationRelativeTo(null);
    }

    public PatientView(HashMap<String, Object> userData, boolean isAdmin) {
        initComponents();
        this.userData = userData;
        this.isAdmin = isAdmin;
        loadUserData();
        loadComboBoxes();
        loadAppointmentsTable();
        configurarBackButton();
        this.setLocationRelativeTo(null);
    }

    private void configurarBackButton() {
        btnBack.setVisible(isAdmin);
    }

    private void loadUserData() {
        txtFirstName.setText((String) userData.get("firstname"));
        txtLastname.setText((String) userData.get("lastname"));
        txtEmail.setText((String) userData.get("email"));
        txtPhone.setText(String.valueOf(userData.get("phone")));
        txtAdress.setText((String) userData.get("address"));
        txtUser.setText((String) userData.get("username"));

        Object birthdate = userData.get("birthdate");
        if (birthdate != null) {
            txtBirthdate.setText(birthdate.toString());
        }

        Object gender = userData.get("gender");
        if (gender != null) {
            boolean isMale = (Boolean) gender;
            cmbSelect.setSelectedIndex(isMale ? 2 : 1);
        }
    }

    private void loadComboBoxes() {
        StorageHospital storage = StorageHospital.getInstance();

        cmbSelectOne2.removeAllItems();
        cmbSelectOne2.addItem("Select one");
        for (Doctor doc : storage.getAllDoctors().values()) {
            cmbSelectOne2.addItem(doc.getId() + " - " + doc.getFirstname() + " " + doc.getLastname());
        }

        cmbSelectOne3.removeAllItems();
        cmbSelectOne3.addItem("Select one");
        for (RoomType rt : RoomType.values()) {
            cmbSelectOne3.addItem(rt.name());
        }

        cmbSelectOne.removeAllItems();
        cmbSelectOne.addItem("Select one");

        loadAppointmentCombo();
    }

    private void loadAppointmentCombo() {
        AppointmentController appointmentController = new AppointmentController();
        String patientId = String.valueOf(userData.get("id"));
        Response response = appointmentController.getPatientAppointments(patientId);

        cmbSelectOne4.removeAllItems();
        cmbSelectOne4.addItem("Select one");
        if (response.isSuccess()) {
            ArrayList<HashMap<String, Object>> appointments =
                    (ArrayList<HashMap<String, Object>>) response.getData();
            for (HashMap<String, Object> ap : appointments) {
                cmbSelectOne4.addItem((String) ap.get("id"));
            }
        }
    }

    private void loadAppointmentsTable() {
    AppointmentController ac = new AppointmentController();
    String patientId = String.valueOf(userData.get("id"));
    Response response = ac.getPatientAppointments(patientId);
    DefaultTableModel model = (DefaultTableModel) tblPatientView.getModel();
    model.setRowCount(0);
    if (response.isSuccess()) {
        StorageHospital storage = StorageHospital.getInstance();
        ArrayList<HashMap<String, Object>> apts =
            (ArrayList<HashMap<String, Object>>) response.getData();
        for (HashMap<String, Object> ap : apts) {
            // Obtener nombre del doctor desde storage
            Object docIdObj = ap.get("doctorId");
            String doctorName = "";
            if (docIdObj != null) {
                packagee.model.Doctor doc = storage.getDoctor(((Number) docIdObj).longValue());
                if (doc != null) doctorName = doc.getFirstname() + " " + doc.getLastname();
            }
            model.addRow(new Object[]{
                ap.get("id"),
                ap.get("datetime"),
                doctorName,
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
        lblPatientView = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        tabbedpanePatientView = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPatientView = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblFirstName = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        txtLastname = new javax.swing.JTextField();
        lblBirthdate = new javax.swing.JLabel();
        txtBirthdate = new javax.swing.JTextField();
        lblGender = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lblAdress = new javax.swing.JLabel();
        txtAdress = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        lblPasswordConfirmation = new javax.swing.JLabel();
        txtPasswordConfirmation = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        lblUser = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        cmbSelect = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        lblRequest = new javax.swing.JLabel();
        radiobtnSpecialty = new javax.swing.JRadioButton();
        radiobtnDoctor = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblAppointmentDate = new javax.swing.JLabel();
        txtAppointmentDate = new javax.swing.JTextField();
        txtAppointmentTime = new javax.swing.JTextField();
        lblAppointmentTime = new javax.swing.JLabel();
        lblAppointmentType = new javax.swing.JLabel();
        lblAppointmentReason = new javax.swing.JLabel();
        cmbSelectOne1 = new javax.swing.JComboBox<>();
        btnCreate = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        lblRequestHospi = new javax.swing.JLabel();
        lblHospiReason = new javax.swing.JLabel();
        lblAttendingDoctor = new javax.swing.JLabel();
        cmbSelectOne2 = new javax.swing.JComboBox<>();
        txtEstimDate = new javax.swing.JTextField();
        lblEstimatedDate = new javax.swing.JLabel();
        lbDesired = new javax.swing.JLabel();
        cmbSelectOne3 = new javax.swing.JComboBox<>();
        lblObservations = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtareaObservations = new javax.swing.JTextArea();
        btnCreate1 = new javax.swing.JButton();
        lblCancel = new javax.swing.JLabel();
        lblIdAppointment = new javax.swing.JLabel();
        lblObservations1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtareaObservations1 = new javax.swing.JTextArea();
        btnCancel = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtareaHospiReason = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtareaAppointment = new javax.swing.JTextArea();
        cmbSelectOne4 = new javax.swing.JComboBox<>();
        cmbSelectOne = new javax.swing.JComboBox<>();

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
        btnX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXActionPerformed(evt);
            }
        });

        lblPatientView.setFont(new java.awt.Font("Yu Gothic UI", 0, 14));
        lblPatientView.setText("PATIENT VIEW");

        btnBack.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblPatientView)
                .addGap(29, 29, 29)
                .addComponent(btnBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnX)
                .addGap(19, 19, 19))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnX))
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblPatientView, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tblPatientView.setAutoCreateRowSorter(true);
        tblPatientView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Date", "Doctor", "Specialty", "Type", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] { false, false, false, false, false, false };
            public Class getColumnClass(int columnIndex) { return types[columnIndex]; }
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit[columnIndex]; }
        });
        jScrollPane3.setViewportView(tblPatientView);

        btnRefresh.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnLogout.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(602, 602, 602)
                .addComponent(btnRefresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addGap(78, 78, 78))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefresh)
                    .addComponent(btnLogout))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        tabbedpanePatientView.addTab("Appointment history", jPanel3);

        lblFirstName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblFirstName.setText("Firstname");
        txtFirstName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblLastName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblLastName.setText("Lastname");
        txtLastname.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblBirthdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblBirthdate.setText("Birthdate");
        txtBirthdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblGender.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblGender.setText("Gender");
        lblEmail.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblEmail.setText("Email");
        txtEmail.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPhone.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPhone.setText("Phone");
        txtPhone.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAdress.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAdress.setText("Address");
        txtAdress.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        txtPassword.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPassword.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPassword.setText("Password");
        lblPasswordConfirmation.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPasswordConfirmation.setText("Password confirmation");
        txtPasswordConfirmation.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));

        btnSave.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        lblUser.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblUser.setText("User");
        txtUser.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbSelect.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "Female", "Male" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(lblFirstName)
                .addGap(18, 18, 18)
                .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(lblLastName)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblPhone)
                        .addGap(18, 18, 18)
                        .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAdress)
                        .addGap(18, 18, 18)
                        .addComponent(txtAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtLastname, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblBirthdate)
                        .addGap(18, 18, 18)
                        .addComponent(txtBirthdate, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblGender)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(lblEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(141, 141, 141))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(516, 516, 516)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(btnSave))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(txtPasswordConfirmation, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblPasswordConfirmation)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(lblPassword))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(39, 39, 39)
                                    .addComponent(lblUser)))
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstName)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLastName)
                    .addComponent(txtLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBirthdate)
                    .addComponent(txtBirthdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGender)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhone)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAdress)
                    .addComponent(txtAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addComponent(lblUser)
                .addGap(18, 18, 18)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPassword)
                .addGap(18, 18, 18)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPasswordConfirmation)
                .addGap(18, 18, 18)
                .addComponent(txtPasswordConfirmation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btnSave)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        tabbedpanePatientView.addTab("Modify info", jPanel1);

        lblRequest.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblRequest.setText("Request medical appointment");

        radiobtnSpecialty.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        radiobtnSpecialty.setText("Specialty");
        radiobtnSpecialty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiobtnSpecialtyActionPerformed(evt);
            }
        });

        radiobtnDoctor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        radiobtnDoctor.setText("Doctor");
        radiobtnDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiobtnDoctorActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        lblAppointmentDate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAppointmentDate.setText("Appointment date");
        txtAppointmentDate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        txtAppointmentTime.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAppointmentTime.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAppointmentTime.setText("Appointment time");
        lblAppointmentType.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAppointmentType.setText("Appointment type");
        lblAppointmentReason.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAppointmentReason.setText("Appointment reason");

        cmbSelectOne1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbSelectOne1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "Remote", "In-person" }));

        btnCreate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnCreate.setText("Create");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        lblRequestHospi.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblRequestHospi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRequestHospi.setText("Request hospitalization");
        lblHospiReason.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblHospiReason.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHospiReason.setText("Hospitalization reason");
        lblAttendingDoctor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAttendingDoctor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAttendingDoctor.setText("Attending doctor");

        cmbSelectOne2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbSelectOne2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        txtEstimDate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblEstimatedDate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblEstimatedDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstimatedDate.setText("Estimated date of admission");
        lblEstimatedDate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbDesired.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lbDesired.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDesired.setText("Desired room type");

        cmbSelectOne3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbSelectOne3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        lblObservations.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblObservations.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblObservations.setText("Observations");

        txtareaObservations.setColumns(20);
        txtareaObservations.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        txtareaObservations.setRows(5);
        jScrollPane1.setViewportView(txtareaObservations);

        btnCreate1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnCreate1.setText("Create");
        btnCreate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate1ActionPerformed(evt);
            }
        });

        lblCancel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblCancel.setText("Cancel appointment");
        lblIdAppointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblIdAppointment.setText("ID appointment");
        lblObservations1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblObservations1.setText("Observations");

        txtareaObservations1.setColumns(20);
        txtareaObservations1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        txtareaObservations1.setRows(5);
        jScrollPane2.setViewportView(txtareaObservations1);

        btnCancel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        txtareaHospiReason.setColumns(20);
        txtareaHospiReason.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        txtareaHospiReason.setRows(5);
        jScrollPane4.setViewportView(txtareaHospiReason);

        txtareaAppointment.setColumns(20);
        txtareaAppointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        txtareaAppointment.setRows(5);
        jScrollPane5.setViewportView(txtareaAppointment);

        cmbSelectOne4.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbSelectOne4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        cmbSelectOne.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbSelectOne.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(44, 44, 44)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(radiobtnSpecialty)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(radiobtnDoctor))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(63, 63, 63)
                                    .addComponent(txtAppointmentDate, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(47, 47, 47)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblAppointmentTime)
                                        .addComponent(lblAppointmentDate)
                                        .addComponent(cmbSelectOne, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(63, 63, 63)
                                    .addComponent(txtAppointmentTime, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(38, 38, 38)
                                    .addComponent(lblAppointmentReason))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(46, 46, 46)
                                    .addComponent(lblAppointmentType))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(55, 55, 55)
                                    .addComponent(cmbSelectOne1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(42, 42, 42)
                            .addComponent(lblRequest)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(btnCreate)))
                .addGap(69, 69, 69)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(211, 211, 211)
                            .addComponent(btnCreate1))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(127, 127, 127)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblHospiReason, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(lblRequestHospi, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                                .addComponent(lblAttendingDoctor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addGap(127, 127, 127)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblObservations, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEstimatedDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbDesired, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(cmbSelectOne2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(txtEstimDate, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(cmbSelectOne3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(lblCancel))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(btnCancel))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cmbSelectOne4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblIdAppointment)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(lblObservations1)))
                        .addGap(49, 49, 49)))
                .addGap(81, 81, 81))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblRequestHospi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(lblHospiReason)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAttendingDoctor)
                        .addGap(18, 18, 18)
                        .addComponent(cmbSelectOne2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblEstimatedDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEstimDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(lbDesired)
                        .addGap(18, 18, 18)
                        .addComponent(cmbSelectOne3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblObservations)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCreate1)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblRequest)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(radiobtnSpecialty)
                                    .addComponent(radiobtnDoctor))
                                .addGap(18, 18, 18)
                                .addComponent(cmbSelectOne, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblAppointmentDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtAppointmentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(lblAppointmentTime)
                                .addGap(18, 18, 18)
                                .addComponent(txtAppointmentTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblAppointmentReason)
                                .addGap(24, 24, 24)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblCancel)
                                .addGap(39, 39, 39)
                                .addComponent(lblIdAppointment)
                                .addGap(18, 18, 18)
                                .addComponent(cmbSelectOne4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblObservations1)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(btnCancel)))
                        .addGap(18, 18, 18)
                        .addComponent(lblAppointmentType)
                        .addGap(18, 18, 18)
                        .addComponent(cmbSelectOne1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnCreate)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        tabbedpanePatientView.addTab("Request/Cancel", jPanel2);

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabbedpanePatientView)
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedpanePatientView))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

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

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        AppointmentController controller = new AppointmentController();
        String appointmentId = (String) cmbSelectOne4.getSelectedItem();
        if (appointmentId == null || appointmentId.equals("Select one")) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String patientId = String.valueOf(userData.get("id"));
        Response response = controller.cancelAppointment(appointmentId, patientId);
        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            loadAppointmentsTable();
            loadAppointmentCombo();
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        PatientController controller = new PatientController();
        String gender = (String) cmbSelect.getSelectedItem();
        if (gender.equals("Select one")) gender = "M";
        Response response = controller.updatePatient(
            String.valueOf(userData.get("id")),
            txtUser.getText(),
            txtFirstName.getText(),
            txtLastname.getText(),
            txtPassword.getText(),
            txtPasswordConfirmation.getText(),
            txtEmail.getText(),
            txtPhone.getText(),
            txtBirthdate.getText(),
            gender.equals("Male") ? "M" : "F",
            txtAdress.getText()
        );
        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            userData = (HashMap<String, Object>) response.getData();
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        new LoginView().setVisible(true);
        this.dispose();
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        new AdminView(userData).setVisible(true);
        this.dispose();
    }

    private void radiobtnSpecialtyActionPerformed(java.awt.event.ActionEvent evt) {
        radiobtnDoctor.setSelected(false);
        cmbSelectOne.removeAllItems();
        cmbSelectOne.addItem("Select one");
        for (Specialty spec : Specialty.values()) {
            cmbSelectOne.addItem(spec.name().replace("_", " & "));
        }
    }

    private void radiobtnDoctorActionPerformed(java.awt.event.ActionEvent evt) {
        radiobtnSpecialty.setSelected(false);
        StorageHospital storage = StorageHospital.getInstance();
        cmbSelectOne.removeAllItems();
        cmbSelectOne.addItem("Select one");
        for (Doctor doc : storage.getAllDoctors().values()) {
            cmbSelectOne.addItem(doc.getId() + " - " + doc.getFirstname() + " " + doc.getLastname());
        }
    }

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {
        AppointmentController controller = new AppointmentController();
        String patientId = String.valueOf(userData.get("id"));
        String selectedItem = (String) cmbSelectOne.getSelectedItem();
        String date = txtAppointmentDate.getText();
        String time = txtAppointmentTime.getText();
        String reason = txtareaAppointment.getText();
        Response response;
        if (radiobtnSpecialty.isSelected()) {
            String specialty = selectedItem.replace(" & ", "_").toUpperCase();
            response = controller.requestAppointmentBySpecialty(patientId, specialty, date, time, reason);
        } else if (radiobtnDoctor.isSelected()) {
            String doctorId = selectedItem.split(" - ")[0];
            response = controller.requestAppointmentByDoctor(patientId, doctorId, date, time, reason);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona Specialty o Doctor", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            loadAppointmentsTable();
            loadAppointmentCombo();
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        loadAppointmentsTable();
        loadAppointmentCombo();
    }

    private void btnCreate1ActionPerformed(java.awt.event.ActionEvent evt) {
        HospitalizationController controller = new HospitalizationController();
        String patientId = String.valueOf(userData.get("id"));
        String selectedDoctor = (String) cmbSelectOne2.getSelectedItem();
        if (selectedDoctor == null || selectedDoctor.equals("Select one")) {
            JOptionPane.showMessageDialog(this, "Selecciona un doctor", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String doctorId = selectedDoctor.split(" - ")[0];
        String selectedRoom = (String) cmbSelectOne3.getSelectedItem();
        if (selectedRoom == null || selectedRoom.equals("Select one")) {
            JOptionPane.showMessageDialog(this, "Selecciona un tipo de habitación", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Response response = controller.requestHospitalization(
            patientId,
            doctorId,
            txtEstimDate.getText(),
            txtareaHospiReason.getText(),
            selectedRoom,
            txtareaObservations.getText()
        );
        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnCreate1;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnX;
    private javax.swing.JComboBox<String> cmbSelect;
    private javax.swing.JComboBox<String> cmbSelectOne;
    private javax.swing.JComboBox<String> cmbSelectOne1;
    private javax.swing.JComboBox<String> cmbSelectOne2;
    private javax.swing.JComboBox<String> cmbSelectOne3;
    private javax.swing.JComboBox<String> cmbSelectOne4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbDesired;
    private javax.swing.JLabel lblAdress;
    private javax.swing.JLabel lblAppointmentDate;
    private javax.swing.JLabel lblAppointmentReason;
    private javax.swing.JLabel lblAppointmentTime;
    private javax.swing.JLabel lblAppointmentType;
    private javax.swing.JLabel lblAttendingDoctor;
    private javax.swing.JLabel lblBirthdate;
    private javax.swing.JLabel lblCancel;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEstimatedDate;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblGender;
    private javax.swing.JLabel lblHospiReason;
    private javax.swing.JLabel lblIdAppointment;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblObservations;
    private javax.swing.JLabel lblObservations1;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPasswordConfirmation;
    private javax.swing.JLabel lblPatientView;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblRequest;
    private javax.swing.JLabel lblRequestHospi;
    private javax.swing.JLabel lblUser;
    private packagee.view.PanelRound panelRound1;
    private packagee.view.PanelRound panelRound2;
    private javax.swing.JRadioButton radiobtnDoctor;
    private javax.swing.JRadioButton radiobtnSpecialty;
    private javax.swing.JTabbedPane tabbedpanePatientView;
    private javax.swing.JTable tblPatientView;
    private javax.swing.JTextField txtAdress;
    private javax.swing.JTextField txtAppointmentDate;
    private javax.swing.JTextField txtAppointmentTime;
    private javax.swing.JTextField txtBirthdate;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEstimDate;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastname;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtPasswordConfirmation;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtUser;
    private javax.swing.JTextArea txtareaAppointment;
    private javax.swing.JTextArea txtareaHospiReason;
    private javax.swing.JTextArea txtareaObservations;
    private javax.swing.JTextArea txtareaObservations1;
    // End of variables declaration
}