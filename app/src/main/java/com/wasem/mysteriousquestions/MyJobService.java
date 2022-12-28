package com.wasem.mysteriousquestions;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
