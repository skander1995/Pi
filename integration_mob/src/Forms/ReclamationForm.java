package Forms;

import AbstractClass.AbstractForm;
import Services.ReclamationService;
import Utilities.ToolsUtilities;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.MultiList;
import com.mycompany.Entite.Reclamation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cobwi
 */
public class ReclamationForm extends AbstractForm {

    private Label myPic;
    private Form sender;
    ReclamationService recService = new ReclamationService();
    List<Reclamation> listOfRec = recService.getAllByUser();

    int paramId = -1;

    public ReclamationForm(Form f) {
        super("Mes Reclamations", f);
        this.sender = f;
        //this.setLayout(new BorderLayout());
        this.setLayout(BoxLayout.y());

        this.getToolbar().addCommandToRightBar("+", null, (evt) -> {
            new ReclamationAddForm(sender).show();
        });

        ArrayList<Map<String, Object>> data = new ArrayList<>();
        System.out.println("HOW MANYYYYYYYYYYYY : " + listOfRec.size());
        //listOfRec = recService.getAllByUser();
        for (int i = 0; i < listOfRec.size(); i++) {
            data.add(createListEntry(listOfRec.get(i)));
            DefaultListModel<Map<String, Object>> model = new DefaultListModel<>(data.get(i));
            MultiList ml = new MultiList(model);
            ml.setDraggable(false);

            Button delete = new Button();
            paramId = listOfRec.get(i).getId_pub();
            delete.addActionListener((evt) -> {
                recService.deleteReclamation(paramId);
                new ReclamationForm(sender).show();
            });
            delete.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, delete.getUnselectedStyle()).toImage());
            SwipeableContainer swipeable = new SwipeableContainer(delete, ml);
            swipeable.setSwipeActivated(true);

            Container fina = new Container(BoxLayout.y());
            fina.add(swipeable);

            Container container = new Container(BoxLayout.y());
            container.add(fina);

            this.add(container);
        }

        //this.add(BorderLayout.CENTER, ml);
        //this.show();
    }

    private Map<String, Object> createListEntry(Reclamation reclamation) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("Line1", reclamation.getSujet());
        entry.put("Line2", ToolsUtilities.formater.format(reclamation.getDatePub()));
        int mm = Display.getInstance().convertToPixels(3);
        Image icon = null;
        switch (reclamation.getEtat()) {
            case "traitee": {
                icon = theme.getImage("done.png");
                break;
            }
            case "rejeter": {
                icon = theme.getImage("rejeter.png");
                break;
            }
            default: {
                icon = theme.getImage("attente.png");
                break;
            }
        }

        Label label = new Label(icon);
        entry.put("icon", label);
        return entry;
    }

    private Map<String, Object> createListEntry(String name, String date, Image icon) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("Line1", name);
        entry.put("Line2", date);
        entry.put("icon", icon);
        return entry;
    }

}
