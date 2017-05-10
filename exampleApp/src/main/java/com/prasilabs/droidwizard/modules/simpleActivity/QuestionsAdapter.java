package com.prasilabs.droidwizard.modules.simpleActivity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prasilabs.droidwizard.R;
import com.prasilabs.droidwizard.pojos.QuestionsPojo;
import com.prasilabs.droidwizardlib.core.CoreAdapter;

/**
 * Created by Contus team on 10/5/17.
 */

public class QuestionsAdapter extends CoreAdapter<QuestionsPojo, QuestionsAdapter.QuestionsViewHolder>
{

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = getInlfatedView(parent, R.layout.item_question);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position)
    {
        holder.setDataToView(list.get(position));
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder
    {
        private TextView questionText;

        public QuestionsViewHolder(View itemView) {
            super(itemView);

            questionText = (TextView) itemView.findViewById(R.id.question_text);
        }

        void setDataToView(QuestionsPojo questionsPojo)
        {
            questionText.setText(questionsPojo.getTitle());
        }
    }
}
