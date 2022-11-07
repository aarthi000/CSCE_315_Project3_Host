import java.awt.event.ActionListener;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.io.*;

/**
 * This class contains the action listener associated with all frontend classes
 * except customize window.
 */
public class OrderWindowActionListener implements ActionListener {
    OrderWindow o;
    MenuItem m;
    JFrame f;
    JComboBox j;
    JLabel lbl;

    /*
     * myActionListener(OrderWindow o): This constructor is normally not
     * parameterized. We NEED a paramterized contstructor so
     * we can pass in the OrderWindow object, which keeps track of the current
     * order.
     */

    /**
     * Parameterized constructor so can pass in the OrderWindow object, which keeps
     * track of the current
     * order, and additional objects relevant to button function.
     * 
     * @param order object that allows you to access all of the methods to
     *              manipulate
     *              the inventory
     */
    public OrderWindowActionListener(OrderWindow order) {
        o = order;
    }

    /**
     * Parameterized constructor so can pass in the OrderWindow object, which keeps
     * track of the current
     * order, and additional objects relevant to button function.
     * 
     * @param order object that allows you to access all of the methods to
     *              manipulate the inventory
     * @param item  object that allows you to access the MenuItem class
     */
    public OrderWindowActionListener(OrderWindow order, MenuItem item) {
        o = order;
        m = item;
    }

    /**
     * Parameterized constructor so can pass in the OrderWindow object, which keeps
     * track of the current
     * order, and additional objects relevant to button function.
     * 
     * @param order object that allows you to access all of the methods to
     *              manipulate the inventory
     * @param item  object that allows you to access the MenuItem class
     * @param j     jlabel for front-end portion of actionlistener
     * @param frame object that allows you to access the MenuItem class
     */
    public OrderWindowActionListener(OrderWindow order, MenuItem item, JLabel j, JFrame frame) {
        o = order;
        m = item;
        lbl = j;

        f = frame;
    }

    /**
     * Parameterized constructor so can pass in the OrderWindow object, which keeps
     * track of the current
     * order, and additional objects relevant to button function.
     * 
     * @param order object that allows you to access all of the methods to
     *              manipulate the inventory
     * @param item  object that allows you to access the MenuItem class
     * @param frame object that allows you to access the MenuItem class
     */
    public OrderWindowActionListener(OrderWindow order, MenuItem item, JFrame frame) {
        o = order;
        m = item;
        f = frame;
    }

    /**
     * Parameterized constructor so can pass in the OrderWindow object, which keeps
     * track of the current
     * order, and additional objects relevant to button function.
     * 
     * @param order object that allows you to access all of the methods to
     *              manipulate the inventory
     * @param box   initializes combo box
     */
    public OrderWindowActionListener(OrderWindow order, JComboBox box) {
        o = order;
        j = box;
    }

