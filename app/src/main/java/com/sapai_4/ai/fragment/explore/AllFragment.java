package com.sapai_4.ai.fragment.explore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sapai_4.ai.Adverisements_Activity;
import com.sapai_4.ai.Apology_Activity;
import com.sapai_4.ai.Birthday_Activity;
import com.sapai_4.ai.Company_Bio_Activity;
import com.sapai_4.ai.Content_Write_Activity;
import com.sapai_4.ai.ConversationActivity;
import com.sapai_4.ai.Diet_Plan_Activity;
import com.sapai_4.ai.Email_Activity;
import com.sapai_4.ai.Email_Subject_Activity;
import com.sapai_4.ai.Explain_Code_Activity;
import com.sapai_4.ai.Fight_Activity;
import com.sapai_4.ai.ImproveActivity;
import com.sapai_4.ai.Improve_Email_Activity;
import com.sapai_4.ai.Instagram_Activity;
import com.sapai_4.ai.Into_Tweet_Activity;
import com.sapai_4.ai.Invitation_Activity;
import com.sapai_4.ai.Job_Post_Activity;
import com.sapai_4.ai.Linkedin_Post_Activity;
import com.sapai_4.ai.LyricsActivity;
import com.sapai_4.ai.Name_Generator_Activity;
import com.sapai_4.ai.PoemActivity;
import com.sapai_4.ai.Recipe_Activity;
import com.sapai_4.ai.Sentence_Activity;
import com.sapai_4.ai.Short_Movie_Activity;
import com.sapai_4.ai.Slogan_Activity;
import com.sapai_4.ai.Speech_Activity;
import com.sapai_4.ai.Story_Activity;
import com.sapai_4.ai.Summarize_Activity;
import com.sapai_4.ai.Tell_Joke_Activity;
import com.sapai_4.ai.Tiktok_Activity;
import com.sapai_4.ai.To_Emoji_Activity;
import com.sapai_4.ai.TranslateActivity;
import com.sapai_4.ai.Tweet_Activity;
import com.sapai_4.ai.Up_Line_Activity;
import com.sapai_4.ai.Viral_Video_Activity;
import com.sapai_4.ai.Words_Activity;
import com.sapai_4.ai.Write_Code_Activity;
import com.sapai_4.ai.R;


