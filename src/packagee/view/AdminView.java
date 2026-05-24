/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.view;

import java.util.HashMap;
import javax.swing.JOptionPane;
import packagee.controller.DataController;
import packagee.controller.DoctorController;
import packagee.model.Specialty;
import packagee.model.storage.StorageHospital;
import packagee.util.Observer;
import packagee.util.Response;

/**
 * @author jjlora
 * @author edangulo
 */
public class AdminView extends javax.swing.JFrame implements Observer {

    private int x, y;
    private HashMap<String, Object> userData;

    public AdminView(HashMap<String, Object> userData) {
        initComponents();
        this.userData = userData;
        loadComboBoxes();
        this.setSize(1400, 717);
        this.setLocationRelativeTo(null);
        StorageHospital.getInstance().addObserver(this);
    }

    private void loadComboBoxes() {
        StorageHospital storage = StorageHospital.getInstance();

        cmbDoctor.removeAllItems();
        cmbDoctor.addItem("Select one");
        for (packagee.model.Doctor doc : storage.getAllDoctors().values()) {
            cmbDoctor.addItem(doc.getId() + " - " + doc.getFirstname() + " " + doc.getLastname());
        }

        cmbPatient.removeAllItems();
        cmbPatient.addItem("Select one");
        for (packagee.model.Patient p : storage.getAllPatients().values()) {
            cmbPatient.addItem(p.getId() + " - " + p.getFirstname() + " " + p.getLastname());
        }
    }

