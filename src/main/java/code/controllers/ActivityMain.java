package code.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import code.OperHistory;
import code.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ActivityMain {

    public TextField fieldOperation;
    public TextField fieldAnswer;
    @FXML
    private Button btHistory;

    @FXML
    private Button btAC;

    @FXML
    private Button btC;

    @FXML
    private Button btChangeSign;

    @FXML
    private Button btPercent;

    @FXML
    private Button btDiv;

    @FXML
    private Button btSeven;

    @FXML
    private Button btEight;

    @FXML
    private Button btNine;

    @FXML
    private Button btMult;

    @FXML
    private Button btFour;

    @FXML
    private Button btFive;

    @FXML
    private Button btSix;

    @FXML
    private Button btPlus;

    @FXML
    private Button btOne;

    @FXML
    private Button btTwo;

    @FXML
    private Button btThree;

    @FXML
    private Button btMinus;

    @FXML
    private Button btNull;

    @FXML
    private Button btDot;

    @FXML
    private Button btEquals;

    private boolean decimal = false;

    private BigDecimal prevNum;
    private Character prevOper;

    @FXML
    void btChangeSignAction(ActionEvent event) {
        StringBuilder str = new StringBuilder(fieldAnswer.getText());
        if (str.length() > 0) {
            if (str.charAt(0) != '-')
                str.insert(0, '-');
            else
                str.deleteCharAt(0);
            fieldAnswer.setText(str.toString());
        }
    }

    @FXML
    void btClearAction(ActionEvent event) {
        Button bt = (Button) event.getSource();
        StringBuilder str = new StringBuilder(fieldAnswer.getText());
        if (bt == btC && str.length() > 0) {
            str.deleteCharAt(str.length()-1);
            fieldAnswer.setText(str.toString());
        } else if (bt == btAC) {
            clearFieldAnswer();
            clearFieldOperation();
            prevNum = null;
            prevOper = null;
        }
    }

    @FXML
    void btDotAction(ActionEvent event) {
        if (!decimal){
            StringBuilder str = new StringBuilder(fieldAnswer.getText());
            if (str.length() == 0)
                str.append(0);
            str.append('.');
            fieldAnswer.setText(str.toString());
            decimal = true;

        }
    }

    @FXML
    void btEqualsAction(ActionEvent event) {
        StringBuilder expression = new StringBuilder(fieldOperation.getText());
        if (expression.length() > 0) {
            clearFieldOperation();
            String str = fieldAnswer.getText();
            expression.append(str).append('=');
            calcPrevNum(prevOper, new BigDecimal(str));
            expression.append(prevNum.toString());
            fieldAnswer.setText(prevNum.toString());
            try {
                new BigInteger(prevNum.toString());
            } catch (NumberFormatException e) {
                decimal = true;
            }
            OperHistory.getInstance().addHistory(expression.toString());
            prevNum = null;
            prevOper = null;
        }

    }

    @FXML
    void btHistoryAction(ActionEvent event) throws IOException {
        Transition.integrateRoot("/layouts/activity_history.fxml", this.getClass());
    }

    void addNumToInput(Number num) {
        StringBuilder str = new StringBuilder(fieldAnswer.getText());
        if (str.length() > 0 && str.charAt(0) == '0' && !decimal)
            str.deleteCharAt(0);
        str.append(num);
        fieldAnswer.setText(str.toString());
    }

    @FXML
    void btNumAction(ActionEvent event) {
        Button bt = (Button) event.getSource();
        if (bt == btNull) {
            addNumToInput(0);
        } else if (bt == btOne) {
            addNumToInput(1);
        } else if (bt == btTwo) {
            addNumToInput(2);
        } else if (bt == btThree) {
            addNumToInput(3);
        } else if (bt == btFour) {
            addNumToInput(4);
        } else if (bt == btFive) {
            addNumToInput(5);
        } else if (bt == btSix) {
            addNumToInput(6);
        } else if (bt == btSeven) {
            addNumToInput(7);
        } else if (bt == btEight) {
            addNumToInput(8);
        } else if (bt == btNine) {
            addNumToInput(9);
        }
    }

    @FXML
    void btPercentAction(ActionEvent event) {
        if (!fieldAnswer.getText().isEmpty()) {
            BigDecimal percentVal = new BigDecimal(fieldAnswer.getText())
                    .divide(BigDecimal.valueOf(100),
                            new MathContext(11, RoundingMode.HALF_UP));
            BigDecimal tmp = percentVal;
            if (prevNum != null) {
                tmp = prevNum.multiply(percentVal);
            }
            fieldAnswer.setText(tmp.toString());
        }
    }

    private BigDecimal calcPrevNum(Character oper, BigDecimal num) {
        switch (oper) {
            case '+':
                prevNum = prevNum.add(num);
                break;
            case '-':
                prevNum = prevNum.subtract(num);
                break;
            case '×':
                prevNum = prevNum.multiply(num);
                break;
            case '÷':
                if (num.equals(new BigDecimal(0)))
                    fieldAnswer.setText("error");
                else
                    prevNum = prevNum.divide(num,
                            new MathContext(11, RoundingMode.HALF_UP));
        }
        return prevNum;
    }


    @FXML
    void operationAction(ActionEvent event) {
        Button bt = (Button) event.getSource();
        StringBuilder str = new StringBuilder(fieldAnswer.getText());
        if (prevOper == null) {
            if (str.length() == 0)
                prevNum = new BigDecimal(0);
            else
                prevNum = new BigDecimal(str.toString());
        } else
            calcPrevNum(prevOper, new BigDecimal(str.toString()));

        prevOper = bt.getText().charAt(0);

        fieldOperation.setText(prevNum.toString() + prevOper.toString());
        clearFieldAnswer();

    }

    private void clearFieldAnswer() {
        decimal = false;
        fieldAnswer.setText("");
    }

    private void clearFieldOperation() {
        decimal = false;
        fieldOperation.setText("");
    }

    @FXML
    void initialize() {

    }
}
