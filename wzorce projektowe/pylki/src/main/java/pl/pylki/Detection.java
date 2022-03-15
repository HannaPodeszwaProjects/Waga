package pl.pylki;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Detection {
    private List <TextBox> textBoxList;

    Detection()
    {
        textBoxList = new ArrayList<>();
    }

    public List<TextBox> getTextBoxList() {
        return textBoxList;
    }

    public Detection addTextBox(BoundingBox boundingBox, Text text){
        TextBox newTextBox = new TextBox(boundingBox,text);

        Optional<TextBox> sameBounding = textBoxList.stream().filter(textBox -> textBox.getBoundingBox().
                equals(boundingBox)).findFirst(); //find textBox with the same bounding

        if(!(sameBounding.isEmpty()))
        {
          textBoxList.remove(sameBounding.get());
        }
        textBoxList.add(newTextBox);
        return this;
    }
}