    public void update(String event) {
        loadComboBoxes();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        panelRound1 = new packagee.view.PanelRound();
        panelRound2 = new packagee.view.PanelRound();
        btnX = new javax.swing.JButton();
        lblAdminView = new javax.swing.JLabel();
        panelRound3 = new packagee.view.PanelRound();
        btnDoctorView = new javax.swing.JButton();
        btnPatientView = new javax.swing.JButton();
        lblFirstName = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblSpecialty = new javax.swing.JLabel();
        lblLicenseNumber = new javax.swing.JLabel();
        txtLicenseNumber = new javax.swing.JTextField();
        lblAssignedOffice = new javax.swing.JLabel();
        txtAssignedOffice = new javax.swing.JTextField();
        lblUser = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        lblPasswordConfirmation = new javax.swing.JLabel();
        txtPasswordConfirmation = new javax.swing.JTextField();
        cmbSpecialty = new javax.swing.JComboBox<>();
        btnSave = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        cmbDoctor = new javax.swing.JComboBox<>();
        lblDoctor = new javax.swing.JLabel();
        lblPatient = new javax.swing.JLabel();
        cmbPatient = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        btnLogout = new javax.swing.JButton();

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

        lblAdminView.setFont(new java.awt.Font("Yu Gothic UI", 0, 14));
        lblAdminView.setText("ADMIN VIEW");

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
                panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblAdminView)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnX)
                                .addGap(19, 19, 19))
        );
        panelRound2Layout.setVerticalGroup(
                panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblAdminView))
        );

        btnDoctorView.setFont(new java.awt.Font("Yu Gothic UI", 1, 18));
        btnDoctorView.setText("DOCTOR VIEW");
        btnDoctorView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoctorViewActionPerformed(evt);
            }
        });

        btnPatientView.setFont(new java.awt.Font("Yu Gothic UI", 1, 18));
        btnPatientView.setText("PATIENT VIEW");
        btnPatientView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPatientViewActionPerformed(evt);
            }
        });

        lblFirstName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblFirstName.setText("Firstname");
        txtFirstName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblLastName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblLastName.setText("Lastname");
        txtLastName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblId.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblId.setText("ID");
        txtId.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblSpecialty.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblSpecialty.setText("Specialty");
        lblLicenseNumber.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblLicenseNumber.setText("License Number");
        txtLicenseNumber.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAssignedOffice.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblAssignedOffice.setText("Assigned office");
        txtAssignedOffice.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblUser.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblUser.setText("User");
        txtUser.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPassword.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPassword.setText("Password");
        txtPassword.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPasswordConfirmation.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPasswordConfirmation.setText("Password confirmation");
        txtPasswordConfirmation.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));

        cmbSpecialty.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbSpecialty.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one", "GENERAL_MEDICINE", "CARDIOLOGY", "PEDIATRICS", "NEUROLOGY", "TRAUMATOLOGY_ORTHOPEDICS", "GYNECOLOGY_OBSTETRICS", "DERMATOLOGY", "PSYCHIATRY", "ONCOLOGY", "OPHTHALMOLOGY", "INTERNAL_MEDICINE"}));
        cmbSpecialty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSpecialtyActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        cmbDoctor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbDoctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));

        lblDoctor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblDoctor.setText("Doctor");
        lblPatient.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        lblPatient.setText("Patient");

        cmbPatient.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        cmbPatient.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select one"}));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnLogout.setFont(new java.awt.Font("Yu Gothic UI", 0, 18));
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
                panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelRound3Layout.createSequentialGroup()
                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelRound3Layout.createSequentialGroup()
                                                .addGap(311, 311, 311)
                                                .addComponent(btnSave)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(panelRound3Layout.createSequentialGroup()
                                                .addGap(32, 32, 32)
                                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(panelRound3Layout.createSequentialGroup()
                                                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblFirstName)
                                                                        .addComponent(lblSpecialty))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(panelRound3Layout.createSequentialGroup()
                                                                                .addComponent(cmbSpecialty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(lblLicenseNumber)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(txtLicenseNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(panelRound3Layout.createSequentialGroup()
                                                                                .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(35, 35, 35)
                                                                                .addComponent(lblLastName)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(lblId)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(panelRound3Layout.createSequentialGroup()
                                                                .addComponent(lblAssignedOffice)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(txtAssignedOffice, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addGroup(panelRound3Layout.createSequentialGroup()
                                                                        .addComponent(lblUser)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(panelRound3Layout.createSequentialGroup()
                                                                        .addComponent(lblPassword)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(panelRound3Layout.createSequentialGroup()
                                                                        .addComponent(lblPasswordConfirmation)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(txtPasswordConfirmation, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnDoctorView)
                                                        .addGroup(panelRound3Layout.createSequentialGroup()
                                                                .addGap(12, 12, 12)
                                                                .addComponent(cmbDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(panelRound3Layout.createSequentialGroup()
                                                                .addGap(47, 47, 47)
                                                                .addComponent(lblDoctor)))
                                                .addGap(74, 74, 74))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound3Layout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnLogout)
                                                .addGap(318, 318, 318)))
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelRound3Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnPatientView)
                                                        .addGroup(panelRound3Layout.createSequentialGroup()
                                                                .addGap(13, 13, 13)
                                                                .addComponent(cmbPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(panelRound3Layout.createSequentialGroup()
                                                .addGap(59, 59, 59)
                                                .addComponent(lblPatient)))
                                .addGap(88, 88, 88))
                        .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound3Layout.createSequentialGroup()
                                        .addContainerGap(707, Short.MAX_VALUE)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(523, 523, 523)))
        );
        panelRound3Layout.setVerticalGroup(
                panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelRound3Layout.createSequentialGroup()
                                .addComponent(jSeparator1)
                                .addContainerGap())
                        .addGroup(panelRound3Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblFirstName)
                                        .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblLastName)
                                        .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblId)
                                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblSpecialty)
                                        .addComponent(cmbSpecialty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblLicenseNumber)
                                        .addComponent(txtLicenseNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblAssignedOffice)
                                        .addComponent(txtAssignedOffice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelRound3Layout.createSequentialGroup()
                                                .addGap(81, 81, 81)
                                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblUser)
                                                        .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblPassword)
                                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(15, 15, 15)
                                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblPasswordConfirmation)
                                                        .addComponent(txtPasswordConfirmation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(panelRound3Layout.createSequentialGroup()
                                                .addGap(36, 36, 36)
                                                .addComponent(lblDoctor)
                                                .addGap(18, 18, 18)
                                                .addComponent(cmbDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(43, 43, 43)
                                                .addComponent(btnDoctorView)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                                .addComponent(btnSave)
                                .addGap(123, 123, 123)
                                .addComponent(btnLogout)
                                .addGap(38, 38, 38))
                        .addGroup(panelRound3Layout.createSequentialGroup()
                                .addGap(203, 203, 203)
                                .addComponent(lblPatient)
                                .addGap(18, 18, 18)
                                .addComponent(cmbPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(btnPatientView)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelRound3Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jSeparator2)
                                        .addContainerGap()))
        );

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
                panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelRound3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelRound1Layout.setVerticalGroup(
                panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelRound1Layout.createSequentialGroup()
                                .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelRound3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        // Registrar doctor
        DoctorController controller = new DoctorController();
        String selectedSpec = (String) cmbSpecialty.getSelectedItem();
        Response response = controller.registerDoctor(
                txtId.getText(),
                txtUser.getText(),
                txtFirstName.getText(),
                txtLastName.getText(),
                txtPassword.getText(),
                txtPasswordConfirmation.getText(),
                selectedSpec,
                txtLicenseNumber.getText(),
                txtAssignedOffice.getText()
        );
        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            loadComboBoxes();
            txtId.setText("");
            txtFirstName.setText("");
            txtLastName.setText("");
            txtUser.setText("");
            txtPassword.setText("");
            txtPasswordConfirmation.setText("");
            txtLicenseNumber.setText("");
            txtAssignedOffice.setText("");
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnDoctorViewActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedDoctor = (String) cmbDoctor.getSelectedItem();
        
        long doctorId = Long.parseLong(selectedDoctor.split(" - ")[0]);
        packagee.model.Doctor doc = StorageHospital.getInstance().getDoctor(doctorId);
       
        HashMap<String, Object> docData = doc.serialize();
        docData.put("role", "DOCTOR");
        new DoctorView(docData, true).setVisible(true);
        this.dispose();
    }

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        StorageHospital.getInstance().removeObserver(this);
        new LoginView().setVisible(true);
        this.dispose();
    }

    private void btnPatientViewActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedPatient = (String) cmbPatient.getSelectedItem();
        long patientId = Long.parseLong(selectedPatient.split(" - ")[0]);
        packagee.model.Patient pat = StorageHospital.getInstance().getPatient(patientId);
        HashMap<String, Object> patData = pat.serialize();
        patData.put("role", "PATIENT");
        new PatientView(patData, true).setVisible(true);
        this.dispose();
    }

    private void cmbSpecialtyActionPerformed(java.awt.event.ActionEvent evt) {
        // no action needed
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btnDoctorView;
    private javax.swing.JButton btnPatientView;
    private javax.swing.JComboBox<String> cmbDoctor;
    private javax.swing.JComboBox<String> cmbPatient;
    private javax.swing.JComboBox<String> cmbSpecialty;
    private javax.swing.JButton btnX;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSave;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblAdminView;
    private javax.swing.JLabel lblAssignedOffice;
    private javax.swing.JLabel lblDoctor;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblLicenseNumber;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPasswordConfirmation;
    private javax.swing.JLabel lblPatient;
    private javax.swing.JLabel lblSpecialty;
    private javax.swing.JLabel lblUser;
    private packagee.view.PanelRound panelRound1;
    private packagee.view.PanelRound panelRound2;
    private packagee.view.PanelRound panelRound3;
    private javax.swing.JTextField txtAssignedOffice;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtLicenseNumber;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtPasswordConfirmation;
    private javax.swing.JTextField txtUser;
    // End of variables declaration
}
