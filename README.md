# ganguo
# 干果——简洁易用的gank客户端
**介绍**
---------
一款基于GankIo开发的练手项目。项目采用  MVP+Dagger2+Retrofit + RxJava开发。首页布局样式大致参考掘金客户端。

**应用截图**
-----------
![image](https://github.com/yanyiqun001/ganguo/blob/master/screenshots/Screenshot_20170605-165508_%E7%BC%96%E8%BE%91.png?raw=true)
![image](https://github.com/yanyiqun001/ganguo/blob/master/screenshots/Screenshot_20170605-165525_%E7%BC%96%E8%BE%91.png?raw=true)
![image](https://github.com/yanyiqun001/ganguo/blob/master/screenshots/Screenshot_20170605-165650_%E7%BC%96%E8%BE%91.png?raw=true)
![image](https://github.com/yanyiqun001/ganguo/blob/master/screenshots/Screenshot_20170605-165608_%E7%BC%96%E8%BE%91.png?raw=true)


**Gif**

![image](https://github.com/yanyiqun001/ganguo/blob/master/screenshots/ezgif.com-resize.gif?raw=true)

**特性**
-----------
* 基本遵循MD风格
* MVP+dagger2架构模式的项目应用
* retrofit+rxjava配合使用
* rxbus组件间通讯
* greendao实现本地收藏功能
* 日夜模式平滑切换
* flexbox流式布局的使用
* viewpager+fragment栏目动态切换
* 共享元素和扩散效果

**使用到的开源库**
-----------

* 网络请求：retrofit2
* rxjava2
* 依赖注入：dagger2
* 数据库：greendao3
* 图片加载：glide
* butterknife
* 上拉加载下拉刷新：TwinklingRefreshLayout https://github.com/lcodecorex/TwinklingRefreshLayout
* 流式布局：flexbox-layout 
* 底部导航：PagerBottomTabStrip https://github.com/tyzlmjj/PagerBottomTabStrip

**参考**
-----------
* 架构部分参考项目 https://github.com/MindorksOpenSource/android-mvp-architecture 以及文章
https://juejin.im/entry/58a5992961ff4b006c4455e3?utm_source=gold-miner&utm_medium=readme&utm_campaign=github
如果不了解dagger2，理解起来有些困难。但整体架构思路非常清晰，model层次划分非常细致。

* 日夜切换参考了https://github.com/hefuyicoder/ZhihuDaily 项目以及 http://www.jianshu.com/p/3b55e84742e5 这篇文章。效果非常不错