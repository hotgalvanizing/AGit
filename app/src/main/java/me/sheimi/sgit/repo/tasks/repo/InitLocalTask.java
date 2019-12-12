package me.sheimi.sgit.repo.tasks.repo;

import org.eclipse.jgit.api.Git;

import me.sheimi.sgit.database.RepoContract;
import me.sheimi.sgit.database.models.Repo;

/**
 * 初始化本地仓库的Task
 *
 * @author Lenovo
 */
public class InitLocalTask extends RepoOpTask {
    /**
     * 构造方法
     *
     * @param repo
     */
    public InitLocalTask(Repo repo) {
        super(repo);
    }

    /**
     * 接收输入参数，
     * 执行任务中的耗时操作，
     * 返回线程任务的执行结果
     *
     * @param params
     * @return
     */
    @Override
    protected Boolean doInBackground(Void... params) {
        //后台初始化
        boolean result = init();
        //如果不成功，删除数据库的记录
        if (!result) {
            mRepo.deleteRepoSync();
            return false;
        }
        return true;
    }

    /**
     * 将线程任务执行结果显示在UI组件
     *
     * @param isSuccess
     */
    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
        //如果成功
        if (isSuccess) {
            mRepo.updateLatestCommitInfo();
            mRepo.updateStatus(RepoContract.REPO_STATUS_NULL);
        }
    }

    /**
     * InitLocal的初始化操作
     * 调用JGit的API
     *
     * @return
     */
    public boolean init() {
        try {
            Git.init().setDirectory(mRepo.getDir()).call();
        } catch (Throwable e) {
            setException(e);
            return false;
        }
        return true;
    }
}