    /**
     * Performs the action for a button if text matches what is expected.
     * 
     * @param e Action event for checking what was clicked in
     */
    public void actionPerformed(ActionEvent e) {

        // Autogenerated buttons
        // - button
        if (((JButton) e.getSource()).getText().equals("-")) {
            o.deleteMenuItem(m);
            // System.out.println("- clicked");
            // System.out.println(o.currOrder);

        }

        // + button
        if (((JButton) e.getSource()).getText().equals("+")) {
            try {
                o.addMenuItem(m);
                // System.out.println("+ clicked");
                // System.out.println(o.currOrder);

            } catch (Exception error) {
                error.printStackTrace();
                System.err.println(error.getClass().getName() + ": " + error.getMessage());
                System.exit(0);
            }

        }

        // Customize
        if (((JButton) e.getSource()).getText().equals("Customize")) {
            // System.out.println("Customize order clicked");
            customizeWindow w = new customizeWindow(o, m);

            w.f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            w.f.setVisible(true);
        }

        // edit price feature
        if (((JButton) e.getSource()).getText().equals("Edit Price")) {
            String price = JOptionPane.showInputDialog(f, "Enter new price: ");// Note: input can be null.
            try {
                o.editPrice(m, Double.parseDouble(price));
            } catch (Exception error) {
                // TODO: handle exception
                error.printStackTrace();
                System.err.println(error.getClass().getName() + ": " + error.getMessage());
                System.exit(0);
            }
        }

        // Buttons for whole page
        // Place Order button
        if (((JButton) e.getSource()).getText().equals("Place Order")) {
            try {
                o.prevOrderList = o.placeOrder();
                o.prevOrderExists = true;

            } catch (Exception error) {
                error.printStackTrace();
                System.err.println(error.getClass().getName() + ": " + error.getMessage());
                System.exit(0);
            }
        }

        // Remove last order button
        if (((JButton) e.getSource()).getText().equals("Remove Last Order")) {
            try {

                if (!o.prevOrderExists) {
                    return;
                }
                o.deletePrevOrder(o.prevOrderList);
                o.prevOrderExists = false;
                System.out.println("Remove Last Order clicked");

            } catch (Exception error) {
                error.printStackTrace();
                System.err.println(error.getClass().getName() + ": " + error.getMessage());
                System.exit(0);
            }
        }

        if (((JButton) e.getSource()).getText().equals("Inventory")) {
            JPasswordField pwd = new JPasswordField(10);
            int action = JOptionPane.showConfirmDialog(null, pwd, "Enter Password", JOptionPane.OK_CANCEL_OPTION);
            if (action != 0) // Cancel pressed
                return;

            String password = new String(pwd.getPassword());
            if (!password.equals("AggiePride")) {
                JOptionPane.showMessageDialog(f, "Invalid Password", "Invalid Password", JOptionPane.ERROR_MESSAGE);
                return;
            }
            InventoryTable w = new InventoryTable(o);
            w.f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            w.f.setVisible(true);
        }

        // toggle button indicating game day
        if (((JButton) e.getSource()).getText().equals("Game Day")) {
            System.out.println("GameDay clicked");
            Proj2HelperClass.toggleGameDay();
            if (o.isGameDay) {
                o.isGameDay = false;
            } else {
                o.isGameDay = true;
            }
            System.out.println(o.isGameDay);
        }

        // add item feature
        if (((JButton) e.getSource()).getText().equals("Add New Menu Item")) {
        }
        
        if (((JButton) e.getSource()).getText().equals("Edit Minimum Value")) {
            String minimumValueStr = JOptionPane.showInputDialog(f, "Enter the Minimum Value: ");// Note: input can be
            if (minimumValueStr == null) {
                return;
            }
            Double num = Double.parseDouble(minimumValueStr);
            String ingredient = j.getSelectedItem().toString();
            try {
                o.changeMinimumAmount(ingredient, num);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        // order summary
        if (((JButton) e.getSource()).getText().equals("Order Summary")) {
            o.loadCurrSummaryWindow();
        }

        /* Adding new menu items in OrderWindow */

        // Add the Selected Ingredient for Adding New Menu Item
        if (((JButton) e.getSource()).getText().equals("Add the Selected Ingredient")) {
            String numIngred = JOptionPane.showInputDialog(f, "Number of Selected Ingredient ");// Note: input can be
                                                                                                // null.
            Double num = Double.parseDouble(numIngred);
            String ingredient = j.getSelectedItem().toString();

            o.newIngredientMap.put(ingredient, num);
        }

        if (((JButton) e.getSource()).getText().equals("Input Price of New Menu Item")) {
            String price = JOptionPane.showInputDialog(f, "Enter price of new menu item (must be a Double)");
            Double num = Double.parseDouble(price);

            o.newPrice = num;

        }

        if (((JButton) e.getSource()).getText().equals("Name New Menu Item")) {
            o.newItemName = JOptionPane.showInputDialog(f, "Enter the name of the new menu item.");

        }

        if (((JButton) e.getSource()).getText().equals("Add Type of New Menu Item")) {
            o.newItemType = JOptionPane.showInputDialog(f, "Enter the type of the new menu item.");
        }

        if (((JButton) e.getSource()).getText().equals("Add New Menu Item")) {
            JPasswordField pwd = new JPasswordField(10);
            int action = JOptionPane.showConfirmDialog(null, pwd, "Enter Password", JOptionPane.OK_CANCEL_OPTION);
            if (action != 0) // Cancel pressed
                return;

            String password = new String(pwd.getPassword());
            if (!password.equals("AggiePride")) {
                JOptionPane.showMessageDialog(f, "Invalid Password", "Invalid Password", JOptionPane.ERROR_MESSAGE);
                return;
            }
            o.addNewItem_button();
            
        }

        /* Buttons for Inventory Table */
        // edit inventory featuree
        if (((JButton) e.getSource()).getText().equals("Update Stock")) {
            String remaining = JOptionPane.showInputDialog(f, "Remaining Stock: ");// Note: input can be null.
            try {
                o.restockInventory(j.getSelectedItem().toString(), Double.parseDouble(remaining));

            } catch (Exception error) {
                error.printStackTrace();
                System.err.println(error.getClass().getName() + ": " + error.getMessage());
                System.exit(0);
            }
        }

        if (((JButton) e.getSource()).getText().equals("Add New Ingredient")) {
            JTextField ingredientName = new JTextField();
            JTextField amountRem = new JTextField();
            JTextField amountUsed = new JTextField();
            JTextField amountMinimum = new JTextField();

            Object[] message = {
                "Ingredient Name:", ingredientName,
                "Amount Remaining:", amountRem,
                "Amount Used:", amountUsed,
                "Amount Minimum", amountMinimum
            };

            boolean cancelDialog = false;
            String ingredientNameStr = "";
            String amountRemStr      = "";
            String amountUsedStr     = ""; 
            String amountMinStr      = ""; 

            while (!cancelDialog) {
                int ret = JOptionPane.showConfirmDialog(null,message,"Add New Ingredient", JOptionPane.OK_CANCEL_OPTION);
                if (ret == JOptionPane.OK_OPTION) {
                    ingredientNameStr = ingredientName.getText();
                    amountRemStr      = amountRem.getText();
                    amountUsedStr     = amountUsed.getText();
                    amountMinStr      = amountMinimum.getText();

                    if(ingredientNameStr.equals("")) {
                           JOptionPane.showMessageDialog(null, "Invalid Value", "Invalid Ingredient Name", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(amountRemStr.equals("")) {
                           JOptionPane.showMessageDialog(null, "Invalid Value", "Invalid Amount Remaining Value", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(amountUsedStr.equals("")) {
                           JOptionPane.showMessageDialog(null, "Invalid Value", "Invalid Amount Used Value", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(amountMinStr.equals("")) {
                           JOptionPane.showMessageDialog(null, "Invalid Value", "Invalid Amount Minimum Value", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                           cancelDialog = true;
              }
              else
                  return;
          }

          try {
              Double amountRemaining = Double.parseDouble(amountRemStr);
              Double amountUsedVal      = Double.parseDouble(amountUsedStr);
              Double minimumAmount = Double.parseDouble(amountMinStr);

              o.createIngredient(ingredientNameStr,minimumAmount, amountRemaining, amountUsedVal);
          } 
          catch (Exception error) {
                  error.printStackTrace();
                  System.err.println(error.getClass().getName() + ": " + error.getMessage());
                  System.exit(0);
                }
        }

        if (((JButton) e.getSource()).getText().equals("Delete Ingredient")) {
            String ingredient = j.getSelectedItem().toString();
            try {
                o.deleteIngredient(ingredient);

            } catch (Exception error) {
                error.printStackTrace();
                System.err.println(error.getClass().getName() + ": " + error.getMessage());
                System.exit(0);
            }
        }

        if (((JButton) e.getSource()).getText().equals("Sales Report")) {
            Sales w;
            try {
                w = new Sales(o, m);
                w.f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                w.f.setVisible(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }

        if (((JButton) e.getSource()).getText().equals("Excess Report")) {
            Excess w = new Excess(o, m);
            w.f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            w.f.setVisible(true);
        }

        if (((JButton) e.getSource()).getText().equals("Restock Report")) {
            Restock w = new Restock(o);
            w.f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            w.f.setVisible(true);
        }

        if (((JButton) e.getSource()).getText().equals("Add-On Report")) {
            try {
                String s = "Required input format example: 10/10/22 - 10/18/22";
                String date = JOptionPane.showInputDialog(f, "Enter start and end date for desired report. " + s);// Note:
                                                                                                                  // input
                                                                                                                  // can
                                                                                                                  // be
                                                                                                                  // null.
                o.loadAddOnsReport(date);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

    }

}