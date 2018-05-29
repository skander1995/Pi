/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EspaceEducatif;

import Utilities.ToolsUtilities;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Skander
 */
public class skrating {

    public skrating() {
    }

    private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);
    }

    private Slider createStarRankSlider() {
        Slider starRank = new Slider();
        starRank.setEditable(true);
        starRank.setMinValue(0);
        starRank.setMaxValue(5);
        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte) 0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
        return starRank;
    }

    private int value(Slider s) {
        int v = s.getProgress();
        return v;
    }

    private void rate(int note, int iduser, int idpub) {

        String url = ToolsUtilities.ServerIp+":"+ToolsUtilities.port+"/FosBundleProj/web/app_dev.php/tasks/rate/" + note
                + "/" + iduser
                + "/" + idpub;
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }

    public Container ratingslider(int iduser, int idpub) {
        Container cont = new Container(BoxLayout.x());
        MyResult rs = getrate(idpub);
        Label note = new Label("rated " + rs.getnote() + " out of " + rs.getFviews() + " rates");
        Slider rate = createStarRankSlider();
        Button save = new Button("rate");
        save.addActionListener((evt) -> {

            System.out.println("" + value(rate));
            rate(value(rate), iduser, idpub);
        });
        Container c= new Container(BoxLayout.y());
        
        cont.addAll(rate, save);
        c.addAll(cont,note);
        return c;
    }

   

    MyResult e = new MyResult();
    public MyResult getrate(int idpub) {

        ConnectionRequest con = new ConnectionRequest();
        String Url = ToolsUtilities.ServerIp+":"+ToolsUtilities.port+"/FosBundleProj/web/app_dev.php/tasks/rateval/" + idpub;
        con.setUrl(Url);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                e = consulter(idpub, new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return e;
    }
     public MyResult consulter(int idpub, String json) {
        MyResult evo = new MyResult();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> aides = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) aides.get("root");

             System.out.println("json :  " + json);
            System.out.println("map:  " + aides);
            
            float views = Float.parseFloat(aides.get("nombre").toString());
            float notess = Float.parseFloat(aides.get("note").toString());
            int viewss= (int)views;
            evo.setnote(notess);
            evo.setviews(viewss);

           
            System.out.println(evo.getFviews() + "   " + evo.getnote());

        } catch (IOException ex) {
            System.out.println("ya ma famech ya andek ghalta ");
        }
        //System.out.println(r);
        return evo;
    }
}
