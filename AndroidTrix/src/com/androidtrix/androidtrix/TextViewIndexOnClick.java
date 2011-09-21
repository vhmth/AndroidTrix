/**
 * @author Vinay Hiremath
 * vhiremath4@gmail.com
 * Code that gets a TextView's onClick Index.
 */

package com.androidtrix.androidtrix;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TextViewIndexOnClick extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textviewindexonclick);
        
        //The TextView we'll get the onClick Index from
        TextView manip = (TextView)findViewById(R.id.demo);
        
        //now the actual onClick code
        manip.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				//must get the layout in order to get the x and y coordinates of the click
				Layout layout = ((TextView) v).getLayout();
				int x = (int)event.getX();
				int y = (int)event.getY();
				//layout null check is precautionary but advised when handling dynamically
				//loaded layouts and Views
				if (layout!=null){
					int line = layout.getLineForVertical(y);
					int clickIndex = layout.getOffsetForHorizontal(line, x);//this is what you want, the click index
					Toast.makeText(v.getContext(), "Click Index: "+clickIndex, 2500).show();
				}
				return false;//auto-generated, you should return what you want
			}
		});
        
        //display the code in the Activity
        TextView code = (TextView)findViewById(R.id.code);
        code.setText(
        	"CODE (manip is a TextView):\n"+
        	"------------------------------------\n"+
        	"manip.setOnTouchListener(new View.OnTouchListener() {\n"+
        	"\tpublic boolean onTouch(View v, MotionEvent event) {\n"+
        	"\t\tLayout layout = ((TextView) v).getLayout();\n"+
        	"\t\tint x = (int)event.getX();\n"+
        	"\t\tint y = (int)event.getY();\n"+
        	"\t\tif (layout!=null){\n"+
        	"\t\t\tint line = layout.getLineForVertical(y);\n"+
        	"\t\t\tint clickIndex = layout.getOffsetForHorizontal(line, x);\n"+
        	"\t\t}\n"+
        	"\t\treturn false;\n"+
        	"\t}\n"+
        	"});\n"+
        	"------------------------------------\n"
        );
        
        //back button to go back to the main menu
        Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();	
			}
		});
        
	}
}
