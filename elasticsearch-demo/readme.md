# ElasticSearch

## 安装

### 单机部署  
解压后直接运行如下命令：
> _bin/elasticsearch.bat        [Windows]_  
> _./elasticsearch              [Linux]_  

完成后，在浏览器访问：http://localhost:9200，出现相关信息即表示部署成功。  

```text
[注意]
Linux系统下不允许使用root运行，所以需要创建用户，并给予权限：  
adduser esuser
passwd esuser
chown esuser /opt/elasticsearch-7.13.3 -R
su esuser
切换用户后再执行即可
```
### 安装ElasticSearch-head插件
1.安装Node环境
### 集群部署

修改配置文件config/elasticsearch.yml如下：
```text
cluster.name: es_cluster        # 集群名称，所有机器必须一致
node.name: node-1               # 节点名称
node.master: true               # 是否为主节点
node.data: true                 # 是否为数据存储节点
path.data: D:/app/elasticsearch-7.13.3/data     # 数据存储路径，可以设置多个路径用逗号分隔，有助于提高IO
path.logs: D:/app/elasticsearch-7.13.3/logs     # 日志文件路径
network.host: 192.168.210.185
http.port: 9200                 # 设置对外服务的http端口，默认为9200
transport.tcp.port: 9300        # 设置集群中节点间通信的tcp端口，默认是9300
transport.tcp.compress: true
# 配置集群的主机地址，配置之后集群的主机之间可以自动发现（可以带上端口，例如 127.0.0.1:9300）
discovery.seed_hosts: ["192.168.210.185", "192.168.210.200", "192.168.210.201"]
# 初始的候选 master 节点列表。初始主节点应通过其 node.name 标识，默认为其主机名。确保 cluster.initial_master_nodes 中的值与 node.name 完全匹配。
cluster.initial_master_nodes: ["node-1", "node-2", "node-3"]
```


## 常见错误
1.不能以root用户运行
```text
org.elasticsearch.bootstrap.StartupException: 
java.lang.RuntimeException: can not run elasticsearch as root
at org.elasticsearch.bootstrap.Elasticsearch.init(Elasticsearch.java:125)
```
> 注：出于安全考虑，elasticsearch默认不允许以root账号运行。故需要创建一个用户  
解决办法：  
创建用户，切换到创建的用户，再运行。  

2.文件权限不够  
```text
ERROR: [2] bootstrap checks failed
[1]: max file descriptors [4096] for elasticsearch process is too low, increase to at least [65536]
```
> 这是切换自己创建的用户后运行时，因为不是root，所以文件权限不够。
解决办法：
先切换到root用户登录，然后修改配置文件：
> ```text
> [root@localhost bin]# vim /etc/security/limits.conf
> ```
> 配置文件中添加以下内容： （注意带*）
> ```text
> * soft nofile 65536
> * hard nofile 131072
> * soft nproc 4096
> * hard nproc 4096
> ```
> 记得修改完，先切换到自己创建的用户，再运行elasticsearch

3.用户拥有的内存权限太小，至少least [262144]
```text
ERROR: [1] bootstrap checks failed
[2]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
```
> 解决办法：
> 先切换到root用户下，然后执行修改配置文件
> ( 没有这个文件的话：root用户下vim会自动创建一个新的；自己创建的用户下，不额外配置的话，vim没有权限创建 )
> 所以说，先切换到root用户下
> ```text
> [root@localhost bin]$ su root
> [root@localhost bin]$ vim /etc/sysctl.conf
> ```
> 文件中添加以下内容：
> ```text
> vm.max_map_count=655360
> ```
> 然后执行命令：
> ```text
> sysctl ‐p
> ```

4.线程数不够
```text
[1]: max number of threads [1024] for user [leyou] is too low, increase to at least [4096]
```

> 先切换到root用户下，然后执行修改配置文件
> ```text
> [root@localhost bin]$ vim /etc/security/limits.d/90‐nproc.conf
> ```
> 修改文件中内容：
> ```text
> * soft nproc 1024
> ```
> 改为：
> ```text
> * soft nproc 4096
> ```

5.权限不足
```text
main ERROR RollingFileManager 
(/opt/elasticsearch/logs/elasticsearch.log) 
java.io.FileNotFoundException: /home/leyou/elasticsearch/logs/elasticsearch.log (权限不够) 
java.io.FileNotFoundException: /home/leyou/elasticsearch/logs/elasticsearch.log (权限不够)
```
> 解决办法：
> 切换到root用户下，再cd 到 elasticsearch安装目录下，进行用户授权

> ```text
> # 修改elasticsearch用户组在当前目录下的权限(带点)
> [root@localhost elasticsearch]# chgrp -R elasticsearch .
> # 修改leyou用户在当前目录下的权限(带点)
> [root@localhost elasticsearch]# chown -R esuser .
> ```

#### [注]：
#### 按以上配置各节点单独运行，无法组成集群  
#### _原因有可能为：复制时把data目录下的数据复制了一份，删除复制过来的data目录下的数据，再次启动时即可。_  