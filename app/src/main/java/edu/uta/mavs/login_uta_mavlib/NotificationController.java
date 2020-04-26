package edu.uta.mavs.login_uta_mavlib;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class NotificationController extends JobService {
    private static final String TAG = "NotificationController";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: ");
        doBackgroundWork(params);
        return true;
    }


    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    Log.d(TAG, e.toString());;
                }
                final DBMgr dbMgr = DBMgr.getInstance();
                dbMgr.getCurrentUser(new OnGetUserListener() {
                    @Override
                    public void onSuccess(User user) {
                        Log.d(TAG, "got user " + user.getUserId());
                        dbMgr.getCheckouts(null, user.getUserId(), new OnGetCheckoutsListener() {
                            @Override
                            public void onSuccess(ArrayList<Checkout> checkouts) {

                                boolean notifyMe = false;
                                for (Checkout checkout : checkouts){
                                    Log.d(TAG, "got book "+checkout.getIsbn());

                                    if (!checkout.getDueDate().isEmpty()) {
                                        LocalDate today = LocalDate.now();
                                        LocalDate dueDate = LocalDate.parse(checkout.getDueDate());
                                        long tGo = ChronoUnit.DAYS.between(today, dueDate);
                                        if (tGo < 2)
                                            notifyMe = true;
                                    }
                                }

                                if (notifyMe)
                                    NotificationGenerator.openActivityNotification(getApplicationContext());

                                Log.d(TAG, "Job finished");
                                jobFinished(params, false);
                            }
                            @Override
                            public void onStart() {
                                Log.d(TAG, "onStart: get Checkouts");
                            }
                            @Override
                            public void onFailure() {
                                Log.d(TAG, "Job failed");
                                jobFinished(params, true);
                            }
                        });
                    }
                    @Override
                    public void onStart() {
                        Log.d(TAG, "onStart: get User");
                    }
                    @Override
                    public void onFailure() {
                        Log.d(TAG, "Job failed");
                        jobFinished(params, true);
                    }
                });
            }
        }).start();
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: ");
        jobCancelled = true;
        return true;
    }
}
