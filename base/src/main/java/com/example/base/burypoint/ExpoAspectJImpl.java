package com.example.base.burypoint;

import android.app.Activity;


import androidx.fragment.app.Fragment;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * 切面实现类
 *
 *
 */
@Aspect
public class ExpoAspectJImpl {

    private final static int ON_RESUME = 1;
    private final static int ON_PAUSE = 2;
    private final static int ON_DESTROY = 3;

    @After("execution(* android.app.Activity+.onResume(..)) " +
            "|| execution(* android.support.v4.app.Fragment+.onResume(..))")
    public void onActivityOrFragmentResumeAfter(JoinPoint joinPoint){
        doExpo(joinPoint.getTarget(), ON_RESUME);
    }

    @After("execution(* android.app.Activity+.onPause(..)) " +
            "|| execution(* android.support.v4.app.Fragment+.onPause(..))")
    public void onActivityOrFragmentPauseAfter(JoinPoint joinPoint){
        doExpo(joinPoint.getTarget(), ON_PAUSE);
    }

    @After("execution(* android.app.Activity+.onDestroy(..)) " +
            "|| execution(* android.support.v4.app.Fragment+.onDestroy(..))")
    public void onActivityOrFragmentMethodAfter(JoinPoint joinPoint){
        doExpo(joinPoint.getTarget(), ON_DESTROY);
    }

    /**
     *
     * @param item activity or fragment
     * @param workType 1. onResume 2. onPause 3. onDestroy
     */
    private void doExpo(Object item, int workType){
        if (item instanceof Activity){
            Activity activity = (Activity) item;
            switch (workType){
                case ON_RESUME:
                    ExpoManager.Companion.addActivity(activity);
                    if (ExpoWhiteList.INSTANCE.isInWhiteList(activity)) {
                        ExpoManager.Companion.resumeExpo(ExpoManager.Companion.getActivityExpoName(activity));
                    }
                    break;
                case ON_PAUSE:
                    ExpoManager.Companion.removeActivity(activity);
                    if (ExpoWhiteList.INSTANCE.isInWhiteList(activity)) {
                        ExpoManager.Companion.pauseExpo(ExpoManager.Companion.getActivityExpoName(activity));
                    }
                    break;
                case ON_DESTROY:
                    if (ExpoWhiteList.INSTANCE.isInWhiteList(activity)) {
                        ExpoManager.Companion.removeAllExpo(ExpoManager.Companion.getActivityExpoName(activity));
                    }
                    break;
            }
        }
        if (item instanceof Fragment){
            Fragment fragment = (Fragment) item;
            switch (workType){
                case ON_RESUME:
                    ExpoManager.Companion.addFragment(fragment);
                    if (ExpoWhiteList.INSTANCE.isInWhiteList(fragment)) {
                        ExpoManager.Companion.resumeExpo(ExpoManager.Companion.getFragmentExpoName(fragment));
                    }
                    break;
                case ON_PAUSE:
                    ExpoManager.Companion.removeFragment(fragment);
                    if (ExpoWhiteList.INSTANCE.isInWhiteList(fragment)) {
                        ExpoManager.Companion.pauseExpo(ExpoManager.Companion.getFragmentExpoName(fragment));
                    }
                    break;
                case ON_DESTROY:
                    if (ExpoWhiteList.INSTANCE.isInWhiteList(fragment)) {
                        ExpoManager.Companion.removeAllExpo(ExpoManager.Companion.getFragmentExpoName(fragment));
                    }
                    break;
            }
        }
    }

}
