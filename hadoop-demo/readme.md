# Hadoop
几种安装模式  

* Hadoop本地模式安装  
  > Hadoop本地模式只是用于本地开发调试，或者快速安装体验Hadoop，这部分做简单的介绍。

* Hadoop伪分布式模式安装  
  > 学习Hadoop一般是在伪分布式模式下进行。这种模式是在一台机器上各个进程上运行Hadoop的各个模块，伪分布式的意思是虽然各个模块是在各个进程上分开运行的，但是只是运行在一个操作系统上的，并不是真正的分布式。

* 完全分布式安装  
  > 完全分布式模式才是生产环境采用的模式，Hadoop运行在服务器集群上，生产环境一般都会做HA，以实现高可用。

* Hadoop HA安装  
  > HA是指高可用，为了解决Hadoop单点故障问题，生产环境一般都做HA部署。


# Setup

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

# FYI
* 官方教程
_http://hadoop.apache.org/docs/r1.0.4/cn/cluster_setup.html_

* 详细教程
_https://blog.csdn.net/hliq5399/article/details/78193113_

* 完全分布式安装教程
_https://blog.csdn.net/weixin_44198965/article/details/89603788_
