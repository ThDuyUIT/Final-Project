import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ve_xe.R;

public class information_user extends AppCompatActivity {
    private ImageView imageViewProfile;
    private TextView textViewName, textViewEmail, textViewPhone, textViewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_user);

        imageViewProfile = (ImageView) findViewById(R.id.imageViewProfile);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);

        // Set user information
        String name = "John Doe";
        String email = "johndoe@example.com";
        String phone = "123-456-7890";
        String address = "123 Main Street, Anytown, USA";

        textViewName.setText(name);
        textViewEmail.setText(email);
        textViewPhone.setText(phone);
        textViewAddress.setText(address);

        // Set user profile image
        // Replace "R.drawable.profile" with your own drawable resource
        imageViewProfile.setImageResource(R.drawable.profile);
    }
}

