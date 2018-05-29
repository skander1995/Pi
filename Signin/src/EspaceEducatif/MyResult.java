/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EspaceEducatif;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Skander
 */

final class MyResult {
    private  int views;
    private  float note;
public MyResult()
{
}
    
 public void setviews(int i) {
        this.views=i;
    }
 
 public void setnote(float i) {
        this.note=i;
    }
    public int getFviews() {
        return views;
    }

    public float getnote() {
        return note;
    }
}

        
