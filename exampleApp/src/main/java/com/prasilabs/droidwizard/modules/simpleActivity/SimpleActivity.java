package com.prasilabs.droidwizard.modules.simpleActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.prasilabs.droidwizard.R;
import com.prasilabs.droidwizard.pojos.QuestionsPojo;
import com.prasilabs.droidwizardlib.core.CoreActivity;
import com.prasilabs.droidwizardlib.core.CoreCallBack;

import java.util.List;

/**
 * Created by prasi on 9/5/17.
 */

public class SimpleActivity extends CoreActivity<SimpleActivityPresenter> implements SimpleActivityCallBack
{
    private QuestionsAdapter questionsAdapter = new QuestionsAdapter();

    private RecyclerView recyclerView;
    private LinearLayout emptyLayout;
    private LinearLayout progressLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simple);

        emptyLayout = (LinearLayout) findViewById(R.id.empty_layout);
        progressLayout = (LinearLayout) findViewById(R.id.progress_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(questionsAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        getPresenter().getQuestions();
    }

    @Override
    protected CoreCallBack getCoreCallBack() {
        return this;
    }

    @Override
    protected SimpleActivityPresenter setCorePresenter() {
        return new SimpleActivityPresenter();
    }

    @Override
    public void showQuestions(List<QuestionsPojo> questions)
    {
        questionsAdapter.clearAndAddItem(questions);
        emptyLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty()
    {

        emptyLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternet()
    {
        emptyLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showProgress()
    {
        emptyLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        progressLayout.setVisibility(View.VISIBLE);
    }
}
