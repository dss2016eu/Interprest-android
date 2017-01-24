package coop.biantik.traductor.utils.bus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SelectedLanguageAnswerEvent extends BaseAnswerEvent{

    private String selectedLanguage;


    public SelectedLanguageAnswerEvent(String selectedLanguage){
        this.selectedLanguage = selectedLanguage;
    }
}