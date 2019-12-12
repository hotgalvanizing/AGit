package me.sheimi.sgit.repo.tasks;

import android.os.AsyncTask;
import android.support.annotation.StringRes;

import me.sheimi.sgit.R;
import timber.log.Timber;

/**
 * 异步任务抽象类
 *
 * @author Lenovo
 */
public abstract class SheimiAsyncTask<A, B, C> extends AsyncTask<A, B, C> {

    /**
     * 异常
     */
    protected Throwable mException;
    /**
     * 错误代码
     */
    protected int mErrorRes = 0;

    protected void setException(Throwable e) {
        Timber.e(e, "set exception");
        mException = e;
    }

    protected void setException(Throwable e, int errorRes) {
        Timber.e(e, "set error [%d] exception", errorRes);
        mException = e;
        mErrorRes = errorRes;
    }

    private boolean mIsCanceled = false;

    /**
     * 取消任务
     */
    public void cancelTask() {
        mIsCanceled = true;
    }

    //TODO(kaeptmblaubaer1000): maybe make abstract?

    /**
     * This method is to be overridden and should return the resource that
     * is used as the title as the
     * {@link com.manichord.mgit.dialogs.ExceptionDialog} title when the
     * task fails with an exception.
     */
    @StringRes
    public int getErrorTitleRes() {
        return R.string.dialog_error_title;
    }

    public boolean isTaskCanceled() {
        return mIsCanceled;
    }

    public interface AsyncTaskPostCallback {
        void onPostExecute(Boolean isSuccess);
    }

    public interface AsyncTaskCallback {
        boolean doInBackground(Void... params);

        void onPreExecute();

        void onProgressUpdate(String... progress);

        void onPostExecute(Boolean isSuccess);
    }
}
