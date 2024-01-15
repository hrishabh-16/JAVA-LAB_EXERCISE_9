import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class ElectionParticipant {
    String name;
    String contact;
    int age;
    boolean isVoter;
    boolean isCandidate;

    public ElectionParticipant(String name, String contact, int age, boolean isVoter, boolean isCandidate) {
        this.name = name;
        this.contact = contact;
        this.age = age;
        this.isVoter = isVoter;
        this.isCandidate = isCandidate;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Contact: " + contact + ", Age: " + age +
                ", Voter: " + (isVoter ? "Yes" : "No") + ", Candidate: " + (isCandidate ? "Yes" : "No");
    }
}

public class OnlineElectionSystem extends JFrame {
    private List<ElectionParticipant> participants;
    private JTextArea participantListArea;
    private JTextField nameField;
    private JTextField contactField;
    private JSpinner ageSpinner;
    private JCheckBox voterCheckBox;
    private JCheckBox candidateCheckBox;

    public OnlineElectionSystem() {
        participants = new ArrayList<>();

        setTitle("Online Election System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();

        setVisible(true);
    }

    private void createUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        participantListArea = new JTextArea();
        participantListArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(participantListArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));

        inputPanel.add(new JLabel("Participant Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Participant Contact:"));
        contactField = new JTextField();
        inputPanel.add(contactField);

        inputPanel.add(new JLabel("Participant Age:"));
        SpinnerModel ageModel = new SpinnerNumberModel(18, 18, 100, 1);
        ageSpinner = new JSpinner(ageModel);
        JSpinner.NumberEditor ageEditor = new JSpinner.NumberEditor(ageSpinner);
        ageSpinner.setEditor(ageEditor);
        inputPanel.add(ageSpinner);

        inputPanel.add(new JLabel("Register as Voter:"));
        voterCheckBox = new JCheckBox();
        inputPanel.add(voterCheckBox);

        inputPanel.add(new JLabel("Register as Candidate:"));
        candidateCheckBox = new JCheckBox();
        inputPanel.add(candidateCheckBox);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerParticipant();
            }
        });

        inputPanel.add(new JLabel());
        inputPanel.add(registerButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem viewParticipantsItem = new JMenuItem("View Participants");
        viewParticipantsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewParticipants();
            }
        });

        JMenuItem clearParticipantsItem = new JMenuItem("Clear Participants");
        clearParticipantsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearParticipants();
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(viewParticipantsItem);
        fileMenu.addSeparator();
        fileMenu.add(clearParticipantsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        add(mainPanel);
    }

    private void registerParticipant() {
        String name = nameField.getText();
        String contact = contactField.getText();
        int age = (int) ageSpinner.getValue();
        boolean isVoter = voterCheckBox.isSelected();
        boolean isCandidate = candidateCheckBox.isSelected();

        if (validateName(name) && validateContact(contact)) {
            ElectionParticipant participant = new ElectionParticipant(name, contact, age, isVoter, isCandidate);
            participants.add(participant);

            updateParticipantList();

            nameField.setText("");
            contactField.setText("");
            ageSpinner.setValue(18);
            voterCheckBox.setSelected(false);
            candidateCheckBox.setSelected(false);

            JOptionPane.showMessageDialog(this, "Participant registered successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean validateName(String name) {
        if (name.isEmpty() || !name.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid name with only alphabetic characters.",
                    "Invalid Name", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateContact(String contact) {
        if (contact.isEmpty() || !contact.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid contact with only numeric characters.",
                    "Invalid Contact", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void viewParticipants() {
        StringBuilder participantList = new StringBuilder("Participant List:\n");
        for (ElectionParticipant participant : participants) {
            participantList.append(participant.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(this, participantList.toString(), "View Participants",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearParticipants() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear the participant list?",
                "Clear Participants", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            participants.clear();
            updateParticipantList();
        }
    }

    private void updateParticipantList() {
        participantListArea.setText("");
        for (ElectionParticipant participant : participants) {
            participantListArea.append(participant.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OnlineElectionSystem();
            }
        });
    }
}
