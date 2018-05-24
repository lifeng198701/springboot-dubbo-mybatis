<p>交流QQ群：57158980</p>
<h2><a id="user-content-前言" class="anchor" aria-hidden="true" href="#前言"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>前言</h2>
<p>　　<code>zheng</code>项目不仅仅是一个开发架构，而是努力打造一套从 <strong>前端模板</strong> - <strong>基础框架</strong> - <strong>分布式架构</strong> - <strong>开源项目</strong> - <strong>持续集成</strong> - <strong>自动化部署</strong> - <strong>系统监测</strong> - <strong>无缝升级</strong> 的全方位J2EE企业级开发解决方案。</p>
<h2><a id="user-content-项目介绍" class="anchor" aria-hidden="true" href="#项目介绍"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>项目介绍</h2>
<p>　　基于Spring+SpringMVC+Mybatis分布式敏捷开发系统架构，提供整套公共微服务服务模块：内容管理、支付中心、用户管理（包括第三方）、微信平台、存储系统、配置中心、日志分析、任务和通知等，支持服务治理、监控和追踪，努力为中小型企业打造全方位J2EE企业级开发解决方案。</p>
<h3><a id="user-content-组织结构" class="anchor" aria-hidden="true" href="#组织结构"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>组织结构</h3>
<div class="highlight highlight-source-lua"><pre>zheng
├── zheng<span class="pl-k">-</span>common <span class="pl-c"><span class="pl-c">--</span> SSM框架公共模块</span>
├── zheng<span class="pl-k">-</span>admin <span class="pl-c"><span class="pl-c">--</span> 后台管理模板</span>
├── zheng<span class="pl-k">-</span>ui <span class="pl-c"><span class="pl-c">--</span> 前台thymeleaf模板[端口:1000]</span>
├── zheng<span class="pl-k">-</span>config <span class="pl-c"><span class="pl-c">--</span> 配置中心[端口:1001]</span>
├── zheng<span class="pl-k">-</span>upms <span class="pl-c"><span class="pl-c">--</span> 用户权限管理系统</span>
|    ├── zheng<span class="pl-k">-</span>upms<span class="pl-k">-</span>common <span class="pl-c"><span class="pl-c">--</span> upms系统公共模块</span>
|    ├── zheng<span class="pl-k">-</span>upms<span class="pl-k">-</span>dao <span class="pl-c"><span class="pl-c">--</span> 代码生成模块，无需开发</span>
|    ├── zheng<span class="pl-k">-</span>upms<span class="pl-k">-</span>client <span class="pl-c"><span class="pl-c">--</span> 集成upms依赖包，提供单点认证、授权、统一会话管理</span>
|    ├── zheng<span class="pl-k">-</span>upms<span class="pl-k">-</span>rpc<span class="pl-k">-</span>api <span class="pl-c"><span class="pl-c">--</span> rpc接口包</span>
|    ├── zheng<span class="pl-k">-</span>upms<span class="pl-k">-</span>rpc<span class="pl-k">-</span>service <span class="pl-c"><span class="pl-c">--</span> rpc服务提供者</span>
|    └── zheng<span class="pl-k">-</span>upms<span class="pl-k">-</span>server <span class="pl-c"><span class="pl-c">--</span> 用户权限系统及SSO服务端[端口:1111]</span>
├── zheng<span class="pl-k">-</span>cms <span class="pl-c"><span class="pl-c">--</span> 内容管理系统</span>
|    ├── zheng<span class="pl-k">-</span>cms<span class="pl-k">-</span>common <span class="pl-c"><span class="pl-c">--</span> cms系统公共模块</span>
|    ├── zheng<span class="pl-k">-</span>cms<span class="pl-k">-</span>dao <span class="pl-c"><span class="pl-c">--</span> 代码生成模块，无需开发</span>
|    ├── zheng<span class="pl-k">-</span>cms<span class="pl-k">-</span>rpc<span class="pl-k">-</span>api <span class="pl-c"><span class="pl-c">--</span> rpc接口包</span>
|    ├── zheng<span class="pl-k">-</span>cms<span class="pl-k">-</span>rpc<span class="pl-k">-</span>service <span class="pl-c"><span class="pl-c">--</span> rpc服务提供者</span>
|    ├── zheng<span class="pl-k">-</span>cms<span class="pl-k">-</span>search <span class="pl-c"><span class="pl-c">--</span> 搜索服务[端口:2221]</span>
|    ├── zheng<span class="pl-k">-</span>cms<span class="pl-k">-</span>admin <span class="pl-c"><span class="pl-c">--</span> 后台管理[端口:2222]</span>
|    ├── zheng<span class="pl-k">-</span>cms<span class="pl-k">-</span>job <span class="pl-c"><span class="pl-c">--</span> 消息队列、任务调度等[端口:2223]</span>
|    └── zheng<span class="pl-k">-</span>cms<span class="pl-k">-</span>web <span class="pl-c"><span class="pl-c">--</span> 网站前台[端口:2224]</span>
├── zheng<span class="pl-k">-</span>pay <span class="pl-c"><span class="pl-c">--</span> 支付系统</span>
|    ├── zheng<span class="pl-k">-</span>pay<span class="pl-k">-</span>common <span class="pl-c"><span class="pl-c">--</span> pay系统公共模块</span>
|    ├── zheng<span class="pl-k">-</span>pay<span class="pl-k">-</span>dao <span class="pl-c"><span class="pl-c">--</span> 代码生成模块，无需开发</span>
|    ├── zheng<span class="pl-k">-</span>pay<span class="pl-k">-</span>rpc<span class="pl-k">-</span>api <span class="pl-c"><span class="pl-c">--</span> rpc接口包</span>
|    ├── zheng<span class="pl-k">-</span>pay<span class="pl-k">-</span>rpc<span class="pl-k">-</span>service <span class="pl-c"><span class="pl-c">--</span> rpc服务提供者</span>
|    ├── zheng<span class="pl-k">-</span>pay<span class="pl-k">-</span>sdk <span class="pl-c"><span class="pl-c">--</span> 开发工具包</span>
|    ├── zheng<span class="pl-k">-</span>pay<span class="pl-k">-</span>admin <span class="pl-c"><span class="pl-c">--</span> 后台管理[端口:3331]</span>
|    └── zheng<span class="pl-k">-</span>pay<span class="pl-k">-</span>web <span class="pl-c"><span class="pl-c">--</span> 演示示例[端口:3332]</span>
├── zheng<span class="pl-k">-</span>ucenter <span class="pl-c"><span class="pl-c">--</span> 用户系统(包括第三方登录)</span>
|    ├── zheng<span class="pl-k">-</span>ucenter<span class="pl-k">-</span>common <span class="pl-c"><span class="pl-c">--</span> ucenter系统公共模块</span>
|    ├── zheng<span class="pl-k">-</span>ucenter<span class="pl-k">-</span>dao <span class="pl-c"><span class="pl-c">--</span> 代码生成模块，无需开发</span>
|    ├── zheng<span class="pl-k">-</span>ucenter<span class="pl-k">-</span>rpc<span class="pl-k">-</span>api <span class="pl-c"><span class="pl-c">--</span> rpc接口包</span>
|    ├── zheng<span class="pl-k">-</span>ucenter<span class="pl-k">-</span>rpc<span class="pl-k">-</span>service <span class="pl-c"><span class="pl-c">--</span> rpc服务提供者</span>
|    └── zheng<span class="pl-k">-</span>ucenter<span class="pl-k">-</span>web <span class="pl-c"><span class="pl-c">--</span> 网站前台[端口:4441]</span>
├── zheng<span class="pl-k">-</span>wechat <span class="pl-c"><span class="pl-c">--</span> 微信系统</span>
|    ├── zheng<span class="pl-k">-</span>wechat<span class="pl-k">-</span>mp <span class="pl-c"><span class="pl-c">--</span> 微信公众号管理系统</span>
|    |    ├── zheng<span class="pl-k">-</span>wechat<span class="pl-k">-</span>mp<span class="pl-k">-</span>dao <span class="pl-c"><span class="pl-c">--</span> 代码生成模块，无需开发</span>
|    |    ├── zheng<span class="pl-k">-</span>wechat<span class="pl-k">-</span>mp<span class="pl-k">-</span>service <span class="pl-c"><span class="pl-c">--</span> 业务逻辑</span>
|    |    └── zheng<span class="pl-k">-</span>wechat<span class="pl-k">-</span>mp<span class="pl-k">-</span>admin <span class="pl-c"><span class="pl-c">--</span> 后台管理[端口:5551]</span>
|    └── zheng<span class="pl-k">-</span>ucenter<span class="pl-k">-</span>app <span class="pl-c"><span class="pl-c">--</span> 微信小程序后台</span>
├── zheng<span class="pl-k">-</span>api <span class="pl-c"><span class="pl-c">--</span> API接口总线系统</span>
|    ├── zheng<span class="pl-k">-</span>api<span class="pl-k">-</span>common <span class="pl-c"><span class="pl-c">--</span> api系统公共模块</span>
|    ├── zheng<span class="pl-k">-</span>api<span class="pl-k">-</span>rpc<span class="pl-k">-</span>api <span class="pl-c"><span class="pl-c">--</span> rpc接口包</span>
|    ├── zheng<span class="pl-k">-</span>api<span class="pl-k">-</span>rpc<span class="pl-k">-</span>service <span class="pl-c"><span class="pl-c">--</span> rpc服务提供者</span>
|    └── zheng<span class="pl-k">-</span>api<span class="pl-k">-</span>server <span class="pl-c"><span class="pl-c">--</span> api系统服务端[端口:6666]</span>
├── zheng<span class="pl-k">-</span>oss <span class="pl-c"><span class="pl-c">--</span> 对象存储系统</span>
|    ├── zheng<span class="pl-k">-</span>oss<span class="pl-k">-</span>sdk <span class="pl-c"><span class="pl-c">--</span> 开发工具包</span>
|    ├── zheng<span class="pl-k">-</span>oss<span class="pl-k">-</span>web <span class="pl-c"><span class="pl-c">--</span> 前台接口[端口:7771]</span>
|    └── zheng<span class="pl-k">-</span>oss<span class="pl-k">-</span>admin <span class="pl-c"><span class="pl-c">--</span> 后台管理[端口:7772]</span>
├── zheng<span class="pl-k">-</span>shop <span class="pl-c"><span class="pl-c">--</span> 电子商务系统</span>
└── zheng<span class="pl-k">-</span>demo <span class="pl-c"><span class="pl-c">--</span> 示例模块(包含一些示例代码等)</span>
     ├── zheng<span class="pl-k">-</span>demo<span class="pl-k">-</span>rpc<span class="pl-k">-</span>api <span class="pl-c"><span class="pl-c">--</span> rpc接口包</span>
     ├── zheng<span class="pl-k">-</span>demo<span class="pl-k">-</span>rpc<span class="pl-k">-</span>service <span class="pl-c"><span class="pl-c">--</span> rpc服务提供者</span>
     └── zheng<span class="pl-k">-</span>demo<span class="pl-k">-</span>web <span class="pl-c"><span class="pl-c">--</span> 演示示例[端口:8888]</span></pre></div>
