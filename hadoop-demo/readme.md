# Hadoop
几种安装模式  

* Hadoop本地模式安装  
  > Hadoop本地模式只是用于本地开发调试，或者快速安装体验Hadoop。

* Hadoop伪分布式模式安装  
  > 学习Hadoop一般是在伪分布式模式下进行。这种模式是在一台机器上各个进程上运行Hadoop的各个模块，伪分布式的意思是虽然各个模块是在各个进程上分开运行的，但是只是运行在一个操作系统上的，并不是真正的分布式。

* 完全分布式安装  
  > 完全分布式模式才是生产环境采用的模式，Hadoop运行在服务器集群上，生产环境一般都会做HA，以实现高可用。

* Hadoop HA安装  
  > HA是指高可用，为了解决Hadoop单点故障问题，生产环境一般都做HA部署。


# Setup
## 一、单机安装

> 1.安装JDK，设置环境变量  
修改/etc/profile文件，添加如下：

```
export JAVA_HOME="/opt/modules/jdk1.7.0_67"
export PATH=$JAVA_HOME/bin:$PATH
```

> 2.下载hadoop并解压，如解压到/opt/hadoop目录下  
配置环境变量，修改/etc/profile文件，添加如下：

```
export HADOOP_HOME="/opt/hadoop"
export PATH=$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$PATH
```

执行*source /etc/profile*，使配置生效

> 3.配置 hadoop-env.sh、mapred-env.sh、yarn-env.sh文件的JAVA_HOME参数  
修改JAVA_HOME参数为：
```
export JAVA_HOME="/usr/local/jdk1.8.0_67"
```

> 4.配置core-site.xml
```xml
<configuration>
  <property>
    <name>fs.defaultFS</name>
    <value>hdfs://192.168.210.131:9000/</value>
  </property>
  <property>
    <name>hadoop.tmp.dir</name>
    <value>/opt/hadoop/hadoopdata</value>
  </property>
</configuration>
```

创建临时目录：/opt/hadoop/hadoopdata

> 5.配置、格式化、启动HDFS
> > * 配置hdfs-site.xml
```xml
<configuration>
  <property>
    <name>dfs.replication</name>
    <value>1</value>
  </property>
</configuration>
```
> dfs.replication配置的是HDFS存储时的备份数量，因为这里是伪分布式环境只有一个节点，所以这里设置为1。

> > * 格式化HDFS  
_hdfs namenode –format_  
格式化是对HDFS这个分布式文件系统中的DataNode进行分块，统计所有分块后的初始元数据的存储在NameNode中。  
格式化后，查看core-site.xml里hadoop.tmp.dir指定的目录下是否有了dfs目录，如果有，说明格式化成功。

> > * 启动NameNode  
_hdfs --daemon start namenode_

> > * 启动DataNode  
_hdfs --daemon start datanode_

> > * 启动SecondaryNameNode  
_hdfs --daemon start secondarynamenode_

> > * jps命令查看是否已启动成功  
如结果显示以上3个进程即表示启动成功。

> > * HDFS上测试创建目录、上传、下载文件  
_hdfs dfs -mkdir /demo_  
_hdfs dfs -put /temp/file.txt /demo_  
_hdfs dfs -ls /demo_  
_hdfs dfs -cat /demo/file.txt_

> 6.配置启动yarn

> > * 配置mapred-site.xml
```xml
<configuration>
    <property>
        <name>mapreduce.framework.name</name>
	    <value>yarn</value>
    </property>
    <property>
        <name>yarn.app.mapreduce.am.env</name>
        <value>HADOOP_MAPRED_HOME=${HADOOP_HOME}</value>
    </property>
    <property>
        <name>mapreduce.map.env</name>
        <value>HADOOP_MAPRED_HOME=${HADOOP_HOME}</value>
    </property>
    <property>
        <name>mapreduce.reduce.env</name>
        <value>HADOOP_MAPRED_HOME=${HADOOP_HOME}</value>
    </property>
</configuration>
```

> > * 配置yarn-site.xml
```xml
<configuration>
<!-- Site specific YARN configuration properties -->
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
    <property>
        <name>yarn.resourcemanager.hostname</name>
        <value>192.168.210.131</value>
    </property>
</configuration>
```

> > * 启动Resourcemanager  
_yarn --daemon start resourcemanager_

> > * 启动nodemanager  
_yarn --daemon start nodemanager_

> > * jps命令查看是否已启动成功

> > * yarn的web界面：http://192.168.210.131:8088

> 7.运行MapReduce Job
> > * 创建测试目录  
_hdfs dfs -mkdir /demo/input_  
创建原始文件并上传，如wc.input：
```text
hello wold
hello hadoop
hello mapreduce
```
将wc.input上传到hdfs：  
_hdfs dfs -put /temp/wc.input /demo/input_

> > * 运行WordCount MapReduce Job  
_yarn jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.2.jar wordcount /demo/input /demo/output_

> > * 查看输出结果  
_hdfs dfs -cat /demo/output/part-r-00000_



### 各服务端口（2.X/3.X)：
```text
namenode http ui:			50070/9870  
datanode http ui:			50075/9864  
secondarynamenode http ui:	        50090/9868  
yarn http ui:				8088/8088  
```

