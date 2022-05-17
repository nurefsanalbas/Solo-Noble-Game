/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.nurefsanalbas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author nurefsanalbas
 */
class PegButton extends JButton {

    Peg peg;
}

class Board {

    Peg start;
    Peg head;

    public void create(int size, javax.swing.JPanel gamePanel) {
        start = new Peg(0, 1, "X", null, null);
        head = start;
        head.createDownPeg(size, 1, 1, null);
        head = head.downPeg;
        head.removeMiddleFour(size);
        createBoardLabels(size, gamePanel);
        head.printRow(gamePanel);
    }

    private void createBoardLabels(int size, javax.swing.JPanel gamePanel) {

        int x = 270;
        int y = 160;
        for (int i = 0; i < size; i++) {
            char letter = (char) (65 + i);
            JLabel lbl = new javax.swing.JLabel();
            lbl.setText(Character.toString(letter));
            lbl.setBounds(x, y, 50, 50);
            x += 50;
            gamePanel.add(lbl);
        }
        x = 230;
        y = 200;
        for (int i = 1; i <= size; i++) {
            JLabel lbl = new javax.swing.JLabel();
            lbl.setText(Integer.toString(i));
            lbl.setBounds(x, y, 50, 50);
            y += 50;
            gamePanel.add(lbl);
        }
    }
}

class Peg implements ActionListener {

    public int letter;
    public int num;
    public String data;
    public Peg nextPeg;
    public Peg prevPeg;
    public Peg upPeg;
    public Peg downPeg;
    public PegButton pBtn;
    static int x = 200;
    static int y = 200;
    static PegButton selectedBtn;

    public Peg(int letter, int num, String data, Peg downPeg, Peg prevPeg) {
        this.letter = letter;
        this.num = num;
        this.data = data;
        this.downPeg = downPeg;
        this.prevPeg = prevPeg;
        if (this.downPeg != null) {
            this.downPeg.upPeg = this;
        }
    }

    public Peg(int letter, int num, String data, Peg upPeg) {
        this.letter = letter;
        this.num = num;
        this.data = data;
        this.upPeg = upPeg;
    }

    public void print(javax.swing.JPanel gamePanel) {
        Peg.x += 50;
        PegButton pBtn = new PegButton();
        pBtn.peg = this;
        this.pBtn = pBtn;
        pBtn.setText(data);
        pBtn.setBounds(Peg.x, Peg.y, 50, 50);
        gamePanel.add(pBtn);
        gamePanel.setVisible(true);
        if (nextPeg != null) {
            nextPeg.print(gamePanel);
        } else {
            gamePanel.revalidate();
            gamePanel.repaint();
        }
        pBtn.addActionListener(this);
    }