public class AllFragment extends Fragment {
    LinearLayout paragraphLinear,summarizeLinear,improveLinear,translateLinear,lyricsLinear,poemLinear,storyLinear,shortMovieLinear,
            companyBioLinear,nameGeneratorLinear,sloganLinear,advertisementsLinear,jobPostLinear,birthDayLinear,
    apologyLinear,invitationLinear,pickLineLinear,speechLinear,emailLinear,emailSubjectLinear,improveEmailLinear,
    tweetLinear,intoTweetLinear,linkedinPostLinear,instagramLinear,tiktokLinear,viralVideoLinear,writeCodeLinear,explainCodeLinear,
    recipeLinear,dietPlanLinear,toEmojiLinear,tellJokeLinear,sentenceLinear,fightLinear,conversationLinear,wordsLinear;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_all, container, false);
        paragraphLinear=view.findViewById(R.id.paragraphLinear);
        summarizeLinear=view.findViewById(R.id.summarizeLinear);
        improveLinear=view.findViewById(R.id.improveLinear);
        translateLinear=view.findViewById(R.id.translateLinear);
        lyricsLinear=view.findViewById(R.id.lyricsLinear);
        poemLinear=view.findViewById(R.id.poemLinear);
        storyLinear=view.findViewById(R.id.storyLinear);
        shortMovieLinear=view.findViewById(R.id.shortMovieLinear);
        companyBioLinear=view.findViewById(R.id.companyBioLinear);
        nameGeneratorLinear=view.findViewById(R.id.nameGeneratorLinear);
        sloganLinear=view.findViewById(R.id.sloganLinear);
        advertisementsLinear=view.findViewById(R.id.advertisementsLinear);
        jobPostLinear=view.findViewById(R.id.jobPostLinear);
        birthDayLinear=view.findViewById(R.id.birthdayLinear);
        apologyLinear=view.findViewById(R.id.apologyLinear);
        invitationLinear=view.findViewById(R.id.invitationLinear);
        pickLineLinear=view.findViewById(R.id.pickUpLineLinear);
        speechLinear=view.findViewById(R.id.speechLinear);
        emailLinear=view.findViewById(R.id.emailLinear);
        emailSubjectLinear=view.findViewById(R.id.emailSubjectLinear);
        improveEmailLinear=view.findViewById(R.id.improveEmailLinear);
        tweetLinear=view.findViewById(R.id.tweetLinear);
        intoTweetLinear=view.findViewById(R.id.intoTweetLinear);
        linkedinPostLinear=view.findViewById(R.id.linkedinPostLinear);
        instagramLinear=view.findViewById(R.id.instagramLinear);
        tiktokLinear=view.findViewById(R.id.titokLinear);
        viralVideoLinear=view.findViewById(R.id.viralVideoLinear);
        writeCodeLinear=view.findViewById(R.id.writeCodeLinear);
        explainCodeLinear=view.findViewById(R.id.explainCodeLinear);
        recipeLinear=view.findViewById(R.id.recipeLinear);
        dietPlanLinear=view.findViewById(R.id.dietPlanLinear);
        toEmojiLinear=view.findViewById(R.id.toEmojiLinear);
        tellJokeLinear=view.findViewById(R.id.tellJokeLinear);
        conversationLinear=view.findViewById(R.id.conversationLinear);
        fightLinear=view.findViewById(R.id.fightLinear);
        sentenceLinear=view.findViewById(R.id.sentenceLinear);
        wordsLinear=view.findViewById(R.id.wordsLinear);


        paragraphLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Content_Write_Activity.class);
                startActivity(intent);
            }
        });
        summarizeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Summarize_Activity.class);
                startActivity(intent);
            }
        });
        improveLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ImproveActivity.class);
                startActivity(intent);
            }
        });
        translateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TranslateActivity.class);
                startActivity(intent);
            }
        });
        lyricsLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LyricsActivity.class);
                startActivity(intent);
            }
        });
        poemLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PoemActivity.class);
                startActivity(intent);
            }
        });
        storyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Story_Activity.class);
                startActivity(intent);
            }
        });
        shortMovieLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Short_Movie_Activity.class);
                startActivity(intent);
            }
        });
        companyBioLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Company_Bio_Activity.class);
                startActivity(intent);
            }
        });
        nameGeneratorLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Name_Generator_Activity.class);
                startActivity(intent);
            }
        });
        sloganLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Slogan_Activity.class);
                startActivity(intent);
            }
        });
       advertisementsLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Adverisements_Activity.class);
                startActivity(intent);
            }
        });
        jobPostLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Job_Post_Activity.class);
                startActivity(intent);
            }
        });
        birthDayLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Birthday_Activity.class);
                startActivity(intent);
            }
        });
        apologyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Apology_Activity.class);
                startActivity(intent);
            }
        });
        invitationLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Invitation_Activity.class);
                startActivity(intent);
            }
        });
        pickLineLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Up_Line_Activity.class);
                startActivity(intent);
            }
        });
        speechLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Speech_Activity.class);
                startActivity(intent);
            }
        });

        emailLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Email_Activity.class);
                startActivity(intent);
            }
        });
        emailSubjectLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Email_Subject_Activity.class);
                startActivity(intent);
            }
        });
        improveEmailLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Improve_Email_Activity.class);
                startActivity(intent);
            }
        });
        tweetLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Tweet_Activity.class);
                startActivity(intent);
            }
        });
        intoTweetLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Into_Tweet_Activity.class);
                startActivity(intent);
            }
        });
        linkedinPostLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Linkedin_Post_Activity.class);
                startActivity(intent);
            }
        });
        instagramLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Instagram_Activity.class);
                startActivity(intent);
            }
        });
        tiktokLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Tiktok_Activity.class);
                startActivity(intent);
            }
        });
        viralVideoLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Viral_Video_Activity.class);
                startActivity(intent);
            }
        });
        writeCodeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Write_Code_Activity.class);
                startActivity(intent);
            }
        });
        explainCodeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Explain_Code_Activity.class);
                startActivity(intent);
            }
        });
        recipeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Recipe_Activity.class);
                startActivity(intent);
            }
        });
        dietPlanLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Diet_Plan_Activity.class);
                startActivity(intent);
            }
        });
        toEmojiLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), To_Emoji_Activity.class);
                startActivity(intent);
            }
        });
        tellJokeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Tell_Joke_Activity.class);
                startActivity(intent);
            }
        });
        sentenceLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Sentence_Activity.class);
                startActivity(intent);
            }
        });
        fightLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Fight_Activity.class);
                startActivity(intent);
            }
        });
       conversationLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ConversationActivity.class);
                startActivity(intent);
            }
        });
       wordsLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Words_Activity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}