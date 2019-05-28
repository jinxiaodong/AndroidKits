package debug;

import android.os.Bundle;
import android.view.View;

import com.androidkits.camera.R;
import com.common.library.commons.constants.ArouterPath;
import com.common.library.commons.router.JumpUtils;

import androidx.appcompat.app.AppCompatActivity;

public class ModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_module_activity);
    }

    public void btnClick(View view) {
        JumpUtils.startActivity(this, ArouterPath.camera.AlbumSelectorActivity);
    }
}
