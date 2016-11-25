/*
 * NeuronAnimActivity.java
 *
 * Norbiron Game
 * This is a case study for creating sprites for SamuEntropy/Brainboard.
 *
 * Copyright (C) 2016, Dr. Bátfai Norbert
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Ez a program szabad szoftver; terjeszthető illetve módosítható a
 * Free Software Foundation által kiadott GNU General Public License
 * dokumentumában leírtak; akár a licenc 3-as, akár (tetszőleges) későbbi
 * változata szerint.
 *
 * Ez a program abban a reményben kerül közreadásra, hogy hasznos lesz,
 * de minden egyéb GARANCIA NÉLKÜL, az ELADHATÓSÁGRA vagy VALAMELY CÉLRA
 * VALÓ ALKALMAZHATÓSÁGRA való származtatott garanciát is beleértve.
 * További részleteket a GNU General Public License tartalmaz.
 *
 * A felhasználónak a programmal együtt meg kell kapnia a GNU General
 * Public License egy példányát; ha mégsem kapta meg, akkor
 * tekintse meg a <http://www.gnu.org/licenses/> oldalon.
 *
 * Version history:
 *
 * 0.0.1, 2013.szept.29.
 */
package batfai.samuentropy.brainboard7;


import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.DragEvent;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.content.ClipData;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


/**
 *
 * @author nbatfai
 */
public class NeuronGameActivity extends android.app.Activity {
    private NorbironSurfaceView nSView;
    private LinearLayout bottom;
    private LinearLayout bottom_child;
    private boolean up;
    private Animation animUp;
    private Animation animDown;
    
    @Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    findViewById(R.id.nandironproci).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.matyironproci).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.gretironproci).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.nandironproci2).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.matyironproci2).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.gretironproci2).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.rela).setOnDragListener(new MyDragListener());
        
        nSView = (NorbironSurfaceView) findViewById(R.id.norsurf);
        bottom = (LinearLayout) findViewById(R.id.bottom);
        bottom.setOnDragListener(new MyDragListener());
        bottom_child = (LinearLayout) findViewById(R.id.bottom_child);
        bottom_child.setVisibility(View.GONE);
        
        animUp = AnimationUtils.loadAnimation(this, R.anim.anim_up);
        animDown = AnimationUtils.loadAnimation(this, R.anim.anim_down);
        animDown.setAnimationListener(new AnimationListener() {         
        @Override
        public void onAnimationEnd(Animation animation) {
            bottom_child.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) { }

        @Override
        public void onAnimationStart(Animation animation) { }
    });
        
    final Button button = (Button) findViewById(R.id.buttonD);
         button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 if(up){
                    bottom_child.startAnimation(animDown);
                    up = false;
                }else{
                    bottom_child.setVisibility(View.VISIBLE);
                    bottom_child.startAnimation(animUp);
                    up = true;
                 }
             }
         });
    
    }
    
    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }
    
    class MyDragListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            if(v.getId() == R.id.bottom)
                return true;
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    
                    break;
                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();
                    if(view == null)
                        return true;
                    float x = event.getX();
                    float y = event.getY();
                        switch (view.getId()) {
                            case R.id.nandironproci:
                                nSView.newIron(0, x, y);
                                break;
                            case R.id.nandironproci2:
                                nSView.newIron(1, x, y);
                                break;
                            case R.id.matyironproci:
                                nSView.newIron(2, x, y);
                                break;
                            case R.id.matyironproci2:
                                nSView.newIron(3, x, y);
                                break;
                            case R.id.gretironproci:
                                nSView.newIron(5, x, y);
                                break;
                            case R.id.gretironproci2:
                                nSView.newIron(4, x, y);
                                break;
                        }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
        }
    }
    
    
}
