package com.example.socify.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.socify.ClubFragments.CreateClubFragment;
import com.example.socify.databinding.ActivityCroppperBinding;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class CropperActivity extends AppCompatActivity {

    ActivityCroppperBinding binding;

    public String result;
    public Uri fileURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCroppperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        readintent();

        String dest_uri = UUID.randomUUID().toString() + ".jpg";

        UCrop.Options options = new UCrop.Options();
        options.setCircleDimmedLayer(true);


        UCrop.of(fileURI, Uri.fromFile(new File(getCacheDir(), dest_uri)))
                .withOptions(options)
                .withAspectRatio(0,0)
                .useSourceImageAspectRatio()
                .withMaxResultSize(2000, 2000)
                .start(CropperActivity.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==UCrop.REQUEST_CROP) {
            assert data != null;
            final Uri resultUri = UCrop.getOutput(data);
            Intent returnintent = new Intent();
            returnintent.putExtra("RESULT", resultUri+"");
            setResult(-1, returnintent);
            assert resultUri != null;
            Log.i("URI", resultUri.toString());
            finish();
        }
        else if(resultCode==UCrop.RESULT_ERROR) {
            assert data != null;
            final Throwable cropError = UCrop.getError(data);
            Toast.makeText(this, cropError.toString(), Toast.LENGTH_SHORT).show();
        }
        else{
            if(CreateClubFragment.cropperFlag == 1) {
//                startActivity(new Intent(getApplicationContext(), clubs.class));
            }
            else {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        }
    }

    private void readintent() {
        Intent intent = getIntent();
        if(intent.getExtras()!=null) {
            result = intent.getStringExtra("DATA");
            fileURI = Uri.parse(result);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}