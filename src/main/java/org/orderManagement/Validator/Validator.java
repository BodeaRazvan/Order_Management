package org.orderManagement.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Validator {

    @FXML
    public int isValidId(TextField id){
        if(id.getText().equals("")){
            return -1;
        }
        try {
            Integer.parseInt(id.getText());
        }catch (NumberFormatException exception){
            return -2;
        }
        if(Integer.parseInt(id.getText())<0){
            return -3;
        }

        return 1;
    }

    @FXML
    public int isValidClient(TextField name, TextField address, TextField email){
        if(name.getText().equals("") || address.getText().equals("") || email.getText().equals("")){
            return -4;
        }
        return 1;
    }

    @FXML
    public int isValidProduct(TextField name, TextField quantity, TextField price){
        if(name.getText().equals("") || quantity.getText().equals("") || price.getText().equals("")){
            return -4;
        }
        try{
            if((Integer.parseInt(quantity.getText()) < 0 || Integer.parseInt(price.getText()) < 0)){
                return -5;
            }
        }catch (NumberFormatException exception){
            return -6;
        }

        return 1;
    }

}
