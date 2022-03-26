# README

# FocusPermission-Android

权限管理

# 局限性

- 对于必须申请的权限接口，当在模拟器上申请权限时，用户拒绝并点了不再提醒，会无法跳转到权限设置界面。

# 用法

在需要申请权限的Activity中，示例代码如下：

先导入类：

```java
import com.focus.focuspermission.core.listener.OnMustPermissionListener;
```

然后调用申请权限接口，例如：

```java
FocusPermission.mustRequestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                new OnMustPermissionListener() {
                    @Override
                    public void onFinish() {
                        Log.i(MainActivity.this.getPackageName(),"权限全部通过");
                    }

                    @Override
                    public String getDontAskDeniedPermissionDialogTip() {
                        return "非常抱歉，游戏需要读写权限才能正常运行。是否跳转到权限设置，进行授权？";
                    }
                });
```

- `FocusPermission.*mustRequestPermissions` 接口是必须申请某些权限时调用，当权限被授权后，才执行*`OnMustPermissionListener` 回调。
- `FocusPermission.*requestPermissions` 接口是普通申请权限时调用，回调*`OnNormalPermissionListener`*中参数*`deniedPermissionList` 表示被用户拒绝了的权限。

参考资料：（注：资料不分先后顺序）

- EAWorld的【****[如何优雅地申请Android运行时权限](https://blog.csdn.net/weixin_45443931/article/details/105525348)】**
- 灬大脑斧灬的【****[Fragment中onActivityResult()方法不回调](https://blog.csdn.net/qq_37011271/article/details/80729050)****】