    public void printRow(javax.swing.JPanel gamePanel) {
        print(gamePanel);
        Peg.y += 50;
        Peg.x = 200;
        if (downPeg != null) {
            downPeg.printRow(gamePanel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PegButton pBtn = (PegButton) e.getSource();
        if (Peg.selectedBtn == null) {
            Peg.selectedBtn = pBtn;
            showAvailablePegs(pBtn);
        } else {
            check(pBtn);
        }
    }

    public void showAvailablePegs(PegButton pBtn) {
        if (pBtn.peg.nextPeg != null && pBtn.peg.nextPeg.nextPeg != null && pBtn.peg.nextPeg.nextPeg.data.equals("")) {
            pBtn.peg.nextPeg.nextPeg.pBtn.setBackground(Color.red);
        }
        if (pBtn.peg.prevPeg != null && pBtn.peg.prevPeg.prevPeg != null && pBtn.peg.prevPeg.prevPeg.data.equals("")) {
            pBtn.peg.prevPeg.prevPeg.pBtn.setBackground(Color.red);
        }
        if (pBtn.peg.upPeg != null && pBtn.peg.upPeg.upPeg != null && pBtn.peg.upPeg.upPeg.data.equals("")) {
            pBtn.peg.upPeg.upPeg.pBtn.setBackground(Color.red);
        }
        if (pBtn.peg.downPeg != null && pBtn.peg.downPeg.downPeg != null && pBtn.peg.downPeg.downPeg.data.equals("")) {
            pBtn.peg.downPeg.downPeg.pBtn.setBackground(Color.red);
        }
    }

    public void check(PegButton pBtn) {
        if (pBtn.peg.equals("X")) {
            pBtn.setBackground(Color.GRAY);
            removeSelectedPeg();
        } else if (Peg.selectedBtn.peg.nextPeg != null && pBtn.peg == Peg.selectedBtn.peg.nextPeg.nextPeg) {
            pBtn.peg.prevPeg.data = "";
            pBtn.peg.data = "X";
            pBtn.setText("X");
            pBtn.setBackground(Color.GRAY);
            pBtn.peg.prevPeg.pBtn.setText("");
            removeSelectedPeg();
        } else if (Peg.selectedBtn.peg.prevPeg != null && pBtn.peg == Peg.selectedBtn.peg.prevPeg.prevPeg) {
            pBtn.peg.nextPeg.data = "";
            pBtn.peg.data = "X";
            pBtn.setText("X");
            pBtn.setBackground(Color.GRAY);
            pBtn.peg.nextPeg.pBtn.setText("");
            removeSelectedPeg();
        } else if (Peg.selectedBtn.peg.upPeg != null && pBtn.peg == Peg.selectedBtn.peg.upPeg.upPeg) {
            pBtn.peg.downPeg.data = "";
            pBtn.peg.data = "X";
            pBtn.setText("X");
            pBtn.setBackground(Color.GRAY);
            pBtn.peg.downPeg.pBtn.setText("");
            removeSelectedPeg();
        } else if (Peg.selectedBtn.peg.downPeg != null && pBtn.peg == Peg.selectedBtn.peg.downPeg.downPeg) {
            pBtn.peg.upPeg.data = "";
            pBtn.peg.data = "X";
            pBtn.setText("X");
            pBtn.setBackground(Color.GRAY);
            pBtn.peg.upPeg.pBtn.setText("");
            removeSelectedPeg();
        }
    }

    public void removeSelectedPeg() {
        Peg.selectedBtn.peg.data = "";
        Peg.selectedBtn.setText(" ");
        Peg.selectedBtn = null;
    }
    
    public void createNextPeg(int size, int letter, int num, Peg traversePeg, Peg prevPeg) {
        if (size < letter) {
            return;
        }

        nextPeg = new Peg(letter, num, "X", traversePeg == null ? null : traversePeg.nextPeg, prevPeg);
        nextPeg.createNextPeg(size, ++letter, num, traversePeg == null ? null : traversePeg.nextPeg, nextPeg);
    }

    public void createDownPeg(int size, int letter, int num, Peg upPeg) {
        if (size < num) {
            return;
        }
        int numS = num;
        downPeg = new Peg(letter, num, data, upPeg);
        downPeg.createDownPeg(size, letter, ++num, downPeg);
        downPeg.createNextPeg(size, ++letter, numS, downPeg.downPeg, downPeg);
    }

    public void removeMiddleFour(int size) {
        int midFour = size / 2;
        if (letter == midFour || letter == midFour + 1) {
            if (num == midFour || num == midFour + 1) {
                data = "";
            }
        }
        if (downPeg != null) {
            downPeg.removeMiddleFour(size);
        }
        if (nextPeg != null) {
            nextPeg.removeMiddleFour(size);
        }
    }
}

public class nurefsanAlbasMainClass extends javax.swing.JFrame {

    /**
     * Creates new form nurefsanAlbasMainClass
     */
    public nurefsanAlbasMainClass() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gamePanel = new javax.swing.JPanel();
        lbl_soloNable = new javax.swing.JLabel();
        lbl_size = new javax.swing.JLabel();
        txt_size = new javax.swing.JTextField();
        btn_create = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        gamePanel.setBackground(new java.awt.Color(255, 204, 204));

        lbl_soloNable.setFont(new java.awt.Font("Zapfino", 1, 24)); // NOI18N
        lbl_soloNable.setText("SOLO NOBLE GAME");

        lbl_size.setText("Please, enter game board size:");

        btn_create.setText("CREATE");
        btn_create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_createActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(lbl_soloNable))
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(lbl_size)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_size, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_create)))
                .addContainerGap(157, Short.MAX_VALUE))
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbl_soloNable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_size)
                    .addComponent(txt_size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_create, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(326, Short.MAX_VALUE))
        );

        getContentPane().add(gamePanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_createActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_createActionPerformed
        int size = Integer.parseInt(txt_size.getText());
        Board b = new Board();
        b.create(size, gamePanel);
        gamePanel.revalidate();
        gamePanel.repaint();
    }//GEN-LAST:event_btn_createActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(nurefsanAlbasMainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(nurefsanAlbasMainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(nurefsanAlbasMainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(nurefsanAlbasMainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new nurefsanAlbasMainClass().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_create;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JLabel lbl_size;
    private javax.swing.JLabel lbl_soloNable;
    private javax.swing.JTextField txt_size;
    // End of variables declaration//GEN-END:variables
}
