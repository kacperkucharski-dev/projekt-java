package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SizeDialog extends JDialog {
    private JTextField field1;
    private JTextField field2;
    private Integer value1;
    private Integer value2;
    private boolean confimed = false;

    public SizeDialog(Frame owner, String title) {
        super(owner, title, true);
        initialize();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initialize() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel input = new JPanel(new GridLayout(2,2,5,5));
        input.add(new JLabel("Szerokość:"));
        field1 = new JTextField(10);
        input.add(field1);

        input.add(new JLabel("Wysokość:"));
        field2 = new JTextField(10);
        input.add(field2);

        panel.add(input, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("OK");
        buttons.add(ok);
        JButton cancel = new JButton("Anuluj");
        buttons.add(cancel);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = field1.getText();
                String w = field2.getText();
                try{
                    value1 = Integer.parseInt(s);
                    value2 = Integer.parseInt(w);
                } catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(
                            SizeDialog.this,
                            "Obie wartości muszą być liczbami w zakresie [10-100]",
                            "Błędne dane",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                if(value1 < 10 || value2 < 10 || value1 > 100 || value2 > 100){
                    JOptionPane.showMessageDialog(
                            SizeDialog.this,
                            "Obie wartości muszą być liczbami w zakresie [10-100]",
                            "Błędne dane",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                confimed = true;
                setVisible(false);
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confimed = false;
                setVisible(false);
            }
        });

        buttons.add(ok);
        buttons.add(cancel);
        panel.add(buttons, BorderLayout.SOUTH);

        setContentPane(panel);
        getRootPane().setDefaultButton(ok);
        setResizable(false);
    }

    public boolean isConfimed() {
        setVisible(true);
        return confimed;
    }

    public Integer getValue1() {
        return value1;
    }

    public Integer getValue2() {
        return value2;
    }
}
