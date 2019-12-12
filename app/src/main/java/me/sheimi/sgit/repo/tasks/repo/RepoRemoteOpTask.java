package me.sheimi.sgit.repo.tasks.repo;

import me.sheimi.android.activities.SheimiFragmentActivity;
import me.sheimi.sgit.database.models.Repo;

/**
 * Super class for Tasks that operate on a git remote
 * @author Lenovo
 */

public abstract class RepoRemoteOpTask extends RepoOpTask implements SheimiFragmentActivity.OnPasswordEntered {


    public RepoRemoteOpTask(Repo repo) {
        super(repo);
    }


    @Override
    public void onClicked(String username, String password, boolean savePassword) {
        mRepo.setUsername(username);
        mRepo.setPassword(password);
        if (savePassword) {
            mRepo.saveCredentials();
        }
        //弹出输入用户名密码的弹窗，填写信息后，开启新的Clone任务
        mRepo.removeTask(this);
        getNewTask().executeTask();
    }

    @Override
    public void onCanceled() {

    }

    /**
     * 重新开启CloneTask
     *
     * @return
     */
    public abstract RepoRemoteOpTask getNewTask();
}
