package packagee.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import packagee.controller.AppointmentController;
import packagee.controller.DataController;
import packagee.controller.HospitalizationController;
import packagee.controller.LoginController;
import packagee.controller.PatientController;
import packagee.model.storage.StorageHospital;
import packagee.util.Observer;
import packagee.util.Response;
import packagee.controller.*;

public class PatientView extends javax.swing.JFrame implements Observer {

    private int x, y;
    private long patientId;
    private boolean isAdmin;

    private PatientController patientController;
    private AppointmentController appointmentController;
    private HospitalizationController hospitalizationController;
    private DataController dataController;
    private LoginController loginController;
    private DoctorController doctorController;

    public PatientView(long patientId, PatientController pc, AppointmentController ac,
            HospitalizationController hc, DataController datac, boolean isAdmin) {
        this.patientId = patientId;
        this.patientController = pc;
        this.appointmentController = ac;
        this.hospitalizationController = hc;
        this.dataController = datac;
        this.isAdmin = isAdmin;

        initComponents();
        btnBack.setVisible(true); 
        for (java.awt.event.ActionListener al : btnBack.getActionListeners()) {
            btnBack.removeActionListener(al);
        }

       
        btnBack.addActionListener(e -> {
            this.dispose(); 
            packagee.model.storage.StorageHospital storage = packagee.model.storage.StorageHospital.getInstance();
            long currentAdminId = storage.getAdmin().getId();
            new packagee.view.AdminView(
                    currentAdminId,
                    doctorController,
                    patientController,
                    appointmentController, 
                    hospitalizationController, 
                    dataController
            ).setVisible(true);
        });
        this.setBackground(new Color(0, 0, 0, 0));
        this.setSize(1400, 750);
        this.setLocationRelativeTo(null);
        StorageHospital.getInstance().addObserver(this);
        tabbedpanePatientView.addChangeListener(e -> {
            if (tabbedpanePatientView.getSelectedIndex() == 1) {
                java.util.HashMap<String, Object> data = patientController.getPacienteData(String.valueOf(patientId));
                cargarDatosPaciente(data);
            }
        });

        cargarComboDoctores();
        cargarComboEspecialidades();
        cargarComboRoomTypes();
        cargarTablaAppointments();
        cargarComboAppointmentsCancel();
    }

    @Override
    public void update(String event) {
        switch (event) {
            case "APPOINTMENT_ADDED":
            case "APPOINTMENT_UPDATED":
                cargarTablaAppointments();
                cargarComboAppointmentsCancel();
                break;
            case "DOCTOR_ADDED":
                cargarComboDoctores();
                break;
        }
    }

    private void cargarComboDoctores() {
        cmbSelectDoctor.removeAllItems();
        cmbSelectDoctor.addItem("Select one");
        cmbAttendingDoctor.removeAllItems();
        cmbAttendingDoctor.addItem("Select one");

        Response resDocs = dataController.getAllDoctors();
        if (resDocs.isSuccess()) {
            List<HashMap<String, Object>> docs = (List<HashMap<String, Object>>) resDocs.getData();
            for (HashMap<String, Object> doc : docs) {
                String desc = doc.get("id") + " - " + doc.get("firstname") + " " + doc.get("lastname");
                cmbSelectDoctor.addItem(desc);
                cmbAttendingDoctor.addItem(desc);
            }
        }
    }

    private void cargarComboEspecialidades() {
        cmbSelectDoctor.removeAllItems();
        cmbSelectDoctor.addItem("Select one"); // O "Select a specialty"
        Response res = dataController.getSpecialties();

        if (res.isSuccess()) {
            List<String> specialties = (List<String>) res.getData();
            for (String spec : specialties) {
                cmbSelectDoctor.addItem(spec);
            }
        }
    }

    public void cargarDatosPaciente(java.util.HashMap<String, Object> pacienteData) {
        if (pacienteData != null) {
            txtFirstName.setText(String.valueOf(pacienteData.getOrDefault("firstname", "")));
            txtLastname.setText(String.valueOf(pacienteData.getOrDefault("lastname", "")));
            txtBirthdate.setText(String.valueOf(pacienteData.getOrDefault("birthdate", "")));
            txtPhone.setText(String.valueOf(pacienteData.getOrDefault("phone", "")));
            txtAdress.setText(String.valueOf(pacienteData.getOrDefault("address", "")));
            txtEmail.setText(String.valueOf(pacienteData.getOrDefault("email", "")));
            txtUser.setText(String.valueOf(pacienteData.getOrDefault("username", "")));
        }
    }

