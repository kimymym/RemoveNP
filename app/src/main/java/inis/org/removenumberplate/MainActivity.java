package inis.org.removenumberplate;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import inis.org.removenumberplate.databinding.ActivityMainBinding;
import inis.org.removenumberplate.utils.ImageUtil;

public class MainActivity extends AppCompatActivity {
    private Uri imageUri;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        ImageUtil.getPictureFromGallery(this, C.PICK_IMAGE);
    }

    private void showPreview() {
        binding.preview.setImageURI(imageUri);
        binding.process.setVisibility(View.VISIBLE);
        binding.process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processImages();
            }
        });
    }

    private void processImages() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == C.PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                showPreview();
            }

            return ;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
