package com.rg.rgview.selfView.springActionMenu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.rg.rgview.R;

public class SpringActivity extends AppCompatActivity {
    private ActionMenu actionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring);

        actionMenu = findViewById(R.id.action_menu);
        actionMenu.addView(R.drawable.ic_mood_clam);
        actionMenu.addView(R.drawable.ic_mood_exciting);
        actionMenu.addView(R.drawable.ic_mood_happy);
        actionMenu.addView(R.drawable.ic_mood_unhappy);
        actionMenu.setItemClickListener(new OnActionItemClickListener() {
            @Override
            public void onItemClick(int index) {
                ActionButtonItems actionButtonItems = (ActionButtonItems)actionMenu.getChildAt(0);
                switch (index) {
                    case 1:
                        actionButtonItems.setBitmapIcon(R.drawable.ic_mood_clam);
                        break;
                    case 2:
                        actionButtonItems.setBitmapIcon(R.drawable.ic_mood_exciting);
                        break;
                    case 3:
                        actionButtonItems.setBitmapIcon(R.drawable.ic_mood_happy);
                        break;
                    case 4:
                        actionButtonItems.setBitmapIcon(R.drawable.ic_mood_unhappy);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onAnimationEnd(boolean isOpen) {

            }
        });

    }

    private void showMessage(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