    private void cargarComboRoomTypes() {
        cmbRoomType.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{"Select one", "STANDARD", "ICU", "NICU", "IMC", "ISOLATION"}
        ));
    }

    @SuppressWarnings("unchecked")
    private void cargarTablaAppointments() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblPatientView.getModel();
        model.setRowCount(0); // Limpiar la tabla anterior

        // Llamamos al controlador pasándole nuestro patientId convertido a texto
        packagee.util.Response response = appointmentController.getPatientAppointments(String.valueOf(this.patientId));

        if (response.isSuccess()) {
            java.util.ArrayList<java.util.HashMap<String, Object>> appointments
                    = (java.util.ArrayList<java.util.HashMap<String, Object>>) response.getData();

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

    private void cargarComboAppointmentsCancel() {
        Response r = appointmentController.getPatientAppointments(String.valueOf(patientId));
        cmbAppointmentCancel.removeAllItems();
        cmbAppointmentCancel.addItem("Select one");
        if (!r.isSuccess()) {
            return;
        }
        ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) r.getData();
        for (HashMap<String, Object> a : list) {
            cmbAppointmentCancel.addItem(String.valueOf(a.get("id")));
        }
    }

    // ── initComponents ──
    @SuppressWarnings("unchecked")
    private void initComponents() {
        panelRound1 = new packagee.view.PanelRound();
        panelRound2 = new packagee.view.PanelRound();
        btnX = new javax.swing.JButton();
        lblPatientView = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        tabbedpanePatientView = new javax.swing.JTabbedPane();

        // Tab 1
        jPanelHistory = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPatientView = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        // Tab 2
        jPanelInfo = new javax.swing.JPanel();
        lblFirstName = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        txtLastname = new javax.swing.JTextField();
        lblBirthdate = new javax.swing.JLabel();
        txtBirthdate = new javax.swing.JTextField();
        lblGender = new javax.swing.JLabel();
        cmbGender = new javax.swing.JComboBox<>();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lblAdress = new javax.swing.JLabel();
        txtAdress = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        lblPasswordConfirmation = new javax.swing.JLabel();
        txtPasswordConfirmation = new javax.swing.JTextField();
        lblUser = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        lblInfoError = new javax.swing.JLabel();

        // Tab 3
        jPanelRequest = new javax.swing.JPanel();
        lblRequest = new javax.swing.JLabel();
        radiobtnSpecialty = new javax.swing.JRadioButton();
        radiobtnDoctor = new javax.swing.JRadioButton();
        cmbSelectDoctor = new javax.swing.JComboBox<>();
        lblAppointmentDate = new javax.swing.JLabel();
        txtAppointmentDate = new javax.swing.JTextField();
        lblAppointmentTime = new javax.swing.JLabel();
        txtAppointmentTime = new javax.swing.JTextField();
        lblAppointmentType = new javax.swing.JLabel();
        cmbAppointmentType = new javax.swing.JComboBox<>();
        lblAppointmentReason = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtareaAppointment = new javax.swing.JTextArea();
        btnCreate = new javax.swing.JButton();
        lblAppointmentError = new javax.swing.JLabel();
        lblRequestHospi = new javax.swing.JLabel();
        lblHospiReason = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtareaHospiReason = new javax.swing.JTextArea();
        lblAttendingDoctor = new javax.swing.JLabel();
        cmbAttendingDoctor = new javax.swing.JComboBox<>();
        lblEstimatedDate = new javax.swing.JLabel();
        txtEstimDate = new javax.swing.JTextField();
        lbDesired = new javax.swing.JLabel();
        cmbRoomType = new javax.swing.JComboBox<>();
        lblObservations = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtareaObservations = new javax.swing.JTextArea();
        btnCreateHospi = new javax.swing.JButton();
        lblHospiError = new javax.swing.JLabel();
        lblCancel = new javax.swing.JLabel();
        lblIdAppointment = new javax.swing.JLabel();
        cmbAppointmentCancel = new javax.swing.JComboBox<>();
        lblObservations1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtareaObservations1 = new javax.swing.JTextArea();
        btnCancel = new javax.swing.JButton();
        lblCancelError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        panelRound1.setRadius(50);
        panelRound2.setRadius(50);
        panelRound2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                // Asumo que tienes este método definido en tu clase
                panelRound2MouseDragged(evt);
            }
        });
        panelRound2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                // Asumo que tienes este método definido en tu clase
                panelRound2MousePressed(evt);
            }
        });

        btnX.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnX.setText("X");
        btnX.setBorderPainted(false);
        btnX.setContentAreaFilled(false);
        btnX.addActionListener(e -> System.exit(0));

        lblPatientView.setFont(new java.awt.Font("Yu Gothic UI", 0, 14));
        lblPatientView.setText("PATIENT VIEW");

        btnBack.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnBack.setText("Back");
        btnBack.setVisible(false);

        javax.swing.GroupLayout p2 = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(p2);
        p2.setHorizontalGroup(p2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p2.createSequentialGroup()
                        .addGap(15, 15, 15).addComponent(lblPatientView).addGap(29, 29, 29)
                        .addComponent(btnBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnX).addGap(19, 19, 19)));
        p2.setVerticalGroup(p2.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnX).addComponent(lblPatientView).addComponent(btnBack));

        // ── Tab 1: Appointment history ──
        tblPatientView.setAutoCreateRowSorter(true);
        tblPatientView.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Date", "Doctor", "Specialty", "Type", "Status"}) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        });
        jScrollPane3.setViewportView(tblPatientView);

        btnRefresh.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(e -> {
            cargarTablaAppointments();
            cargarComboAppointmentsCancel();
        });

      
        btnLogout.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnLogout.setText("Logout");
        btnLogout.addActionListener(e -> {
            this.setVisible(false);
            new LoginView(
                    loginController,
                    patientController,
                    doctorController,
                    appointmentController,
                    hospitalizationController,
                    dataController
            ).setVisible(true);
        });

        javax.swing.GroupLayout hist = new javax.swing.GroupLayout(jPanelHistory);
        jPanelHistory.setLayout(hist);
        hist.setAutoCreateGaps(true);
        hist.setAutoCreateContainerGaps(true);
        hist.setHorizontalGroup(hist.createParallelGroup()
                .addComponent(jScrollPane3)
                .addGroup(hist.createSequentialGroup()
                        .addComponent(btnRefresh)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnLogout)));
        hist.setVerticalGroup(hist.createSequentialGroup()
                .addComponent(jScrollPane3, 350, 350, 350)
                .addGap(20)
                .addGroup(hist.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRefresh).addComponent(btnLogout)));
        tabbedpanePatientView.addTab("Appointment history", jPanelHistory);

        // ── Tab 2: Modify info ──
        lblFirstName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblFirstName.setText("Firstname");
        txtFirstName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblLastName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblLastName.setText("Lastname");
        txtLastname.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblBirthdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblBirthdate.setText("Birthdate (YYYY-MM-DD)");
        txtBirthdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblGender.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblGender.setText("Gender");
        cmbGender.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one", "Female", "Male"}));
        lblEmail.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblEmail.setText("Email");
        txtEmail.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPhone.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPhone.setText("Phone");
        txtPhone.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAdress.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAdress.setText("Address");
        txtAdress.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPassword.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPassword.setText("Password");
        txtPassword.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPasswordConfirmation.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPasswordConfirmation.setText("Password confirmation");
        txtPasswordConfirmation.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblUser.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblUser.setText("User");
        txtUser.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnSave.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnSave.setText("Save");
        btnSave.addActionListener(e -> btnSaveActionPerformed());
        lblInfoError.setFont(new java.awt.Font("Yu Gothic UI", 0, 14));
        lblInfoError.setText(" ");

        javax.swing.GroupLayout info = new javax.swing.GroupLayout(jPanelInfo);
        jPanelInfo.setLayout(info);
        info.setAutoCreateGaps(true);
        info.setAutoCreateContainerGaps(true);
        info.setHorizontalGroup(info.createSequentialGroup().addGap(50)
                .addGroup(info.createParallelGroup()
                        .addGroup(info.createSequentialGroup()
                                .addComponent(lblFirstName).addComponent(txtFirstName, 130, 130, 130)
                                .addComponent(lblLastName).addComponent(txtLastname, 130, 130, 130)
                                .addComponent(lblBirthdate).addComponent(txtBirthdate, 130, 130, 130)
                                .addComponent(lblGender).addComponent(cmbGender))
                        .addGroup(info.createSequentialGroup()
                                .addComponent(lblPhone).addComponent(txtPhone, 130, 130, 130)
                                .addComponent(lblAdress).addComponent(txtAdress, 130, 130, 130)
                                .addComponent(lblEmail).addComponent(txtEmail, 130, 130, 130))
                        .addGroup(info.createSequentialGroup()
                                .addComponent(lblUser).addComponent(txtUser, 130, 130, 130)
                                .addComponent(lblPassword).addComponent(txtPassword, 130, 130, 130)
                                .addComponent(lblPasswordConfirmation)
                                .addComponent(txtPasswordConfirmation, 130, 130, 130))
                        .addComponent(lblInfoError)
                        .addComponent(btnSave))
                .addGap(0, 0, Short.MAX_VALUE));
        info.setVerticalGroup(info.createSequentialGroup().addGap(40)
                .addGroup(info.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblFirstName)
                        .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblLastName)
                        .addComponent(txtLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblBirthdate)
                        .addComponent(txtBirthdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblGender)
                        .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(info.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPhone)
                        .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblAdress)
                        .addComponent(txtAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEmail)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(info.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblUser)
                        .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblPassword)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblPasswordConfirmation)
                        .addComponent(txtPasswordConfirmation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(lblInfoError)
                .addComponent(btnSave)
                .addContainerGap(200, Short.MAX_VALUE));
        tabbedpanePatientView.addTab("Modify info", jPanelInfo);

        // ── Tab 3: Request/Cancel ──
        lblRequest.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblRequest.setText("Request medical appointment");
        radiobtnSpecialty.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        radiobtnSpecialty.setText("By Specialty");
        radiobtnSpecialty.addActionListener(e -> {
            radiobtnDoctor.setSelected(false);
            cargarComboEspecialidades();
        });
        radiobtnDoctor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        radiobtnDoctor.setText("By Doctor");
        radiobtnDoctor.addActionListener(e -> {
            radiobtnSpecialty.setSelected(false);
            cargarComboDoctores();
        });
        cmbSelectDoctor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAppointmentDate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAppointmentDate.setText("Date (YYYY-MM-DD)");
        txtAppointmentDate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAppointmentTime.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAppointmentTime.setText("Time (HH:mm)");
        txtAppointmentTime.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAppointmentType.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAppointmentType.setText("Type");
        cmbAppointmentType.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbAppointmentType.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{"Select one", "Remote", "In-person"}));
        lblAppointmentReason.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAppointmentReason.setText("Reason");
        txtareaAppointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        txtareaAppointment.setRows(4);
        jScrollPane5.setViewportView(txtareaAppointment);
        btnCreate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnCreate.setText("Create Appointment");
        btnCreate.addActionListener(e -> btnCreateActionPerformed());
        lblAppointmentError.setFont(new java.awt.Font("Yu Gothic UI", 0, 14));
        lblAppointmentError.setText(" ");

        lblRequestHospi.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblRequestHospi.setText("Request hospitalization");
        lblHospiReason.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblHospiReason.setText("Reason");
        txtareaHospiReason.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        txtareaHospiReason.setRows(4);
        jScrollPane4.setViewportView(txtareaHospiReason);
        lblAttendingDoctor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAttendingDoctor.setText("Attending doctor");
        cmbAttendingDoctor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblEstimatedDate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblEstimatedDate.setText("Estimated date (YYYY-MM-DD)");
        txtEstimDate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lbDesired.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lbDesired.setText("Room type");
        cmbRoomType.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblObservations.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblObservations.setText("Observations");
        txtareaObservations.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        txtareaObservations.setRows(4);
        jScrollPane1.setViewportView(txtareaObservations);
        btnCreateHospi.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnCreateHospi.setText("Request Hospitalization");
        btnCreateHospi.addActionListener(e -> btnCreateHospiActionPerformed());
        lblHospiError.setFont(new java.awt.Font("Yu Gothic UI", 0, 14));
        lblHospiError.setText(" ");

        lblCancel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblCancel.setText("Cancel appointment");
        lblIdAppointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblIdAppointment.setText("Appointment ID");
        cmbAppointmentCancel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblObservations1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblObservations1.setText("Observations");
        txtareaObservations1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        txtareaObservations1.setRows(4);
        jScrollPane2.setViewportView(txtareaObservations1);
        btnCancel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(e -> btnCancelActionPerformed());
        lblCancelError.setFont(new java.awt.Font("Yu Gothic UI", 0, 14));
        lblCancelError.setText(" ");

        jPanelRequest.setLayout(new java.awt.GridLayout(1, 3, 20, 0));

        javax.swing.JPanel colAppointment = new javax.swing.JPanel();
        colAppointment.setLayout(new javax.swing.BoxLayout(colAppointment, javax.swing.BoxLayout.Y_AXIS));
        colAppointment.add(lblRequest);
        colAppointment.add(javax.swing.Box.createVerticalStrut(8));
        colAppointment.add(radiobtnSpecialty);
        colAppointment.add(radiobtnDoctor);
        colAppointment.add(cmbSelectDoctor);
        colAppointment.add(javax.swing.Box.createVerticalStrut(8));
        colAppointment.add(lblAppointmentDate);
        colAppointment.add(txtAppointmentDate);
        colAppointment.add(lblAppointmentTime);
        colAppointment.add(txtAppointmentTime);
        colAppointment.add(lblAppointmentType);
        colAppointment.add(cmbAppointmentType);
        colAppointment.add(lblAppointmentReason);
        colAppointment.add(jScrollPane5);
        colAppointment.add(lblAppointmentError);
        colAppointment.add(btnCreate);

        javax.swing.JPanel colHospi = new javax.swing.JPanel();
        colHospi.setLayout(new javax.swing.BoxLayout(colHospi, javax.swing.BoxLayout.Y_AXIS));
        colHospi.add(lblRequestHospi);
        colHospi.add(javax.swing.Box.createVerticalStrut(8));
        colHospi.add(lblHospiReason);
        colHospi.add(jScrollPane4);
        colHospi.add(lblAttendingDoctor);
        colHospi.add(cmbAttendingDoctor);
        colHospi.add(lblEstimatedDate);
        colHospi.add(txtEstimDate);
        colHospi.add(lbDesired);
        colHospi.add(cmbRoomType);
        colHospi.add(lblObservations);
        colHospi.add(jScrollPane1);
        colHospi.add(lblHospiError);
        colHospi.add(btnCreateHospi);

        javax.swing.JPanel colCancel = new javax.swing.JPanel();
        colCancel.setLayout(new javax.swing.BoxLayout(colCancel, javax.swing.BoxLayout.Y_AXIS));
        colCancel.add(lblCancel);
        colCancel.add(javax.swing.Box.createVerticalStrut(8));
        colCancel.add(lblIdAppointment);
        colCancel.add(cmbAppointmentCancel);
        colCancel.add(lblObservations1);
        colCancel.add(jScrollPane2);
        colCancel.add(lblCancelError);
        colCancel.add(btnCancel);

        jPanelRequest.add(colAppointment);
        jPanelRequest.add(colHospi);
        jPanelRequest.add(colCancel);
        tabbedpanePatientView.addTab("Request/Cancel", jPanelRequest);

        // ── Root layout ──
        javax.swing.GroupLayout p1 = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(p1);
        p1.setHorizontalGroup(p1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelRound2, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabbedpanePatientView));
        p1.setVerticalGroup(p1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(p1.createSequentialGroup()
                        .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabbedpanePatientView)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pack();
    }

    private void panelRound2MousePressed(java.awt.event.MouseEvent evt) {
        x = evt.getX();
        y = evt.getY();
    }

    private void panelRound2MouseDragged(java.awt.event.MouseEvent evt) {
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }

    // ── Handlers ──
    private void btnSaveActionPerformed() {
        String genderStr = cmbGender.getSelectedIndex() == 2 ? "Male" : (cmbGender.getSelectedIndex() == 1 ? "Female" : "");
        Response r = patientController.updatePatient(
                String.valueOf(patientId), txtUser.getText(), txtFirstName.getText(), txtLastname.getText(),
                txtPassword.getText(), txtPasswordConfirmation.getText(), txtEmail.getText(), txtPhone.getText(),
                txtBirthdate.getText(), genderStr, txtAdress.getText()
        );
        lblInfoError.setText(r.getMessage());
        lblInfoError.setForeground(r.isSuccess() ? new Color(0, 180, 0) : Color.RED);
    }

    private void btnCreateActionPerformed() {
        String selected = (String) cmbSelectDoctor.getSelectedItem();
        Response r;
        if (radiobtnDoctor.isSelected()) {
            r = appointmentController.requestAppointmentByDoctor(String.valueOf(patientId), selected.split(" - ")[0], txtAppointmentDate.getText(), txtAppointmentTime.getText(), txtareaAppointment.getText());
        } else {
            r = appointmentController.requestAppointmentBySpecialty(String.valueOf(patientId), selected, txtAppointmentDate.getText(), txtAppointmentTime.getText(), txtareaAppointment.getText());
        }
        lblAppointmentError.setText(r.getMessage());
        lblAppointmentError.setForeground(r.isSuccess() ? new Color(0, 180, 0) : Color.RED);
    }

    private void btnCreateHospiActionPerformed() {
        String docId = ((String) cmbAttendingDoctor.getSelectedItem()).split(" - ")[0];
        Response r = hospitalizationController.requestHospitalization(String.valueOf(patientId), docId, txtEstimDate.getText(), txtareaHospiReason.getText(), (String) cmbRoomType.getSelectedItem(), txtareaObservations.getText());
        lblHospiError.setText(r.getMessage());
        lblHospiError.setForeground(r.isSuccess() ? new Color(0, 180, 0) : Color.RED);
    }

    private void btnCancelActionPerformed() {
        Response r = appointmentController.cancelAppointment((String) cmbAppointmentCancel.getSelectedItem(), String.valueOf(patientId));
        lblCancelError.setText(r.getMessage());
        lblCancelError.setForeground(r.isSuccess() ? new Color(0, 180, 0) : Color.RED);
    }

    // Variables declaration
    private javax.swing.JButton btnBack, btnCancel, btnCreate, btnCreateHospi,
            btnLogout, btnRefresh, btnSave, btnX;
    private javax.swing.JComboBox<String> cmbAppointmentCancel, cmbAppointmentType,
            cmbAttendingDoctor, cmbGender, cmbRoomType, cmbSelectDoctor;
    private javax.swing.JPanel jPanelHistory, jPanelInfo, jPanelRequest;
    private javax.swing.JScrollPane jScrollPane1, jScrollPane2, jScrollPane3,
            jScrollPane4, jScrollPane5;
    private javax.swing.JLabel lbDesired, lblAdress, lblAppointmentDate,
            lblAppointmentError, lblAppointmentReason, lblAppointmentTime, lblAppointmentType,
            lblAttendingDoctor, lblBirthdate, lblCancel, lblCancelError, lblEmail,
            lblEstimatedDate, lblFirstName, lblGender, lblHospiError, lblHospiReason,
            lblIdAppointment, lblInfoError, lblLastName, lblObservations, lblObservations1,
            lblPassword, lblPasswordConfirmation, lblPatientView, lblPhone, lblRequest,
            lblRequestHospi, lblUser;
    private packagee.view.PanelRound panelRound1, panelRound2;
    private javax.swing.JRadioButton radiobtnDoctor, radiobtnSpecialty;
    private javax.swing.JTabbedPane tabbedpanePatientView;
    private javax.swing.JTable tblPatientView;
    private javax.swing.JTextField txtAdress, txtAppointmentDate, txtAppointmentTime,
            txtBirthdate, txtEmail, txtEstimDate, txtFirstName, txtLastname, txtPassword,
            txtPasswordConfirmation, txtPhone, txtUser;
    private javax.swing.JTextArea txtareaAppointment, txtareaHospiReason,
            txtareaObservations, txtareaObservations1;
}
