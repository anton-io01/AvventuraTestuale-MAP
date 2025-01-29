package it.uniba.game.util;

import it.uniba.game.Engine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    private JTextArea mainTextArea;
    private JPanel sidePanel;
    private ArrayList<String> inventory;
    private JTextField inputField;
    private boolean isInventoryShowing = false;
    private boolean isMapShowing = false;
    private Engine engine; // Aggiungi un'istanza di Engine

    public GUI() {
        super("Gioco Avventura Testuale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLayout(new BorderLayout());

        // Inizializza Engine
        engine = new Engine();

        // Colori personalizzati
        Color darkBg = new Color(15, 23, 42);
        Color panelBg = new Color(30, 41, 59);
        Color textColor = new Color(203, 213, 225);
        Color accentRed = new Color(239, 68, 68);
        Color accentOrange = new Color(251, 146, 60);
        Color accentGreen = new Color(74, 222, 128);
        Color accentPurple = new Color(192, 132, 252);

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
        helpButton.setFont(new Font("Consolas", Font.BOLD, 14));

        JButton mapButton = new JButton("MAPPA");
        mapButton.setForeground(accentOrange);
        mapButton.setFont(new Font("Consolas", Font.BOLD, 14));

        JButton invButton = new JButton("INVENTARIO");
        invButton.setForeground(accentGreen);
        invButton.setFont(new Font("Consolas", Font.BOLD, 14));

        // Aggiunta dei pulsanti nell'ordine richiesto
        topPanel.add(helpButton);
        topPanel.add(mapButton);
        topPanel.add(invButton);
        add(topPanel, BorderLayout.NORTH);

        // Area testo principale
        mainTextArea = new JTextArea();
        mainTextArea.setBackground(darkBg);
        mainTextArea.setForeground(textColor);
        mainTextArea.setFont(new Font("Consolas", Font.PLAIN, 14));
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
        inputField.setFont(new Font("Consolas", Font.PLAIN, 14));
        inputField.setBackground(new Color(51, 65, 85));
        inputField.setForeground(textColor);
        inputField.setCaretColor(textColor);
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JButton sendButton = new JButton("INVIA");
        sendButton.setFont(new Font("Consolas", Font.BOLD, 14));
        sendButton.setForeground(accentPurple);

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        bottomPanel.add(inputPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        mapButton.addActionListener(e -> {
            String output = engine.processCommand("mappa");
            if (output != null) {
                appendToMainText(output);
                isMapShowing = false;
                isInventoryShowing = false;
                updateSidePanel();
            }
        });
        invButton.addActionListener(e -> {
            isInventoryShowing = !isInventoryShowing;
            isMapShowing = false;
            updateSidePanel();
        });
        helpButton.addActionListener(e -> {
            String output = engine.processCommand("aiuto");
            if (output != null) {
                appendToMainText(output);
                isMapShowing = false;
                isInventoryShowing = false;
                updateSidePanel();
            }
        });

        ActionListener sendAction = e -> {
            String text = inputField.getText().trim().toLowerCase();
            if (!text.isEmpty()) {
                appendToMainText("> " + text + "\n\n");
                Object output = engine.processCommand(text);
                if (text.startsWith("inventario")) {
                    isInventoryShowing = true;
                    isMapShowing = false;
                    updateSidePanel();
                } else if (text.startsWith("mappa")) {
                    if (output instanceof String) {
                        appendToMainText((String) output);
                        isMapShowing = false;
                        isInventoryShowing = false;
                        updateSidePanel();
                    }
                } else if (output instanceof String) {
                    appendToMainText((String) output);
                    isMapShowing = false;
                    isInventoryShowing = false;
                    updateSidePanel();
                } else {
                    isInventoryShowing = false;
                    isMapShowing = false;
                    updateSidePanel();
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
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(titleLabel);
        sidePanel.add(Box.createVerticalStrut(10));

        String output = (String) engine.processCommand("inventario");

        if (output != null) {
            String[] items = output.split("\n");
            for (String item : items) {
                if (!item.trim().isEmpty()) {
                    JPanel itemPanel = new JPanel();
                    itemPanel.setLayout(new GridBagLayout());
                    itemPanel.setBackground(new Color(51, 65, 85));
                    itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    itemPanel.setMaximumSize(new Dimension(230, 50));
                    itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                    JLabel itemLabel = new JLabel(item.replace("- ", ""));
                    Font currentFont = itemLabel.getFont();
                    Font newFont = currentFont.deriveFont(currentFont.getSize() + 2f);

                    itemLabel.setForeground(Color.WHITE);
                    itemLabel.setFont(newFont);


                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.weightx = 1.0; // Utilizzare lo spazio orizzontale disponibile
                    gbc.anchor = GridBagConstraints.WEST; // Allineamento a sinistra
                    gbc.fill = GridBagConstraints.HORIZONTAL;  // espandi orizzontalmente per allineamento altezza
                    itemPanel.add(itemLabel, gbc);

                    sidePanel.add(itemPanel);
                    sidePanel.add(Box.createVerticalStrut(5));
                }
            }
        }
    }


    private void showMap() {
        JLabel titleLabel = new JLabel("Mappa");
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(30, 41, 59));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.add(titleLabel);

        sidePanel.add(titlePanel);
        sidePanel.add(Box.createVerticalStrut(10));

        String output = engine.processCommand("mappa");
        JTextArea textArea = new JTextArea(output);
        textArea.setEditable(false);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(new Color(30, 41, 59));
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidePanel.add(textArea);
    }


    private void appendToMainText(String text) {
        try {
            String utf8Text = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            mainTextArea.append(utf8Text);
            mainTextArea.setCaretPosition(mainTextArea.getDocument().getLength());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}