<h3><a id="user-content-技术选型" class="anchor" aria-hidden="true" href="#技术选型"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>技术选型</h3>
<h4><a id="user-content-后端技术" class="anchor" aria-hidden="true" href="#后端技术"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>后端技术:</h4>
<table>
<thead>
<tr>
<th>技术</th>
<th>名称</th>
<th>官网</th>
</tr>
</thead>
<tbody>
<tr>
<td>Spring Framework</td>
<td>容器</td>
<td><a href="http://projects.spring.io/spring-framework/" rel="nofollow">http://projects.spring.io/spring-framework/</a></td>
</tr>
<tr>
<td>SpringMVC</td>
<td>MVC框架</td>
<td><a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc" rel="nofollow">http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc</a></td>
</tr>
<tr>
<td>Apache Shiro</td>
<td>安全框架</td>
<td><a href="http://shiro.apache.org/" rel="nofollow">http://shiro.apache.org/</a></td>
</tr>
<tr>
<td>Spring session</td>
<td>分布式Session管理</td>
<td><a href="http://projects.spring.io/spring-session/" rel="nofollow">http://projects.spring.io/spring-session/</a></td>
</tr>
<tr>
<td>MyBatis</td>
<td>ORM框架</td>
<td><a href="http://www.mybatis.org/mybatis-3/zh/index.html" rel="nofollow">http://www.mybatis.org/mybatis-3/zh/index.html</a></td>
</tr>
<tr>
<td>MyBatis Generator</td>
<td>代码生成</td>
<td><a href="http://www.mybatis.org/generator/index.html" rel="nofollow">http://www.mybatis.org/generator/index.html</a></td>
</tr>
<tr>
<td>PageHelper</td>
<td>MyBatis物理分页插件</td>
<td><a href="http://git.oschina.net/free/Mybatis_PageHelper" rel="nofollow">http://git.oschina.net/free/Mybatis_PageHelper</a></td>
</tr>
<tr>
<td>Druid</td>
<td>数据库连接池</td>
<td><a href="https://github.com/alibaba/druid">https://github.com/alibaba/druid</a></td>
</tr>
<tr>
<td>FluentValidator</td>
<td>校验框架</td>
<td><a href="https://github.com/neoremind/fluent-validator">https://github.com/neoremind/fluent-validator</a></td>
</tr>
<tr>
<td>Thymeleaf</td>
<td>模板引擎</td>
<td><a href="http://www.thymeleaf.org/" rel="nofollow">http://www.thymeleaf.org/</a></td>
</tr>
<tr>
<td>Velocity</td>
<td>模板引擎</td>
<td><a href="http://velocity.apache.org/" rel="nofollow">http://velocity.apache.org/</a></td>
</tr>
<tr>
<td>ZooKeeper</td>
<td>分布式协调服务</td>
<td><a href="http://zookeeper.apache.org/" rel="nofollow">http://zookeeper.apache.org/</a></td>
</tr>
<tr>
<td>Dubbo</td>
<td>分布式服务框架</td>
<td><a href="http://dubbo.io/" rel="nofollow">http://dubbo.io/</a></td>
</tr>
<tr>
<td>TBSchedule &amp; elastic-job</td>
<td>分布式调度框架</td>
<td><a href="https://github.com/dangdangdotcom/elastic-job">https://github.com/dangdangdotcom/elastic-job</a></td>
</tr>
<tr>
<td>Redis</td>
<td>分布式缓存数据库</td>
<td><a href="https://redis.io/" rel="nofollow">https://redis.io/</a></td>
</tr>
<tr>
<td>Solr &amp; Elasticsearch</td>
<td>分布式全文搜索引擎</td>
<td><a href="http://lucene.apache.org/solr/" rel="nofollow">http://lucene.apache.org/solr/</a> <a href="https://www.elastic.co/" rel="nofollow">https://www.elastic.co/</a></td>
</tr>
<tr>
<td>Quartz</td>
<td>作业调度框架</td>
<td><a href="http://www.quartz-scheduler.org/" rel="nofollow">http://www.quartz-scheduler.org/</a></td>
</tr>
<tr>
<td>Ehcache</td>
<td>进程内缓存框架</td>
<td><a href="http://www.ehcache.org/" rel="nofollow">http://www.ehcache.org/</a></td>
</tr>
<tr>
<td>ActiveMQ</td>
<td>消息队列</td>
<td><a href="http://activemq.apache.org/" rel="nofollow">http://activemq.apache.org/</a></td>
</tr>
<tr>
<td>JStorm</td>
<td>实时流式计算框架</td>
<td><a href="http://jstorm.io/" rel="nofollow">http://jstorm.io/</a></td>
</tr>
<tr>
<td>FastDFS</td>
<td>分布式文件系统</td>
<td><a href="https://github.com/happyfish100/fastdfs">https://github.com/happyfish100/fastdfs</a></td>
</tr>
<tr>
<td>Log4J</td>
<td>日志组件</td>
<td><a href="http://logging.apache.org/log4j/1.2/" rel="nofollow">http://logging.apache.org/log4j/1.2/</a></td>
</tr>
<tr>
<td>Swagger2</td>
<td>接口测试框架</td>
<td><a href="http://swagger.io/" rel="nofollow">http://swagger.io/</a></td>
</tr>
<tr>
<td>sequence</td>
<td>分布式高效ID生产</td>
<td><a href="http://git.oschina.net/yu120/sequence" rel="nofollow">http://git.oschina.net/yu120/sequence</a></td>
</tr>
<tr>
<td>AliOSS &amp; Qiniu &amp; QcloudCOS</td>
<td>云存储</td>
<td><a href="https://www.aliyun.com/product/oss/" rel="nofollow">https://www.aliyun.com/product/oss/</a> <a href="http://www.qiniu.com/" rel="nofollow">http://www.qiniu.com/</a> <a href="https://www.qcloud.com/product/cos" rel="nofollow">https://www.qcloud.com/product/cos</a></td>
</tr>
<tr>
<td>Protobuf &amp; json</td>
<td>数据序列化</td>
<td><a href="https://github.com/google/protobuf">https://github.com/google/protobuf</a></td>
</tr>
<tr>
<td>Jenkins</td>
<td>持续集成工具</td>
<td><a href="https://jenkins.io/index.html" rel="nofollow">https://jenkins.io/index.html</a></td>
</tr>
<tr>
<td>Maven</td>
<td>项目构建管理</td>
<td><a href="http://maven.apache.org/" rel="nofollow">http://maven.apache.org/</a></td>
</tr></tbody></table>
  </body>
</html>