----------------------------------------------

## 二、集群安装
这里准备三台CentOS服务器：
> 主机：master（192.168.210.200）  
> 从机：slave0（192.168.210.201）  
> 从机：slave1（192.168.210.202）  

每一个节点的安装与配置是相同的，在实际工作中，通常在master节点上完成安装和配置后，然后将安装目录复制到其他节点就可以，没有必要把所有节点都配置一遍，那样没有效率。  
注意：所有操作都是root用户权限

1.同上配置JAVA及Hadoop环境  
2.配置核心组件文件（只在master做）  
* 编辑core-site.xml文件
```xml
<configuration>
  <property>
    <name>fs.defaultFS</name>
    <value>hdfs://192.168.210.131:9000/</value>
  </property>
  <property>
    <name>hadoop.tmp.dir</name>
    <value>/opt/hadoop/hadoopdata</value>
  </property>
</configuration>
```

3.配置文件系统（只在master做）  
* 编辑hdfs-site.xml文件  
```xml
<configuration>
  <property>
    <name>dfs.replication</name>
    <value>1</value>
  </property>
</configuration>
```
4.配置 yarn-site.xml 文件（只在master做）  
* 编辑yarn-site.xml文件
```xml
<configuration>
  <property>
  	<name>yarn.nodemanager.aux-services</name>
  	<value>mapreduce_shuffle</value>
  </property>
  <property>
  	<name>yarn.resourcemanager.address</name>
  	<value>master:18040</value>
  </property>
  <property>
  	<name>yarn.resourcemanager.scheduler.address</name>
  	<value>master:18030</value>
  </property>
  <property>
  	<name>yarn.resourcemanager.resource-tracker.address</name>
  	<value>master:18025</value>
  </property>
  <property>
  	<name>yarn.resourcemanager.admin.address</name>
  	<value>master:18141</value>
  </property>
  <property>
  	<name>yarn.resourcemanager.webapp.address</name>
  	<value>master:18088</value>
  </property>
</configuration>
```
5.配置MapReduce计算框架文件
* 编辑mapred-site.xml文件  
```xml
<configuration>
  <property>
    <name>mapreduce.framework.name</name>
    <value>yarn</value>
  </property>
</configuration>
```
6.配置master的slaves文件  
_[注意]：在hadoop3.x该文件被workers替代_ 
```text
192.168.210.201
192.168.210.202
```
7.复制master上的Hadoop到slave节点（只在master做）  
_scp -r /opt/hadoop root@192.168.210.201:/opt_

8.创建Hadoop数据目录（只在master做）
_mkdir /opt/hadoop/hadoopdata_

9.格式化文件系统（只在master做）  
_hadoop namenode -format_

10.到此，hadoop已经安装完成。但是如果现在启动集群，从机是无法启动服务的，这里需要建立从机可SSH免密登录，具体步骤如下：  
_ssh生成密钥有rsa和dsa两种生成方式，默认情况下采用rsa方式。_  
（1）首先安装openssh-server  
> _yum install openssh-server_  
>
（2）创建ssh-key，这里我们采用rsa方式  
> _ssh-keygen -t rsa -P ""   //(P是要大写的，后面跟"")_  
   （注：回车后会在~/.ssh/下生成两个文件：id_rsa和id_rsa.pub这两个文件是成对出现的）  

（3）进入~/.ssh/目录下，将id_rsa.pub追加到authorized_keys授权文件中，开始是没有authorized_keys文件的  
> _cd ~/.ssh_  
> _cat id_rsa.pub >> authorized_keys_  

完成后就可以无密码登录本机了，上述三步需要在两台从机上安装。  
（4）配置master无密码登录slave1  
    mater主机中输入命令复制一份公钥到home中  
> _cp .ssh/id_rsa.pub ~/id_rsa_master.pub_  
	
	把master的home目录下的id_rsa_master.pub拷到slave1的home下  
    slave1的home目录下分别输入命令  
    _cat id_rsa_master.pub >> .ssh/authorized_keys_  
    
    至此实现了mater对slave1的无密码登录  
    
11.配置Hadoop用户  
进入sbin目录：  
> _cd /opt/hadoop/hadoop/sbin_  

修改文件start-dfs.sh和stop-dfs.sh，在第2行添加如下：
```text
HDFS_DATANODE_USER=root  
HDFS_DATANODE_SECURE_USER=hdfs  
HDFS_NAMENODE_USER=root  
HDFS_SECONDARYNAMENODE_USER=root 
```
修改文件start-yarn.sh和stop-yarn.sh，在第2行添加如下：
```text
YARN_RESOURCEMANAGER_USER=root
HADOOP_SECURE_DN_USER=yarn
YARN_NODEMANAGER_USER=root
```

12.启动和关闭Hadoop集群（只在master做）  
启动命令：  
> _start-all.sh_  

关闭命令：  
> _stop-all.sh_

# FYI
* 官方教程
_http://hadoop.apache.org/docs/r1.0.4/cn/cluster_setup.html_

* 详细教程
_https://blog.csdn.net/hliq5399/article/details/78193113_

* 完全分布式安装教程
_https://blog.csdn.net/weixin_44198965/article/details/89603788_
