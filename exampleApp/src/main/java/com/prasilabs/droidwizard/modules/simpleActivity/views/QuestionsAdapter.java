package com.prasilabs.droidwizard.modules.simpleActivity.views;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prasilabs.droidwizard.R;
import com.prasilabs.droidwizard.pojos.QuestionsPojo;
import com.prasilabs.droidwizard.utils.CircleTransform;
import com.prasilabs.droidwizard.utils.MyViewUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Contus team on 10/5/17.
 */

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>
{
    private List<QuestionsPojo> questionsPojoList;

    public void setQuestionsPojoList(List<QuestionsPojo> questionsPojoList) {
        this.questionsPojoList = questionsPojoList;
        notifyDataSetChanged();
    }

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, null);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {
        holder.setQuestionDataToViewHolder(questionsPojoList.get(position));
    }

    @Override
    public int getItemCount() {
        if(questionsPojoList != null) {
            return questionsPojoList.size();
        }
        return 0;
    }

    class QuestionsViewHolder extends RecyclerView.ViewHolder {

        TextView questionText;
        TextView starCountText;
        TextView viewCountText;
        TextView answerCountText;
        TextView userNameText;
        ImageView userImage;

        QuestionsViewHolder(View itemView) {
            super(itemView);

            questionText = (TextView) itemView.findViewById(R.id.question_text);
            starCountText = (TextView) itemView.findViewById(R.id.star_count_text);
            viewCountText = (TextView) itemView.findViewById(R.id.view_count_text);
            answerCountText = (TextView) itemView.findViewById(R.id.answer_count_text);
            userNameText = (TextView) itemView.findViewById(R.id.user_name_text);
            userImage = (ImageView) itemView.findViewById(R.id.user_image);
        }

        void setQuestionDataToViewHolder(QuestionsPojo questionsPojo) {

            questionText.setText(MyViewUtils.toCamelCase(questionsPojo.getTitle()));
            starCountText.setText(String.valueOf(questionsPojo.getScore()));
            viewCountText.setText(String.valueOf(questionsPojo.getView_count()));
            answerCountText.setText(String.valueOf(questionsPojo.getAnswer_count()));

            userNameText.setText(MyViewUtils.toCamelCase(questionsPojo.getOwner().getDisplay_name()));
            Picasso.with(userImage.getContext()).load(questionsPojo.getOwner().getProfile_image()).transform(new
                    CircleTransform()).into(userImage);
        }
    }
}
