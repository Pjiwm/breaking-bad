package com.example.breakingbadv2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.breakingbadv2.domain.Character;
import com.example.breakingbadv2.services.QuotesAPITask;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoreInfoActivity extends AppCompatActivity implements QuotesAPITask.QuotesListener {
    private TextView mCharacterName;
    private TextView mCharacterJob;
    private TextView mCharacterNickname;
    private TextView mCharacterSeasons;
    private ImageView mCharacterImage;
    private TextView mCharacterQuotes;
    private TextView mCharacterStatus;
    private final String TAG = this.getClass().getSimpleName();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "called onCreate");

        setContentView(R.layout.activity_more_info);
        Intent intent = getIntent();
        Character character = (Character) intent.getSerializableExtra("character");

        mCharacterName = findViewById(R.id.character_name_tv);
        mCharacterName.setText(character.getName() + " ("  + character.getBirthDate() + ")");

        mCharacterNickname = findViewById(R.id.character_nickname_tv);
        mCharacterNickname.setText("Known as: " + character.getNickname());

        mCharacterJob = findViewById(R.id.character_job_tv);
        mCharacterJob.setText("works as: " + character.getJobTitle());

        mCharacterSeasons = findViewById(R.id.character_seasons_tv);
        mCharacterSeasons.setText("participated in season(s): " + character.getParticipatedSeasons());

        mCharacterStatus = findViewById(R.id.character_status_tv);
        mCharacterStatus.setText("status: " + character.getStatus());






        mCharacterImage = findViewById(R.id.character_image_iv);
        Picasso
                .get()
                .load(character.getImageUrl())
                .into(mCharacterImage);

        mCharacterQuotes = findViewById(R.id.character_quotes_tv);

        String[] params = {
                "https://www.breakingbadapi.com/api/quote?author=" + character.getName()
        };
        new QuotesAPITask(this).execute(params); // Background Thread

    }
    /**
     * sets quotes after data from API has been fetched
     * @param quotes - the list of quotes that will be displayed
     */
    @Override
    public void onQuotesAvailable(List<String> quotes) {
        try {
            Log.d(TAG, "called onQuotesAvailable");
            if (quotes.size() > 0) {
                StringBuilder quotesString = new StringBuilder("Quotes: \n");
                for (String quote : quotes) {
                    quotesString.append(quote).append("\n \n");
                }
                mCharacterQuotes.setText(quotesString);
            }
        } catch (NullPointerException e) {
            Toast.makeText(this, "API offline", Toast.LENGTH_LONG);
        }
    }
}
