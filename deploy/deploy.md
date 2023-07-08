# 部署

## 本地安装虚拟机（不需要可跳过） 需要环境变量中配置 VAGRANT_EXPERIMENTAL=disks

> - 安装 **[Vagrant2.3.4](https://www.vagrantup.com/downloads)**
> - 安装 **[VirtualBox7.0.6](https://www.virtualbox.org/wiki/Downloads)**

### 构建CentOS7虚拟机

> - 搜索[centos镜像](https://app.vagrantup.com/boxes/search)
> - 创建vagrant_vm目录
> - vagrant plugin install vagrant-disksize
> - 进入vagrant_vm目录，执行`vagrant init centos/7`（来自镜像网站）命令
> - 修改目录下的配置

#### ps: vagrant 配置

```
Vagrant.configure("2") do |config|
  config.vm.box = "centos/7"
  config.vm.define "zclcs" do |zclcs|
    zclcs.vm.network "private_network", ip: "192.168.33.10"
    zclcs.vm.hostname = "zclcs"
    zclcs.vm.disk :disk, size: "40GB", primary: true
    zclcs.vm.provider "virtualbox" do |v|
      v.memory = 12288
      v.cpus = 2
    end
  end
end
```

#### ps: root账户登录

> - `sudo -s`
> - `passwd`
> - `vi /etc/ssh/sshd_config`
> - 修改配置**PermitRootLogin** 为 **yes**
> - 修改配置**PasswordAuthentication** 为 **yes**
> - `vagrant reload`

## 安装docker

### 安装Docker所需要的包：

```
yum install -y yum-utils \
  device-mapper-persistent-data \
  lvm2
```

### 设置稳定的仓库：

```
yum-config-manager \
    --add-repo \
    https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

### 安装最新版的Docker引擎：

```
yum install docker-ce docker-ce-cli containerd.io
```

### 启动Docker：

```
systemctl start docker
```

### 开机自启动Docker：

```
systemctl enable docker
```

## 安装Docker Compose

### 获取Docker Compose的最新稳定版本：

```
curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
```

### 对二进制文件授予可执行权限：

```
chmod +x /usr/local/bin/docker-compose
```

### 创建link：

```
ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
```

## 部署harbor镜像仓库

### 安装harbor

> - github下载harbor最新[离线安装包](https://github.com/goharbor/harbor/releases)
> - 上传到服务器解压`tar -xzvf harbor-offline-installer-v2.7.1.tgz`
> - 复制harbor配置`harbor.yml`到/harbor目录
> - 进入harbor目录执行`./install.sh`
> - 使用配置文件中的admin账号和密码登录harbor,创建仓库`cloud`, 权限公开
> - 进入/etc/docker，创建daemon.json，写入配置：

```
{
    "data-root":"/zclcs/docker",
    "hosts":[
        "tcp://0.0.0.0:2375",
        "unix:///var/run/docker.sock"
    ],
    "registry-mirrors":[
        "https://docker.mirrors.ustc.edu.cn"
    ],
    "insecure-registries":[
        "192.168.33.10:3000"
    ],
    "log-driver":"json-file",
    "log-opts":{
        "max-size":"100m",
        "max-file":"3"
    }
}
```

> - 重启docker：`service docker restart`

### 如果起不来：

```
进入 /usr/lib/systemd/system 修改 docker.serivce 修改配置 ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock 为 ExecStart=/usr/bin/dockerd
修改完 执行 systemctl daemon-reload 再执行上面得命令启动
```

> - 重启harbor：`docker-compose down``docker-compose up -d`
> - 登录harbor仓库：`docker login 192.168.33.10:3000`

### 安装mysql

> - 复制deploy目录下得install.sh至服务器，chmod +x install.sh
> - ./install.sh common
> - ./install.sh mysql

### 构建jdk基础镜像

> - 使用 Dockerfile 构建基础镜像`docker build -t 192.168.33.10:3000/library/17-centos7:1.0.0 .`
> - push 镜像到harbor仓库`docker push 192.168.33.10:3000/library/17-centos7:1.0.0`
> - 这里最好新增两个镜像一个生产用 jre 一个开发用 jdk、jdk 8 也建一份
> - 进入本项目目录执行maven命令(如果harbor账号密码不是默认记得改，地址及端口也是一样)
    ：`jib -DsendCredentialsOverHttp=true`

## 部署第三方依赖：minio、mysql、nacos、nginx、rabbitmq、redis

# 若无minio基础镜像先创建基础镜像

> - 复制项目 /deploy/dev/minio 下的内容，上传至服务器
> - 执行docker build -t 192.168.33.10:3000/library/minio:RELEASE.2021-06-17T00-10-46Z .
> - push 镜像到harbor仓库`docker push 192.168.33.10:3000/library/minio:RELEASE.2021-06-17T00-10-46Z`

# 若无mysql基础镜像先创建基础镜像

> - 复制项目 /deploy/dev/mysql 下的内容，上传至服务器
> - 执行docker build -t 192.168.33.10:3000/library/mysql:5.7.35 .
> - push 镜像到harbor仓库`docker push 192.168.33.10:3000/library/mysql:5.7.35`

# 若无nacos基础镜像先创建基础镜像

> - 复制项目 /deploy/dev/nacos 下的内容，上传至服务器
> - 执行docker build -t 192.168.33.10:3000/library/nacos-server:v2.2.1 .
> - push 镜像到harbor仓库`docker push 192.168.33.10:3000/library/nacos-server:v2.2.1`

# 若无nginx基础镜像先创建基础镜像

> - 复制项目 /deploy/dev/nginx 下的内容，上传至服务器
> - 执行docker build -t 192.168.33.10:3000/library/nginx:1.21.3 .
> - push 镜像到harbor仓库`docker push 192.168.33.10:3000/library/nginx:1.21.3`

# 若无rabbitmq基础镜像先创建基础镜像

> - 复制项目 /deploy/dev/rabbitmq 下的内容，上传至服务器
> - 执行docker build -t 192.168.33.10:3000/library/rabbitmq:3.9.13-management .
> - push 镜像到harbor仓库`docker push 192.168.33.10:3000/library/rabbitmq:3.9.13-management`

# 若无redis基础镜像先创建基础镜像

> - 复制项目 /deploy/dev/redis 下的内容，上传至服务器
> - 执行docker build -t 192.168.33.10:3000/library/redis:6.0.8 .
> - push 镜像到harbor仓库`docker push 192.168.33.10:3000/library/redis:6.0.8`

# 若无xxl-job基础镜像先创建基础镜像

> - 克隆项目 https://github.com/zclcs/xxl-job.git
> - 执行mvn -DsendCredentialsOverHttp=true clean package

# 部署

> - 创建`third-part`目录, 复制项目 /cloud/third-part下的内容，上传至服务器
> - 复制`third-part`目录下的文件, 上传服务器
> - 进入`third-part`目录, 执行`docker-compose up -d`

## 部署服务及前端

> - 创建`cloud`目录, 复制项目 /cloud/cloud下的内容，上传至服务器
> - 在`cloud`目录下创建`web`目录, 复制前端`dist`目录下的文件到`web`目录
> - 进入`cloud`目录, 执行`docker-compose up -d`


