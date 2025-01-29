package it.uniba.game.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI extends JFrame {
    private JTextArea mainTextArea;
    private JPanel sidePanel;
    private ArrayList<String> inventory;
    private JTextField inputField;
    private boolean isInventoryShowing = false;
    private boolean isMapShowing = false;

    public GUI() {
        super("Gioco Avventura Testuale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLayout(new BorderLayout());

        // Colori personalizzati
        Color darkBg = new Color(15, 23, 42);
        Color panelBg = new Color(30, 41, 59);
        Color textColor = new Color(203, 213, 225);
        Color accentRed = new Color(239, 68, 68);
        Color accentOrange = new Color(251, 146, 60);
        Color accentGreen = new Color(74, 222, 128);
        Color accentPurple = new Color(192, 132, 252); // Nuovo colore per il pulsante INVIA

        // Imposta look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crea menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Salva");
        JMenuItem saveAndExitItem = new JMenuItem("Salva ed Esci");
        JMenuItem exitItem = new JMenuItem("Esci");

        fileMenu.add(saveItem);
        fileMenu.add(saveAndExitItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Action listeners per il menu
        saveItem.addActionListener(e -> {
            // Implementa la logica di salvataggio
        });

        saveAndExitItem.addActionListener(e -> {
            // Implementa la logica di salvataggio
            System.exit(0);
        });

        exitItem.addActionListener(e -> System.exit(0));

        // Pannello superiore con pulsanti
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        topPanel.setBackground(panelBg);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        JButton helpButton = new JButton("AIUTO");
        helpButton.setForeground(accentRed);
        helpButton.setFont(new Font("Monospaced", Font.BOLD, 14));

        JButton mapButton = new JButton("MAPPA");
        mapButton.setForeground(accentOrange);
        mapButton.setFont(new Font("Monospaced", Font.BOLD, 14));

        JButton invButton = new JButton("INVENTARIO");
        invButton.setForeground(accentGreen);
        invButton.setFont(new Font("Monospaced", Font.BOLD, 14));

        // Aggiunta dei pulsanti nell'ordine richiesto
        topPanel.add(helpButton);
        topPanel.add(mapButton);
        topPanel.add(invButton);
        add(topPanel, BorderLayout.NORTH);

        // Area testo principale
        mainTextArea = new JTextArea();
        mainTextArea.setBackground(darkBg);
        mainTextArea.setForeground(textColor);
        mainTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);
        mainTextArea.setEditable(false);
        mainTextArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(mainTextArea);
        scrollPane.setBackground(darkBg);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Pannello laterale (per mappa e inventario)
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(panelBg);
        sidePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        sidePanel.setPreferredSize(new Dimension(0, 0)); // Inizialmente nascosto

        // Panel contenitore principale
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(darkBg);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(sidePanel, BorderLayout.EAST);
        add(contentPanel, BorderLayout.CENTER);

        // Pannello inferiore (input)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(panelBg);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));

        // Pannello input
        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(panelBg);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inputField.setBackground(new Color(51, 65, 85));
        inputField.setForeground(textColor);
        inputField.setCaretColor(textColor);
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JButton sendButton = new JButton("INVIA");
        sendButton.setFont(new Font("Monospaced", Font.BOLD, 14));
        sendButton.setForeground(accentPurple); // Nuovo colore per il pulsante INVIA

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        bottomPanel.add(inputPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Inizializza inventario
        inventory = new ArrayList<>();
        inventory.add("Badge della polizia");
        inventory.add("Laptop forense");
        inventory.add("Kit di analisi");
        inventory.add("Smartphone criptato");

        // Action Listeners
        mapButton.addActionListener(e -> toggleMap());
        invButton.addActionListener(e -> toggleInventory());
        helpButton.addActionListener(e -> appendToMainText("Comandi disponibili:\n- mappa: mostra la mappa\n- inventario: mostra l'inventario\n- aiuto: mostra questo messaggio\n\n"));

        ActionListener sendAction = e -> {
            String text = inputField.getText().trim().toLowerCase();
            if (!text.isEmpty()) {
                appendToMainText("> " + text + "\n\n");
                if (text.equals("mappa")) {
                    toggleMap();
                } else if (text.equals("inventario")) {
                    toggleInventory();
                } else if (text.equals("aiuto")) {
                    appendToMainText("Comandi disponibili:\n- mappa: mostra la mappa\n- inventario: mostra l'inventario\n- aiuto: mostra questo messaggio\n\n");
                }
                inputField.setText("");
            }
        };

        sendButton.addActionListener(sendAction);
        inputField.addActionListener(sendAction);

        // Testo iniziale
        appendToMainText("Posizione: Ospedale Centrale - Reparto Cardiologia\n\n");
        appendToMainText("Ti trovi nel reparto di cardiologia dell'ospedale centrale. "
                + "I monitor dei computer mostrano attività sospette nei sistemi che controllano i pacemaker. "
                + "È necessario investigare per prevenire ulteriori attacchi.\n\n");

        setLocationRelativeTo(null);
    }

    private void toggleMap() {
        isMapShowing = !isMapShowing;
        isInventoryShowing = false;
        updateSidePanel();
    }

    private void toggleInventory() {
        isInventoryShowing = !isInventoryShowing;
        isMapShowing = false;
        updateSidePanel();
    }

    private void updateSidePanel() {
        sidePanel.removeAll();
        if (!isMapShowing && !isInventoryShowing) {
            sidePanel.setPreferredSize(new Dimension(0, 0));
        } else {
            sidePanel.setPreferredSize(new Dimension(250, 0));

            if (isInventoryShowing) {
                showInventory();
            } else if (isMapShowing) {
                showMap();
            }
        }

        sidePanel.revalidate();
        sidePanel.repaint();
        this.revalidate();
    }

    private void showInventory() {
        JLabel titleLabel = new JLabel("Inventario");
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(titleLabel);
        sidePanel.add(Box.createVerticalStrut(10));

        for (String item : inventory) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setBackground(new Color(51, 65, 85));
            itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            itemPanel.setMaximumSize(new Dimension(230, 50));
            itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel itemLabel = new JLabel(item);
            itemLabel.setForeground(Color.WHITE);
            itemLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
            itemPanel.add(itemLabel);

            sidePanel.add(itemPanel);
            sidePanel.add(Box.createVerticalStrut(5));
        }
    }

    private void showMap() {
        JLabel titleLabel = new JLabel("Mappa");
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(30, 41, 59));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.add(titleLabel);

        sidePanel.add(titlePanel);
        sidePanel.add(Box.createVerticalStrut(10));

        // Aggiunta del testo descrittivo
        JTextArea textArea = new JTextArea("Sei nella reception.\nA nord la stanza cappella.\nA sud la stanza ingresso");
        textArea.setEditable(false);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(new Color(30, 41, 59));
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidePanel.add(textArea);
    }

    private void appendToMainText(String text) {
        mainTextArea.append(text);
        mainTextArea.setCaretPosition(mainTextArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}