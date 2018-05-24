<p>交流QQ群：57158980</p>
<h2><a id="user-content-前言" class="anchor" aria-hidden="true" href="#前言"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>前言</h2>
<p>　　此项目基于springboot-dubbo-mybatis构架一个通用的开发 <strong>分布式框架</strong></p>
<h2><a id="user-content-项目介绍" class="anchor" aria-hidden="true" href="#项目介绍"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>项目介绍</h2>
<p>　　基于springboot+dubbo+Mybatis提供整套公共微服务服务模块：服务模块,控制层模块。</p>
<h3><a id="user-content-组织结构" class="anchor" aria-hidden="true" href="#组织结构"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>组织结构</h3>
<div class="highlight highlight-source-lua"><pre>springboot-dubbo-mybatis
└──commons<span class="pl-c"><span class="pl-c">--</span> 公共通用模块</span>
     ├── service<span class="pl-c">--</span> rpc服务层模块</span>
     ├── sql<span class="pl-c">--</span> 数据库脚本</span>
     └── web-api<span class="pl-c">--</span> web控制层</span></pre></div>
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
<td>Springboot</td>
<td>Springboot框架</td>
<td><a href="https://projects.spring.io/spring-boot/" rel="nofollow">https://projects.spring.io/spring-boot/</a></td>
</tr>

<tr>
<td>MyBatis</td>
<td>ORM框架</td>
<td><a href="http://www.mybatis.org/mybatis-3/zh/index.html" rel="nofollow">http://www.mybatis.org/mybatis-3/zh/index.html</a></td>
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
<td>Elasticsearch</td>
<td>分布式全文搜索引擎</td>
<td><a href="http://lucene.apache.org/solr/" rel="nofollow">http://lucene.apache.org/solr/</a> <a href="https://www.elastic.co/" rel="nofollow">https://www.elastic.co/</a></td>
</tr>
<tr>
<td>ActiveMQ</td>
<td>消息队列</td>
<td><a href="http://activemq.apache.org/" rel="nofollow">http://activemq.apache.org/</a></td>
</tr>
<tr>
<td>Log4J</td>
<td>日志组件</td>
<td><a href="http://logging.apache.org/log4j/1.2/" rel="nofollow">http://logging.apache.org/log4j/1.2/</a></td>
</tr>
<tr>
<td>Maven</td>
<td>项目构建管理</td>
<td><a href="http://maven.apache.org/" rel="nofollow">http://maven.apache.org/</a></td>
</tr></tbody></table>
  </body>
</html>
