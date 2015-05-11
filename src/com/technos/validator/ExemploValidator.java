package com.technos.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("ExemploValidator")
public class ExemploValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        try{

            float teste = Float.parseFloat(value.toString());
            //No xhtml vamos adicionar a tag <f:validator />
            
    		 /* ou
    		  * 
    		  * validatorMessage="Invalid email format"
    			value="#{userBean.email}">
    			  <f:validateRegex pattern="^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$" /> */

        }catch(NumberFormatException x){
            FacesMessage msg = new FacesMessage("Atenção!", 
                    "Classificação no formato incorreto!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}