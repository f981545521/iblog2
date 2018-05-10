### Git操作手册

创建分支： $ git branch mybranch
切换分支： $ git checkout mybranch
创建并切换分支： $ git checkout -b mybranch

#### 忽略文件
Git 通过.gitignore文件来配置需要忽略的文件/文件夹
将指定文件加入版本管理
git add <file>
PS F:\WebStorm\crawler> git add .

#### 提交
更新master主线上的东西到该分支上
F:\WebStorm\crawler>git rebase master

提交：git commit -a
对最近一次commit的进行修改：git commit -a -–amend

删除分支： $ git branch -d mybranch
强制删除分支： $ git branch -D mybranch
列出所有分支： $ git branch
查看各个分支最后一次提交： $ git branch -v

更新远程库到本地： $ git fetch origin


#### 冲突与合并
> 其中：冲突标记<<<<<<< （7个<）与=======之间的内容是我的修改，=======与>>>>>>>之间的内容是别人的修改。

git merge b # 将b分支合并到当前分支
同样 git rebase  #  也是把 b分支合并到当前分支

重置merge，不想merge了，恢复之前的状态：F:\WebStorm\crawler>git reset --merge
重置rebase，不想rebase了，恢复之前的状态：F:\WebStorm\crawler>git rebase --abort


#### 查看冲突
F:\WebStorm\crawler>git diff
Git diff branch1 branch2 --stat   //显示出所有有差异的文件列表
Git diff branch1 branch2 文件名(带路径)   //显示指定文件的详细差异
Git diff branch1 branch2                   //显示出所有有差异的文件的详细差异
