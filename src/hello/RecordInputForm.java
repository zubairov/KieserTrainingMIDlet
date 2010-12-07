package hello;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author jkindler
 */
public class RecordInputForm extends Form {
    private Record data;
    private TextField tId;
    private DateField dAppointment;
    private TextField tLength;
    private TextField tInfo;
    private ChoiceGroup cValid;
    private DateField dCreated;
    private DateField dUpdated;
    private StringItem cmdCancel;
    private StringItem cmdSave;

    public RecordInputForm(String title) {
        super(title);
        tId = new TextField("ID   ", "0", 5, TextField.UNEDITABLE);
        dAppointment = new DateField("Appointment", DateField.DATE_TIME);
        tLength = new TextField("Length", "0", 4, TextField.DECIMAL);
        tInfo = new TextField("Info ", "", 15, TextField.ANY);
        cValid = new ChoiceGroup("Valid", ChoiceGroup.EXCLUSIVE
                , new String[]{"True", "False"}, null);
        dCreated = new DateField("Created", DateField.DATE_TIME);
        dUpdated = new DateField("Updated", DateField.DATE_TIME);
        cmdCancel = new StringItem(null, "Cancel", StringItem.BUTTON);
        cmdSave = new StringItem(null, "Save", StringItem.BUTTON);

        this.append(tId);
        this.append(dAppointment);
        this.append(tLength);
        this.append(tInfo);
        this.append(cValid);
        this.append(dCreated);
        this.append(dUpdated);
        this.append(cmdCancel);
        this.append(cmdSave);
    }


    public void setData(Record data) {
        this.data = data;
        tId.setString("" + data.getId());
        dAppointment.setDate(data.getAppointment());
        tLength.setString("" + data.getLength());
        tInfo.setString(data.getInfo());

        if (data.isValid()) {
            cValid.setSelectedIndex(0, true);
        } else {
            cValid.setSelectedIndex(1, true);
        }
        dCreated.setDate(data.getCreated());
        dUpdated.setDate(data.getUpdated());
    }

    public void addCommand(Command cmd, ItemCommandListener iListener) {
        if (iListener != null) {
            if ("Store".equals(cmd.getLabel())) {
                this.cmdSave.setDefaultCommand(cmd);
                this.cmdSave.setItemCommandListener(iListener);

            } else if ("List".equals(cmd.getLabel())) {
                this.cmdCancel.setDefaultCommand(cmd);
                this.cmdCancel.setItemCommandListener(iListener);            
            }
        } else  {
            super.addCommand(cmd);
        }
    }
    
    public Record getData() {
        if (this.data == null) {
            this.data = new Record();
        }

        this.data.setId(Integer.parseInt(tId.getString()));
        this.data.setAppointment(dAppointment.getDate());
        this.data.setInfo(tInfo.getString());
        this.data.setValid(cValid.isSelected(0));
        this.data.setLength(Short.parseShort(tLength.getString()));
        return this.data;
    }
}
